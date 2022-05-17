package com.example.myapplication.factory;

import com.example.myapplication.prop.BaseProp;
import com.example.myapplication.prop.PropImmune;

public class PropImmuneFactory implements PropFactory{
    @Override
    public BaseProp createProp(int locationX, int locationY){return new PropImmune(locationX,locationY,0,5);}
}
