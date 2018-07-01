package bin.gamelogic.server;

import bin.gamelogic.food.Apple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerStart implements Runnable{

    private int port;
    private int playerCount;
    private String[] data;

    public ServerStart(int playerCount, int port) {
        this.playerCount = playerCount;
        this.port = port;
        data = new String[playerCount];
    }

    @Override
    public void run() {
        Socket client[] = new Socket[playerCount];
        DataOutputStream out[] = new DataOutputStream[playerCount];
        DataInputStream in[] = new DataInputStream[playerCount];
        Random random = new Random();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("bin.gamelogic.server created");
            for (int i = 0; i < playerCount ; i++) {
                client[i] = server.accept();
                System.out.println("client "+ i +" connected");
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
                String randomFood = " ?";
                double probability = random.nextDouble();
                if (probability < playerCount*0.045) {
                    Apple temp = new Apple();
                    randomFood += " Apple_"+ temp.getCenterX()+"_"+temp.getCenterY();
                }
                if(probability < playerCount*0.025 && playerCount >= 2){
                    Apple temp = new Apple();
                    randomFood += " Apple_"+ temp.getCenterX()+"_"+temp.getCenterY();
                }
                if(probability < playerCount*0.01 && playerCount >= 3){
                    Apple temp = new Apple();
                    randomFood += " Apple_"+ temp.getCenterX()+"_"+temp.getCenterY();
                }
                if(probability < playerCount*0.003 && playerCount >= 4){
                    Apple temp = new Apple();
                    randomFood += " Apple_"+ temp.getCenterX()+"_"+temp.getCenterY();
                }
                for (int k = 0; k <playerCount ; k++) {
                    for (int i = 0; i < playerCount; i++) {
                        out[k].writeUTF(data[i]+ randomFood);
                    }
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
