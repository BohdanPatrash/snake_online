package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Testclient {
    private static Socket socket;
    public static void main(String[] args) {

        try {

            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket("localhost", 3355);
            System.out.println("Client connected to socket");




        } catch(Exception e){
            e.printStackTrace();
        }
        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
        DataInputStream ois = new DataInputStream(socket.getInputStream()))
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("Client oos & ois initialized");
            ////here write info that client sends to the server
            oos.writeUTF("go");
            ////here write info that client sends to the server
            oos.flush();
            ////here write info that client accepts from the server

            System.out.println(ois.readInt());


            ////here write info that client accepts from the server
            String consolecommand;
            do{
                consolecommand = sc.nextLine();
                oos.writeUTF(consolecommand);
            }while (!consolecommand.equals("quit"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}