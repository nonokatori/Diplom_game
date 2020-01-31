package tttnet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

