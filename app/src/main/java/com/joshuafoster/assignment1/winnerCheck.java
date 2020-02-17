package com.joshuafoster.assignment1;

import android.widget.Button;

public class winnerCheck {
    private static final String wildSave = "wild.txt";
    Boolean playerTurn=true;
    String playerName;
    //checks for a winner
    public boolean winnerCheck(Button[][] board) {
        //change turn
        playerTurn = !playerTurn;
        if(playerTurn!=true){
            playerName ="Player1";
        }else{
            playerName ="Player2";
        }

        //assign board to a string 2d array
        String[][] boardString = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                boardString[i][x] = board[i][x].getText().toString();

            }
        }
        //checks for horizontal winner
        for (int i = 0; i<3; i++){
            if((boardString[i][0].equals(boardString[i][1])
                    && (boardString[i][0].equals(boardString[i][2]))
                    && !boardString[i][0].equals(""))) {
                return true;
            }
        }
        //Vertical check
        for (int x = 0; x<3; x++) {
            if ((boardString[0][x].equals(boardString[1][x])
                    && (boardString[0][x].equals(boardString[2][x]))
                    && !boardString[0][x].equals(""))) {
                return true;
            }
            //Diagonal checks
            if (boardString[0][0].equals(boardString[1][1])
                    && (boardString[0][0].equals(boardString[2][2]))
                    || (boardString[2][0].equals(boardString[1][1]))
                    && (boardString[2][0].equals(boardString[0][2]))
                    && !boardString[0][0].equals("")
                    && !boardString[2][0].equals("")){
                return true;
            }
        }
        return false;
    }

}
