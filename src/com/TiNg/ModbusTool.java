package com.TiNg;

import java.awt.*;

public class ModbusTool {
    public static void main(String[] args) {
        String windowName = "参数功能设置  -v0.3.5";
        int windowWidth = 710;
        int windowHeight = 450;
        FirstWindow firstWindow = new FirstWindow(windowName, windowWidth, windowHeight);
        Toolkit tool=firstWindow.getToolkit();
        Image icon=tool.getImage("D:\\Documents\\JAVA\\ModbusTool\\res\\Control Panel.png");
        firstWindow.setIconImage(icon);
    }
}