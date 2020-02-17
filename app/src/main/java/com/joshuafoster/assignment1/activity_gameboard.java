package com.joshuafoster.assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_gameboard extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    Button board[][] = new Button[3][3];
    Boolean playerTurn=true;
    String playerName;
    int round=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
    }

    @Override
    protected void onStart(){
        super.onStart();

        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                String Id_button = "button" + i + x;
                int Button_Resource = getResources().getIdentifier(Id_button, "id", getPackageName());
                board[i][x] = findViewById(Button_Resource);
                board[i][x].setOnClickListener(this);
                board[i][x].setOnLongClickListener(this);
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        ((Button) v).setText("X");
        round++;

        if (winnerCheck()==true){
            Toast.makeText(this,playerName+" wins!",Toast.LENGTH_LONG).show();
            resetBoard();
        }
        else if (round==9){
            resetBoard();
            Toast.makeText(this,"Draw!",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onLongClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return true;
        }
        ((Button)v).setText("O");
        //round increment
        round++;

        if(winnerCheck()==true){
            Toast.makeText(this,playerName+" wins!",Toast.LENGTH_LONG).show();
            resetBoard();
        }
        else if (round==9){
            resetBoard();
            Toast.makeText(this,"Draw!",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //checks for a winner
    public boolean winnerCheck() {

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

    public void resetBoard(){
        //reset the playerTurn
        playerTurn=true;
        //reset the round
        round = 0;
        //reset the board to a blank state
        for(int i=0; i<3; i++){
            for(int x=0; x<3; x++){
                board[i][x].setText("");
            }
        }
    }

}
