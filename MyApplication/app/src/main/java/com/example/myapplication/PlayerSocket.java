package com.example.myapplication;

import com.example.myapplication.application.GameView;
import com.example.myapplication.application.InternetGameView;
import com.example.myapplication.login.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

public class PlayerSocket implements Runnable{
    private InternetUser player = new InternetUser(LoginActivity.currentUser,0);
    @Override
    public void run(){
        Socket socket=null;
        InetAddress serverIP= null;
        try {
            serverIP = InetAddress.getByName("10.250.43.104");

            while (true) {
                socket = new Socket(serverIP, 8888);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String id = null;
                while ((id=in.readLine())==null){
                    in.readLine();
                }
                if(id=="1"){
                    player.setID(1);
                }else if(id=="2"){
                    player.setID(2);
                }
                while (!Objects.equals(in.readLine(), "connected")){
                    in.readLine();
                }
                //传递当前用户
                OutputStream os=socket.getOutputStream();
                ObjectOutputStream oos=new ObjectOutputStream(os);
                oos.writeObject(player);

                os.flush();
                socket.shutdownOutput();
                //更新当前用户
                if(ModeSelectActivity.gameView!=null){
                    player.setSocre(ModeSelectActivity.gameView.getScore());
                    player.setLife(ModeSelectActivity.gameView.getLife());
                }
                //更新对方用户
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
                InternetUser otherPlayer = (InternetUser) ois.readObject();
                ModeSelectActivity.gameView.otherUser = otherPlayer;

                socket.close();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
