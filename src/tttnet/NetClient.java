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

    public NetClient() {}

    public char start() throws IOException {
        connection = new Connection(new Socket(server, port));
        return connection.firstRead();
    }

    public void send(MessageArr message) throws IOException, ClassNotFoundException, InterruptedException {
        connection.sendMessage(message);
    }

    public MessageArr read() throws IOException, ClassNotFoundException {
        return connection.readMessage();
    }


    public static char load() {
        char let = 0;
        try (InputStream inputStream = NetClient.class.getClassLoader().getResourceAsStream("config.properties")){

            Properties properties = new Properties();
            properties.load(inputStream);

            String server = properties.getProperty("server");
            int port = Integer.parseInt(properties.getProperty("port"));
            System.out.println(server);
            NetClient messageClient = new NetClient(server, port);
            let = messageClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return let;
    }
}
