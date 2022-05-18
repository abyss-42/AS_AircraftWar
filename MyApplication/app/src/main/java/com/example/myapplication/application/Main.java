package com.example.myapplication.application;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.gui.Menu;
import com.example.myapplication.gui.ScorePanel;

import javax.swing.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main extends AppCompatActivity {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object lock = new Object();
    /**
     * 确定哪种模式，在菜单的监听事件中将其修改
     */
    public static String whichMode = null;
    public static int score = 0;

    public void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //通过线程锁的方式切换屏幕
        synchronized (lock){
            Menu menu = new Menu();
            frame.setContentPane(menu);
            frame.setVisible(true);
            menu.selectDiff();
            try {
                lock.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            GameView game;
            if(whichMode == "简单模式"){
                game = new SimpleGameView(this);
            }else if(whichMode == "普通模式"){
                game = new NormalGameView(this);
            }else {
                game = new DifficultGameView(this);
            }
            frame.remove(menu);
            frame.setContentPane(game);
            frame.setVisible(true);
            game.action();
            try{
                lock.wait();
                score=game.getScore();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            frame.remove(game);
            ScorePanel scorePanel = new ScorePanel();
            frame.setContentPane(scorePanel);
            frame.setVisible(true);
            scorePanel.scoreAction();
        }

    }
}
