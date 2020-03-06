package tttlogic;

import javafx.application.Platform;
import javafx.scene.control.Label;
import tttnet.MessageArr;

import javafx.scene.control.Button;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class Tic_Toe {

    private Enum player1;
    private Enum player2;
    private char letter = 'X';
    private volatile String ID;
    private char myChar;
    private char enemyChar;

    private boolean winnerArrGor;
    private boolean winnerArrVer;
    private boolean winnerArrDiagPl;
    private boolean winnerArrDiagMin;

    private Button [] buttons = new Button[9];
    private Label going;

    MessageArr messageArr;
    ArraySync arraySync = new ArraySync();
    LevelAI easyLevel = new LevelAI(arraySync.getArrField());

    public boolean mainLogic() {
        Enum flag;
        char state = this.letter;

        switch (state) {
            case 'X':
                nextMove(player1);
                break;
            case 'O':
                nextMove(player2);
                break;
        }
        flag = resultLogic();
        if (!EnumGame.State.NOTHING.equals(flag)) {
            return true;
        }
        return false;
    }

    public Enum resultLogic () {
        Enum st = field(letter);
        AtomicReference<String> result = new AtomicReference<>();
        if(EnumGame.State.WIN.equals(st))  {
            result.set("Победили " + (getLetter() == 'X' ? "крестики" : "нолики"));
        }
        else if (EnumGame.State.DRAW.equals(st)) {
            result.set("Ничья, начните \nновую игру");
        }
        if (arraySync.isSet()) {
            arraySync.setSet(false);
            letter = letter == myChar ? enemyChar : myChar;
            result.set(letter == myChar ? "Ваш ход" : "Ход оппонента");
            ID = ID != null ? null : ID;
        }
        Platform.runLater(() -> {
            update();
            going.setText(String.valueOf(result));
        });
        return EnumGame.State.NOTHING;
    }

    public void initState () {
        for (int i=0; i < arraySync.getArrField().length; i++)
            for (int j = 0; j < arraySync.getArrField().length; j++)
                arraySync.setCoord(i, j,' ');
        player1 = null;
        player2 = null;
        letter = 'X';
        ID = null;
    }

    public void clicked(Button btn) {
        this.ID = btn.getId().substring(3, 5);
        System.out.println(ID);
    }

    private void nextMove(Enum type) {
        int i = 0,k = 0;
        if (EnumGame.Type.USER.equals(type)) {
            while (ID == null) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i= Character.getNumericValue(ID.charAt(0));
            k = Character.getNumericValue(ID.charAt(1));

        }
        else {
            int [] coord = easyLevel.lvlSelect(type, letter);
            i = coord[0];
            k = coord[1];
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        arraySync.setCoord(i,k, letter);

    }

    public char randomLetter() {
        Random random = new Random();
        int let = random.nextInt(2);
        myChar = /*let == 0? 'X' : */'X';
        enemyChar = myChar == 'X' ? 'O' : 'X';
        return enemyChar;
    }

    public void setPlayer1(Enum player1) {
        this.player1 = player1;
    }
    public void setPlayer2(Enum player2) {
        this.player2 = player2;
    }
    public void setMyChar (char c) {
        myChar = c;
        enemyChar = myChar == 'X' ? 'O' : 'X';
    }
    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }
    public void setGoing(Label going) {
        this.going = going;
    }

    public char getLetter() {
        return letter;
    }

    public char getMyChar() {
        return myChar;
    }

    public char getEnemyChar() {
        return enemyChar;
    }

    public Enum getPlayer1() {
        return player1;
    }

    public Enum getPlayer2() {
        return player2;
    }

    public char[][] getArrField() {
        return arraySync.getArrField();
    }

    public MessageArr getMessageArr() {
        while (ID == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int [] coord = {Character.getNumericValue(ID.charAt(0)),
                Character.getNumericValue(ID.charAt(1))};
        messageArr = new MessageArr(coord);
        arraySync.setCoord(coord[0],coord[1],myChar);
        return messageArr;
    }

    public void setMessageArr(MessageArr messageArr) {
        arraySync.setCoord( messageArr.getCoord()[0], messageArr.getCoord()[1], letter);
    }

    private void update() {
        int coun = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++){
                buttons[coun].setText(Character.toString(getArrField()[i][j]));
                buttons[coun].setDisable(buttons[coun].getText().equals("X")
                        || buttons[coun].getText().equals("O"));
                coun++;
            }
    }

    public EnumGame.State field(char letter) {
        if (winnerSelection(letter))
            return EnumGame.State.WIN;
        else if (drawNewField(letter))
            return EnumGame.State.DRAW;
        printTicTac();
        return EnumGame.State.NOTHING;
    }

    private boolean drawNewField(char letter) {

        boolean flag;
        boolean flagFin = false;
        int coun = 0;


        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                flag = arraySync.getArrField()[i][j] == ' ';
                if (!flag) coun++;
            }

        if (coun == 9) {
            System.out.println("Draw new field.");
            flagFin = true;
        }
        printTicTac();

        return flagFin;
    } //заполнено ли поле

    public void printTicTac () {

        int n = 0;

        System.out.println("---------");
        for (int j = n; j <3; j++)
        {
            System.out.print("| ");
            for (int i = 0; i <3; i++)
            {
                System.out.print(arraySync.getArrField()[j][i] + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");

    }

    private boolean winnerSelection(char letter) {
        winnerArrDiagPl = (arraySync.getArrField()[0][0] ==  arraySync.getArrField()[1][1])
                && (arraySync.getArrField()[1][1] ==  arraySync.getArrField()[2][2])
                && (arraySync.getArrField()[1][1] == letter); // диагональ положительная
        winnerArrDiagMin = (arraySync.getArrField()[2][0] == arraySync.getArrField()[1][1]) &&
                (arraySync.getArrField()[1][1] == arraySync.getArrField()[0][2])
                && (arraySync.getArrField()[1][1] == letter); //диагональ отрицательная

        for (int j = 0; j < 3; j++) {
            winnerArrVer = (arraySync.getArrField()[0][j] == arraySync.getArrField()[1][j])
                    && (arraySync.getArrField()[1][j] == arraySync.getArrField()[2][j])
                    && (arraySync.getArrField()[1][j] == letter); //ver
            winnerArrGor = (arraySync.getArrField()[j][0] == arraySync.getArrField()[j][1])
                    && (arraySync.getArrField()[j][1] == arraySync.getArrField()[j][2])
                    && (arraySync.getArrField()[j][1] == letter); // gor

            if (winnerArrGor || winnerArrVer) break;
        }

        printTicTac();
        return winnerArrDiagMin || winnerArrDiagPl || winnerArrGor || winnerArrVer;
    } //проверка победных комбинаций

}

