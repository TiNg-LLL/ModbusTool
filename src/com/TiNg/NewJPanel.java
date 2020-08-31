package com.TiNg;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class NewJPanel extends JPanel {
    JLabel jLabel1;
    int i;
    JTextField jTextField;
    DataTreat dataTreat = new DataTreat();
    JComboBox<String> jComboBox = new JComboBox<String>();
    int choose;
    String mory;
    JButton jButton;
    MouseAdapter mouseAdapter;
    ActionListener actionListener;

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, int addressData, Modbus modbus, int slaveId, JLabel jLabel2) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
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
        this.changeAddress(addressData);

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
        /**setBackground(Color.LIGHT_GRAY);*/
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

    public NewJPanel(JPanel jPanel, int width, int height, String jButtonName, int addressData, Modbus modbus, int slaveId, JLabel jLabel2, int choose, String mory) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        jButton = new JButton(jButtonName);
        jLabel1 = new JLabel("当前值：");
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 12));
        add(jButton);
        add(jLabel1);
        jPanel.add(this);
        this.mory = mory;
        if (mory.equals("M")) {
            i = addressData;
        } else {
            i = addressData + 18432;
        }
        this.choose = choose;

        mouseAdapter = new MouseInputAdapter() {
            public void mousePressed(MouseEvent e) {
                modbus.ModbuswritetrueMultipleCoils(slaveId, i);
            }

            public void mouseReleased(MouseEvent e) {
                modbus.ModbuswritefalseMultipleCoils(slaveId, i);
            }
        };

        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean[] b = modbus.ModbusreadCoils(slaveId, i, 1);
                if (b[0]) {
                    modbus.ModbuswritefalseMultipleCoils(slaveId, i);
                } else {
                    modbus.ModbuswritetrueMultipleCoils(slaveId, i);
                }
            }
        };
    }

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, NewJPanel newJPanel, JLabel jLabel2, String meiluanyong) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        jComboBox.addItem("M");
        jComboBox.addItem("Y");
        jTextField = new JTextField(6);
        if (newJPanel.getMory().equals("M")) {
            jComboBox.setSelectedIndex(0);
            jTextField.setText(String.valueOf(newJPanel.getAddress()));
        } else {
            jComboBox.setSelectedIndex(1);
            jTextField.setText(String.valueOf(newJPanel.getAddress() - 18432));
        }
        JButton jButton1 = new JButton("设置");
        JComboBox choosejComboBox = new JComboBox();
        choosejComboBox.addItem("点动");
        choosejComboBox.addItem("切换");
        add(jLabel);
        add(jComboBox);
        add(jTextField);
        add(choosejComboBox);
        add(jButton1);
        jPanel.add(this);

        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newJPanel.coilChangeAddress(Integer.valueOf(jTextField.getText()), jComboBox);
                if (choosejComboBox.getSelectedIndex() == 0) {
                    try {
                        newJPanel.getJButton().removeActionListener(newJPanel.getJButton().getAction());
                    } catch (Exception e1) {
                        System.out.println("切换失败");
                    }
                    if (newJPanel.getJButton().getMouseListeners()[0] == null) {
                        newJPanel.getJButton().addMouseListener(newJPanel.getMouseAdapter());
                    }
                    System.out.println("切换成功");
                } else {
                    try {
                        newJPanel.getJButton().removeMouseListener(newJPanel.getJButton().getMouseListeners()[0]);
                    } catch (Exception e1) {
                        System.out.println("切换失败");
                    }
                    if (newJPanel.getJButton().getAction() == null) {
                        newJPanel.getJButton().addActionListener(newJPanel.getActionListener());
                    }
                    System.out.println("切换成功");
                }
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
        if (i < 8000) {
            this.i = i;
        } else {
            this.i = i + 8384;
        }
    }

    public void coilChangeAddress(int i, JComboBox jComboBox) {
        if (jComboBox.getSelectedIndex() == 0) {
            this.i = i;
        } else {
            this.i = i + 18432;
        }
    }

    public String getMory() {
        return this.mory;
    }

    public void setChoose(int i) {
        this.choose = i;
    }

    public JButton getJButton() {
        return this.jButton;
    }

    public MouseAdapter getMouseAdapter() {
        return this.mouseAdapter;
    }

    public ActionListener getActionListener() {
        return this.actionListener;
    }
}
