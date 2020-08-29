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
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        setResizable(false);

//顶部-------------------------------------------------------------------------------------------------------------------

        //顶部COM数据功能JPanel
        JPanel top1JPanel = new JPanel();
        top1JPanel.setPreferredSize(new Dimension(300, 50));
        top1JPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        top1JPanel.setBackground(Color.DARK_GRAY);
        add(top1JPanel, BorderLayout.NORTH);

        //顶部COM数据功能JPanel中添加的模块
        JComboBox<String> comList = new JComboBox<String>();
        JButton connectButton = new JButton("连接");
        JButton comflashButton = new JButton("端口刷新");
        JButton comDataSetButton = new JButton("参数设置");
        top1JPanel.add(comList);
        top1JPanel.add(comflashButton);
        top1JPanel.add(comDataSetButton);
        top1JPanel.add(connectButton);

        //顶部COM参数设置弹窗
        String comDataWindowName = "COM参数设置";
        int comDataWindowWidth = 600;
        int comDataWindowHeight = 200;
        NewWindow comDataWindow = new NewWindow(comDataWindowName, comDataWindowWidth, comDataWindowHeight);

        //顶部COM参数设置弹窗按钮实现
        buttonAddAction(comDataSetButton, comDataWindow);

//中部-------------------------------------------------------------------------------------------------------------------

        //中部主功能JPanel
        JPanel middle1JPanel = new JPanel();
        middle1JPanel.setPreferredSize(new Dimension(300, 50));
        middle1JPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        middle1JPanel.setBackground(Color.GRAY);
        add(middle1JPanel, BorderLayout.CENTER);

        //中部主功能二级JPanel1
        JPanel middle2JPanel1 = new JPanel();
        middle2JPanel1.setPreferredSize(new Dimension(350, 335));
        middle2JPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        middle2JPanel1.setBackground(Color.GREEN);
        middle1JPanel.add(middle2JPanel1, BorderLayout.CENTER);


        //中部主功能三级JPanel
        NewJPanel middle3JPanel1 = new NewJPanel(middle2JPanel1, 340, 30, "目标速度");
        NewJPanel middle3JPanel2 = new NewJPanel(middle2JPanel1, 340, 30, "目标加减速度");
        NewJPanel middle3JPanel3 = new NewJPanel(middle2JPanel1, 340, 30, "目标位置1");
        NewJPanel middle3JPanel4 = new NewJPanel(middle2JPanel1, 340, 30, "目标位置2");
        NewJPanel middle3JPanel5 = new NewJPanel(middle2JPanel1, 340, 30, "目标位置3");
        NewJPanel middle3JPanel6 = new NewJPanel(middle2JPanel1, 340, 30, "目标位置4");
        NewJPanel middle3JPanel7 = new NewJPanel(middle2JPanel1, 340, 30, "目标位置5");
        NewJPanel middle3JPanel8 = new NewJPanel(middle2JPanel1, 340, 30, "目标位置6");
        JButton addressDataSetButton = new JButton("地址参数设置");
        middle2JPanel1.add(addressDataSetButton);

        //中部地址参数设置弹窗
        String addressDataWindowName = "地址参数设置";
        int addressDataWindowWidth = 600;
        int addressDataWindowHeight = 200;
        NewWindow addressDataWindow = new NewWindow(addressDataWindowName, addressDataWindowWidth, addressDataWindowHeight);

        //中部地址参数设置弹窗按钮实现
        buttonAddAction(addressDataSetButton, addressDataWindow);

        //中部主功能二级JPanel2
        JPanel middle2JPanel2 = new JPanel();
        middle2JPanel2.setPreferredSize(new Dimension(250, 300));
        middle2JPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        middle2JPanel2.setBackground(Color.BLUE);
        middle1JPanel.add(middle2JPanel2, BorderLayout.CENTER);

        //中部主功能二级JPanel3
        JPanel middle2JPanel3 = new JPanel();
        middle2JPanel3.setPreferredSize(new Dimension(250, 300));
        middle2JPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        middle2JPanel3.setBackground(Color.MAGENTA);
        middle1JPanel.add(middle2JPanel3, BorderLayout.CENTER);

//扫尾工作---------------------------------------------------------------------------------------------------------------
        //FirstWindow主窗口实现
        setVisible(true);
    }

    public void buttonAddAction(JButton jButton, NewWindow newWindow) {
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newWindow.setVisible(true);
            }
        });
    }
}