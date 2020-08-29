package com.TiNg;

import javax.swing.*;
import java.awt.*;

public class NewJPanel extends JPanel {
    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 2));
        setBackground(Color.LIGHT_GRAY);
        JLabel jLabel = new JLabel(jLabelName);
        JTextField jTextField = new JTextField(6);
        JButton jButton = new JButton("设置");
        JLabel jLabel1 = new JLabel("当前值:                         ");
        add(jLabel);
        add(jTextField);
        add(jButton);
        add(jLabel1);
        jPanel.add(this);
    }
}
