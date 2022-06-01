package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.myapplication.application.GameView;
import com.example.myapplication.login.LoginActivity;
import com.example.myapplication.market.MarketActivity;

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
        getSupportActionBar().hide();
    }

    public void startGame(View view){
        //界面切换
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void LoginGame(View view){
        Intent intent = new Intent(this, MarketActivity.class);
        startActivity(intent);
    }

    public void RegisterGame(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onCheckboxClicked(View view){
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            GameView.setMusic();
        }
        else{
            GameView.closeMusic();
        }
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