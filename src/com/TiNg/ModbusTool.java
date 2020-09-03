package com.TiNg;

import java.awt.*;

public class ModbusTool {
    public static void main(String[] args) {
        String windowName = "参数功能设置 v0.4.2";
        int windowWidth = 730;
        int windowHeight = 450;
        FirstWindow firstWindow = new FirstWindow(windowName, windowWidth, windowHeight);
        Toolkit tool=firstWindow.getToolkit();
        Image icon=tool.getImage("res\\Control Panel.png");
        firstWindow.setIconImage(icon);
    }
}