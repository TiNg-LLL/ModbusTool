package com.TiNg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewJPanel extends JPanel {
    JLabel jLabel1;
    int i;
    JTextField jTextField;

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, int addressData, Modbus modbus, int slaveId, JLabel jLabel2) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        setBackground(Color.LIGHT_GRAY);
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        JTextField jTextField = new JTextField(6);
        JButton jButton = new JButton("设置");
        jLabel1 = new JLabel("当前值:                         ");
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 12));
        add(jLabel);
        add(jTextField);
        add(jButton);
        add(jLabel1);
        jPanel.add(this);
        i = addressData;

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    modbus.ModbuswriteSingleRegister(slaveId, i, Integer.valueOf(jTextField.getText()));
                    jLabel2.setText(jLabelName + "---设置成功");
                } catch (Exception e1) {
                    jLabel2.setText(jLabelName + "---设置失败");
                }

            }
        });


    }

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, NewJPanel newJPanel, JLabel jLabel2) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        setBackground(Color.LIGHT_GRAY);
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        jTextField = new JTextField(6);
        jTextField.setText(String.valueOf(newJPanel.getAddress()));
        JButton jButton = new JButton("设置");
        add(jLabel);
        add(jTextField);
        add(jButton);
        jPanel.add(this);

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newJPanel.changeAddress(Integer.valueOf(jTextField.getText()));
            }
        });
    }

    public JLabel setNowData() {
        return jLabel1;
    }

    public int getAddress() {
        return i;
    }

    public void changeAddress(int i) {
        this.i = i;
    }
}
