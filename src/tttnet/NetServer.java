package tttnet;
import tttclogic.ArraySync;

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
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started...");
            while (true){
                Socket socket = serverSocket.accept();
                connection = new Connection(socket);

                connection.sendMessage(new ArraySync()); //что это может значить
            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
    }
    }


    public static void main(String[] args) {
        int port = 8090;
        NetServer messageServer = new NetServer(port);
        try {
            messageServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ReaderThread implements Runnable {

    private Connection connection;
    private String port;

    @Override
    public void run() {

//        Thread.
    }
}
