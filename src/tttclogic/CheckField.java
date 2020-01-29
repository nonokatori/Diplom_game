package tttclogic;

public class CheckField {

    private int length = 3;
    private char[][] fieldArr;
    private char letter;

    private char[] winChar = new char[4];
    private char[] winCharGor = new char[length];
    private char[] winCharVer = new char[length];
    private char winCharDiagPl;
    private char winCharDiagMin;

    private boolean[] winnerArr = new boolean[4];
    private boolean[] winnerArrGor = new boolean[length];
    private boolean[] winnerArrVer = new boolean[length];
    private boolean winnerArrDiagPl;
    private boolean winnerArrDiagMin;

    public CheckField(char[][] _arr) {
        fieldArr = _arr;
    }


    public GameState field(char letter) {

        this.letter = letter;

        if (winnerSelection())
            return GameState.WIN;
        else if (drawNewField())
            return GameState.DRAW;
        return null;
    }


    private boolean drawNewField() {

        boolean flag;
        boolean flagFin = false;
        int coun = 0;


        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++) {
                flag = fieldArr[i][j] == ' ';
                if (!flag) coun++;
            }

        if (coun == length * length) {
            System.out.println("Draw new field.");
            flagFin = true;
        }

        return flagFin;
    } //заполнено ли поле


    private boolean winnerSelection() {
        boolean stateFlag = false;

        gorVerWinner();
        diagWinner();

        winnerFind(winnerArrVer, winCharVer, 0);
        winnerFind(winnerArrGor, winCharGor, 1);
        winnerArr[2] = winnerArrDiagPl;
        winnerArr[3] = winnerArrDiagMin;

        winChar[2] = winCharDiagPl;
        winChar[3] = winCharDiagMin;

        for (int i = 0; i < winnerArr.length; i++) {
            if (winnerArr[i]) {
                System.out.println(winChar[i] + " wins");
                stateFlag = true;
                break;
            } else stateFlag = false;
        }

        return stateFlag;
    }

    private void winnerFind(boolean[] _arr, char[] _char, int _n) {

        for (int i = 0; i < _arr.length; i++) {// выставляем флаг, что победитель найден
            if (_arr[i] == true) {
                winnerArr[_n] = true;
                winChar[_n] = _char[i];
                break;
            } else winnerArr[_n] = false;
        }
    }

    private void diagWinner() {

        winnerArrDiagPl = (fieldArr[0][0] == fieldArr[1][1]) &&
                (fieldArr[1][1] == fieldArr[2][2]) && (fieldArr[1][1] == letter); // диагональ положительная
        winnerArrDiagMin = (fieldArr[2][0] == fieldArr[1][1]) &&
                (fieldArr[1][1] == fieldArr[0][2]) && (fieldArr[1][1] == letter); //диагональ отрицательная

        if (winnerArrDiagMin)
            winCharDiagMin = fieldArr[1][1];   //winDiagMinCh,
        if (winnerArrDiagPl) winCharDiagPl = fieldArr[1][1];  //winDiagPlCh,

    }

    private void gorVerWinner() {

        for (int j = 0; j < length; j++)
            for (int i = 1; i < length - 1; i++) {

                winnerArrGor[j] = (fieldArr[j][i - 1] == fieldArr[j][i])
                        && (fieldArr[j][i] == fieldArr[j][i + 1]) && (fieldArr[j][i] == letter); // gor

                winnerArrVer[j] = (fieldArr[i - 1][j] == fieldArr[i][j])
                        && (fieldArr[i][j] == fieldArr[i + 1][j]) && (fieldArr[i][j] == letter); //ver


                if (winnerArrGor[j]) {
                    winCharGor[j] = fieldArr[j][i]; //winGorCh,

                }
                if (winnerArrVer[j]) {
                    winCharVer[j] = fieldArr[i][j];
                }
                // if (winChar[])//winGorCh,
            } //for (int j = 0; j < 3; j++)
    }

}



