package com.joshuafoster.assignment1;

// Team Members: Joshua Foster, Lionel Sosa Estrada, and Stephanie Escue

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class activity_gameboard extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String wildSave = "wild.txt";
    Button board[][] = new Button[3][3];
    String[][] boardString = new String[3][3];
    Boolean playerTurn=true;
    String playerName;
    int round=0;

    @Override
    protected void onStop(){
        super.onStop();
        saveState();
    }


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
        readState();

    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        ((Button) v).setText("X");
        round++;
        //saveState();
        if(winnerCheck() ==true){
            String winnerIntent;
            if (playerTurn)
                winnerIntent = "1";
            else
                winnerIntent = "2";
            resetBoard();
            Intent intent = new Intent(getApplicationContext(), ExitActivity.class);
            intent.putExtra("winner", winnerIntent);
            startActivity(intent);
        }
        else if (round==9){ //draw
            resetBoard();
            Intent intent = new Intent(getApplicationContext(), ExitActivity.class);
            intent.putExtra("winner","draw");
            startActivity(intent);
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

        if(winnerCheck() ==true){
            String winnerIntent;
            if (playerTurn)
                winnerIntent = "1";
            else
                winnerIntent = "2";
            resetBoard();
            Intent intent = new Intent(getApplicationContext(), ExitActivity.class);
            intent.putExtra("winner", winnerIntent);
            startActivity(intent);
        }
        else if (round==9){ //draw
            resetBoard();
            Intent intent = new Intent(getApplicationContext(), ExitActivity.class);
            intent.putExtra("winner","draw");
            startActivity(intent);
        }
        return false;
    }

    //checks for a winner
    public boolean winnerCheck() {

        //change turn
        playerTurn = !playerTurn;
        /*if(playerTurn){
            playerName ="2";
        }else{
            playerName ="1";
        }*/

        //assign board to a string 2d array

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
        }
        //Diagonal checks
        if         (boardString[0][0].equals(boardString[1][1])
                && (boardString[0][0].equals(boardString[2][2]))
                && !boardString[0][0].equals("")
                ||
                (boardString[2][0].equals(boardString[1][1]))
                && (boardString[2][0].equals(boardString[0][2]))
                && !boardString[2][0].equals("")){
            return true;
        }
        saveState();
        return false;
    }

    public void saveState() {
        FileOutputStream fos;
        try {
            fos = openFileOutput(wildSave, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("yes");
            for (int i = 0; i < 3; i++) {
                for (int x = 0; x < 3; x++) {
                    //added an if to save a dash if the square hasn't been played
                    //this was needed to restore the game
                    if (boardString[i][x].equals("X") || boardString[i][x].equals("O"))
                        pw.println(boardString[i][x]);
                    else
                        pw.println("-");
                    Log.i("Writing", "here in saveState");
                }
            }
            pw.close();

            //save file with game type
            FileOutputStream fos2 = openFileOutput("savedGame.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw2 = new OutputStreamWriter(fos2);
            BufferedWriter bw2 = new BufferedWriter(osw2);
            PrintWriter pw2 = new PrintWriter(bw2);
            pw2.println("wild");
            pw2.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readState(){
        try { //tries to open file with game saved
            FileInputStream fis = openFileInput("wild.txt");
            Scanner scanner = new Scanner(fis);

            String firstLine = scanner.next();
            if (firstLine.equals("yes")) {
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++) {
                        String temp = scanner.next();
                        if (temp.equals("X") || temp.equals("O")) {
                            boardString[i][j] = temp;
                            board[i][j].setText(temp);
                            round++;
                        }
                    }
                if (round % 2 == 0)
                    playerTurn = true;
                else
                    playerTurn = false;

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
        //reset the boardString to a blank state
        for(int i=0; i<3; i++){
            for(int x=0; x<3; x++){
                boardString[i][x] ="";
            }
        }
        //************
        //reset the wild.txt file
       FileOutputStream fos;
        try {
            fos = openFileOutput(wildSave, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);
            pw.print("");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //************

    }

}
