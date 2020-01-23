package ttclogic;

import java.util.Scanner;

import static java.lang.Character.toUpperCase;


public class Tic_Toe {

    String cell;

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

        pl1 = word[1];
        pl2 = word[2];

        enter.PrintTicTac();

        String state = "_letter1";
        char letter = 'X';


        while (state!= "win") {

            switch (state) {
                case "_letter1":
                    nextMove(pl1, letter);
                    state = "print";
                    letter = 'O';
                    break;

                case  "_letter2":
                    nextMove(pl2, letter);
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

    private void nextMove(String __letter, char _letter) {
        if ( __letter.equals("user")) enter.Coordinates(_letter);
        else easyLevel.LvlSelect(__letter, _letter);

    }


    private void CheckLength() {
        if (cell.length() != lengthArr*lengthArr) {
            System.out.println("Bad length");
            System.exit(0);
        }
    }

    private void CheckSymbols() {

        for (int i = 0; i < lengthArr; i++)
            for(int j = 0; j < lengthArr; j++)
            {
                arrField[j][i] = toUpperCase(arrField[j][i]);         // перевести символы в верний регистр
                if (arrField[j][i] != 'X' && arrField[j][i] != ' ' && arrField[j][i] != 'O')
                {
                    System.out.println("Bad char " + arrField[j][i]);
                    System.exit(0);
                }
            }
    }

}
