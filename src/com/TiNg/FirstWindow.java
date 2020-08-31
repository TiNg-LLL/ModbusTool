package com.TiNg;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstWindow extends JFrame {

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();     //获取屏幕的尺寸
    int screenWidth = screenSize.width;             //获取屏幕的宽
    int screenHeight = screenSize.height;           //获取屏幕的高
    Modbus modbus = new Modbus();
    DataTreat dataTreat = new DataTreat();

    public FirstWindow(String windowName, int windowWidth, int windowHeight) {

        //FirstWindow主窗口
        setTitle(windowName);
        setBounds(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2, windowWidth, windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setResizable(false);

//底部-------------------------------------------------------------------------------------------------------------------

        //底部信息输出JPanel
        JPanel bottonJPanel = new JPanel();
        bottonJPanel.setPreferredSize(new Dimension(800, 25));
        bottonJPanel.setLayout(new BorderLayout());
        bottonJPanel.setBackground(Color.LIGHT_GRAY);
        add(bottonJPanel, BorderLayout.SOUTH);

        //底部信息
        JLabel comMessage = new JLabel("");
        comMessage.setFont(new Font("宋体", Font.PLAIN, 12));
        comMessage.setForeground(Color.DARK_GRAY);
        bottonJPanel.add(comMessage, BorderLayout.WEST);
        JLabel dataMessage = new JLabel("");
        dataMessage.setFont(new Font("宋体", Font.PLAIN, 12));
        dataMessage.setForeground(Color.DARK_GRAY);
        bottonJPanel.add(dataMessage, BorderLayout.EAST);

//顶部-------------------------------------------------------------------------------------------------------------------

        //顶部COM数据功能JPanel
        JPanel top1JPanel = new JPanel();
        top1JPanel.setPreferredSize(new Dimension(300, 35));
        top1JPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
        top1JPanel.setBackground(Color.LIGHT_GRAY);
        add(top1JPanel, BorderLayout.NORTH);

        //顶部COM数据功能JPanel中添加的模块
        JComboBox<String> comList = new JComboBox<String>();
        JButton comflashButton = new JButton("端口刷新");
        JButton comDataSetButton = new JButton("参数设置");
        JButton connectButton = new JButton("连接");
        top1JPanel.add(comList);
        top1JPanel.add(comflashButton);
        top1JPanel.add(comDataSetButton);
        top1JPanel.add(connectButton);

        //顶部COM端口刷新按钮实现
        comListAdd(comList);
        comflashButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comListAdd(comList);
            }
        });

        //顶部COM参数设置弹窗
        String comDataWindowName = "COM参数设置";
        int comDataWindowWidth = 700;
        int comDataWindowHeight = 135;
        NewWindow comDataWindow = new NewWindow(comDataWindowName, comDataWindowWidth, comDataWindowHeight);

        //顶部COM参数设置弹窗内内容
        JPanel comDateWindowJPanel1 = new JPanel();
        comDateWindowJPanel1.setPreferredSize(new Dimension(500, 50));
        comDateWindowJPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 12));
        /**comDateWindowJPanel1.setBackground(Color.WHITE);*/
        JLabel baudrateJLabel = new JLabel("波特率：");
        Integer[] baudrateintegers = {4800, 9600, 14400, 19200, 38400, 56000, 115200};
        JComboBox<Integer> baudrateJComboBox = new JComboBox<Integer>();
        for (int i = 0; i < baudrateintegers.length; i++) {
            baudrateJComboBox.addItem(baudrateintegers[i]);
        }
        baudrateJComboBox.setSelectedIndex(3);
        JLabel dataBitJLabel = new JLabel("    比特位(默认8)：");
        JTextField dataBitJTextField = new JTextField(2);
        dataBitJTextField.setText("8");
        JLabel stopBitJLabel = new JLabel("    结束位(默认1)：");
        JTextField stopBitJTextField = new JTextField(2);
        stopBitJTextField.setText("1");
        JLabel doubleEvenBitJLabel = new JLabel("    奇偶校验：");
        String[] doubleEvenstrings = {"奇", "偶", "无"};
        JComboBox<String> doubleEvenJComboBox = new JComboBox<String>();
        for (int i = 0; i < doubleEvenstrings.length; i++) {
            doubleEvenJComboBox.addItem(doubleEvenstrings[i]);
        }
        doubleEvenJComboBox.setSelectedIndex(1);
        JLabel comAddressJLabel = new JLabel("    地址(默认1)：");
        JTextField comAddressJTextField = new JTextField(2);
        comAddressJTextField.setText("1");
        comDateWindowJPanel1.add(baudrateJLabel);
        comDateWindowJPanel1.add(baudrateJComboBox);
        comDateWindowJPanel1.add(dataBitJLabel);
        comDateWindowJPanel1.add(dataBitJTextField);
        comDateWindowJPanel1.add(stopBitJLabel);
        comDateWindowJPanel1.add(stopBitJTextField);
        comDateWindowJPanel1.add(doubleEvenBitJLabel);
        comDateWindowJPanel1.add(doubleEvenJComboBox);
        comDateWindowJPanel1.add(comAddressJLabel);
        comDateWindowJPanel1.add(comAddressJTextField);

        JPanel comDateWindowJPanel2 = new JPanel();
        comDateWindowJPanel2.setPreferredSize(new Dimension(500, 10));
        comDateWindowJPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        /**comDateWindowJPanel2.setBackground(Color.YELLOW);*/
        JButton comDateSetJButton = new JButton("应用");
        comDateWindowJPanel2.add(comDateSetJButton);

        comDataWindow.add(comDateWindowJPanel1, BorderLayout.NORTH);
        comDataWindow.add(comDateWindowJPanel2, BorderLayout.CENTER);

        //顶部COM端口连接按钮实现
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String comName = comList.getSelectedItem().toString();
                Integer baudrate = (Integer) baudrateJComboBox.getSelectedItem();
                int dataBit = Integer.parseInt(dataBitJTextField.getText());
                int stopBit = Integer.parseInt(stopBitJTextField.getText());
                String doubleEven = doubleEvenJComboBox.getSelectedItem().toString();
                if (connectButton.getText().equals("连接")) {
                    modbus.ModbusConnect(comName, baudrate, dataBit, stopBit, doubleEven);
                    if (modbus.ModbusisConnected()) {
                        System.out.println("COM口已连接成功");
                        comMessage.setText("COM口已连接成功");
                        comList.setEnabled(false);
                        comflashButton.setEnabled(false);
                        comDataSetButton.setEnabled(false);
                        connectButton.setText("断开");
                        top1JPanel.setBackground(Color.GREEN);
                    } else {
                        System.out.println("COM口连接失败");
                        comMessage.setText("COM口连接失败");
                    }
                } else {
                    modbus.ModbusDisconnect();
                    System.out.println("COM口已断开");
                    comMessage.setText("COM口已断开");
                    comList.setEnabled(true);
                    comflashButton.setEnabled(true);
                    comDataSetButton.setEnabled(true);
                    connectButton.setText("连接");
                    top1JPanel.setBackground(Color.LIGHT_GRAY);
                }
            }
        });

        //顶部COM参数设置弹窗按钮实现
        buttonAddAction(comDataSetButton, comDataWindow);

