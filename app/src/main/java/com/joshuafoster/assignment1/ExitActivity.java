package com.joshuafoster.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ExitActivity extends AppCompatActivity implements View.OnClickListener{

    String winner = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        /*Bundle extras = getIntent().getExtras();
                if(extras != null){
            winner = extras.getString("winner");
        }

         */

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent extraIntent = getIntent();
        winner = extraIntent.getStringExtra("winner");

        String output = "Player ";
        if ((winner .equals( "1")) || (winner .equals( "2")))
            output += winner;
        output += " wins!";

        if (winner == "draw")
            output = "There was no winner";

        TextView results = findViewById(R.id.info_textview);
        results.setText(output);


    }
    @Override
    public void onClick(View view) {
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


        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);

    }


}
