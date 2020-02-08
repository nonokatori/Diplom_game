package tttlogic;

import javafx.scene.control.Button;
import tttnet.MessageArr;
import tttnet.NetClient;
import tttnet.NetServer;

import java.io.IOException;
import java.util.Random;

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



    private volatile MessageArr messageArr;
    private Enum online;
    private NetServer server;
    private NetClient client;


    ArraySync arraySync = new ArraySync();
    LevelAI easyLevel = new LevelAI(arraySync.getArrField());

    public Enum mainLogic() {
        char state = this.letter;

        if(player2==null) return player2;
        switch (state) {
            case 'X':
                nextMove(player1);
                break;
            case 'O':
                nextMove(player2);
                break;
        }
        Enum st = field(letter); //может выдать ошибку в знаке
        if(EnumGame.State.WIN.equals(st)) return EnumGame.State.WIN;
        if (EnumGame.State.DRAW.equals(st)) return EnumGame.State.DRAW;
        if (arraySync.isSet()) {
            arraySync.setSet(false);
            letter = letter == 'X' ? 'O' : 'X';
            ID = ID != null? null: ID;
            return EnumGame.State.SET;
        }
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
    }

    public Enum onlineLogic() {
        char state = 'X';
        int i = 0,k = 0;

        if (state == enemyChar) {
            try {
                messageArr = client == null? client.read(): server.read();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            i = messageArr.getCoord()[0];
            k = messageArr.getCoord()[1];
        }

        else if(state == myChar) {
            while (ID == null) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i= Character.getNumericValue(ID.charAt(0));
            k = Character.getNumericValue(ID.charAt(1));
            messageArr.setCoord(new int[]{i, k});
            try {
                if (client == null) {
                    client.send(messageArr);
                } else {
                    server.send(messageArr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        arraySync.setCoord(i,k, state);
        Enum st = field(state); //может выдать ошибку в знаке
        if(EnumGame.State.WIN.equals(st)) return EnumGame.State.WIN;
        if (EnumGame.State.DRAW.equals(st)) return EnumGame.State.DRAW;
        if (arraySync.isSet()) {
            arraySync.setSet(false);
            state = state == myChar ? enemyChar : myChar;
            ID = ID != null? null: ID;
            return EnumGame.State.SET;
        }
        return EnumGame.State.NOTHING;
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

    public Enum getOnline() {
        return online;
    }
    public void setOnline(Enum online) {
        this.online = online;
        if (EnumGame.Online.SERVER.equals(online)) {
            server = new NetServer();
            myChar = randomLetter();
            enemyChar = myChar == 'X' ? 'O' : 'X';
            NetServer.load(myChar);
        }
        else if(EnumGame.Online.CLIENT.equals(online)) {
            client = new NetClient();
            myChar = NetClient.load();
        }
    }

    public char randomLetter() {
        Random random = new Random();
        int let = random.nextInt(2);
        return let == 0? 'X' : 'O';
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


    public char getLetter() {
        return letter;
    }

    public Enum getPlayer2() {
        return player2;
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

    //для проверки в консоли
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

    public char[][] getArrField() {
        return arraySync.getArrField();
    }

}

