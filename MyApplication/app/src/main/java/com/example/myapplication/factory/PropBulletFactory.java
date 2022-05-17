package com.example.myapplication.factory;

import com.example.myapplication.prop.BaseProp;
import com.example.myapplication.prop.PropBullet;

/**火力道具工厂*/
public class PropBulletFactory implements PropFactory{
    @Override
    public BaseProp createProp(int locationX, int locationY){
        return new PropBullet(locationX, locationY, 0, 5);
    }
}
