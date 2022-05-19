package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;

        Button start = findViewById(R.id.start);
        Button login = findViewById(R.id.Login);
        Button rank = findViewById(R.id.Rank);
        getSupportActionBar().hide();
    }

    public void startGame(View view){
        //界面切换
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void LoginGame(View view){

    }

    public void ShowRankTable(View view){

    }

//    public void onClick(View view){
//        Intent intent;
//        switch (view.getId()){
//            case R.id.start:
//                intent = new Intent(this, GameActivity.class);
//                startActivity(intent);
//                break;
//            case  R.id.Login:
//                break;
//            case R.id.Rank:
//                break;
//            default:
//                break;
//        }
//    }

}