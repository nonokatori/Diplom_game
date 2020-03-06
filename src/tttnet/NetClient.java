package tttnet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class NetClient {
    private String server;
    private int port;
    private Connection connection;
    private Socket socket;

    public NetClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public void start() {
        while (true) {
            try {
                socket = new Socket(server, port);
                connection = new Connection(socket);
                break;
            } catch (IOException e){
                System.out.println("Ожидание сервера");
            }
        }
    }

    public char setLetter () throws IOException {
        char c;
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

    public void send(MessageArr message) throws IOException {
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

    public void close() {
        try {
            connection.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Socket closed");
    }
}
