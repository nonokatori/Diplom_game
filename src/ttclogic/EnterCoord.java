package ttclogic;

import java.util.Scanner;

public class EnterCoord {

    Scanner scan = new Scanner(System.in);

    int len;
    int X, Y;
    char let;
    char [][] arrField;

    public  EnterCoord (char [][]_arr, int _n) {
        len= _n;
        arrField = _arr;
    }

    public boolean Coordinates (char _let) {
        boolean state = true;
        int cs = 0;

        let = _let;


        while (cs != 2) {
            switch (cs) {
                case 0:
                    System.out.println("Enter the coordinates: ");
                    if (FindErrors()) {
                        cs = 0;
                        break;
                    }
                    else cs = 1;
                case 1:
                    if(!AddNewCoord()) {
                        cs = 0;
                        System.out.println("This cell is occupied! Choose another one!");
                        break;
                    }
                    else {
                        //PrintTicTac();
                        cs = 2;
                    }
            }//switch (cs)
        }//while (cs == 2)


        return state;

    }


    public boolean FindErrors(){

        boolean state;

        String scanXY = scan.nextLine();
        char coordX = scanXY.charAt(0);
        char coordY = scanXY.charAt(2);

        X = Character.digit(coordX,10);
        Y = Character.digit(coordY, 10);
        if (X==-1 || Y==-1) {
            System.out.println("You should enter numbers!");
            return state = true;
        }

        else if ((X >len|| Y > len) || (X<0 && Y<0)) {
            System.out.println("Coordinates should be from 1 to " + len + "!");
            state = true;
        }
        else state = false;

        return state;
    }

    public boolean AddNewCoord() {
        int st = 0;
        X = X - 1;
        Y = Y - 1;

        switch (st) {
            case 0: if (Y==0) {
                Y = 2;
                break;
            }
            case 1: if(Y==2)
                Y = 0;
        }

        if (arrField[Y][X]!= ' ') {
            return false;
        }

        arrField[Y][X] = let;

        return true;
    }

    public void PrintTicTac () {

        int n = 0;

        System.out.println("---------");
        for (int j = n; j <len; j++)
        {
            System.out.print("| ");
            for (int i = 0; i <len; i++)
            {
                System.out.print(arrField[j][i] + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");

    }


}
