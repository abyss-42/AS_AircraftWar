package com.example.myapplication.gui;
//
//import com.example.myapplication.application.Game;
//import com.example.myapplication.application.Main;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//
//class SelectAction implements ActionListener{
//    private boolean flag = false;//限制按钮点击只能唤醒一次
//    @Override
//    public void actionPerformed(ActionEvent event) {
//        synchronized (Main.lock) {
//            JButton button = (JButton) event.getSource();
//            Main.whichMode = button.getText();
//            if(!flag) {
//                flag = true;
//                Main.lock.notifyAll();
//            }
//        }
//    }
//
//}
//
//public class Menu extends JPanel{
//    private JButton easyButton = new JButton("简单模式");
//    private JButton normalButton = new JButton("普通模式");
//    private JButton difficultButton = new JButton("困难模式");
//    private JCheckBox checkBox = new JCheckBox("音效");
//
//    public Menu(){
//        super(new GridLayout(1, 3));
//        Font font = new Font("宋体",Font.PLAIN,20);
//        GridLayout layout = new GridLayout(5,3,60,60);
//        JPanel panel = new JPanel(layout);
//        checkBox.setFont(font);
//        easyButton.setFont(font);
//        normalButton.setFont(font);
//        difficultButton.setFont(font);
//        panel.add(new JPanel());
//        panel.add(easyButton);
//        panel.add(normalButton);
//        panel.add(difficultButton);
//        panel.add(checkBox);
//        add(new JPanel());
//        add(panel);
//        add(new JPanel());
//    }
//
//    public void selectDiff() {
//        //添加监听器
//        Runnable r = () -> {
//            easyButton.addActionListener(new SelectAction());
//            normalButton.addActionListener(new SelectAction());
//            difficultButton.addActionListener(new SelectAction());
//            checkBox.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    JCheckBox box = (JCheckBox) e.getSource();
//                    if(box.isSelected()){
//                        Game.setMusic();
//                    }else{
//                        Game.closeMusic();
//                    }
//                }
//            });
//        };
//        new Thread(r).start();
//    }
//
//}

