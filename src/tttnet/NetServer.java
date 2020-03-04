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
    }

    public void send(MessageArr message) throws IOException {
        connection.sendMessage(message);
    }

    public MessageArr read() throws IOException, ClassNotFoundException {
        return connection.readMessage();
    }

    public static NetServer create(char c) {
        int port = 8090;
        NetServer messageServer = new NetServer(port);
        messageServer.start(c);
        return messageServer;
    }

}
