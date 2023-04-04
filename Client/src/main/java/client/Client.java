package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private static ObjectOutputStream outStream;
    private static ObjectInputStream inStream;
    private static String message;


    public Client(String ipAddress, Integer port){
        try {
            clientSocket = new Socket(ipAddress, port);
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("Server not found: " + e.getMessage());
            System.exit(0);
        }
    }

    public static void sendMessage(String message){
        try {
            outStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendObject(Object object){
        try {
            outStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readMessage() throws IOException {
        try {
            message = (String) inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public static Object readObject(){
        Object object = new Object();
        try {
            object = inStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}

