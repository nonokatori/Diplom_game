package tttclogic;

import javafx.scene.control.Button;

public class Tic_Toe {

    private Enum player1;
    private Enum player2;
    private char letter = 'X';
    private String ID;
    private boolean wait; //
    private boolean goGame; //
    private char [][] arrField = new char[3][3];
    private Button [] btn = new Button[9];

    CheckField check = new CheckField(arrField);
    EasyLevel easyLevel = new EasyLevel(arrField);

    public void mainLogic() {
        char state = this.letter;
        while (true) {
            if (player1!=(null) && player2!=(null)) {
                break;
            }
        }

        while (true) {
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
        }
    }

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
        wait = true;
        btn.setText(String.valueOf(letter)); //устанавливаем знак
        this.ID = btn.getId().substring(3,5);
    }
    /* для работы с поля в контроллере и  */

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
/*        if (st != (null)) {
            winDraw = st;
            //TODO как остановить игру....
        }*/
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

//    public Enum getWinDraw() {
//        return winDraw;
//    }

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

    public void setGoGame(boolean goGame) {
        this.goGame = goGame;
    }

    public char[][] getArrField() {
        return arrField;
    }

    public void setBtn(Button[] btn) {
        this.btn = btn;
    }

    public enum GameState {
        WIN , DRAW /*{
            @Override
            public void state() {
                new Controller().paneImage.setVisible(true);
                try {
                    new ImageView(new Image(new FileInputStream("draw.jpg")));
                } catch (FileNotFoundException e) {
                    System.out.println("О БОЖИ ОШИБКА В ЕНУМЕ НИЧЬЕЙ");
                }
                new Controller().congrat.setText("Ничья");
            }

        }*/
    }
}

