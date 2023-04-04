package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerRun implements Runnable{
    protected int port = 1280;

    protected ServerSocket server = null;
    protected boolean isStopped = false;

    public ServerRun() {
    }

    public static void main(String[] args) {
        (new Thread(new ServerRun())).start();
    }

    @Override
    public void run() {
        openServerSocket();
        while(!isStopped()){
            Socket client = null;
            try {
                client = this.server.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Сервер остановлен.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            new Thread(new Work(client)).start();
            System.out.println("Клиент подключен.");
        }
        System.out.println("Сервер остановлен.") ;
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private void openServerSocket() {
        System.out.println("Сервер запущен.");
        try {
            this.server = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.port, e);
        }
    }
}
