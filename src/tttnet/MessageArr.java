package tttnet;

import java.io.Serializable;
import java.util.Arrays;

public class MessageArr implements Serializable {
    private boolean waitFlag = true;
    private int [] coord = new int[2];

    public MessageArr(int[] coord) {
        this.coord = coord;
    }

    public int[] getCoord() {
        return coord;
    }

    public void setCoord(int[] coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "MessageArr{" +
                "waitFlag=" + waitFlag +
                ", coord=" + Arrays.toString(coord) +
                '}';
    }
}
