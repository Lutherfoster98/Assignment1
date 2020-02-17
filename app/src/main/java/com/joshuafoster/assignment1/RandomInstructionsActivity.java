package com.joshuafoster.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RandomInstructionsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_instructions);
        Button play = findViewById(R.id.PlayButton);
        play.setOnClickListener(this);
        play = findViewById(R.id.back_button);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        if (v.getId() == R.id.PlayButton){
            Intent intent = new Intent(getApplicationContext(),
                    random_gameboard.class);
            startActivity(intent);
        } else if (v.getId() == R.id.back_button) {
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
        }

    }


}
