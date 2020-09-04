package com.TiNg;

import javax.swing.*;
import java.awt.*;

public class NewWindow extends JFrame {

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();     //获取屏幕的尺寸
    int screenWidth = screenSize.width;             //获取屏幕的宽
    int screenHeight = screenSize.height;           //获取屏幕的高

    public NewWindow(String windowName, int windowWidth, int windowHeight, String filename) {
        setTitle(windowName);
        setBounds(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2, windowWidth, windowHeight);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setResizable(false);
        setVisible(false);
        Toolkit tool = this.getToolkit();
        Image icon = tool.getImage(filename);
        this.setIconImage(icon);
    }
}
