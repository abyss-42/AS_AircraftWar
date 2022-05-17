package com.example.myapplication.factory;

import com.example.myapplication.prop.BaseProp;
import com.example.myapplication.prop.PropBlood;

/**加血道具工厂*/
public class PropBloodFactory implements PropFactory{
    @Override
    public BaseProp createProp(int locationX, int locationY){
        return new PropBlood(locationX, locationY, 0, 5);
    }
}
