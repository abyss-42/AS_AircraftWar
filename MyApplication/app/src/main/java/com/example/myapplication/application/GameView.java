package com.example.myapplication.application;

import static com.example.myapplication.MainActivity.WINDOW_HEIGHT;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapplication.GameActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.aircraft.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.myapplication.R;
import com.example.myapplication.basic.AbstractFlyingObject;
import com.example.myapplication.bullet.BaseBullet;
import com.example.myapplication.bullet.EnemyBullet;
import com.example.myapplication.factory.BaseEnemyFactory;
import com.example.myapplication.factory.EliteEnemyFactory;
import com.example.myapplication.factory.MobEnemyFactory;
import com.example.myapplication.Prop.BaseProp;
import com.example.myapplication.Prop.PropBomb;
import com.example.myapplication.Prop.PropBullet;
import com.example.myapplication.Prop.PropImmune;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint mPaint;
    protected int backGroundTop = 0;
    private ImageManager imageManager;


    /**判断boss是否已经出现*/
    protected boolean bossFlag = false;
    protected boolean isGameOverFlag;

    protected int timeCycleCount = 0;



    /**
     * Scheduled 线程池，用于任务调度
     */
//    private final ScheduledExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<BaseEnemy> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;

    private int enemyMaxNumber = 4;

    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     * 英雄机子弹发射频率与敌机不一样
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private int heroAircraftShootCycle = 400;
    private int heroCycleTime = 0;
    /**
     * 决定是否开启音效
     */
    private static boolean musicTurnOn = false;
