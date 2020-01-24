package tttclogic;

import java.util.Scanner;


public class Tic_Toe {


    private Enum player1;
    private Enum player2;

    public void setPlayer1(Enum player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Enum player2) {
        this.player2 = player2;
    }

    int lengthArr = 3;
    char [][] arrField = new char[lengthArr][lengthArr];


    CheckField check = new CheckField(arrField, lengthArr);
    EnterCoord enter = new EnterCoord(arrField, lengthArr);
    EasyLevel easyLevel = new EasyLevel(arrField, lengthArr);
    Scanner scanner  = new Scanner(System.in);

    public Tic_Toe()
    {
        for (int i = 0; i < lengthArr; i++)
            for (int j = 0; j < lengthArr; j++)
            {
                arrField[i][j] = ' ';
            }// check.SetArrayAndLength(arrField, lengthArr);*/
    }

    public void TicTacToe_Game () {


        String stEx;
        String st;

        String pl1, pl2;
        String [] word = new String[3];


        st = "read";

        while (!st.equals("further")) {
            switch (st) {
                case "read":
                    System.out.print("Input command: ");
                    stEx = scanner.nextLine();
                    word = stEx.split("\\s");
                    st = word[0];
                    break;

                case "exit":
                    System.exit(0);
                    break;

                case "start":
                    if (word.length < 3) {
                        System.out.println("Bad parameters!");
                        st = "read";
                        break;
                    }
                    else st = "further";
            }
        }


        enter.PrintTicTac();

        String state = "_letter1";
        char letter = 'X';


        while (state!= "win") {

            switch (state) {
                case "_letter1":
                    nextMove(player1, letter);
                    state = "print";
                    letter = 'O';
                    break;

                case  "_letter2":
                    nextMove(player2, letter);
                    state = "print";
                    letter = 'X';
                    break;

                case "print":
                    enter.PrintTicTac();
                    state = "check_field";
                    break;

                case "check_field": // вводит пользователь
                    if (check.Field())
                        state = "win"; //выход из программы
                    else if (letter == 'O') state = "_letter2";
                    else state = "_letter1";
                    break;

            }//switch (state)
        }//while (state!=4)*/
    }

    private void nextMove(Enum type, char _letter) {
        if (TypeGame.USER.equals(type)) enter.Coordinates(_letter);
        else easyLevel.lvlSelect(type, _letter);

    }
}


