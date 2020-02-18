package com.joshuafoster.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    Button board[][] = new Button[3][3];
    Boolean playerTurn=true;
    String playerName;
    int numTurns=0;
    int p1Points=0;
    int p2Points=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button wildButton = findViewById(R.id.button);
        Button numericButton = findViewById(R.id.numericBtn);
        wildButton.setOnClickListener(this);
        numericButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            setContentView(R.layout.wildttt);
            Button play = findViewById(R.id.PlayButton);
            play.setOnClickListener(this);
        }

        else if (v.getId() == R.id.numericBtn) {
            Intent goToMisereGame = new Intent(this,MisereGame.class);
            startActivity(goToMisereGame);
        }

        else if (v.getId() == R.id.PlayButton){
            setContentView(R.layout.gameboard);

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
        else {
            if (!((Button) v).getText().toString().equals("")) {
                return;
            }
            ((Button) v).setText("X");
            if (winnerCheck()==true){
                Toast.makeText(this,playerName+" wins!",Toast.LENGTH_LONG).show();
                resetBoard();
            }
        }
    }
    @Override
    public boolean onLongClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return true;
        }
        ((Button)v).setText("O");
        if (winnerCheck()==true){
            Toast.makeText(this,playerName+" wins!",Toast.LENGTH_LONG).show();
            resetBoard();
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
        //reset the board to a blank state
        for(int i=0; i<3; i++){
            for(int x=0; x<3; x++){
                board[i][x].setText("");
            }
        }
    }


}
