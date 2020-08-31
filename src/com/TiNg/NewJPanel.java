package com.TiNg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewJPanel extends JPanel {
    JLabel jLabel1;
    int i;
    JTextField jTextField;
    DataTreat dataTreat = new DataTreat();

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
                if (modbus.ModbusisConnected()) {
                    try {
                        int a = Integer.valueOf(jTextField.getText());
                        if (a < 32768) {
                            modbus.ModbuswriteSingleRegister(slaveId, i, a);
                            modbus.ModbuswriteSingleRegister(slaveId, i + 1, 0);
                            jLabel2.setText(jLabelName + "---设置成功");
                        } else {
                            if (a < 65536) {
                                modbus.ModbuswriteSingleRegister(slaveId, i, a);
                                modbus.ModbuswriteSingleRegister(slaveId, i + 1, 0);
                                jLabel2.setText(jLabelName + "---设置成功");
                            } else {
                                modbus.ModbuswriteSingleRegister(slaveId, i, dataTreat.tenToBinary(a)[0]);
                                modbus.ModbuswriteSingleRegister(slaveId, i + 1, dataTreat.tenToBinary(a)[1]);
                                jLabel2.setText(jLabelName + "---设置成功");
                            }
                        }
                    } catch (Exception e1) {
                        jLabel2.setText(jLabelName + "---设置失败");
                        e1.printStackTrace();
                    }
                } else {
                    jLabel2.setText("端口未连接");
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
