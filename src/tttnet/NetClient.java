package tttnet;

import tttlogic.ArraySync;
import tttlogic.Tic_Toe;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class NetClient {
    private String server;
    private int port;
    private Connection connection;

    public NetClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public void start() throws IOException {
        connection = new Connection(new Socket(server, port));
    }

    public char setLetter () throws IOException {
        char c = 0;
        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c = connection.firstRead();
            if(c !='O' || c != 'X') break;
        }
        return c;
    }

    public void send(MessageArr message) throws IOException, ClassNotFoundException, InterruptedException {
        connection.sendMessage(message);
    }

    public MessageArr read() throws IOException, ClassNotFoundException {
        return connection.readMessage();
    }

    public static NetClient create() {
        NetClient messageClient = null;
        try (InputStream inputStream = NetClient.class.getClassLoader().getResourceAsStream("config.properties")){

            Properties properties = new Properties();
            properties.load(inputStream);

            String server = properties.getProperty("server");
            int port = Integer.parseInt(properties.getProperty("port"));
            System.out.println(server);
            messageClient = new NetClient(server, port);
            messageClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageClient;
    }
}
