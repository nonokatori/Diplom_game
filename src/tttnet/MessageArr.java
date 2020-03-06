package tttnet;

import java.io.Serializable;
import java.util.Arrays;

public class MessageArr implements Serializable {
    private volatile int [] coord = new int[2];
    private static final long serialVersionUID = 5506756117605352206L;

    public MessageArr(int[] coord) {
        this.coord = coord;
    }


    public int[] getCoord() {
        return coord;
    }

//    public void setCoord(int k, int j) {
//        coord[0] = k;
//        coord[1] = j;
//    }

    @Override
    public String toString() {
        return "MessageArr{" +
                "coord=" + Arrays.toString(coord) +
                '}';
    }
}
