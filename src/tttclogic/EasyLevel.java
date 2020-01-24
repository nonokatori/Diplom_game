package tttclogic;

import java.util.ArrayList;
import java.util.List;

import static tttclogic.TypeGame.*;

public class EasyLevel {


    int len;
    char [][] arrField;
    char let;

    int score = 0;
    int bestScore;
    int bestMove = 0;

    char [] field = new char[9];
    char letterOur, letterEnemy;


    String [] level = {
            "easy",
            "medium",
            "hard"
    };

    public EasyLevel (char [][] _arr, int _n) {
        len = _n;
        arrField = _arr;

        int  n = 0;
        for (int i = 0; i < _n; i++)
            for (int j = 0; j < _n; j++){
                field[n]  = _arr [i][j];
                n++;
            }

    }

    public void lvlSelect(Enum type, char _let) {

        let = _let;
        System.out.println ("Making move level " + type);


        if (EASY.equals(type)) {
            randomMove();
        } else if (MIDDLE.equals(type)) {
            if (!MediumLevelMove())
                randomMove();
        } else if (HARD.equals(type)) {
            letter(_let);
        }
    }

    private boolean MediumLevelMove() {

        int counX = 0, counO = 0;
        char state;
        int l = 0, k=0; /////

        boolean moveDo = false;

        for (int j = 0; j < len; j++) {
            for (int i = 0; i < len; i++)
            {
                state = arrField[i][j];
                switch (state) {
                    case 'X':
                        counX++;
                        break;
                    case 'O':
                        counO++;
                        break;
                    case ' ':
                        l = i;
                }
                if ((counX == 2 || counO == 2) && arrField[l][j]==' ') {

                    arrField[l][j] = let;
                    return moveDo = true;
                }
            }
            counO = 0;
            counX = 0;
        }

        for (int i = 0; i < len; i++) {

            for (int j = 0; j < len; j++)
            {
                state = arrField[i][j];
                switch (state) {
                    case 'X':
                        counX++;
                        break;
                    case 'O':
                        counO++;
                        break;
                    case ' ':
                        l = j;
                }
                if ((counX == 2 || counO == 2) && arrField[i][l]==' ') {

                    arrField[i][l] = let;
                    return moveDo = true;
                }
            }
            counO = 0;
            counX = 0;
        }

        for (int i = 0; i < len; i++) {
            state = arrField[i][i];
            switch (state) {
                case 'X':
                    counX++;
                    break;
                case 'O':
                    counO++;
                    break;
                case ' ':
                    l = i;
            }
        }
        if ((counX == 2 || counO == 2) && arrField[l][l]==' ') {

            arrField[l][l] = let;
            return moveDo = true;
        }

        for (int i = 0, j =0; i < len; j++, i++)  {
            state = arrField[i][j];
            switch (state) {
                case 'X':
                    counX++;
                    break;
                case 'O':
                    counO++;
                    break;
                case ' ':
                    l = i;
                    k = j;
            }
            if ((counX == 2 || counO == 2) && arrField[l][k]==' ') {
                arrField[l][k] = let;
                return moveDo = true;
            }
        }

        return moveDo;

    }


    private void randomMove() {

        int max = 0;
        int min = 0;

        String num;
        String[] arrRandom = new String[9];

        for (int i = 0; i < len; i++)
            for (int j = 0; j < len; j++) {

                if (arrField[i][j] == ' ') {
                    num = i + " " + j;
                    arrRandom[max] = num;
                    max++;
                }
            }

        int rndNum = min + (int) (Math.random() * max);
        char k = arrRandom[rndNum].charAt(0),
                l = arrRandom[rndNum].charAt(2);
        System.out.println(Character.digit(k,10) + " " + Character.digit(l,10));

        arrField[Character.digit(k,10)][Character.digit(l,10)] = let;

    }


    private void letter (char _letter) {
        letterOur = _letter;

        letterEnemy = letterOur == 'X' ? 'O' : 'X';
        int[] let = methodMinimax(2,letterOur, arrField);

        arrField[let[1]][let[2]] = _letter;
    }

    private int[] methodMinimax (int _depth, char _player, char[][] _newField) {
        List<int[]> nextMoves = generateMoves();

        int bestScore = (_player == letterOur) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || _depth == 0) {
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                // Try this move for the current "player"
                _newField[move[0]][move[1]] = _player;
                if (_player == letterOur) {  // mySeed (computer) is maximizing player
                    currentScore = methodMinimax(_depth - 1, letterEnemy,_newField)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // oppSeed is minimizing player
                    currentScore = methodMinimax(_depth - 1, letterOur, _newField)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // Undo move
                _newField[move[0]][move[1]] = ' ';
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List

        // If gameover, i.e., no next move
        if (hasWon(letterOur) || hasWon(letterEnemy)) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < arrField.length; ++row) {
            for (int col = 0; col <arrField.length; ++col) {
                if (arrField[row][col] == ' ') {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }


    private int evaluate() {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (arrField[row1][col1] == letterOur) {
            score = 1;
        } else if (arrField[row1][col1] == letterEnemy) {
            score = -1;
        }

        // Second cell
        if (arrField[row2][col2] == letterOur) {
            if (score == 1) {   // cell1 is mySeed
                score = 10;
            } else if (score == -1) {  // cell1 is oppSeed
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (arrField[row2][col2] == letterEnemy) {
            if (score == -1) { // cell1 is oppSeed
                score = -10;
            } else if (score == 1) { // cell1 is mySeed
                return 0;
            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (arrField[row3][col3] == letterOur) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (arrField[row3][col3] == letterEnemy) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10;
            } else if (score > 1) {  // cell1 and/or cell2 is mySeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }

    private int[] winningPatterns = {
            0b111000000, 0b000111000, 0b000000111, // rows
            0b100100100, 0b010010010, 0b001001001, // cols
            0b100010001, 0b001010100               // diagonals
    };

    private boolean hasWon(char _player) {
        int pattern = 0b000000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < arrField.length; ++row) {
            for (int col = 0; col < arrField.length; ++col) {
                if (arrField[row][col] == _player) {
                    pattern |= (1 << (row * arrField.length + col));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) return true;
        }
        return false;
    }

}
