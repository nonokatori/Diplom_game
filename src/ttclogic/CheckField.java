package ttclogic;


public class CheckField {

    int length;
    char[][] fieldArr;

    private char[] winChar;
    private char[] winCharGor;
    private char[] winCharVer;
    private char winCharDiagPl;
    private char winCharDiagMin;

    private boolean[] winnerArr;
    private boolean[] winnerArrGor;
    private boolean[] winnerArrVer;
    private boolean winnerArrDiagPl;
    private boolean winnerArrDiagMin;

    private boolean [] impArr;

    public CheckField(char [][] _arr, int _len) {

        length = _len;
        fieldArr = _arr;

        winChar = new char[4];
        winCharGor = new char[length];
        winCharVer = new char[length];
        winnerArr = new boolean [4];
        winnerArrGor = new boolean[length];
        winnerArrVer = new boolean[length];
        impArr = new boolean[4];
    }


    public boolean Field() {

        int state = 0;
        boolean win = false;


        switch (state) {
            case 0:
                if (ImpossibilityTest()) {
                    win = true;
                    break;
                }

            case 1:
                if (WinnerSelection()) {
                    win = true;
                    break;
                }

            case 2:
                if (DrawNewField()) {
                    win = true;
                    break;
                }

            case 3:


        }
        return win;
    } // проверяет поле на жизнеспособность


    private boolean DrawNewField() {        // "Draw" - when no side has a three in a arrField and the field has no empty cells;

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
            flagFin = true; //флаг, что поле перерисовываем
        }

        return flagFin;
    } //заполнено ли поле


    private boolean WinnerSelection() {
        // "Impossible" - when the field has three X in a arrField as well as three O in a arrField. Or the field has a lot more X's that O's or vice versa (if the difference is 2 or more, should be 1 or 0).*/



        boolean stateFlag = false;

        GorVerWinner();
        DiagWinner();

        WinnerFind(winnerArrVer, winCharVer, 0);
        WinnerFind(winnerArrGor, winCharGor, 1);
        winnerArr [2] = winnerArrDiagPl;
        winnerArr [3] = winnerArrDiagMin;

        winChar[2] = winCharDiagPl;
        winChar[3] = winCharDiagMin;

        for (int i = 0; i < winnerArr.length; i++) {
            if (winnerArr[i]) {
                System.out.println(winChar[i] + " wins");
                stateFlag = true;
                break;
            }
            else stateFlag = false;
        }

        return stateFlag;
    }

    private boolean ImpossibilityTest () {

        boolean flagImpos,
                flagFin   = false;

        int     countX = 0,
                countO = 0;

        for (int j = 0; j < length; j++)
            for (int k = 0; k < length; k++) {
                if ( fieldArr[k][j] == 'X') countX++;
                if ( fieldArr[k][j] == 'O') countO++;
            }

        flagImpos = ((countX - countO) > 1) || ((countO - countX) > 1);

        GorVerWinner();
        DiagWinner();

        impArr [0] = TestArr(winnerArrGor);
        impArr [1] = TestArr(winnerArrVer);
        impArr [2] = winnerArrDiagMin;
        impArr [3] = winnerArrDiagPl;

        for ( int i = 1; i < impArr.length; i++) { // переписать
            if ((impArr[i-1]==true) && impArr[i-1]==impArr[i]) {
                flagFin = true;
                break;
            }
            else impArr[i] = impArr[i-1];
        }


        if (flagImpos==true || flagFin==true) System.out.println("Impossible");


        return flagFin = flagFin || flagImpos;
    }

    private void WinnerFind (boolean [] _arr, char [] _char, int _n) {

        for (int i = 0; i < _arr.length; i++) {// выставляем флаг, что победитель найден
            if(_arr[i]==true) {
                winnerArr[_n] = true;
                winChar[_n] = _char[i];
                break;
            }
            else winnerArr[_n] = false;
        }
    }


    private void DiagWinner() {

        winnerArrDiagPl = (fieldArr[0][0] == fieldArr[1][1]) && (fieldArr[1][1] == fieldArr[2][2]) && ((fieldArr[1][1]=='X')||(fieldArr[1][1]=='O')); // диагональ положительная
        winnerArrDiagMin = (fieldArr[2][0] == fieldArr[1][1]) && (fieldArr[1][1] == fieldArr[0][2]) && ((fieldArr[1][1]=='X')||(fieldArr[1][1]=='O')); //диагональ отрицательная

        if (winnerArrDiagMin)
            winCharDiagMin = fieldArr[1][1];   //winDiagMinCh,
        if (winnerArrDiagPl) winCharDiagPl = fieldArr[1][1];  //winDiagPlCh,

    }

    private void GorVerWinner() {

        for (int j = 0; j < length; j++)
            for (int i = 1; i < length-1; i++) {

                winnerArrGor[j] = (fieldArr[j][i-1] == fieldArr[j][i])
                        && (fieldArr[j][i] == fieldArr[j][i + 1]) && ((fieldArr[j][i]=='X')||(fieldArr[j][i]=='O')); // gor

                winnerArrVer[j] = (fieldArr[i-1][j] == fieldArr[i][j])
                        && (fieldArr[i][j] == fieldArr[i + 1][j]) && ((fieldArr[i][j]=='X')||(fieldArr[i][j]=='O')); //ver


                if (winnerArrGor[j]) {
                    winCharGor[j] = fieldArr[j][i]; //winGorCh,

                }
                if (winnerArrVer[j]) {
                    winCharVer[j] = fieldArr[i][j];
                }
                // if (winChar[])//winGorCh,
            } //for (int j = 0; j < 3; j++)

    }


    private boolean TestArr (boolean [] _arr) {

        boolean flagFin = false;

        for (int i = 1; i < _arr.length; i++) {

            if (_arr[i - 1] == _arr[i] && (_arr[i - 1] == true)) {
                flagFin = true;
                break;
            } else {
                _arr[i] = _arr[i - 1];
                flagFin = false;
            }
        }
        return flagFin;
    }

//    public void SetArray (char [][] _arr, int _len)
//    {
//        length = _len;
//        fieldArr = _arr;
//    }
}
