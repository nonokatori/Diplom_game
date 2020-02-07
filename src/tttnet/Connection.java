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

    public void sendMessage(ArraySync array) throws IOException {
        out.writeObject(array);
        out.flush();
    }

    public ArraySync readMessage() throws IOException, ClassNotFoundException {
        return (ArraySync) in.readObject();
    }

    @Override
    public void close() throws Exception {
        out.close();
        in.close();
    }
}
