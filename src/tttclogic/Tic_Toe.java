package tttclogic;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Tic_Toe {

    private Enum player1;
    private Enum player2;
    private char letter = 'X';
    private String ID;
    private boolean wait;
    private boolean goGame;
    private char [][] arrField = new char[3][3];
    private Button [] btn = new Button[9];
    private Label going;

    private boolean winnerArrGor;
    private boolean winnerArrVer;
    private boolean winnerArrDiagPl;
    private boolean winnerArrDiagMin;

    EasyLevel easyLevel = new EasyLevel(arrField);

    public Enum mainLogic() {
        char state = this.letter;

        if(player2==null) return player2;

        switch (state) {
            case 'X':
                nextMove(player1);
                letter = 'O';
                break;
            case 'O':
                nextMove(player2);
                letter = 'X';
                break;
        }
        GameState st = field(state);
        if(GameState.WIN.equals(st)) {
            String player = letter == 'X' ? "игрок 1" :"игрок 2";
            going.setText("Победил "+ player);
            return GameState.WIN;
        }
        if (GameState.DRAW.equals(st)) {
                going.setText("Ничья");
            return GameState.DRAW;
            }

        return GameState.NOTHING;
    }

    public void initArray () {
        for (int i=0; i < arrField.length; i++)
            for (int j = 0; j < arrField.length; j++)
                arrField[i][j] = ' ';
        for (Button bt : btn) {
            bt.setText("");
            bt.setDisable(false);
        }
        setPlayer1(null);
        setPlayer2(null);
        letter = 'X';
        going.setText("");
    }

    public void clicked(Button btn) {
        wait = true;
        this.ID = btn.getId().substring(3,5);
    }

    private void nextMove(Enum type) {
        if (TypeGame.USER.equals(type)) {
            while (!wait) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            arrField[Character.getNumericValue(ID.charAt(0))][Character.getNumericValue(ID.charAt(1))] = letter;
            wait = false;
        }
        else easyLevel.lvlSelect(type, letter);

    }

    public Enum getPlayer1() {
        return player1;
    }

    public void setPlayer1(Enum player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Enum player2) {
        this.player2 = player2;
    }

    public void setGoGame(boolean goGame) {
        this.goGame = goGame;
    }

    public char[][] getArrField() {
        return arrField;
    }

    public void setBtn(Button[] btn, Label going) {
        this.btn = btn;
        this.going = going;

    }

    public GameState field(char letter) {
        if (winnerSelection(letter))
            return GameState.WIN;
        else if (drawNewField(letter))
            return GameState.DRAW;
        return GameState.NOTHING;
    }

    private boolean drawNewField(char letter) {

        boolean flag;
        boolean flagFin = false;
        int coun = 0;


        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                flag = arrField[i][j] == ' ';
                if (!flag) coun++;
            }

        if (coun == 9) {
            System.out.println("Draw new field.");
            flagFin = true;
        }
        printTicTac();

        return flagFin;
    } //заполнено ли поле

    //для проверки в консоли
    public void printTicTac () {

        int n = 0;

        System.out.println("---------");
        for (int j = n; j <3; j++)
        {
            System.out.print("| ");
            for (int i = 0; i <3; i++)
            {
                System.out.print(arrField[j][i] + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");

    }

    private boolean winnerSelection(char letter) {
        winnerArrDiagPl = (arrField[0][0] == arrField[1][1]) &&
                (arrField[1][1] == arrField[2][2]) && (arrField[1][1] == letter); // диагональ положительная
        winnerArrDiagMin = (arrField[2][0] == arrField[1][1]) &&
                (arrField[1][1] == arrField[0][2]) && (arrField[1][1] == letter); //диагональ отрицательная

        for (int j = 0; j < 3; j++) {
            winnerArrVer = (arrField[0][j] == arrField[1][j])
                    && (arrField[1][j] == arrField[2][j]) && (arrField[1][j] == letter); //ver
            winnerArrGor = (arrField[j][0] == arrField[j][1])
                    && (arrField[j][1] == arrField[j][2]) && (arrField[j][1] == letter); // gor

            if (winnerArrGor || winnerArrVer) break;
        }

        printTicTac();
        return winnerArrDiagMin || winnerArrDiagPl || winnerArrGor || winnerArrVer;
    }

    public enum GameState {
        WIN, DRAW, NOTHING
    }
}

