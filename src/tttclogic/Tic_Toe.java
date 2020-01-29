package tttclogic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tttinterface.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Tic_Toe {


    private Enum player1;
    private Enum player2;
    private Controller controller;

    private char letter = 'X';

    private String ID;
    private boolean wait;

    private char [][] arrField = new char[3][3];

    CheckField check = new CheckField(arrField);
    EasyLevel easyLevel = new EasyLevel(arrField);

    public Tic_Toe()
    {
        for (int i=0,j = 0; i < arrField.length; i++, j++)
            arrField[i][j] = ' ';
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public void setLetter(char letter) { this.letter = letter; }

    public void setID(String ID) {
        this.ID = ID;
    }

    public char getLetter() {
        return letter;
    }

    public Enum getPlayer2() {
        return player2;
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

        if (!check.field(letter).equals(null)) {
            check.field(letter).state(controller);
            //TODO как останоовить игру....

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
}

enum GameState {
    WIN {
        @Override
        public void state(Controller controller) {
            String player =  new Tic_Toe().getLetter() == 'X' ? " игрок 1" : " игрок 2";
            controller.getPaneImage().setVisible(true);
            try {
                ImageView iv = new ImageView(new Image(new FileInputStream("win.jpg")));
            } catch (FileNotFoundException e) {
                System.out.println("О БОЖИ ОШИБКА В ЕНУМЕ ПОБЕДИТЕЛЯ");
            }
            controller.getCongrat().setText("Победитель" + player );
        }
    },
    DRAW {
        @Override
        public void state(Controller controller) {
            controller.getPaneImage().setVisible(true);
            try {
                ImageView iv = new ImageView(new Image(new FileInputStream("draw.jpg")));
            } catch (FileNotFoundException e) {
                System.out.println("О БОЖИ ОШИБКА В ЕНУМЕ НИЧЬЕЙ");
            }
            controller.getCongrat().setText("Ничья");
        }

    };

    public abstract void state (Controller controller);
}
