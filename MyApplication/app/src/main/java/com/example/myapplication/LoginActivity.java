package com.example.myapplication;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
//登录界面
public class LoginActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_login);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
        PwdEdit pasEditText;
        EditText userNameEdit;

        pasEditText = findViewById(R.id.ed_pas);
        userNameEdit = findViewById(R.id.userName);
        pasEditText.setEditTextHint(getResources().getString(R.string.ed_hint_pas));

//        获得输入内容
        String pas = pasEditText.getText();
        String userName = userNameEdit.getText().toString();
        getSupportActionBar().hide();
    }
}
