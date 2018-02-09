package com.done.doneserialport.serial;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * Created by Done on 2017/9/21.
 */

public class SerialPortSettings {

    public static final SerialPortSettings DEFAULT = new SerialPortSettings();

    public static final SerialPort.BAUDRATE DEFAULT_BAUNDRATE = SerialPort.BAUDRATE.B115200;

    public static final SerialPort.STOPB DEFAULT_STOPB = SerialPort.STOPB.B1;

    public static final SerialPort.DATAB DEFAULT_DATAB = SerialPort.DATAB.CS8;

    public static final SerialPort.PARITY DEFAULT_PARIFY = SerialPort.PARITY.NONE;

    public static final SerialPort.FLOWCON DEFAULT_FLOWCON = SerialPort.FLOWCON.NONE;

    public static final String DEFAULT_DEV_FILE = "/dev/ttyHSL0"; //默认使用移远的串口0 （和升迈的通信串口）

    private SerialPort.BAUDRATE mBAUNDRATE = DEFAULT_BAUNDRATE;

    private SerialPort.STOPB mSTOPB = DEFAULT_STOPB;

    private SerialPort.DATAB mDATAB = DEFAULT_DATAB;

    private SerialPort.PARITY mPARIFY = DEFAULT_PARIFY;

    private SerialPort.FLOWCON mFLOWCON = DEFAULT_FLOWCON;

    private String mDevFile = DEFAULT_DEV_FILE;

    public String getmDevFile() {
        return mDevFile.equals(DEFAULT_DEV_FILE) ? DEFAULT_DEV_FILE : mDevFile;
    }

    public SerialPortSettings setmDevFile(String devFile) {
        this.mDevFile = devFile;
        return this;
    }

    public SerialPort.BAUDRATE getmBAUNDRATE() {
        return mBAUNDRATE == DEFAULT_BAUNDRATE ? DEFAULT_BAUNDRATE : mBAUNDRATE;
    }

    public SerialPortSettings setmBAUNDRATE(SerialPort.BAUDRATE mBAUNDRATE) {
        this.mBAUNDRATE = mBAUNDRATE;
        return this;
    }

    public SerialPort.STOPB getmSTOPB() {
        return mSTOPB == DEFAULT_STOPB ? DEFAULT_STOPB : mSTOPB;
    }

    public SerialPortSettings setmSTOPB(SerialPort.STOPB mSTOPB) {
        this.mSTOPB = mSTOPB;
        return this;
    }

    public SerialPort.DATAB getmDATAB() {
        return mDATAB == DEFAULT_DATAB ? DEFAULT_DATAB : mDATAB;
    }

    public SerialPortSettings setmDATAB(SerialPort.DATAB mDATAB) {
        this.mDATAB = mDATAB;
        return this;
    }

    public SerialPort.PARITY getmPARIFY() {
        return mPARIFY == DEFAULT_PARIFY ? DEFAULT_PARIFY : mPARIFY;
    }

    public SerialPortSettings setmPARIFY(SerialPort.PARITY mPARIFY) {
        this.mPARIFY = mPARIFY;
        return this;
    }

    public SerialPort.FLOWCON getmFLOWCON() {
        return mFLOWCON == DEFAULT_FLOWCON ? DEFAULT_FLOWCON : mFLOWCON;
    }

    public SerialPortSettings setmFLOWCON(SerialPort.FLOWCON mFLOWCON) {
        this.mFLOWCON = mFLOWCON;
        return this;
    }
}
