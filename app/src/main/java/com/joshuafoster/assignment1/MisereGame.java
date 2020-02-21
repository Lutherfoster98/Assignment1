package com.joshuafoster.assignment1;

// Team Members: Joshua Foster, Lionel Sosa Estrada, and Stephanie Escue

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class MisereGame extends AppCompatActivity implements View.OnClickListener {
    Button resetBtn, back_button;
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
        back_button = findViewById(R.id.back_button);
        p1ScoreTV = findViewById(R.id.player1TV);
        p2ScoreTV = findViewById(R.id.player2TV);
        drawScoreTV = findViewById(R.id.drawScoreTV);

        // Set starting game score
        showScore();

        // Setting listeners for top row of action buttons
        resetBtn.setOnClickListener(this);
        back_button.setOnClickListener(this);

        // Getting ids for game board and setting listeners
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                String id_button = "button" + i + x;
                int Button_Resource = getResources().getIdentifier(id_button, "id", getPackageName());
                board[i][x] = findViewById(Button_Resource);
                board[i][x].setOnClickListener(this);
             }
        }
        readState();
    } // End onCreate

    @Override
    protected void onStop(){
        super.onStop();
        saveState();
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.resetBtn){
            resetGame();
        } // End Reset If

        else if (v.getId() == R.id.back_button) {
            Intent goToHomeScreen = new Intent(this,MainActivity.class);
            startActivity(goToHomeScreen);
        } // End Home If

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

    private void resetGame() {
        //reset the playerTurn
        player1Turn = true;
        numTurns=0;
        p1Score=0;
        p2Score=0;
        numDraws=0;

        //reset the board to a blank state
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                board[i][x].setText("");
            } // End x loop
        } // End i loop
        showScore();
     } // End resetGame

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
    } // End resetGame

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
        //Toast.makeText(this, "Great job Player 1! You win!", Toast.LENGTH_SHORT).show();
        showScore();
        resetBoard();
        Intent intent = new Intent(getApplicationContext(), ExitActivity.class);
        intent.putExtra("winner","1");
        startActivity(intent);
    }

    private void player2Wins() {
        p2Score++;
        //Toast.makeText(this, "Great job Player 2! You win!", Toast.LENGTH_SHORT).show();
        showScore();
        resetBoard();
        Intent intent = new Intent(getApplicationContext(), ExitActivity.class);
        intent.putExtra("winner","2");
        startActivity(intent);
    }

    private void draw() {
        numDraws++;
        //Toast.makeText(this, "It's a draw! No winner.", Toast.LENGTH_SHORT).show();
        showScore();
        resetBoard();
        Intent intent = new Intent(getApplicationContext(), ExitActivity.class);
        intent.putExtra("winner","0");
        startActivity(intent);

    }

    private void showScore() {
        p1ScoreTV.setText(getResources().getString(R.string.player1) + p1Score);
        p2ScoreTV.setText(getResources().getString(R.string.player2)+ p2Score);
        drawScoreTV.setText(getResources().getString(R.string.draw)  + numDraws);
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

    public void saveState() {
        FileOutputStream fos;
        try {
            fos = openFileOutput("misere.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("yes");
            for (int i = 0; i < 3; i++) {
                for (int x = 0; x < 3; x++) {
                    //this was needed to restore the game
                    if (board[i][x].getText().equals("X") || board[i][x].getText().equals("O"))
                        pw.println(board[i][x].getText());
                    else
                        pw.println("-");
                }
            }
            pw.close();

            //save file with game type
            FileOutputStream fos2 = openFileOutput("savedGame.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw2 = new OutputStreamWriter(fos2);
            BufferedWriter bw2 = new BufferedWriter(osw2);
            PrintWriter pw2 = new PrintWriter(bw2);
            pw2.println("misere");
            pw2.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readState(){ //restores saved game
        try { //tries to open file with game saved
            FileInputStream fis = openFileInput("misere.txt");
            Scanner scanner = new Scanner(fis);

            String firstLine = scanner.next();
            if (firstLine.equals("yes")) {
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++) {
                        String temp = scanner.next();
                        if (temp.equals("X") || temp.equals("O")) {
                            board[i][j].setText(temp);
                            numTurns++;
                        }
                    }
                if (numTurns % 2 == 0)
                    player1Turn = true;
                else
                    player1Turn = false;

            } else
                scanner.close();

            //open file again to delete contents
            FileOutputStream fos = openFileOutput("random.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write("no");
            pw.close();

        } catch (FileNotFoundException e) {
            // no file to read
        }


    }


} // End class
