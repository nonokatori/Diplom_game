package tttnet;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {

    private char [] chars;

    public Message(char[] chars) {
        this.chars = chars;
    }
    public Message() {};

    public char[] getChars() {
        return chars;
    }

    @Override
    public String toString() {
        return "Message{" +
                "chars=" + Arrays.toString(chars) +
                '}';
    }
}
