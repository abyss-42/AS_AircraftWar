package com.example.myapplication.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ModeSelectActivity;
import com.example.myapplication.R;
import com.example.myapplication.SQLite.MyDatabaseHelper;
import com.example.myapplication.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

//登录界面
public class LoginActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    private MyDatabaseHelper dbHelper;

    //设置为static方便对战的时候使用
    public static Socket socket = new Socket();

    //登录后存储用户
    public static User currentUser = null;
    public static String userNameOutput;

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

        //好像没有用，为啥呢
//        String pas = pasEditText.getText();
//        String userName = userNameEdit.getText().toString();
        getSupportActionBar().hide();

        Button register_button = findViewById(R.id.registerButton);

        //数据库
//        this.deleteDatabase("user.db");
//        dbHelper = new MyDatabaseHelper(this, "newUser.db", null, 1);

        //实现用户注册
        register_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                //获取输入，trim()去掉空格和制表符
                userNameOutput = userNameEdit.getText().toString().trim();
                String password = pasEditText.getText().toString().trim();
                //看看有没有获取到
                System.out.println("pas:" + password);
                System.out.println("userName:" + userNameOutput);

//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                if(!TextUtils.isEmpty(userNameOutput)&&!TextUtils.isEmpty(password)) {
//                    User user = new User(userNameOutput, password, 0);
//                    dbHelper.add(db, user);
//                    Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//                    currentUser = user;
//                    Intent intent = new Intent(LoginActivity.this, ModeSelectActivity.class);
//                    startActivity(intent);
//                }
//                else{
//                    Toast.makeText(LoginActivity.this,"信息不全，注册失败",Toast.LENGTH_SHORT).show();
//                }
                if (!TextUtils.isEmpty(userNameOutput) && !TextUtils.isEmpty(password)) {
                    User user = new User(userNameOutput, password, 0);
                    try {
                        //连接服务器
                        socket.connect(new InetSocketAddress
                                ("10.250.43.104",9999),5000);
                        //向服务器说明为注册模式
                        PrintWriter writer;
                        writer = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream(),"UTF-8")),true);
                        writer.println("register");
                        //将用户传给服务器
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(user);
                        Log.i("client", "send user to server");
                        //获取服务器响应，若为success则说明注册成功
                        BufferedReader in;
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        if(in.readLine().equals("success")){
                            Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            currentUser = user;
                            Intent intent = new Intent(LoginActivity.this, ModeSelectActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                        //不能关闭流，socket会随之一起关闭
//                        in.close();
//                        writer.close();
//                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(LoginActivity.this,"信息不全，注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入，trim()去掉空格和制表符
                userNameOutput = userNameEdit.getText().toString().trim();
                String password = pasEditText.getText().toString().trim();
                //看看有没有获取到
                System.out.println("pas:" + password);
                System.out.println("userName:" + userNameOutput);

//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                if(!TextUtils.isEmpty(userNameOutput)&&!TextUtils.isEmpty(password)) {
//                    //获取密码
//                    String pwd = dbHelper.getPasswordByUsername(db, userNameOutput);
//                    if(password.equals(pwd)){
//                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                        currentUser = new User(userNameOutput,password,0);
//                        Intent intent = new Intent(LoginActivity.this, ModeSelectActivity.class);
//                        startActivity(intent);
//                    }
//                    else{
//                        Toast.makeText(LoginActivity.this, "密码错误，登录失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else{
//                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
//                }
                if(!TextUtils.isEmpty(userNameOutput)&&!TextUtils.isEmpty(password)) {
                    try {
                        User user = new User(userNameOutput, password, 0);
                        //连接服务器
                        socket.connect(new InetSocketAddress
                                ("10.250.43.104",9999),5000);
                        //传递服务器登录模式
                        PrintWriter writer;
                        writer = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream(),"UTF-8")),true);
                        writer.println("login");
                        //将当前用户输入传递给服务器
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(user);
                        //获取服务器返回值，如果为success表示登录成功
                        BufferedReader in;
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        //登录成功，则将服务器存储的用户数据返回
                        if(in.readLine().equals("success")){
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            currentUser = (User)ois.readObject();
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ModeSelectActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
