package com.example.myapplication.factory;

import com.example.myapplication.prop.BaseProp;

public interface PropFactory {
    public BaseProp createProp(int locationX, int locationY);
}
