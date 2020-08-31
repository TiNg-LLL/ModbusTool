package com.TiNg;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortFactoryJSerialComm;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;

public class Modbus {

    SerialParameters sp = new SerialParameters();
    ModbusMaster mm = null;

    //连接
    public void ModbusConnect(String comName, Integer baudrate, Integer dataBits, Integer stopBits, String doubleEven) {
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSerialComm());
        com.intelligt.modbus.jlibmodbus.Modbus.setLogLevel(com.intelligt.modbus.jlibmodbus.Modbus.LogLevel.LEVEL_DEBUG);
        sp.setDevice(comName);
        sp.setBaudRate(com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate.getBaudRate(baudrate));
        sp.setDataBits(dataBits);
        sp.setStopBits(stopBits);
        switch (doubleEven) {
            case "奇":
                sp.setParity(SerialPort.Parity.ODD);
                break;
            case "偶":
                sp.setParity(SerialPort.Parity.EVEN);
                break;
            case "无":
                sp.setParity(SerialPort.Parity.NONE);
                break;
        }
        try {
            mm = ModbusMasterFactory.createModbusMasterRTU(sp);
            mm.setResponseTimeout(100);
            mm.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //断开
    public void ModbusDisconnect() {
        try {
            mm.disconnect();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }

    //判断是否连接
    public boolean ModbusisConnected() {
        boolean a = false;
        if (mm == null) {
            return false;
        } else {
            if (mm.isConnected()) {
                a = true;
            }
            return a;
        }
    }

    //单个寄存器写入
    public Exception ModbuswriteSingleRegister(int slaveId, int offset, int quantity) {
        try {
            mm.writeSingleRegister(slaveId, offset, quantity);
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    //寄存器读取
    public int[] ModbusreadHoldingRegisters(int slaveId, int offset, int quantity) {
        try {
            int[] i = mm.readHoldingRegisters(slaveId, offset, quantity);
            return i;
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //线圈闭合
    public Exception ModbuswritetrueMultipleCoils(int slaveId, int offset) {
        boolean[] b = new boolean[1];
        b[0] = true;
        try {
            mm.writeMultipleCoils(slaveId, offset, b);
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    //线圈断开
    public void ModbuswritefalseMultipleCoils(int slaveId, int offset) {
        boolean[] b = new boolean[1];
        b[0] = false;
        try {
            mm.writeMultipleCoils(slaveId, offset, b);
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
    }

    public boolean[] ModbusreadCoils(int slaveId, int offset, int quantity) {
        try {
            boolean[] b = mm.readCoils(slaveId, offset, quantity);
            return b;
        } catch (ModbusProtocolException e) {
            e.printStackTrace();
        } catch (ModbusNumberException e) {
            e.printStackTrace();
        } catch (ModbusIOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
