package com.example.myapplication.gui;

import com.example.myapplication.application.DifficultGame;
import com.example.myapplication.application.Main;
import com.example.myapplication.application.NormalGame;
import com.example.myapplication.application.SimpleGame;
import com.example.myapplication.dao.Record;
import com.example.myapplication.dao.RecordDaoImple;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScorePanel extends JPanel{
    private static RecordDaoImple recordDaoImple;
    private JButton button = new JButton("删除");
    private JTable scoreTable = new JTable();
    private JScrollPane tableScrollPanel = new JScrollPane();
    private static DefaultTableModel model;

    public static void monitor(JTable scoreTable, JScrollPane tableScrollPanel){
        String[] columnName = {"名次","玩家名","得分","记录时间"};
        if(Main.whichMode == "简单模式"){
            recordDaoImple = new RecordDaoImple("easyRecords.txt");
        }else if(Main.whichMode == "普通模式"){
            recordDaoImple = new RecordDaoImple("normalRecords.txt");
        }else {
            recordDaoImple = new RecordDaoImple("difficultRecords.txt");
        }
        String[][]tableData=recordDaoImple.printRecord();
        model = new DefaultTableModel(tableData, columnName){
            @Override public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
    }

    //初始化
    public ScorePanel(){
        super(new BorderLayout());
        JLabel title = new JLabel("难度",SwingConstants.LEFT);
        title.setText("难度："+Main.whichMode);
        title.setFont(new Font("宋体",Font.PLAIN,20));
        JLabel label = new JLabel("排行榜",SwingConstants.CENTER);
        label.setFont(new Font("宋体",Font.PLAIN,20));
        label.setForeground(Color.blue);
        JPanel paintScore = new JPanel(new BorderLayout());
        button.setFont(new Font("宋体",Font.PLAIN,20));
        ScorePanel.monitor(scoreTable, tableScrollPanel);
        paintScore.add(label, BorderLayout.NORTH);
        paintScore.add(tableScrollPanel, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
        add(paintScore, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }

    public void scoreAction(){
        JPanel panel = this;
        String name = JOptionPane.showInputDialog(this, "输入用户名以保存得分记录,你的得分为"+Main.score);
        //获取用户输入并加入排行榜中
        if(name!=null && !name.equals("")){
            Record record = new Record(name, Main.score);
            recordDaoImple.addRecord(record);
            ScorePanel.monitor(scoreTable, tableScrollPanel);
        }
        Runnable r = () ->{
            //给删除键添加事件监听
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = scoreTable.getSelectedRow();
                    if(row != -1){
                        //弹出确认窗口
                        int select = JOptionPane.showConfirmDialog(panel,"确定删除吗","删除",JOptionPane.YES_NO_CANCEL_OPTION);
                        if(select == 0){
                            recordDaoImple.deleteRecord(row);
                            ScorePanel.monitor(scoreTable, tableScrollPanel);
                        }

                    }
                }
            });

        };
        new Thread(r).start();
    }

}
