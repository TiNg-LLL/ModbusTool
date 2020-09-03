package com.TiNg;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

public class NewJPanel extends JPanel {
    JLabel jLabel1;
    int i;
    int bujinxifen;
    int wulisubi;
    int maxdataMM;
    JTextField jTextField;
    DataTreat dataTreat = new DataTreat();
    JComboBox<String> jComboBox = new JComboBox<String>();
    int choose;
    String mory;
    JButton jButton;
    MouseAdapter mouseAdapter;
    ActionListener actionListener;

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, int addressData, Modbus modbus, int slaveId, JLabel jLabel2) {   //左侧四级JPanel
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        jTextField = new JTextField(6);
        jButton = new JButton("应用");
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
                    jLabel2.setText(jLabelName + "：端口未连接");
                }
            }
        });
    }

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, int addressData, Modbus modbus, int slaveId, JLabel jLabel2, int maxdataMM) {   //左侧四级JPanel
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        jTextField = new JTextField(6);
        jButton = new JButton("应用");
        jLabel1 = new JLabel("当前值:                         ");
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 12));
        add(jLabel);
        add(jTextField);
        add(jButton);
        add(jLabel1);
        jPanel.add(this);
        this.changeAddress(addressData);
        this.maxdataMM = maxdataMM;

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (modbus.ModbusisConnected()) {
                    try {
                        if (maxdataMM == 10) {
                            if ((Float.parseFloat(jTextField.getText())) < 11) {
                                float f = Float.parseFloat(jTextField.getText());
                                float f1 = (float) ((f / 0.5 * wulisubi * bujinxifen) / 10);
                                int a = (int) f1;
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
                            } else {
                                jLabel2.setText(jLabelName + "---超出最大值");
                            }
                        } else {
                            float f = Float.parseFloat(jTextField.getText());
                            float f1 = (float) ((f / 0.5 * wulisubi * bujinxifen) / 10);
                            int a = (int) f1;
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
                        }
                    } catch (Exception e1) {
                        jLabel2.setText(jLabelName + "---设置失败");
                        e1.printStackTrace();
                    }
                } else {
                    jLabel2.setText(jLabelName + "：端口未连接");
                }
            }
        });
    }

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, NewJPanel[] newJPanel, JLabel jLabel2, String xifensubi, int bw) {   //左侧地址参数设置JPanel 步进细分
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        jTextField = new JTextField(6);
        jButton = new JButton("应用");
        add(jLabel);
        add(jTextField);
        add(jButton);
        jPanel.add(this);

        if (bw == 1) {
            jTextField.setText(xifensubi); //初始步进细分
            for (int j = 0; j < newJPanel.length; j++) {
                newJPanel[j].setbujinxifen(Integer.valueOf(jTextField.getText()));
            }
            jButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < newJPanel.length; j++) {
                        newJPanel[j].setbujinxifen(Integer.valueOf(jTextField.getText()));
                    }
                }
            });
        } else if (bw == 2) {
            jTextField.setText(xifensubi);  //初始物理速比
            for (int j = 0; j < newJPanel.length; j++) {
                newJPanel[j].setwulisubi(Integer.valueOf(jTextField.getText()));
            }
            jButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < newJPanel.length; j++) {
                        newJPanel[j].setwulisubi(Integer.valueOf(jTextField.getText()));
                    }
                }
            });
        }
    }

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, NewJPanel newJPanel, JLabel jLabel2) {   //左侧地址参数设置JPanel
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        jTextField = new JTextField(6);
        jTextField.setText(String.valueOf(newJPanel.getAddress()));
        jButton = new JButton("应用");
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

    public NewJPanel(JPanel jPanel, int width, int height, String jButtonName, int addressData, Modbus modbus, int slaveId, JLabel jLabel2, int choose, String mory) {   //中部四级JPanel
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        /**setBackground(Color.LIGHT_GRAY);*/
        jButton = new JButton(jButtonName);
        jLabel1 = new JLabel("当前值：");
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 12));
        add(jButton);
        add(jLabel1);
        jPanel.add(this);
        this.mory = mory;
        i = addressData;

        if (mory.equals("M")) {
            if (i < 8000) {
                this.i = i;
            } else {
                this.i = i + 16576;
            }
        } else if (mory.equals("Y")) {
            if (addressData < 8) {
                i = addressData + 18432;
            } else {
                i = addressData + 18430;
            }
        } else {
            if (addressData < 8) {
                i = addressData + 16384;
            } else {
                i = addressData + 16382;
            }
        }

        this.choose = choose;

        mouseAdapter = new MouseInputAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    modbus.ModbuswritetrueMultipleCoils(slaveId, i);
                } catch (Exception e1) {
                    jLabel2.setText(jButtonName + "：端口未连接");
                }

            }

            public void mouseReleased(MouseEvent e) {
                try {
                    modbus.ModbuswritefalseMultipleCoils(slaveId, i);
                } catch (Exception e1) {
                    jLabel2.setText(jButtonName + "：端口未连接");
                }

            }
        };

        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean[] b = modbus.ModbusreadCoils(slaveId, i, 1);
                    if (b[0]) {
                        modbus.ModbuswritefalseMultipleCoils(slaveId, i);
                    } else {
                        modbus.ModbuswritetrueMultipleCoils(slaveId, i);
                    }
                } catch (Exception e1) {
                    jLabel2.setText(jButtonName + "：端口未连接");
                }

            }
        };

        if (choose == 1) {
            jButton.addMouseListener(mouseAdapter);
        } else {
            jButton.addActionListener(actionListener);
        }
    }

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, NewJPanel newJPanel, JLabel jLabel2, String meiluanyong) {      //中部线圈地址参数设置JPanel
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        jComboBox.addItem("M");
        jComboBox.addItem("Y");
        jComboBox.addItem("X");
        jTextField = new JTextField(6);
        if (newJPanel.getMory().equals("M")) {
            jComboBox.setSelectedIndex(0);
            jTextField.setText(String.valueOf(newJPanel.getAddress()));
        } else if (newJPanel.getMory().equals("X")) {
            jComboBox.setSelectedIndex(2);
            jTextField.setText(String.valueOf(newJPanel.getAddress() - 16384));
        } else {
            jComboBox.setSelectedIndex(1);
            jTextField.setText(String.valueOf(newJPanel.getAddress() - 18432));
        }
        JButton jButton1 = new JButton("应用");
        JComboBox choosejComboBox = new JComboBox();
        choosejComboBox.addItem("点动");
        choosejComboBox.addItem("切换");
        if (newJPanel.choose == 2) {
            choosejComboBox.setSelectedIndex(1);
        }
        add(jLabel);
        add(jComboBox);
        add(jTextField);
        add(choosejComboBox);
        add(jButton1);
        jPanel.add(this);

        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newJPanel.coilChangeAddress(Integer.valueOf(jTextField.getText()), jComboBox, jLabel2);
                if (choosejComboBox.getSelectedIndex() == 0) {
                    try {
                        newJPanel.getJButton().removeActionListener(newJPanel.getJButton().getActionListeners()[0]);
                        newJPanel.getJButton().addMouseListener(newJPanel.getMouseAdapter());
                        System.out.println("切换成功");
                        jLabel2.setText("<" + newJPanel.getJButton().getText() + ">" + "设为点动模式");
                    } catch (Exception e1) {
                        System.out.println("切换失败");
                    }
                } else {
                    try {
                        System.out.println(newJPanel.getJButton().getMouseListeners().length);
                        newJPanel.getJButton().removeMouseListener(newJPanel.getJButton().getMouseListeners()[1]); //[0]是按钮自带的MouseListener
                        newJPanel.getJButton().addActionListener(newJPanel.getActionListener());
                        System.out.println("切换成功");
                        jLabel2.setText("<" + newJPanel.getJButton().getText() + ">" + "设为切换模式");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        System.out.println("切换失败");
                    }
                }
            }
        });
    }

    public NewJPanel(JPanel jPanel, int width, int height, String jLabelName, int addressData, Modbus modbus, int slaveId, JLabel jLabel2, String meiluanyong1) {   //左侧四级JPanel
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        /**setBackground(Color.LIGHT_GRAY);*/
        JLabel jLabel = new JLabel(jLabelName);
        jLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        JComboBox<String> jComboBox = new JComboBox<String>();
        JButton jButton = new JButton("应用");
        add(jLabel);
        add(jComboBox);
        add(jButton);
        jPanel.add(this);
        this.changeAddress(addressData);

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JLabel setNowData() {
        return jLabel1;
    }

    public int getAddress() {
        return i;
    }

    public void setbujinxifen(int bujinxifen) {
        this.bujinxifen = bujinxifen;
    }

    public void setwulisubi(int wulisubi) {
        this.wulisubi = wulisubi;
    }

    public float getbujinxifen() {
        return bujinxifen;
    }

    public float getwulisubi() {
        return wulisubi;
    }

    public int getMaxDataMM() {
        return maxdataMM;
    }

    public JTextField getJTextField() {
        return jTextField;
    }

    public void changeAddress(int i) {
        if (i < 8000) {
            this.i = i;
        } else {
            this.i = i + 8384;
        }
    }

    public void coilChangeAddress(int i, JComboBox jComboBox, JLabel jLabel) {
        if (jComboBox.getSelectedIndex() == 0) {
            if (i < 8000) {
                this.i = i;
            } else {
                this.i = i + 16576;
            }
        } else if (jComboBox.getSelectedIndex() == 1) {
            if (i < 8) {
                this.i = i + 18432;
            } else if (i == 8 || i == 9) {
                jLabel.setText("Y地址错误");
            } else {
                this.i = i + 18430;
            }
        } else if (jComboBox.getSelectedIndex() == 2) {
            if (i < 8) {
                this.i = i + 16384;
            } else if (i == 8 || i == 9) {
                jLabel.setText("Y地址错误");
            } else {
                this.i = i + 16382;
            }
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
