package com.example.myapplication.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.example.myapplication.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager extends SurfaceView {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND_IMAGE;
    public static Bitmap BACKGROUND_IMAGE_SIMPLE;
    public static Bitmap BACKGROUND_IMAGE_DIFFICULT;
    public static Bitmap BACKGROUND_IMAGE_NORMAL;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_IMAGE;
    public static Bitmap PROP_BLOOD_IMAGE;
    public static Bitmap PROP_BOMB_IMAGE;
    public static Bitmap PROP_BULLET_IMAGE;
    public static Bitmap PROP_IMMUNE_IMAGE;

    //不用decodeFile，因为参数直接传路径的话会被认为不安全，抛出异常FileNotFoundException

    public ImageManager(Context context) {
        super(context);
        BACKGROUND_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        BACKGROUND_IMAGE_SIMPLE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg2);
        BACKGROUND_IMAGE_DIFFICULT = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg5);
        BACKGROUND_IMAGE_NORMAL = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg4);
        HERO_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero);
        MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.mob);
        HERO_BULLET_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_hero);
        ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_enemy);
        ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.elite);
        BOSS_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss);
        PROP_BLOOD_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_blood);
        PROP_BOMB_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bomb);
        PROP_BULLET_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bullet);
        PROP_IMMUNE_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_immune);
    }

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }
}