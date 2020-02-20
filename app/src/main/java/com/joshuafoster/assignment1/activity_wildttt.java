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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class activity_wildttt extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wildttt);
        Button play = findViewById(R.id.PlayButton);
        play.setOnClickListener(this);
        play = findViewById(R.id.back_button);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
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

        if (v.getId() == R.id.PlayButton){
            Intent intent = new Intent(getApplicationContext(),
                    activity_gameboard.class);
            startActivity(intent);
        } else if (v.getId() == R.id.back_button) {
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
        }

    }



}
