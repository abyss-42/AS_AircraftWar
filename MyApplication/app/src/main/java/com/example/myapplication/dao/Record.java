package com.example.myapplication.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 记录数据类
 * 记录每局游戏结束后的分数、时间、用户名、日期
 */
public class Record implements Serializable,Comparable<Record> {
    private String name;
    private int score,hour,minute;
    private String date;

    public Record(String name,int score){
        this.name = name;
        this.score = score;
        date = LocalDate.now().toString();
        hour = LocalTime.now().getHour();
        minute = LocalTime.now().getMinute();
    }

    public String getName(){
        return this.name;
    }

    public int getHour(){
        return this.hour;
    }

    public int getScore() {
        return score;
    }

    public int getMinute() {
        return minute;
    }

    public String getDate() {
        return date;
    }

   @Override
    public int compareTo(Record o){
        return o.score-this.score;
   }
}
