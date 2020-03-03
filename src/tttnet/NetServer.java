package tttnet;

import tttnet.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetServer {
    private int port;
    private Connection connection;

    public NetServer(int port){
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        connection = new Connection(socket);

        System.out.println("Server started...");
    }

    public void getLetter (char c) {
        try {
            connection.firstSend(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(MessageArr message) throws IOException, ClassNotFoundException, InterruptedException {
        connection.sendMessage(message);
    }

    public MessageArr read() throws IOException, ClassNotFoundException {
        return connection.readMessage();
    }

    public static NetServer create() {
        int port = 8090;
        NetServer messageServer = new NetServer(port);
        try {
            messageServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageServer;
    }

}
