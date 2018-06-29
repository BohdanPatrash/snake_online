package server;

import bin.food.Apple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStart {

    private static volatile String[] data = new String[2];

    public static void main(String[] args) {
        int playerCount = 2 ;
        Socket client[] = new Socket[playerCount];
        DataOutputStream out[] = new DataOutputStream[playerCount];
        DataInputStream in[] = new DataInputStream[playerCount];
        try (ServerSocket server = new ServerSocket(3355)) {
            System.out.println("server created");
            String coordinates = "";
            for (int j = 0; j <4 ; j++) {
                Apple temp = new Apple();
                coordinates+=temp.getCenterX()+"_"+temp.getCenterY()+" ";
            }
            coordinates=coordinates.substring(0,coordinates.length()-1);
            for (int i = 0; i < playerCount ; i++) {
                client[i] = server.accept();
                System.out.println("client "+ i+" connected");
                out[i] = new DataOutputStream(client[i].getOutputStream());
                in[i] = new DataInputStream(client[i].getInputStream());
                out[i].writeInt(i);
                out[i].writeInt(playerCount);
                out[i].writeUTF(coordinates);
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


                for (int k = 0; k <playerCount ; k++) {
                    for (int i = 0; i < playerCount; i++) {
                        out[k].writeUTF(data[i]);
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
