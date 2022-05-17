package com.example.myapplication.dao;


/**
 * 数据访问对象接口
 */
public interface RecordDao {
    /**
     * 将新产生的记录写入文件中
     */
    public void addRecord(Record record);
    /**
     * 打印得分排行榜
     */
    public String[][] printRecord();
    /**
     * 将记录删除
     */
    public void deleteRecord(int index);
}
