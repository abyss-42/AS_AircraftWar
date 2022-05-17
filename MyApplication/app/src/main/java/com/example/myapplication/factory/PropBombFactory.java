package com.example.myapplication.factory;

import com.example.myapplication.prop.BaseProp;
import com.example.myapplication.prop.PropBomb;

/**炸弹道具工厂*/
public class PropBombFactory implements PropFactory{
    @Override
    public BaseProp createProp(int locationX, int locationY){
        return new PropBomb(locationX, locationY, 0, 5);
    }
}
