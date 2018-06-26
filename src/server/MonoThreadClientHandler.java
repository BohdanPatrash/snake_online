package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {
    private static Socket clientDialog;

    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {

        try {

            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());

            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {
                if(in.available() > 0) {

                    double entry = in.readDouble();
                    double entry1 = in.readDouble();

                    System.out.println(entry+"\n"+entry1);

                    out.flush();
                }
                Thread.sleep(10);
            }

            in.close();
            out.close();
            clientDialog.close();
            System.out.println("client disconnected");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
