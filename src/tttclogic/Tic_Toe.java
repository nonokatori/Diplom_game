package tttclogic;

import javafx.scene.control.Button;

public class Tic_Toe {

    private Enum player1;
    private Enum player2;

    private Enum winDraw;

    private char letter = 'X';

    private String ID;
    private boolean wait;

    private char [][] arrField = new char[3][3];

    CheckField check = new CheckField(arrField);
    EasyLevel easyLevel = new EasyLevel(arrField);

    public void initArray (Button [] buttons) {
        for (int i=0; i < arrField.length; i++)
            for (int j = 0; j < arrField.length; j++)
                arrField[i][j] = ' ';
        for (Button bt : buttons) {
            bt.setText("");
            bt.setDisable(false);
        }
        setPlayer1(null);
        setPlayer2(null);
        letter = 'X';
    }

    public void clicked(Button btn) {
        if(!"".equals(btn.getText())) {
//            new Controller().going.setText("Эта клетка занята,\nиспользуйте другую");
            return;
        }
        wait = true;
        btn.setText(String.valueOf(letter)); //устанавливаем знак
        this.ID = btn.getId().substring(3,5);
        ticTacToe_Game();
    }
    /* для работы с поля в контроллере и  */
    public char getLetter() {
        return letter;
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

    public void ticTacToe_Game () {

        char state = letter;
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

        GameState st = check.field(state);
        if (st != (null)) {
            winDraw = st;
            //TODO как остановить игру....
        }
    }

    private void nextMove(Enum type) {
        if (TypeGame.USER.equals(type)) {
            while (true)
                if (wait) {
                    wait = false;
                    break;
                }
            arrField[Character.getNumericValue(ID.charAt(0))][Character.getNumericValue(ID.charAt(1))] = letter;
        }
        else easyLevel.lvlSelect(type, letter);
    }

    public Enum getWinDraw() {
        return winDraw;
    }
}

