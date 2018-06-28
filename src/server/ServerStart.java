package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerStart {

    private static volatile String[][] data = new String[2][5];

    public static void main(String[] args) {
<<<<<<< Updated upstream
        int playerCount = 3;
        Socket client[] = new Socket[playerCount];
        DataOutputStream out[] = new DataOutputStream[playerCount];
        DataInputStream in[] = new DataInputStream[playerCount];
        try (ServerSocket server = new ServerSocket(3355);
=======
        Socket client[] = new Socket[2];
        DataOutputStream out[] = new DataOutputStream[2];
        DataInputStream in[] = new DataInputStream[2];
        try (ServerSocket server = new ServerSocket(8984);
>>>>>>> Stashed changes
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("server created");
            for (int i = 0; i < playerCount ; i++) {
                client[i] = server.accept();
                System.out.println("client "+ i +" connected");
                out[i] = new DataOutputStream(client[i].getOutputStream());
                in[i] = new DataInputStream(client[i].getInputStream());
                out[i].writeInt(i);

            }
            while (true) {
                for (int i = 0; i <playerCount ; i++) {
                    while (!client[i].isClosed()) {
                        if (in[i].available() > 0) {
                            for (int j = 0; j < 5; j++) {
                                data[i][j] = in[i].readUTF();
                            }
                            break;
                        }
                        Thread.sleep(1);
                    }
                }


                for (int k = 0; k <playerCount ; k++) {
                    for (int i = 0; i < playerCount; i++) {
                        System.out.print(k + " snake: ");
                        for (int j = 0; j < 5; j++) {
                            System.out.print(data[i][j] + " ");
                            out[k].writeUTF(data[i][j]);
                        }
                        System.out.println();
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
