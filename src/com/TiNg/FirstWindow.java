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
        /**middle1JPanel.setBackground(Color.GRAY);*/
        add(middle1JPanel, BorderLayout.CENTER);

        //中部主功能二级左侧JPanel1-------------------------------------------------------------------------------------------
        JPanel middle2JPanel1 = new JPanel();
        middle2JPanel1.setPreferredSize(new Dimension(310, 360));
        middle2JPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        /**middle2JPanel1.setBackground(Color.GREEN);*/
        middle1JPanel.add(middle2JPanel1, BorderLayout.CENTER);

        //中部主功能三级左侧JPanel1
        JPanel lift3JPanel1 = new JPanel();
        lift3JPanel1.setPreferredSize(new Dimension(310, 313));
        lift3JPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        /**lift3JPanel1.setBackground(Color.GREEN);*/
        middle2JPanel1.add(lift3JPanel1, BorderLayout.NORTH);

        //中部主功能三级左侧JPanel2
        JPanel lift3JPanel2 = new JPanel();
        lift3JPanel2.setPreferredSize(new Dimension(310, 60));
        lift3JPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        /**lift3JPanel2.setBackground(Color.DARK_GRAY);*/
        middle2JPanel1.add(lift3JPanel2, BorderLayout.SOUTH);

        //中部主功能四级左侧JPanel
        String lift1Name1 = " 目标速度";
        String lift1Name2 = " 加减速度";
        String lift1Name3 = "目标位置1";
        String lift1Name4 = "目标位置2";
        String lift1Name5 = "目标位置3";
        String lift1Name6 = "目标位置4";
        String lift1Name7 = " 材料厚度";
        String lift1Name8 = "    延时1";
        String lift1Name9 = "    延时2";
        NewJPanel lift4JPanel1 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name1, 10, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel2 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name2, 20, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel3 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name3, 30, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel4 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name4, 40, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel5 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name5, 50, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel6 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name6, 60, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel7 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name7, 70, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel8 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name8, 80, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel9 = new NewJPanel(lift3JPanel1, 310, 30, lift1Name9, 90, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        JButton addressDataSetButton = new JButton("寄存器地址参数设置");
        JButton alladdressDataSetButton = new JButton("通用");
        lift3JPanel2.add(addressDataSetButton);
        lift3JPanel2.add(alladdressDataSetButton);

        //中部地址参数设置左侧弹窗
        String addressDataWindowName = "地址参数设置";
        int addressDataWindowWidth = 250;
        int addressDataWindowHeight = 380;
        NewWindow addressDataWindow = new NewWindow(addressDataWindowName, addressDataWindowWidth, addressDataWindowHeight);
        JPanel addressDataJPanel = new JPanel();
        addressDataJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        addressDataWindow.add(addressDataJPanel);
        NewJPanel addressDataJPanel1 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name1 + ":D", lift4JPanel1, dataMessage);
        NewJPanel addressDataJPanel2 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name2 + ":D", lift4JPanel2, dataMessage);
        NewJPanel addressDataJPanel3 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name3 + ":D", lift4JPanel3, dataMessage);
        NewJPanel addressDataJPanel4 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name4 + ":D", lift4JPanel4, dataMessage);
        NewJPanel addressDataJPanel5 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name5 + ":D", lift4JPanel5, dataMessage);
        NewJPanel addressDataJPanel6 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name6 + ":D", lift4JPanel6, dataMessage);
        NewJPanel addressDataJPanel7 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name7 + ":D", lift4JPanel7, dataMessage);
        NewJPanel addressDataJPanel8 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name8 + ":D", lift4JPanel8, dataMessage);
        NewJPanel addressDataJPanel9 = new NewJPanel(addressDataJPanel, 220, 30, lift1Name9 + ":D", lift4JPanel9, dataMessage);

        //中部地址参数设置弹窗左侧按钮实现
        buttonAddAction(addressDataSetButton, addressDataWindow);

        //中部通用设置弹窗
        String alladdressDataWindowName = "通用参数设置";
        int alladdressDataWindowWidth = 600;
        int alladdressDataWindowHeight = 200;
        NewWindow alladdressDataWindow = new NewWindow(alladdressDataWindowName, alladdressDataWindowWidth, alladdressDataWindowHeight);

        //中部通用设置弹窗按钮实现
        buttonAddAction(alladdressDataSetButton, alladdressDataWindow);

        //中部主功能二级中间JPanel2----------------------------------------------------------------------------------------
        JPanel middle2JPanel2 = new JPanel();
        middle2JPanel2.setPreferredSize(new Dimension(330, 360));
        middle2JPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        /**middle2JPanel2.setBackground(Color.BLUE);*/
        middle1JPanel.add(middle2JPanel2, BorderLayout.CENTER);

        //中部主功能三级中间JPanel1
        JPanel middle3JPanel1 = new JPanel();
        middle3JPanel1.setPreferredSize(new Dimension(330, 313));
        middle3JPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));
        /**lift3JPanel2.setBackground(Color.DARK_GRAY);*/
        middle2JPanel2.add(middle3JPanel1, BorderLayout.SOUTH);

        //中部主功能三级中间JPanel2
        JPanel middle3JPanel2 = new JPanel();
        middle3JPanel2.setPreferredSize(new Dimension(310, 60));
        middle3JPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        /**lift3JPanel2.setBackground(Color.DARK_GRAY);*/
        middle2JPanel2.add(middle3JPanel2, BorderLayout.SOUTH);

        //中部主功能四级中间JPanel
        String middle4Name1 = "上升";
        String middle4Name2 = "下降";
        String middle4Name3 = "停止";
        String middle4Name4 = "功能1";
        String middle4Name5 = "功能2";
        String middle4Name6 = "功能3";
        String middle4Name7 = "功能4";
        String middle4Name8 = "功能5";
        String middle4Name9 = "功能6";
        String middle4Name10 = "功能7";
        NewJPanel middle4JPanel1 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name1, 0, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "Y");//addressData默认地址  choose：1按下true松开false  2第一次true第二次false 3 M线圈 Y线圈
        NewJPanel middle4JPanel2 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name2, 1, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "Y");
        NewJPanel middle4JPanel3 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name3, 2, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "Y");
        NewJPanel middle4JPanel4 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name4, 3, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "Y");
        NewJPanel middle4JPanel5 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name5, 4, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "M");
        NewJPanel middle4JPanel6 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name6, 5, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "M");
        NewJPanel middle4JPanel7 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name7, 6, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "M");
        NewJPanel middle4JPanel8 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name8, 7, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "M");
        NewJPanel middle4JPanel9 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name9, 8, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "M");
        NewJPanel middle4JPanel10 = new NewJPanel(middle3JPanel1, 155, 30, middle4Name10, 9, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 2, "M");
        JButton coiladdressDataSetButton = new JButton("线圈地址参数设置");
        middle3JPanel2.add(coiladdressDataSetButton);

        //中部地址参数设置中间弹窗
        String coiladdressDataWindowName = "线圈地址参数设置";
        int coiladdressDataWindowWidth = 320;
        int coiladdressDataWindowHeight = 400;
        NewWindow coiladdressDataWindow = new NewWindow(coiladdressDataWindowName, coiladdressDataWindowWidth, coiladdressDataWindowHeight);
        JPanel coiladdressDataJPanel = new JPanel();
        coiladdressDataJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        coiladdressDataWindow.add(coiladdressDataJPanel);
        NewJPanel coiladdressDataJPanel1 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name1, middle4JPanel1, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel2 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name2, middle4JPanel2, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel3 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name3, middle4JPanel3, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel4 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name4, middle4JPanel4, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel5 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name5, middle4JPanel5, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel6 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name6, middle4JPanel6, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel7 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name7, middle4JPanel7, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel8 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name8, middle4JPanel8, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel9 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name9, middle4JPanel9, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel10 = new NewJPanel(coiladdressDataJPanel, 280, 30, middle4Name10, middle4JPanel10, dataMessage, "没卵用");

        //中部地址参数设置弹窗中间按钮实现
        buttonAddAction(coiladdressDataSetButton, coiladdressDataWindow);


        //中部主功能二级右侧JPanel3----------------------------------------------------------------------------------------
        JPanel middle2JPanel3 = new JPanel();
        middle2JPanel3.setPreferredSize(new Dimension(50, 350));
        middle2JPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        /**middle2JPanel3.setBackground(Color.MAGENTA);*/
        middle1JPanel.add(middle2JPanel3, BorderLayout.CENTER);

