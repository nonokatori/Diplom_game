package tttclogic;

import java.io.Serializable;

public class ArraySync implements Serializable {
    private volatile char [][] arrField = new char[3][3];
    private boolean set = false;

    public char[][] getArrField() {
        return arrField;
    }

    public void setArrField(char[][] arrField) {
        this.arrField = arrField;
    }

    public void setCoord(int i, int k, char letter) {
        arrField[i][k] = letter;
        set = true;
    }

    public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }
}
