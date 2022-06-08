package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.rank.RankActivity;

public class ModeSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        Button singleButton = findViewById(R.id.singleButton);

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelectActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        Button multiButton = findViewById(R.id.multiButton);

        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelectActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }



}