//中部-------------------------------------------------------------------------------------------------------------------

        //中部主功能一级JPanel
        JPanel middle1JPanel = new JPanel();
        middle1JPanel.setPreferredSize(new Dimension(300, 50));
        middle1JPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        middle1JPanel.setBackground(Color.GRAY);
        add(middle1JPanel, BorderLayout.CENTER);

        //中部主功能二级JPanel1-------------------------------------------------------------------------------------------
        JPanel middle2JPanel1 = new JPanel();
        middle2JPanel1.setPreferredSize(new Dimension(350, 350));
        middle2JPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        middle2JPanel1.setBackground(Color.GREEN);
        middle1JPanel.add(middle2JPanel1, BorderLayout.CENTER);

        //中部主功能三级JPanel
        String name1 = " 目标速度";
        String name2 = " 加减速度";
        String name3 = "目标位置1";
        String name4 = "目标位置2";
        String name5 = "目标位置3";
        String name6 = "目标位置4";
        String name7 = "目标位置5";
        String name8 = "目标位置6";
        String name9 = "目标位置7";
        NewJPanel middle3JPanel1 = new NewJPanel(middle2JPanel1, 340, 30, name1, 1, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel2 = new NewJPanel(middle2JPanel1, 340, 30, name2, 2, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel3 = new NewJPanel(middle2JPanel1, 340, 30, name3, 3, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel4 = new NewJPanel(middle2JPanel1, 340, 30, name4, 4, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel5 = new NewJPanel(middle2JPanel1, 340, 30, name5, 5, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel6 = new NewJPanel(middle2JPanel1, 340, 30, name6, 6, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel7 = new NewJPanel(middle2JPanel1, 340, 30, name7, 7, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel8 = new NewJPanel(middle2JPanel1, 340, 30, name8, 8, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel middle3JPanel9 = new NewJPanel(middle2JPanel1, 340, 30, name9, 9, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        JButton addressDataSetButton = new JButton("地址参数设置");
        JButton alladdressDataSetButton = new JButton("通用");
        middle2JPanel1.add(addressDataSetButton);
        middle2JPanel1.add(alladdressDataSetButton);

        //中部地址参数设置弹窗
        String addressDataWindowName = "地址参数设置";
        int addressDataWindowWidth = 250;
        int addressDataWindowHeight = 380;
        NewWindow addressDataWindow = new NewWindow(addressDataWindowName, addressDataWindowWidth, addressDataWindowHeight);
        JPanel addressDataJPanel = new JPanel();
        addressDataJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        addressDataWindow.add(addressDataJPanel);
        NewJPanel addressDataJPanel1 = new NewJPanel(addressDataJPanel, 220, 30, name1, middle3JPanel1, dataMessage);
        NewJPanel addressDataJPanel2 = new NewJPanel(addressDataJPanel, 220, 30, name2, middle3JPanel2, dataMessage);
        NewJPanel addressDataJPanel3 = new NewJPanel(addressDataJPanel, 220, 30, name3, middle3JPanel3, dataMessage);
        NewJPanel addressDataJPanel4 = new NewJPanel(addressDataJPanel, 220, 30, name4, middle3JPanel4, dataMessage);
        NewJPanel addressDataJPanel5 = new NewJPanel(addressDataJPanel, 220, 30, name5, middle3JPanel5, dataMessage);
        NewJPanel addressDataJPanel6 = new NewJPanel(addressDataJPanel, 220, 30, name6, middle3JPanel6, dataMessage);
        NewJPanel addressDataJPanel7 = new NewJPanel(addressDataJPanel, 220, 30, name7, middle3JPanel7, dataMessage);
        NewJPanel addressDataJPanel8 = new NewJPanel(addressDataJPanel, 220, 30, name8, middle3JPanel8, dataMessage);
        NewJPanel addressDataJPanel9 = new NewJPanel(addressDataJPanel, 220, 30, name9, middle3JPanel9, dataMessage);

        //中部地址参数设置弹窗按钮实现
        buttonAddAction(addressDataSetButton, addressDataWindow);

        //中部通用设置弹窗
        String alladdressDataWindowName = "通用参数设置";
        int alladdressDataWindowWidth = 600;
        int alladdressDataWindowHeight = 200;
        NewWindow alladdressDataWindow = new NewWindow(alladdressDataWindowName, alladdressDataWindowWidth, alladdressDataWindowHeight);

        //中部通用设置弹窗按钮实现
        buttonAddAction(alladdressDataSetButton, alladdressDataWindow);

        //中部主功能二级JPanel2--------------------------------------------------------------------------------------------
        JPanel middle2JPanel2 = new JPanel();
        middle2JPanel2.setPreferredSize(new Dimension(150, 300));
        middle2JPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        middle2JPanel2.setBackground(Color.BLUE);
        middle1JPanel.add(middle2JPanel2, BorderLayout.CENTER);

        //中部主功能二级JPanel3--------------------------------------------------------------------------------------------
        JPanel middle2JPanel3 = new JPanel();
        middle2JPanel3.setPreferredSize(new Dimension(150, 300));
        middle2JPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        middle2JPanel3.setBackground(Color.MAGENTA);
        middle1JPanel.add(middle2JPanel3, BorderLayout.CENTER);

//扫尾工作---------------------------------------------------------------------------------------------------------------
        //FirstWindow主窗口实现
        setVisible(true);

        //读取线程
        class ReadThread extends Thread {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    try {
                        if (modbus.ModbusisConnected()) {
                            int[] i1 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel1.getAddress(), 2);
                            int[] i2 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel2.getAddress(), 2);
                            int[] i3 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel3.getAddress(), 2);
                            int[] i4 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel4.getAddress(), 2);
                            int[] i5 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel5.getAddress(), 2);
                            int[] i6 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel6.getAddress(), 2);
                            int[] i7 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel7.getAddress(), 2);
                            int[] i8 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel8.getAddress(), 2);
                            int[] i9 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), middle3JPanel9.getAddress(), 2);
                            middle3JPanel1.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i1));
                            middle3JPanel2.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i2));
                            middle3JPanel3.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i3));
                            middle3JPanel4.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i4));
                            middle3JPanel5.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i5));
                            middle3JPanel6.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i6));
                            middle3JPanel7.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i7));
                            middle3JPanel8.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i8));
                            middle3JPanel9.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i9));
                        } else {
                            middle3JPanel1.setNowData().setText("当前值：");
                            middle3JPanel2.setNowData().setText("当前值：");
                            middle3JPanel3.setNowData().setText("当前值：");
                            middle3JPanel4.setNowData().setText("当前值：");
                            middle3JPanel5.setNowData().setText("当前值：");
                            middle3JPanel6.setNowData().setText("当前值：");
                            middle3JPanel7.setNowData().setText("当前值：");
                            middle3JPanel8.setNowData().setText("当前值：");
                            middle3JPanel9.setNowData().setText("当前值：");
                        }
                    } catch (Exception e) {
                        System.out.println("COM端无返回值");
                        middle3JPanel1.setNowData().setText("当前值：null");
                        middle3JPanel2.setNowData().setText("当前值：null");
                        middle3JPanel3.setNowData().setText("当前值：null");
                        middle3JPanel4.setNowData().setText("当前值：null");
                        middle3JPanel5.setNowData().setText("当前值：null");
                        middle3JPanel6.setNowData().setText("当前值：null");
                        middle3JPanel7.setNowData().setText("当前值：null");
                        middle3JPanel8.setNowData().setText("当前值：null");
                        middle3JPanel9.setNowData().setText("当前值：null");
                    }
                }
            }
        }
        ;
        ReadThread readThread = new ReadThread();
        readThread.start();
    }

    //方法---------------------------------------------------------------------------------------------------------------
    //独立跳窗按钮方法-----------------------------------------------------------------------------------------------------
    public void buttonAddAction(JButton jButton, NewWindow newWindow) {
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newWindow.setVisible(true);
            }
        });
    }

    //COM端口获取方法-----------------------------------------------------------------------------------------------------
    public void comListAdd(JComboBox<String> jComboBox) {
        jComboBox.removeAllItems();
        SerialPort[] portNames = SerialPort.getCommPorts();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < portNames.length; i++) {
            list.add(portNames[i].getSystemPortName());
        }
        Collections.sort(list);
        for (int i = 0; i < portNames.length; i++) {
            jComboBox.addItem(list.get(i));
        }
    }

    public int readdDataTreat(int a, int b) {
        if (a < 32768) {
            return a;
        } else {
            return b; //未完成
        }
    }
}