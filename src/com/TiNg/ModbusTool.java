package com.TiNg;

import java.awt.*;

public class ModbusTool {
    public static void main(String[] args) {
        String windowName = "参数功能设置 v0.5.3";
        int windowWidth = 770;
        int windowHeight = 450;
        FirstWindow firstWindow = new FirstWindow(windowName, windowWidth, windowHeight);
        Toolkit tool=firstWindow.getToolkit();
        Image icon=tool.getImage("res\\tools-icon.png");
        firstWindow.setIconImage(icon);
    }
}