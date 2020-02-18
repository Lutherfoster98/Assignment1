package com.joshuafoster.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MisereGame extends AppCompatActivity implements View.OnClickListener {
    Button resetBtn, numRulesBtn, homeBtn;
    Button board[][] = new Button[3][3];
    Boolean player1Turn=true;
    int numTurns=0, p1Score=0, p2Score=0, numDraws=0;

    String playerName;
    TextView p1ScoreTV;
    TextView p2ScoreTV;
    TextView drawScoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misere_layout);

        if (savedInstanceState != null) {
            numTurns = savedInstanceState.getInt("numTurns");
            p1Score = savedInstanceState.getInt("p1score");
            p2Score = savedInstanceState.getInt("p2score");
            numDraws = savedInstanceState.getInt("numDraws");
            player1Turn = savedInstanceState.getBoolean("player1Turn");
        }


        // Getting resources
        resetBtn = findViewById(R.id.resetBtn);
        numRulesBtn = findViewById(R.id.RulesBtn);
        homeBtn = findViewById(R.id.homeBtn);
        p1ScoreTV = findViewById(R.id.player1TV);
        p2ScoreTV = findViewById(R.id.player2TV);
        drawScoreTV = findViewById(R.id.drawScoreTV);

        // Setting listeners for top row of action buttons
        resetBtn.setOnClickListener(this);
        numRulesBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);

        // Getting ids for game board and setting listeners
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                String id_button = "button" + i + x;
                int Button_Resource = getResources().getIdentifier(id_button, "id", getPackageName());
                board[i][x] = findViewById(Button_Resource);
                board[i][x].setOnClickListener(this);
             }
        }
    } // End onCreate

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.resetBtn){
            resetBoard();
        } // End Reset If

        else if (v.getId() == R.id.homeBtn) {
            Intent goToHomeScreen = new Intent(this,MainActivity.class);
            startActivity(goToHomeScreen);
        } // End Home If

        else if (v.getId() == R.id.RulesBtn) {
            Intent goToNumericRules = new Intent(this, MisereRules.class);
            startActivity(goToNumericRules);
        } // End Rules If

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        numTurns++;

        if (winnerCheck()) {
            if (player1Turn) {
                player2Wins();
            } else {
                player1Wins();
            }
        } else if (numTurns == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    } // End onClick

    private void resetBoard() {
        //reset the playerTurn
        player1Turn = true;
        numTurns=0;

        //reset the board to a blank state
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                board[i][x].setText("");
            } // End x loop
        } // End i loop
    } // End resetBoard

    //checks for a winner
    public boolean winnerCheck() {

        //assign board to a string 2d array
        String[][] boardString = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                boardString[i][x] = board[i][x].getText().toString();
            } // End inner loop
        } // End outer loop

        // Horizontals check
        for (int i = 0; i<3; i++){
            if ((boardString[i][0].equals(boardString[i][1])
                    && (boardString[i][0].equals(boardString[i][2]))
                    && !boardString[i][0].equals(""))) {
                return true;
            } // End horizontal if statement
        } // End horizontal for loop

        // Verticals check
        for (int x = 0; x<3; x++) {
            if ((boardString[0][x].equals(boardString[1][x])
                    && (boardString[0][x].equals(boardString[2][x]))
                    && !boardString[0][x].equals(""))) {
                return true;
            } // // End vertical if statement
        } // End vertical for loop

        //Diagonal checks
        if (boardString[0][0].equals(boardString[1][1])
            && boardString[0][0].equals(boardString[2][2])
            && !boardString[0][0].equals("")) {
                return true;
            } // End top to bottom diagonal check

        if (boardString[2][0].equals(boardString[1][1])
            && boardString[2][0].equals(boardString[0][2])
            && !boardString[2][0].equals("")) {
                return true;
            } // End bottom to top diagonal check
        return false;
    } // End winnerCheck

    private void player1Wins() {
        p1Score++;
        Toast.makeText(this, "Great job Player 1! You win!", Toast.LENGTH_SHORT).show();
        incrementPoints();
        resetBoard();
    }

    private void player2Wins() {
        p2Score++;
        Toast.makeText(this, "Great job Player 2! You win!", Toast.LENGTH_SHORT).show();
        incrementPoints();
        resetBoard();
    }

    private void draw() {
        numDraws++;
        Toast.makeText(this, "It's a draw! No winner.", Toast.LENGTH_SHORT).show();
        incrementPoints();
        resetBoard();
    }

    private void incrementPoints() {
        p1ScoreTV.setText("Player 1(X\'s): " + p1Score);
        p2ScoreTV.setText("Player 2:(O\'s) " + p2Score);
        drawScoreTV.setText("Draw: " + numDraws);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("numTurns", numTurns);
        outState.putInt("p1score", p1Score);
        outState.putInt("p2score", p2Score);
        outState.putInt("numDraws", numDraws);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        numTurns = savedInstanceState.getInt("numTurns");
        p1Score = savedInstanceState.getInt("p1score");
        p2Score = savedInstanceState.getInt("p2score");
        numDraws = savedInstanceState.getInt("numDraws");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }


} // End class
