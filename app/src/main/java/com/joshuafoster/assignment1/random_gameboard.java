package com.joshuafoster.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

public class random_gameboard extends AppCompatActivity implements View.OnClickListener{

    ImageButton[][] buttons = new ImageButton[3][3];
    int[][] status = new int[3][3]; //piece played on a square

    int currentPlayer = -1;
    int chosenI = -11, chosenJ = -11;
    Button buttonNext;
    int played; //squares played
    char chosenPiece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_gameboard);

        createButtons(); //instantiates the buttons of the grid
        resetGame(); //resets buttons to no icon
        assignListener(); //assigns buttons to <this> listener
    }



    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            //save file with game type
            FileOutputStream fos = openFileOutput("savedGame.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);

            //write data
            pw.println("random");
            pw.close();

            //save game squares
            FileOutputStream fos2 = openFileOutput("random.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw2 = new OutputStreamWriter(fos2);
            BufferedWriter bw2 = new BufferedWriter(osw2);
            PrintWriter pw2 = new PrintWriter(bw2);

            for (int i = 0; i<3; i++)
                for (int j=0; j<3; j++)
                    pw2.println(status[i][j]);

            pw2.close();


            Toast.makeText(getApplicationContext(), "Game was saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.e("FILE", "Cannot open file for writing");
        }
    }

    private void createButtons(){
        buttons[0][0] = findViewById(R.id.button1_1);
        buttons[0][1] = findViewById(R.id.button1_2);
        buttons[0][2] = findViewById(R.id.button1_3);
        buttons[1][0] = findViewById(R.id.button2_1);
        buttons[1][1] = findViewById(R.id.button2_2);
        buttons[1][2] = findViewById(R.id.button2_3);
        buttons[2][0] = findViewById(R.id.button3_1);
        buttons[2][1] = findViewById(R.id.button3_2);
        buttons[2][2] = findViewById(R.id.button3_3);
        buttonNext = findViewById(R.id.next_player_button);
    }

    private void resetGame(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                buttons[i][j].setImageResource(R.drawable.n); //resets squares
                status[i][j] = 0; //resets array of values
            }
        }
        played = 0;
        chosenPiece = '-';
        currentPlayer = -1;
        buttonNext.setVisibility(View.VISIBLE);

        setInstructions("Welcome to Random Tic-Tac-Toe\n\nIn this game, a random player is chosen" +
                " at the beginning of each turn.\nPlayer 1 is X, player 2 is O.\n\n" +
                "Please tap \"Next Turn\" to begin.");
    }

    private void assignListener(){
        for (int i=0; i<3; i++)
            for (int j = 0; j <3; j++)
                buttons[i][j].setOnClickListener(this);
        buttonNext.setOnClickListener(this);

    }

    public void setInstructions(String text){
        TextView instructions = findViewById(R.id.game_instructions_text);
        instructions.setText(text);
        instructions.setTextColor(getResources().getColor(R.color.text));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttonNext.getId()) {
            currentPlayer = new Random().nextInt(2) + 1;
            buttonNext.setVisibility(View.GONE);
            String output = "Player " + currentPlayer + " select a square to place a ";
            if (currentPlayer == 1)
                output += "X";
            else
                output += "O";
            setInstructions(output);
        }

        //validate if a player has been randomly selected, otherwise toast alert
        if ((currentPlayer == 1) || (currentPlayer == 2)) {

            //validate if button was a square
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (view == buttons[i][j]) {
                        chosenI = i;
                        chosenJ = j;
                        play(currentPlayer);
                    }
        } else { //message to alert players that no player has been randomly assigned
            Toast.makeText(getApplicationContext(), "You must click \"Next Turn\" first", Toast.LENGTH_SHORT).show();
        }
    }

    //A square and a piece have been selected. Playing the piece now.
    public void play(int player){
        if (status[chosenI][chosenJ] == 0) { //validates that square hasn't been played already
            if (player == 1) //changes button icon
                buttons[chosenI][chosenJ].setImageResource(R.drawable.x);
            else
                buttons[chosenI][chosenJ].setImageResource(R.drawable.o);

            status[chosenI][chosenJ] = player; //saves piece to square
            played++; //increase turns played
            buttonNext.setVisibility(View.VISIBLE); //shows button to choose next player

            setInstructions("Tap \"Next Turn\" to choose a random player.");

            if (played >= 3) //minimum movements needed to win
                checkEndOfGame(player);

            //reset choices
            currentPlayer = -1;

        } else {
            Toast.makeText(getApplicationContext(), "That square was played already, choose a different one", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkEndOfGame(int lastPlayer){
        if (checkForWinner(lastPlayer)){
            endGame(currentPlayer);
        } else if (played == 9)
            endGame(0);
    }

    private boolean checkForWinner(int possibleWinner){
        for (int i = 0; i < 3; i++){
            if ((status[i][0] == possibleWinner) && (status[i][1]== possibleWinner) && (status[i][2] == possibleWinner)) //checks horizontally
                return true;
            else if ((status[0][i] == possibleWinner) && (status[1][i]== possibleWinner) && (status[2][i] == possibleWinner)) //checks vertically
                return true;
        }
        if ((status[0][0] == possibleWinner) && (status[1][1]== possibleWinner) && (status[2][2] == possibleWinner)) //checks diagonally
            return true;
        if ((status[2][2] == possibleWinner) && (status[1][1]== possibleWinner) && (status[0][0] == possibleWinner)) //checks diagonally
            return true;

        return false; //no winner
    }

    private void endGame(int winner) {
        String output = "";
        if ((winner == 1) || (winner == 2))
            output = "The winner is:\n\nPlayer " + winner;
        else
            output = "There was no winner this time";
        setInstructions(output);
        buttonNext.setText("Go back");

        TextView myText = findViewById(R.id.game_instructions_text );
        myText.setTextColor(Color.RED);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(5);
        myText.startAnimation(anim);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
