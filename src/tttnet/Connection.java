package tttnet;

import tttlogic.ArraySync;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements AutoCloseable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(this.socket.getOutputStream());
        in = new ObjectInputStream(this.socket.getInputStream());
    }

    public void sendMessage(MessageArr message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public void  firstSend(char letter) throws IOException {
        out.writeChar(letter);
        out.flush();
        System.out.println("send1");
    }

    public MessageArr readMessage() throws IOException, ClassNotFoundException {
        return (MessageArr) in.readObject();
    }

    public char firstRead () throws IOException {
        return in.readChar();
    }
    @Override
    public void close() throws Exception {
        out.close();
        in.close();
    }
}
