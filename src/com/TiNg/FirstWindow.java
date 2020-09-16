package com.TiNg;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class FirstWindow extends JFrame {

    Toolkit kit = Toolkit.getDefaultToolkit();
    Dimension screenSize = kit.getScreenSize();     //获取屏幕的尺寸
    int screenWidth = screenSize.width;             //获取屏幕的宽
    int screenHeight = screenSize.height;           //获取屏幕的高
    Modbus modbus = new Modbus();
    DataTreat dataTreat = new DataTreat();
    //boolean passward = false;
    //配置文件设置
    Properties properties = new Properties();
    FileInputStream inputStream;

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String nowTime = date.format(formatter);
    String endTime = new String("13-12-2020");
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    Boolean b5;

    {
        try {
            Date sd1 = df.parse(endTime);
            Date sd2 = df.parse(nowTime);
            b5 = sd1.after(sd2);
        } catch (
                ParseException parseException) {
            parseException.printStackTrace();
        }
    }

    {
        try {
            inputStream = new FileInputStream("res/address.properties");
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FirstWindow(String windowName, int windowWidth, int windowHeight) {

        //FirstWindow主窗口
        setTitle(windowName);
        setBounds((screenWidth / 2) - (windowWidth / 2), (screenHeight / 2) - (windowHeight / 2), windowWidth, windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setResizable(false);

//底部-------------------------------------------------------------------------------------------------------------------

        //底部信息输出JPanel
        JPanel bottonJPanel = new JPanel();
        bottonJPanel.setPreferredSize(new Dimension(700, 25));
        bottonJPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
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
        try {
            comList.setSelectedIndex(Integer.valueOf(properties.getProperty("defaultCOM")));   //默认端口
        } catch (Exception e) {

        }

        //顶部COM参数设置弹窗
        String comDataWindowName = "COM参数设置";
        int comDataWindowWidth = 700;
        int comDataWindowHeight = 100;
        NewWindow comDataWindow = new NewWindow(comDataWindowName, comDataWindowWidth, comDataWindowHeight, "res\\gear-icon.png");

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
        comAddressJTextField.setText(properties.getProperty("comSlaveId"));
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
        /**JButton comDateSetJButton = new JButton("应用");*/
        /**comDateWindowJPanel2.add(comDateSetJButton);*/

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
                if (b5) {
                    if (connectButton.getText().equals("连接")) {
                        modbus.ModbusConnect(comName, baudrate, dataBit, stopBit, doubleEven);
                        if (modbus.ModbusisConnected()) {
                            System.out.println("COM端口已连接成功");
                            comMessage.setText("COM端口已连接成功");
                            dataMessage.setText(" ");
                            comList.setEnabled(false);
                            comflashButton.setEnabled(false);
                            comDataSetButton.setEnabled(false);
                            connectButton.setText("断开");
                            top1JPanel.setBackground(Color.GREEN);
                        } else {
                            System.out.println("COM端口连接失败");
                            comMessage.setText("COM端口连接失败");
                        }
                    } else {
                        modbus.ModbusDisconnect();
                        System.out.println("COM端口已断开");
                        comMessage.setText("COM端口已断开");
                        comList.setEnabled(true);
                        comflashButton.setEnabled(true);
                        comDataSetButton.setEnabled(true);
                        connectButton.setText("连接");
                        top1JPanel.setBackground(Color.LIGHT_GRAY);
                        try {
                            Thread.sleep(100);
                        } catch (Exception e1) {
                        }
                        dataMessage.setText(" ");
                    }
                } else {
                    comMessage.setText("已过期");
                }
            }
        });

        //顶部COM参数设置弹窗按钮实现
        buttonAddAction(comDataSetButton, comDataWindow);

//中部-------------------------------------------------------------------------------------------------------------------

        //中部主功能一级JPanel
        JPanel middle1JPanel = new JPanel();
        middle1JPanel.setPreferredSize(new Dimension(500, 50));
        middle1JPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        //middle1JPanel.setBackground(Color.ORANGE);
        add(middle1JPanel, BorderLayout.CENTER);

        //中部主功能二级左侧JPanel1----------------------------------------------------------------------------------------
        JPanel middle2JPanel1 = new JPanel();
        middle2JPanel1.setPreferredSize(new Dimension(390, 360));
        middle2JPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        //middle2JPanel1.setBackground(Color.GREEN);
        middle1JPanel.add(middle2JPanel1);

        //中部主功能三级左侧JPanel1
        JPanel lift3JPanel1 = new JPanel();
        lift3JPanel1.setPreferredSize(new Dimension(390, 320));
        lift3JPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        //lift3JPanel1.setBackground(Color.BLUE);
        middle2JPanel1.add(lift3JPanel1, BorderLayout.NORTH);

        //中部主功能三级左侧JPanel2
        JPanel lift3JPanel2 = new JPanel();
        lift3JPanel2.setPreferredSize(new Dimension(390, 28));
        lift3JPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //lift3JPanel2.setBackground(Color.DARK_GRAY);
        middle2JPanel1.add(lift3JPanel2, BorderLayout.SOUTH);

        //中部主功能四级左侧JPanel
        String lift1Name1 = "    点动速度(m/s)";
        String lift1Name2 = "   加减速时间(ms)";
        String lift1Name3 = "原点回归速度(m/s)";
        String lift1Name4 = "    爬行速度(m/s)";
        String lift1Name5 = "    移动速度(m/s)";
        String lift1Name6 = "     上层位置(mm)";
        String lift1Name7 = "     下层位置(mm)";
        String lift1Name8 = "     材料厚度(mm)";
        String lift1Name9 = "     当前位置(mm)";
        boolean b1 = true;
        boolean b2 = true;
        boolean b3 = true;
        boolean b4 = true;
        boolean b5 = true;
        boolean b6 = true;
        boolean b7 = true;
        boolean b8 = true;
        boolean b9 = true;
        NewJPanel lift4JPanel1 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name1, Integer.valueOf(properties.getProperty("Daddress1")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 10);  //maxdataMM 10有限制最大值   0没有限制
        NewJPanel lift4JPanel2 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name2, Integer.valueOf(properties.getProperty("Daddress2")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage);
        NewJPanel lift4JPanel3 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name3, Integer.valueOf(properties.getProperty("Daddress3")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 10);
        NewJPanel lift4JPanel4 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name4, Integer.valueOf(properties.getProperty("Daddress4")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 10);
        NewJPanel lift4JPanel5 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name5, Integer.valueOf(properties.getProperty("Daddress5")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 10);
        NewJPanel lift4JPanel6 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name6, Integer.valueOf(properties.getProperty("Daddress6")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 0);
        NewJPanel lift4JPanel7 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name7, Integer.valueOf(properties.getProperty("Daddress7")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 0);
        NewJPanel lift4JPanel8 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name8, Integer.valueOf(properties.getProperty("Daddress8")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, 0);
        NewJPanel lift4JPanel9 = new NewJPanel(lift3JPanel1, 390, 30, lift1Name9, Integer.valueOf(properties.getProperty("Daddress9")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, "当前位置");
        lift4JPanel1.setVisible(b1);
        lift4JPanel2.setVisible(b2);
        lift4JPanel3.setVisible(b3);
        lift4JPanel4.setVisible(b4);
        lift4JPanel5.setVisible(b5);
        lift4JPanel6.setVisible(b6);
        lift4JPanel7.setVisible(b7);
        lift4JPanel8.setVisible(b8);
        lift4JPanel9.setVisible(b9);
        JButton addressDataSetButton = new JButton("寄存器地址参数设置");
        JButton alladdressDataSetButton = new JButton("设置");
        lift3JPanel2.add(addressDataSetButton);
        lift3JPanel2.add(alladdressDataSetButton);

        //中部地址参数设置左侧弹窗
        String addressDataWindowName = "地址参数设置";
        int addressDataWindowWidth = 320;
        int addressDataWindowHeight = 450;
        NewWindow addressDataWindow = new NewWindow(addressDataWindowName, addressDataWindowWidth, addressDataWindowHeight, "res\\gear-icon.png");
        JPanel addressDataJPanel = new JPanel();
        addressDataJPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        addressDataWindow.add(addressDataJPanel);
        int width = 300;
        NewJPanel[] newJPanels = {lift4JPanel1, lift4JPanel3, lift4JPanel4, lift4JPanel5, lift4JPanel6, lift4JPanel7, lift4JPanel8, lift4JPanel9}; //需要步进细分 物理速比的数组；
        NewJPanel addressDataJPanel1 = new NewJPanel(addressDataJPanel, width, 30, lift1Name1 + ":D", lift4JPanel1, dataMessage);
        NewJPanel addressDataJPanel2 = new NewJPanel(addressDataJPanel, width, 30, lift1Name2 + ":D", lift4JPanel2, dataMessage);
        NewJPanel addressDataJPanel3 = new NewJPanel(addressDataJPanel, width, 30, lift1Name3 + ":D", lift4JPanel3, dataMessage);
        NewJPanel addressDataJPanel4 = new NewJPanel(addressDataJPanel, width, 30, lift1Name4 + ":D", lift4JPanel4, dataMessage);
        NewJPanel addressDataJPanel5 = new NewJPanel(addressDataJPanel, width, 30, lift1Name5 + ":D", lift4JPanel5, dataMessage);
        NewJPanel addressDataJPanel6 = new NewJPanel(addressDataJPanel, width, 30, lift1Name6 + ":D", lift4JPanel6, dataMessage);
        NewJPanel addressDataJPanel7 = new NewJPanel(addressDataJPanel, width, 30, lift1Name7 + ":D", lift4JPanel7, dataMessage);
        NewJPanel addressDataJPanel8 = new NewJPanel(addressDataJPanel, width, 30, lift1Name8 + ":D", lift4JPanel8, dataMessage);
        NewJPanel addressDataJPanel9 = new NewJPanel(addressDataJPanel, width, 30, lift1Name9 + ":D", lift4JPanel9, dataMessage);
        NewJPanel addressDataJPanel81 = new NewJPanel(addressDataJPanel, width, 30, "步进细分", newJPanels, dataMessage, properties.getProperty("bujinxifen"), 1);
        NewJPanel addressDataJPanel82 = new NewJPanel(addressDataJPanel, width, 30, "物理速比", newJPanels, dataMessage, properties.getProperty("wulisubi"), 2);
        addressDataJPanel1.setVisible(b1);
        addressDataJPanel2.setVisible(b2);
        addressDataJPanel3.setVisible(b3);
        addressDataJPanel4.setVisible(b4);
        addressDataJPanel5.setVisible(b5);
        addressDataJPanel6.setVisible(b6);
        addressDataJPanel7.setVisible(b7);
        addressDataJPanel8.setVisible(b8);
        addressDataJPanel81.setVisible(b8);
        addressDataJPanel82.setVisible(b8);
        addressDataJPanel9.setVisible(b9);

        //中部地址参数设置弹窗左侧按钮实现
        buttonAddAction(addressDataSetButton, addressDataWindow);

        //中部主功能二级中间JPanel2----------------------------------------------------------------------------------------
        JPanel middle2JPanel2 = new JPanel();
        middle2JPanel2.setPreferredSize(new Dimension(375, 360));
        middle2JPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        //middle2JPanel2.setBackground(Color.MAGENTA);
        middle1JPanel.add(middle2JPanel2);

        //中部主功能三级中间JPanel1
        JPanel middle3JPanel1 = new JPanel();
        middle3JPanel1.setPreferredSize(new Dimension(365, 190));
        middle3JPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        //middle3JPanel1.setBackground(Color.DARK_GRAY);
        middle2JPanel2.add(middle3JPanel1, BorderLayout.NORTH);

        //中部主功能三级中间JPanel3
        JPanel middle3JPanel3 = new JPanel();
        middle3JPanel3.setPreferredSize(new Dimension(365, 125));
        middle3JPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        //middle3JPanel3.setBackground(Color.BLUE);
        middle2JPanel2.add(middle3JPanel3, BorderLayout.CENTER);

        //中部主功能三级中间JPanel2
        JPanel middle3JPanel2 = new JPanel();
        middle3JPanel2.setPreferredSize(new Dimension(310, 28));
        middle3JPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        //middle3JPanel2.setBackground(Color.GREEN);
        middle2JPanel2.add(middle3JPanel2, BorderLayout.SOUTH);

        //中部主功能四级中间JPanel1
        String middle4Name1 = "  上升  ";
        String middle4Name2 = "  下降  ";
        String middle4Name3 = "  启动  ";
        String middle4Name4 = "  停止  ";
        String middle4Name5 = "上层位置";
        String middle4Name6 = "下层位置";
        String middle4Name7 = "回原点";
        String middle4Name8 = "上限位";
        String middle4Name9 = "下限位";
        String middle4Name10 = "  原点  ";
        boolean b11 = true;
        boolean b22 = true;
        boolean b33 = true;
        boolean b44 = true;
        boolean b55 = true;
        boolean b66 = true;
        boolean b77 = true;
        boolean b88 = true;
        boolean b99 = true;
        boolean b100 = true;
        NewJPanel middle4JPanel1 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name1, Integer.valueOf(properties.getProperty("MXYaddress1")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose1")), properties.getProperty("MXY1"));//addressData默认地址  choose：1按下true松开false  2第一次true第二次false 3 M线圈 Y线圈
        NewJPanel middle4JPanel2 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name2, Integer.valueOf(properties.getProperty("MXYaddress2")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose2")), properties.getProperty("MXY2"));
        NewJPanel middle4JPanel3 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name3, Integer.valueOf(properties.getProperty("MXYaddress3")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose3")), properties.getProperty("MXY3"));
        NewJPanel middle4JPanel4 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name4, Integer.valueOf(properties.getProperty("MXYaddress4")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose4")), properties.getProperty("MXY4"));
        NewJPanel middle4JPanel5 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name5, Integer.valueOf(properties.getProperty("MXYaddress5")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose5")), properties.getProperty("MXY5"));
        NewJPanel middle4JPanel6 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name6, Integer.valueOf(properties.getProperty("MXYaddress6")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose6")), properties.getProperty("MXY6"));
        NewJPanel middle4JPanel7 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name7, Integer.valueOf(properties.getProperty("MXYaddress7")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose7")), properties.getProperty("MXY7"));
        NewJPanel middle4JPanel8 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name8, Integer.valueOf(properties.getProperty("MXYaddress8")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose8")), properties.getProperty("MXY8"));
        NewJPanel middle4JPanel9 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name9, Integer.valueOf(properties.getProperty("MXYaddress9")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose9")), properties.getProperty("MXY9"));
        NewJPanel middle4JPanel10 = new NewJPanel(middle3JPanel1, 175, 30, middle4Name10, Integer.valueOf(properties.getProperty("MXYaddress10")), modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage, Integer.valueOf(properties.getProperty("MXYchoose10")), properties.getProperty("MXY10"));
        middle4JPanel1.setVisible(b11);
        middle4JPanel2.setVisible(b22);
        middle4JPanel3.setVisible(b33);
        middle4JPanel4.setVisible(b44);
        middle4JPanel5.setVisible(b55);
        middle4JPanel6.setVisible(b66);
        middle4JPanel7.setVisible(b77);
        middle4JPanel8.setVisible(b88);
        middle4JPanel9.setVisible(b99);
        middle4JPanel10.setVisible(b100);
        JButton coiladdressDataSetButton = new JButton("线圈地址参数设置");
        middle3JPanel2.add(coiladdressDataSetButton);

/*        //中部主功能四级中间JPanel3
        String middle4Name31 = "功能切换";
        String middle4Name32 = "功能切换";
        NewJPanel middle4JPanel31 = new NewJPanel(middle3JPanel3, 155, 30, middle4Name31, 0, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage,"没卵用1");
        NewJPanel middle4JPanel32 = new NewJPanel(middle3JPanel3, 155, 30, middle4Name32, 0, modbus, Integer.valueOf(comAddressJTextField.getText()), dataMessage,"没卵用1");*/

        //中部通用设置弹窗-------------------------------------------------------------------------------------------------
        String alladdressDataWindowName = "权限";
        int alladdressDataWindowWidth = 400;
        int alladdressDataWindowHeight = 100;
        NewWindow alladdressDataWindow = new NewWindow(alladdressDataWindowName, alladdressDataWindowWidth, alladdressDataWindowHeight, "res\\key-icon.png");
        JPanel jPanelPassward = new JPanel();
        jPanelPassward.setPreferredSize(new Dimension(500, 50));
        jPanelPassward.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 12));
        JLabel jLabelPassward = new JLabel("请输入密码：");
        JPasswordField jPasswordFieldPassward = new JPasswordField(8);
        JButton jButtonPassward = new JButton("登入");
        jPanelPassward.add(jLabelPassward);
        jPanelPassward.add(jPasswordFieldPassward);
        jPanelPassward.add(jButtonPassward);
        alladdressDataWindow.add(jPanelPassward);
        NewJPanel[] newJPanelss = {lift4JPanel1, lift4JPanel2, lift4JPanel3, lift4JPanel4, lift4JPanel5, middle4JPanel8, middle4JPanel9, middle4JPanel10};  //需要权限的JPanel
        for (int i = 0; i < newJPanelss.length; i++) {
            newJPanelss[i].getJButton().setEnabled(false);
            if (!((newJPanelss[i].getJTextField()) == null)) {
                newJPanelss[i].getJTextField().setEnabled(false);
            }
        }
        NewJPanel[] lift4JPanels = {lift4JPanel1, lift4JPanel2, lift4JPanel3, lift4JPanel4, lift4JPanel5};  //需要隐藏的JPanel
        for (int i = 0; i < lift4JPanels.length; i++) {
            lift4JPanels[i].setVisible(false);
        }
        addressDataSetButton.setEnabled(false);
        comDataSetButton.setEnabled(false);
        coiladdressDataSetButton.setEnabled(false);

        alladdressDataSetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (alladdressDataSetButton.getText().equals("设置")) {
                    alladdressDataWindow.setVisible(true);
                } else {
                    for (int i = 0; i < newJPanelss.length; i++) {
                        newJPanelss[i].getJButton().setEnabled(false);
                        if (!((newJPanelss[i].getJTextField()) == null)) {
                            newJPanelss[i].getJTextField().setEnabled(false);
                        }
                    }
                    addressDataSetButton.setEnabled(false);
                    comDataSetButton.setEnabled(false);
                    coiladdressDataSetButton.setEnabled(false);
                    for (int i = 0; i < lift4JPanels.length; i++) {
                        lift4JPanels[i].setVisible(false);
                    }
                    alladdressDataSetButton.setText("设置");
                }
            }
        });

        jButtonPassward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(jPasswordFieldPassward.getPassword()).equals("123456")) {
                    alladdressDataWindow.setVisible(false);
                    alladdressDataSetButton.setText("退出");
                    jPasswordFieldPassward.setText("");
                    dataMessage.setText("登入成功");
                    for (int i = 0; i < newJPanelss.length; i++) {
                        newJPanelss[i].getJButton().setEnabled(true);
                        if (!((newJPanelss[i].getJTextField()) == null)) {
                            newJPanelss[i].getJTextField().setEnabled(true);
                        }
                    }
                    addressDataSetButton.setEnabled(true);
                    comDataSetButton.setEnabled(true);
                    coiladdressDataSetButton.setEnabled(true);
                    for (int i = 0; i < lift4JPanels.length; i++) {
                        lift4JPanels[i].setVisible(true);
                    }
                } else {
                    dataMessage.setText("密码错误");
                    jPasswordFieldPassward.setText("");
                }
            }
        });


        //中部地址参数设置中间弹窗
        String coiladdressDataWindowName = "线圈地址参数设置";
        int coiladdressDataWindowWidth = 325;
        int coiladdressDataWindowHeight = 410;
        NewWindow coiladdressDataWindow = new NewWindow(coiladdressDataWindowName, coiladdressDataWindowWidth, coiladdressDataWindowHeight, "res\\gear-icon.png");
        JPanel coiladdressDataJPanel = new JPanel();
        coiladdressDataJPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        coiladdressDataWindow.add(coiladdressDataJPanel);
        int width1 = 300;
        NewJPanel coiladdressDataJPanel1 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name1, middle4JPanel1, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel2 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name2, middle4JPanel2, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel3 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name3, middle4JPanel3, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel4 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name4, middle4JPanel4, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel5 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name5, middle4JPanel5, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel6 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name6, middle4JPanel6, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel7 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name7, middle4JPanel7, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel8 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name8, middle4JPanel8, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel9 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name9, middle4JPanel9, dataMessage, "没卵用");
        NewJPanel coiladdressDataJPanel10 = new NewJPanel(coiladdressDataJPanel, width1, 30, middle4Name10, middle4JPanel10, dataMessage, "没卵用");
        coiladdressDataJPanel1.setVisible(b11);
        coiladdressDataJPanel2.setVisible(b22);
        coiladdressDataJPanel3.setVisible(b33);
        coiladdressDataJPanel4.setVisible(b44);
        coiladdressDataJPanel5.setVisible(b55);
        coiladdressDataJPanel6.setVisible(b66);
        coiladdressDataJPanel7.setVisible(b77);
        coiladdressDataJPanel8.setVisible(b88);
        coiladdressDataJPanel9.setVisible(b99);
        coiladdressDataJPanel10.setVisible(b100);

        //中部地址参数设置弹窗中间按钮实现
        buttonAddAction(coiladdressDataSetButton, coiladdressDataWindow);


/*        //中部主功能二级右侧JPanel3----------------------------------------------------------------------------------------
        JPanel middle2JPanel3 = new JPanel();
        middle2JPanel3.setPreferredSize(new Dimension(50, 350));
        middle2JPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        middle2JPanel3.setBackground(Color.MAGENTA);
        middle1JPanel.add(middle2JPanel3, BorderLayout.CENTER);*/

//扫尾工作---------------------------------------------------------------------------------------------------------------
        //FirstWindow主窗口实现
        setVisible(true);

        //读取线程

        class ReadThread extends Thread {
            final int[] ii = {1};

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                    try {
                        if (modbus.ModbusisConnected()) {
                            if (ii[0] == 1) {
                                comMessage.setText("COM端口已连接成功");
                            } else if (ii[0] == 2) {
                                comMessage.setText("COM端口已连接成功》");
                            } else if (ii[0] == 3) {
                                comMessage.setText("COM端口已连接成功》》");
                            } else if (ii[0] == 4) {
                                comMessage.setText("COM端口已连接成功》》》");
                            } else if (ii[0] == 5) {
                                comMessage.setText("COM端口已连接成功《《《");
                            } else if (ii[0] == 6) {
                                comMessage.setText("COM端口已连接成功《《");
                            } else if (ii[0] == 7) {
                                comMessage.setText("COM端口已连接成功《");
                                ii[0] = 0;
                            }
                            ii[0]++;
                            int[] i1 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel1.getAddress(), 2);
                            int[] i2 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel2.getAddress(), 2);
                            int[] i3 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel3.getAddress(), 2);
                            int[] i4 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel4.getAddress(), 2);
                            int[] i5 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel5.getAddress(), 2);
                            int[] i6 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel6.getAddress(), 2);
                            int[] i7 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel7.getAddress(), 2);
                            int[] i8 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel8.getAddress(), 2);
                            int[] i9 = modbus.ModbusreadHoldingRegisters(Integer.valueOf(comAddressJTextField.getText()), lift4JPanel9.getAddress(), 2);
                            lift4JPanel1.setNowData().setText("当前值：" + dataTreat.readtenToBinarytoMM(i1, lift4JPanel1));
                            lift4JPanel2.setNowData().setText("当前值：" + dataTreat.readtenToBinary(i2));
                            lift4JPanel3.setNowData().setText("当前值：" + dataTreat.readtenToBinarytoMM(i3, lift4JPanel3));
                            lift4JPanel4.setNowData().setText("当前值：" + dataTreat.readtenToBinarytoMM(i4, lift4JPanel4));
                            lift4JPanel5.setNowData().setText("当前值：" + dataTreat.readtenToBinarytoMM(i5, lift4JPanel5));
                            lift4JPanel6.setNowData().setText("当前值：" + dataTreat.readtenToBinarytoMM(i6, lift4JPanel6));
                            lift4JPanel7.setNowData().setText("当前值：" + dataTreat.readtenToBinarytoMM(i7, lift4JPanel7));
                            lift4JPanel8.setNowData().setText("当前值：" + dataTreat.readtenToBinarytoMM(i8, lift4JPanel8));
                            lift4JPanel9.setNowData().setText("：" + dataTreat.readtenToBinarytoMM(i9, lift4JPanel9));

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
                            lift4JPanel9.setNowData().setText("：");
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
                        lift4JPanel1.setNowData().setText("当前值：null");
                        lift4JPanel2.setNowData().setText("当前值：null");
                        lift4JPanel3.setNowData().setText("当前值：null");
                        lift4JPanel4.setNowData().setText("当前值：null");
                        lift4JPanel5.setNowData().setText("当前值：null");
                        lift4JPanel6.setNowData().setText("当前值：null");
                        lift4JPanel7.setNowData().setText("当前值：null");
                        lift4JPanel8.setNowData().setText("当前值：null");
                        lift4JPanel9.setNowData().setText("：null");
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
                        System.out.println("COM端口无返回值，或被占用");
                        dataMessage.setText("COM端口无返回值，或被占用");
                        //e.printStackTrace();
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