//    private LoopMusicThread backGroundMusic;


    public GameView(Context context) {
        super(context);
        imageManager = new ImageManager(context);
        //获取图片
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
        //解决长宽不匹配问题
        bitmap = Bitmap.createScaledBitmap(bitmap, GameActivity.WINDOW_WIDTH, GameActivity.WINDOW_HEIGHT, true);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        heroAircraft = HeroAircraft.getHeroAircraft();
        mPaint = new Paint();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
//        this.executorService = new ScheduledThreadPoolExecutor(1,
//                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
//        this.scheduledExecutorService = Executors.newScheduledThreadPool(1, new BasicThreadFactory.DefaultThreadBuilder().namingPattern("game-action-%d").daemon(true).build());
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

        //启动英雄机鼠标监听
        System.out.println("hero");
        new HeroController(this, heroAircraft);
    }

    public static boolean isMusicTurnOn() {
        return musicTurnOn;
    }

    public int getScore() {
        return score;
    }
    //设置背景音乐
    public static void setMusic() {
        musicTurnOn = true;
    }
    //关闭背景音乐
    public static void closeMusic(){
        musicTurnOn = false;
    }
    //设置除boss机外的敌机最大数量
    public void setEnemyMaxNumber(int enemyMaxNumber) {
        this.enemyMaxNumber = enemyMaxNumber;
    }
    //设置敌机产生周期
    public void setCycleDuration(int cycleDuration) {
        this.cycleDuration = cycleDuration;
    }

    public void setBossFlag(boolean bossFlag) {
        this.bossFlag = bossFlag;
    }

    public List<BaseEnemy> getEnemyAircrafts() {
        return enemyAircrafts;
    }

    public void closeBossMusic(){};

    /**
     * 按一定概率创建普通敌机和精英敌机
     */
    //把Simple的复制过来当默认了((
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
    };


    /**
     * 游戏启动入口，执行游戏逻辑
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public final void action() throws InterruptedException{

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            System.out.println("In Runnable Task~~~~~~~~~~~~~~~");

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    enemyAircrafts.add(createEnemy());
                }
                // 飞机射出子弹
                enemyShootAction();
            }

            if(heroTimeCountAndNewCycleJudge()){
                heroShootAction();
            }
            //Boss机出现检测
            createBossAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
//            repaint();
            draw();
            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                //关闭背景音乐、如果敌机存在，关闭敌机音乐
//                if(musicTurnOn){
//                    backGroundMusic.setJump();
//                    backGroundMusic.setStop();
//                    if(bossFlag){
//                        closeBossMusic();
//                    }
//                    new MusicThread("src/videos/game_over.wav").start();
//                }
//                executorService.shutdown();
                scheduledExecutorService.shutdown();
                gameOverFlag = true;
                PropBullet.setJump();
                PropImmune.setJump();
                System.out.println("Game Over!");
//                synchronized (MainActivity.lock){
//                    MainActivity.lock.notifyAll();
//                }
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
//        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
//        if(musicTurnOn){
//            backGroundMusic = new LoopMusicThread("src/videos/bgm.wav");
//            backGroundMusic.start();
//        }
        scheduledExecutorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
//            Thread thread = new Thread(task);
//            thread.start();
//
//            Thread.sleep(timeInterval);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            timeCycleCount+=1;
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean heroTimeCountAndNewCycleJudge() {
        heroCycleTime += timeInterval;
        if (heroCycleTime >= heroAircraftShootCycle && heroCycleTime - timeInterval < heroCycleTime) {
            // 跨越到新的周期
            heroCycleTime %= heroAircraftShootCycle;
            return true;
        } else {
            return false;
        }
    }

    private void enemyShootAction() {
        // TODO 敌机射击
        for(BaseEnemy enemy : enemyAircrafts){
            List<BaseBullet> bullets = enemy.executeShoot();
            if(bullets.size() != 0){
                enemyBullets.addAll(bullets);
            }
        }

    }
    // 英雄射击
    private void heroShootAction(){
        heroBullets.addAll(heroAircraft.executeShoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (BaseEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction(){
        for (BaseProp prop : props){
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet enemybullet : enemyBullets) {
            if (enemybullet.notValid()) {
                continue;
            }
            if(heroAircraft.crash(enemybullet) && !heroAircraft.isImmune()){
                // 英雄机撞击到精英机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(enemybullet.getPower());
                enemybullet.vanish();
            }else if(heroAircraft.crash(enemybullet) && heroAircraft.isImmune()){
                enemybullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (BaseEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
//                    if(musicTurnOn){
//                        new MusicThread("src/videos/bullet_hit.wav").start();
//                    }
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (enemyAircraft instanceof Boss){
                            bossFlag = false;
                            if(musicTurnOn){
                                closeBossMusic();
                            }
                        }
                        List<BaseProp> prop = enemyAircraft.dropProp();
                        if(prop != null){
                            props.addAll(prop);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (BaseProp prop : props){
            if (heroAircraft.crash(prop)){
//                if(musicTurnOn){
//                    new MusicThread("src/videos/get_supply.wav").start();
//                }
                if(prop instanceof PropBomb){
                    for(BaseEnemy enemyAircraft:enemyAircrafts){
                        ((PropBomb) prop).addSubscriber(enemyAircraft);
                    }
                    for(BaseBullet enemyBullet:enemyBullets){
                        ((PropBomb) prop).addSubscriber((EnemyBullet)enemyBullet);
                    }
                }
                prop.propFunction(heroAircraft);
                prop.vanish();
            }
        }

    }

    /**
     * Boss机检测：
     * 得分到达一定值且当前没有boss机则产生boss
     */
    public void createBossAction(){};

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * 4. 删除已经生效的道具
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        for(BaseEnemy enemy:enemyAircrafts){
            if(enemy.getHp()==0){
                score+=10;
            }
        }
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }





    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        new Thread(this).start();
        System.out.println("In SurfaceCreated func~~~~~~~~~~~~");
        isGameOverFlag = false;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        isGameOverFlag = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run(){
        System.out.println("In Run func11111111~~~~~~~~~~~~");
        try {
            action();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
//        while(!isGameOverFlag) {
//            System.out.println("In Run func22222222222~~~~~~~~~~~~");
//            synchronized (surfaceHolder) {
//                try {
//                    System.out.println("Draw on~~~~~~~~~~~~~~~~~~~");
//                    action();
//                    draw();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
    //***********************
    //      Paint 各部分
    //***********************

    public void paintBackground(Paint mPaint, Canvas canvas){
        //绘制滚动背景图片
        System.out.println("Draw on~~~~~~~~~~~~~~~~~~~");
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, backGroundTop-GameActivity.WINDOW_HEIGHT, mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, backGroundTop,mPaint);
        backGroundTop+=1;
        if(backGroundTop == WINDOW_HEIGHT);
            backGroundTop = 0;

    }

    public void draw(){
        //获取画布对象
        canvas = surfaceHolder.lockCanvas();
        //绘制获取的图片

        if(surfaceHolder == null || canvas == null){
            return;
        }

        paintBackground(mPaint, canvas);
        System.out.println("In Draw func~~~~~~~~~~~~~~~~~~~");
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(mPaint, enemyBullets);
        paintImageWithPositionRevised(mPaint, heroBullets);
        paintImageWithPositionRevised(mPaint, props);
        paintImageWithPositionRevised(mPaint, enemyAircrafts);
        System.out.println("draw plane~~~~~~~~~~~~~~~~~~~");

        //绘制英雄机
        canvas.drawBitmap(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2, heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, mPaint);
        //绘制得分
        paintScoreAndLife(mPaint);
        //绘图
        surfaceHolder.unlockCanvasAndPost(canvas);

    }

    private void paintImageWithPositionRevised(Paint mPaint, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (int i = 0; i<objects.size();  i++) {
            Bitmap image = objects.get(i).getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, objects.get(i).getLocationX() - image.getWidth()/2, objects.get(i).getLocationY() - image.getHeight() / 2, mPaint);

        }
    }

    private void paintScoreAndLife(Paint mPaint) {
        int x = 10;
        int y = 25;
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(22);
        mPaint.setTypeface(Typeface.SANS_SERIF);
        mPaint.setFakeBoldText(true);
//        mPaint.setFont(new Font("SansSerif", Font.BOLD, 22));
        canvas.drawText("SCORE:" + this.score, x, y, mPaint);
        y = y + 20;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);
    }
    public int getBackGroundTop() {
        return backGroundTop;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return true;
    }

}
