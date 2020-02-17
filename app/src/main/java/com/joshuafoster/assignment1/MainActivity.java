package com.joshuafoster.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button assignButton = findViewById(R.id.button_wild);
        assignButton.setOnClickListener(this);

        assignButton = findViewById(R.id.button_random);
        assignButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_wild){
            Intent intent = new Intent(getApplicationContext(),
                    activity_wildttt.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.button_random){
            Intent intent = new Intent(getApplicationContext(),
                    RandomInstructionsActivity.class);
            startActivity(intent);

        }
    }



}
