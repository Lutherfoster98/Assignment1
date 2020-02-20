package com.joshuafoster.assignment1;

// Team Members: Joshua Foster, Lionel Sosa Estrada, and Stephanie Escue

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button assignButton = findViewById(R.id.button_wild);
        assignButton.setOnClickListener(this);

        assignButton = findViewById(R.id.button_random);
        assignButton.setOnClickListener(this);

        assignButton = findViewById(R.id.button_misere);
        assignButton.setOnClickListener(this);

        try { //tries to open file with game saved
            FileInputStream fis = openFileInput("savedGame.txt");
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNext()) {
                String loadGame = scanner.next();

                //open file again to delete contents
                FileOutputStream fos = openFileOutput("savedGame.txt", Context.MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter pw = new PrintWriter(bw);
                pw.write("");
                pw.close();

                switch(loadGame){
                    case "random":
                        restoreRandom();
                        break;
                    case "wild":
                        restoreWild();
                        break;
                    case "misere":
                        restoreMisere();
                        break;
                }
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            Log.e("File", "Couldn't open a file.");
        }


    }



    @Override
    public void onClick(View v) {

        // START NEW GAMES
        if(v.getId() == R.id.button_wild){
            delete_saved_game(); //start new game
            Intent intent = new Intent(getApplicationContext(),
                    activity_wildttt.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.button_random){
            delete_saved_game(); //start new game
            Intent intent = new Intent(getApplicationContext(),
                    RandomInstructionsActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.button_misere) {
            delete_saved_game(); //start new game
            Intent intent = new Intent(getApplicationContext(),
                    MisereRules.class);
            startActivity(intent);
        }

    }

    private void restoreRandom(){
        Intent intent = new Intent(getApplicationContext(),
                random_gameboard.class);
        startActivity(intent);

    }

    private void restoreWild(){
        Intent intent = new Intent(getApplicationContext(),
                activity_gameboard.class);
        startActivity(intent);

    }

    private void restoreMisere(){
        Intent intent = new Intent(getApplicationContext(),
                random_gameboard.class);
        startActivity(intent);

    }

    @Override
    protected void onStop(){
        super.onStop();
        delete_saved_game();
    }

    private void delete_saved_game(){
        //deletes saved game when exiting from the main screen
        try {
            // Exiting from main screen, deleting saved game
            FileOutputStream fos = openFileOutput("savedGame.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);
            pw.write("");
            pw.close();
        } catch (FileNotFoundException e) {
            Log.e("File", "Couldn't open a file.");
        }

        try {
            FileOutputStream fos2 = openFileOutput("random.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw2 = new OutputStreamWriter(fos2);
            BufferedWriter bw2 = new BufferedWriter(osw2);
            PrintWriter pw2 = new PrintWriter(bw2);
            pw2.write("no");
            pw2.close();

        } catch (FileNotFoundException e) {
            Log.e("File", "Couldn't open a file.");
        }

        try {
            FileOutputStream fos2 = openFileOutput("wild.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw2 = new OutputStreamWriter(fos2);
            BufferedWriter bw2 = new BufferedWriter(osw2);
            PrintWriter pw2 = new PrintWriter(bw2);
            pw2.write("no");
            pw2.close();

        } catch (FileNotFoundException e) {
            Log.e("File", "Couldn't open a file.");
        }

        try {
            FileOutputStream fos2 = openFileOutput("misere.txt", Context.MODE_PRIVATE);
            OutputStreamWriter osw2 = new OutputStreamWriter(fos2);
            BufferedWriter bw2 = new BufferedWriter(osw2);
            PrintWriter pw2 = new PrintWriter(bw2);
            pw2.write("no");
            pw2.close();

        } catch (FileNotFoundException e) {
            Log.e("File", "Couldn't open a file.");
        }

    }

}
