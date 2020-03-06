package tttnet;

import tttnet.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetServer {
    private int port;
    private Connection connection;
    private ServerSocket serverSocket;

    public NetServer(int port){
        this.port = port;
    }

    public void start(char c) {
        try {
            System.out.println("create");
            serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            connection = new Connection(socket);
            connection.firstSend(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started...");
    }

    public void close () {
        try {
            serverSocket.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server closed");
    }

    public void send(MessageArr message) throws IOException {
        connection.sendMessage(message);
    }

    public MessageArr read() throws IOException, ClassNotFoundException {
        return connection.readMessage();
    }

    public static NetServer create(char c) {
        int port = 8090;

        System.out.println("create");
        NetServer messageServer = new NetServer(port);
        System.out.println("start");
        messageServer.start(c);
        return messageServer;
    }
}
