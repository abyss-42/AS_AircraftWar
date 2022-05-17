package com.example.myapplication.application;

import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.example.myapplication.aircraft.BaseEnemy;
import com.example.myapplication.factory.BaseEnemyFactory;
import com.example.myapplication.factory.EliteEnemyFactory;
import com.example.myapplication.factory.MobEnemyFactory;

import java.awt.*;
import java.util.Random;

public class SimpleGame extends Game{

    public SimpleGame(){
        super();
        System.out.println("简单模式，周期600ms，加血道具概率0.15，火力道具概率0.15，免疫道具概率0.15，炸弹道具概率0.1");
    }

    /**精英机掉落
     * 按8:2概率创建普通敌机和精英敌机
     */
    @Override
    public BaseEnemy createEnemy(){
        Random propR = new Random();
        float whichProp;
        whichProp = propR.nextFloat();
        BaseEnemyFactory enemyFactory;
        if(whichProp<=0.2){
            enemyFactory = new EliteEnemyFactory();
        }else{
            enemyFactory = new MobEnemyFactory();
        }
        enemyFactory.setHp(30);
        return enemyFactory.createEnemy();
    }
    @Override
    public void paintBackground(Graphics g){
        g.drawImage(ImageManager.BACKGROUND_IMAGE_SIMPLE, 0, super.getBackGroundTop() - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE_SIMPLE, 0, super.getBackGroundTop(), null);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {

    }
}
