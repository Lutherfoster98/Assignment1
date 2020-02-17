package com.joshuafoster.assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class activity_gameboard extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String wildSave = "wild.txt";
    Button board[][] = new Button[3][3];
    String[][] boardString = new String[3][3];
    Boolean playerTurn=true;
    String playerName;
    int round=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        readState();
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
        //saveState();
        if (winnerCheck()==true){
            resetBoard();
            Intent intent = new Intent(getApplicationContext(),
                    ExitActivity.class);
            intent.putExtra("winner",playerName);
            startActivity(intent);


        }
        else if (round==9){ //draw
            resetBoard();
            Intent intent = new Intent(getApplicationContext(),
                    ExitActivity.class);
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
            resetBoard();
            Intent intent = new Intent(getApplicationContext(),
                    ExitActivity.class);
            intent.putExtra("winner",playerName);

            startActivity(intent);

        }
        else if (round==9){ //draw
            resetBoard();
            Intent intent = new Intent(getApplicationContext(),
                    ExitActivity.class);
            intent.putExtra("winner","draw");

            startActivity(intent);
        }
        return false;
    }

    //checks for a winner
    public boolean winnerCheck() {

        //change turn
        playerTurn = !playerTurn;
        if(playerTurn!=true){
            playerName ="1";
        }else{
            playerName ="2";
        }

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
            for (int i = 0; i < 3; i++) {
                for (int x = 0; x < 3; x++) {
                pw.println(boardString[i][x]);
                    Log.i("Writing", "here in saveState");
                }
            }
        pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void readState(){

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