package com.done.doneserialport.serial;

import android.support.annotation.NonNull;

import com.done.doneserialport.util.HexUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

public class SerialPorter {

    private static final String TAG = "SerialPorter";

    private SerialPortSettings mSettings;

    private SerialPort mSerialPort;

    private OnSerialListener onSerialListener;

    SerialReceiver receiver;

    @Deprecated
    public SerialPorter(@NonNull OnSerialListener onSerialListener) {
        this(SerialPortSettings.DEFAULT, onSerialListener);
    }

    public SerialPorter(@NonNull SerialPortSettings settings, @NonNull OnSerialListener onSerialListener) {
        this.mSettings = settings;
        this.onSerialListener = onSerialListener;
        mSerialPort = new SerialPort();
    }

    public void begin() {
        if (mSerialPort.open(new File(mSettings.getmDevFile()),
                mSettings.getmBAUNDRATE(),
                mSettings.getmSTOPB(),
                mSettings.getmDATAB(),
                mSettings.getmPARIFY(),
                mSettings.getmFLOWCON())) {
            receiver = new SerialReceiver(mSettings.getmDevFile(), mSerialPort.getInputStream(), mSerialPort.getOutputStream());
            receiver.startReceive();
            onSerialListener.onConnect();

        } else {
            onSerialListener.onError(SerialPortCodes.ERROR_OPEN, "open serial port error!", null);
        }
    }

    public void close() {
        mSerialPort.close();
        receiver.close();
    }

    public boolean send(String hexData) {
        return receiver.send(hexData);
    }

    public boolean send(byte[] data) {
        return receiver.send(data);
    }

    public String getStringForNative() {
        return mSerialPort.stringFromJNI();
    }

    private class SerialReceiver extends Thread {

        private int readLen = -1;

        private boolean isRun = false;

        private InputStream inputStream;

        private OutputStream outputStream;

        private byte[] sourceData;

        private byte[] copyData;

        private String copyString;

        public SerialReceiver(@NonNull String name,
                              @NonNull InputStream inputStream,
                              @NonNull OutputStream outputStream) {
            super(name);
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            sourceData = new byte[1024];
        }

        @Override
        public void run() {
            while (isRun) {
                try {
                    readLen = inputStream.read(sourceData);
                    if (readLen != -1) {
                        copyData = new byte[readLen];
                        System.arraycopy(sourceData, 0, copyData, 0, readLen);
                        copyString = HexUtil.byte2HexStr(copyData).trim();
                        onSerialListener.onReceive(copyString, copyData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SerialLogger.e(TAG, "serial port receive data error!");
                    onSerialListener.onError(SerialPortCodes.ERROR_BLOCK, "serial port receive data error!", null);
                    close();
                }
            }
        }

        public boolean send(String hexStr) {
            try {
                SerialLogger.d(TAG, "serial send:" + hexStr);
                send(HexUtil.hexStringToBytes(hexStr));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public boolean send(byte[] data) {
            try {
                mSerialPort.getOutputStream().write(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public void startReceive() {
            isRun = true;
            this.start();
        }

        public void close() {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                inputStream = null;
                outputStream = null;
                isRun = false;
                onSerialListener.onDisconnect(SerialPortCodes.SERIAL_DISCONN, "serial port is disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
