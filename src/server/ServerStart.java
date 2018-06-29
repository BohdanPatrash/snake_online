package server;

import bin.food.Apple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerStart {

    private static volatile String[] data = new String[2];

    public static void main(String[] args) {
        int playerCount = 2 ;
        Socket client[] = new Socket[playerCount];
        DataOutputStream out[] = new DataOutputStream[playerCount];
        DataInputStream in[] = new DataInputStream[playerCount];
        Random random = new Random();
        try (ServerSocket server = new ServerSocket(3355)) {
            System.out.println("server created");
            for (int i = 0; i < playerCount ; i++) {
                client[i] = server.accept();
                System.out.println("client "+ i+" connected");
                out[i] = new DataOutputStream(client[i].getOutputStream());
                in[i] = new DataInputStream(client[i].getInputStream());
                out[i].writeInt(i);
                out[i].writeInt(playerCount);
            }
            while (true) {
                for (int i = 0; i <playerCount ; i++) {
                    while (!client[i].isClosed()) {
                        if (in[i].available() > 0) {
                                data[i] = in[i].readUTF();
                            break;
                        }
                        Thread.sleep(1);
                    }
                }
                String randomApple = " ?";
                if (random.nextDouble() < 0.05) {
                    Apple temp = new Apple();
                    randomApple = " "+ temp.getCenterX()+"_"+temp.getCenterY();
                }
                for (int k = 0; k <playerCount ; k++) {
                    for (int i = 0; i < playerCount; i++) {
                        out[k].writeUTF(data[i]+ randomApple);
                    }
                    System.out.print(k + " snake: ");
                    System.out.print(data[k] + " ");
                    System.out.println();

                }

                Thread.sleep(10);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
