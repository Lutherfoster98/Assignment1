package com.joshuafoster.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MisereRules extends AppCompatActivity implements View.OnClickListener {
    Button homeBtn, gameBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misererules);

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(this);

        gameBtn = findViewById(R.id.gameBtn);
        gameBtn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.homeBtn) {
            Intent goToHomeScreen = new Intent(this,MainActivity.class);
            startActivity(goToHomeScreen);
        }

        else if (v.getId() == R.id.gameBtn) {
            Intent goBackToGame = new Intent(this, MisereGame.class);
            startActivity(goBackToGame);
        }
    }
}
