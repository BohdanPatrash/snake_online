package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerStart {
    static ExecutorService executeIt = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(3355);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("server created");
            while (!server.isClosed()) {

                Socket client = server.accept();
                System.out.println("client connecting...");

                executeIt.execute(new MonoThreadClientHandler(client));
                System.out.println("client connected");

            }

            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
