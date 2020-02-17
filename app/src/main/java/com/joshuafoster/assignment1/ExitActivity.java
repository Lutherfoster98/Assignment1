package com.joshuafoster.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);

    }
}
