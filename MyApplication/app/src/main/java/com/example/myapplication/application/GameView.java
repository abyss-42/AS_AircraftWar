package com.example.myapplication.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.myapplication.GameActivity;
import com.example.myapplication.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Bitmap bitmap;

    public GameView(Context context) {
        super(context);
//        ImageManager imageManager = new ImageManager(context);
        //获取图片
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
        //解决长宽不匹配问题
        bitmap = Bitmap.createScaledBitmap(bitmap, GameActivity.WINDOW_WIDTH, GameActivity.WINDOW_HEIGHT, true);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void run() {
        while (true){
            draw();
        }
    }

    public void draw(){
        //获取画布对象
        canvas = surfaceHolder.lockCanvas();
        //绘制获取的图片
        canvas.drawBitmap(bitmap, 0, 0, null);
        //绘图
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
