package tttclogic;

public class CheckField {

    private int length = 3;
    private char[][] fieldArr;
    private char letter;
    private boolean win;

    private boolean[] winnerArr = new boolean[4];
    private boolean winnerArrGor;
    private boolean winnerArrVer;
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
        PrintTicTac();

        return flagFin;
    } //заполнено ли поле

    public void PrintTicTac () {

        int n = 0;

        System.out.println("---------");
        for (int j = n; j <3; j++)
        {
            System.out.print("| ");
            for (int i = 0; i <3; i++)
            {
                System.out.print(fieldArr[j][i] + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");

    }
    private boolean winnerSelection() {
        gorVerWinner();
        diagWinner();
        PrintTicTac();
        return winnerArrDiagMin || winnerArrDiagPl || winnerArrGor || winnerArrVer;
    }

   /* private void winnerFind(boolean[] _arr, char[] _char, int _n) {

        for (int i = 0; i < _arr.length; i++) {// выставляем флаг, что победитель найден
            if (_arr[i] == true) {
                winnerArr[_n] = true;
                winChar[_n] = _char[i];
                break;
            } else winnerArr[_n] = false;
        }
    }*/

    private void diagWinner() {

        winnerArrDiagPl = (fieldArr[0][0] == fieldArr[1][1]) &&
                (fieldArr[1][1] == fieldArr[2][2]) && (fieldArr[1][1] == letter); // диагональ положительная
        winnerArrDiagMin = (fieldArr[2][0] == fieldArr[1][1]) &&
                (fieldArr[1][1] == fieldArr[0][2]) && (fieldArr[1][1] == letter); //диагональ отрицательная
    }

    private void gorVerWinner() {

        for (int j = 0; j < length; j++)
            for (int i = 1; i < length - 1; i++) {

                winnerArrGor = (fieldArr[j][i - 1] == fieldArr[j][i])
                        && (fieldArr[j][i] == fieldArr[j][i + 1]) && (fieldArr[j][i] == letter); // gor

                winnerArrVer = (fieldArr[i - 1][j] == fieldArr[i][j])
                        && (fieldArr[i][j] == fieldArr[i + 1][j]) && (fieldArr[i][j] == letter); //ver

                if (winnerArrGor || winnerArrVer) {
                    return;
                }
            } //for (int j = 0; j < 3; j++)
    }

}



