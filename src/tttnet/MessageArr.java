package tttnet;

import java.io.Serializable;
import java.util.Arrays;

public class MessageArr implements Serializable {
    private volatile boolean waitFlag = true;
    private volatile int [] coord = new int[2];

    public MessageArr(int[] coord) {
        this.coord = coord;
    }


    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int k, int j) {
        coord[0] = k;
        coord[1] = j;
    }

    @Override
    public String toString() {
        return "MessageArr{" +
                "waitFlag=" + waitFlag +
                ", coord=" + Arrays.toString(coord) +
                '}';
    }
}
