package com.TiNg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow extends JFrame {

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();     //获取屏幕的尺寸
    int screenWidth = screenSize.width;             //获取屏幕的宽
    int screenHeight = screenSize.height;           //获取屏幕的高

    public FirstWindow(String windowName, int windowWidth, int windowHeight) {

        //FirstWindow主窗口
        setTitle(windowName);
        setBounds(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2, windowWidth, windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        setResizable(false);

        //顶层COM数据功能JPanel
        JPanel top1JPanel = new JPanel();
        top1JPanel.setPreferredSize(new Dimension(300, 50));
        top1JPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        top1JPanel.setBackground(Color.DARK_GRAY);
        add(top1JPanel, BorderLayout.NORTH);

        //顶层COM数据功能JPanel中添加的模块
        JComboBox<String> comList = new JComboBox<String>();
        JButton connectButton = new JButton("连接");
        JButton comflashButton = new JButton("端口刷新");
        JButton comdataSetButton = new JButton("参数设置");
        top1JPanel.add(comList);
        top1JPanel.add(comflashButton);
        top1JPanel.add(comdataSetButton);
        top1JPanel.add(connectButton);

        //顶层COM参数设置弹窗
        String comDataWindowName = "COM参数设置";
        int comDataWindowWidth = 600;
        int comDataWindowHeight = 200;
        NewWindow comDataWindow = new NewWindow(comDataWindowName, comDataWindowWidth, comDataWindowHeight);

        //顶层COM参数设置弹窗按钮实现
        comdataSetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comDataWindow.setVisible(true);
            }
        });

        //FirstWindow主窗口实现
        setVisible(true);
    }
}