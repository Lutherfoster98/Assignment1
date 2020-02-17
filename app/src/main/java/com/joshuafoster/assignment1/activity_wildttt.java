package com.joshuafoster.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_wildttt extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wildttt);
        Button play = findViewById(R.id.PlayButton);
        play.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        if (v.getId() == R.id.PlayButton){
            Intent intent = new Intent(getApplicationContext(),
                    activity_gameboard.class);
            startActivity(intent);

        }


    }


}
