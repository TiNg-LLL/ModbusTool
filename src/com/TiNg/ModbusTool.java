package com.TiNg;

import java.awt.*;

public class ModbusTool {
    public static void main(String[] args) {
        String windowName = "参数功能设置 v0.6.1";
        int windowWidth = 785;
        int windowHeight = 460;
        FirstWindow firstWindow = new FirstWindow(windowName, windowWidth, windowHeight);
        //窗口图标
        Toolkit tool = firstWindow.getToolkit();
        Image icon = tool.getImage("res\\tools-icon.png");
        firstWindow.setIconImage(icon);
    }
}