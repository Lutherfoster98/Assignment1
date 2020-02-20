package com.joshuafoster.assignment1;

// Team Members: Joshua Foster, Lionel Sosa Estrada, and Stephanie Escue

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MisereRules extends AppCompatActivity implements View.OnClickListener {
    Button back_button, gameBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misererules);

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this);

        gameBtn = findViewById(R.id.gameBtn);
        gameBtn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_button) {
            Intent goToHomeScreen = new Intent(this,MainActivity.class);
            startActivity(goToHomeScreen);
        }

        else if (v.getId() == R.id.gameBtn) {
            Intent playMisereGame = new Intent(this, MisereGame.class);
            startActivity(playMisereGame);
        }
    }
}