//扫尾工作---------------------------------------------------------------------------------------------------------------
        //FirstWindow主窗口实现
        setVisible(true);

        //读取线程
        class ReadThread extends Thread {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                    try {
                        if (modbus.ModbusisConnected()) {
                            int[] i1 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel1.getAddress(), 2);
                            int[] i2 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel2.getAddress(), 2);
                            int[] i3 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel3.getAddress(), 2);
                            int[] i4 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel4.getAddress(), 2);
                            int[] i5 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel5.getAddress(), 2);
                            int[] i6 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel6.getAddress(), 2);
                            int[] i7 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel7.getAddress(), 2);
                            int[] i8 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel8.getAddress(), 2);
                            int[] i9 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel9.getAddress(), 2);
                            lift4JPanel1.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i1));
                            lift4JPanel2.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i2));
                            lift4JPanel3.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i3));
                            lift4JPanel4.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i4));
                            lift4JPanel5.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i5));
                            lift4JPanel6.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i6));
                            lift4JPanel7.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i7));
                            lift4JPanel8.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i8));
                            lift4JPanel9.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i9));

                            boolean[] b1 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel1.getAddress(), 1);
                            boolean[] b2 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel2.getAddress(), 1);
                            boolean[] b3 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel3.getAddress(), 1);
                            boolean[] b4 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel4.getAddress(), 1);
                            boolean[] b5 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel5.getAddress(), 1);
                            boolean[] b6 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel6.getAddress(), 1);
                            boolean[] b7 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel7.getAddress(), 1);
                            boolean[] b8 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel8.getAddress(), 1);
                            boolean[] b9 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel9.getAddress(), 1);
                            boolean[] b10 = modbus.ModbusreadCoils(Integer.valueOf(comAddressJTextField.getText()), middle4JPanel10.getAddress(), 1);
                            middle4JPanel1.setNowData().setText("当前值：" + b1[0]);
                            middle4JPanel2.setNowData().setText("当前值：" + b2[0]);
                            middle4JPanel3.setNowData().setText("当前值：" + b3[0]);
                            middle4JPanel4.setNowData().setText("当前值：" + b4[0]);
                            middle4JPanel5.setNowData().setText("当前值：" + b5[0]);
                            middle4JPanel6.setNowData().setText("当前值：" + b6[0]);
                            middle4JPanel7.setNowData().setText("当前值：" + b7[0]);
                            middle4JPanel8.setNowData().setText("当前值：" + b8[0]);
                            middle4JPanel9.setNowData().setText("当前值：" + b9[0]);
                            middle4JPanel10.setNowData().setText("当前值：" + b10[0]);
                        } else {
                            lift4JPanel1.setNowData().setText("当前值：");
                            lift4JPanel2.setNowData().setText("当前值：");
                            lift4JPanel3.setNowData().setText("当前值：");
                            lift4JPanel4.setNowData().setText("当前值：");
                            lift4JPanel5.setNowData().setText("当前值：");
                            lift4JPanel6.setNowData().setText("当前值：");
                            lift4JPanel7.setNowData().setText("当前值：");
                            lift4JPanel8.setNowData().setText("当前值：");
                            lift4JPanel9.setNowData().setText("当前值：");
                            middle4JPanel1.setNowData().setText("当前值：");
                            middle4JPanel2.setNowData().setText("当前值：");
                            middle4JPanel3.setNowData().setText("当前值：");
                            middle4JPanel4.setNowData().setText("当前值：");
                            middle4JPanel5.setNowData().setText("当前值：");
                            middle4JPanel6.setNowData().setText("当前值：");
                            middle4JPanel7.setNowData().setText("当前值：");
                            middle4JPanel8.setNowData().setText("当前值：");
                            middle4JPanel9.setNowData().setText("当前值：");
                            middle4JPanel10.setNowData().setText("当前值：");
                        }
                    } catch (Exception e) {
                        System.out.println("COM端无返回值");
                        dataMessage.setText("COM端无返回值");
                        lift4JPanel1.setNowData().setText("当前值：null");
                        lift4JPanel2.setNowData().setText("当前值：null");
                        lift4JPanel3.setNowData().setText("当前值：null");
                        lift4JPanel4.setNowData().setText("当前值：null");
                        lift4JPanel5.setNowData().setText("当前值：null");
                        lift4JPanel6.setNowData().setText("当前值：null");
                        lift4JPanel7.setNowData().setText("当前值：null");
                        lift4JPanel8.setNowData().setText("当前值：null");
                        lift4JPanel9.setNowData().setText("当前值：null");
                        middle4JPanel1.setNowData().setText("当前值：null");
                        middle4JPanel2.setNowData().setText("当前值：null");
                        middle4JPanel3.setNowData().setText("当前值：null");
                        middle4JPanel4.setNowData().setText("当前值：null");
                        middle4JPanel5.setNowData().setText("当前值：null");
                        middle4JPanel6.setNowData().setText("当前值：null");
                        middle4JPanel7.setNowData().setText("当前值：null");
                        middle4JPanel8.setNowData().setText("当前值：null");
                        middle4JPanel9.setNowData().setText("当前值：null");
                        middle4JPanel10.setNowData().setText("当前值：null");
                        e.printStackTrace();
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
}