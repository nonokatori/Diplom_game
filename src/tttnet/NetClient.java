package tttnet;

import tttlogic.ArraySync;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class NetClient {

    private String server;
    private int port;

    public NetClient(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public static void main(String[] args) {
        try (InputStream inputStream = NetClient.class.getClassLoader().getResourceAsStream("config.properties");) {
            Properties properties = new Properties();
            properties.load(inputStream);

            String server = properties.getProperty("server");
            int port = Integer.parseInt(properties.getProperty("port"));
            System.out.println(server);
            NetClient mesClient = new NetClient(server, port);
            mesClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) { //
/*            new Thread(new ReaderThread())*/
        }
    }
}

class MessageClient {
    private String server;
    private int port;
    private Scanner scanner;

    public MessageClient(String server, int port) {
        this.server = server;
        this.port = port;
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        System.out.println("Введите имя");

        String name = scanner.nextLine();
        String messageText;
        while (true){
            System.out.println("Введите сообщение");
            messageText = scanner.nextLine();
            try {
                sendAndGetMessage(name, messageText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAndGetMessage(String name, String messageText) throws Exception {
        try (Connection connection = new Connection(new Socket(server, port))){
            ArraySync message = new ArraySync();
            connection.sendMessage(message);

            message = connection.readMessage();
            System.out.println("ответ от сервера: " + message);
        }
    }


    public static void main(String[] args) {
        try (InputStream inputStream = MessageClient.class.getClassLoader().getResourceAsStream("config.properties")){

            Properties properties = new Properties();
            properties.load(inputStream);

            String server = properties.getProperty("server");//"127.0.0.1";
            int port = Integer.parseInt(properties.getProperty("port")); //8090;
            System.out.println(server);
            MessageClient messageClient = new MessageClient(server, port);
            messageClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
