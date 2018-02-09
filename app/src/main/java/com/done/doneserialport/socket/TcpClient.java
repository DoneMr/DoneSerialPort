package com.done.doneserialport.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.done.doneserialport.serial.SerialLogger;
import com.done.doneserialport.socket.constants.TcpConstants;
import com.done.doneserialport.socket.interfaces.OnTcpClientListener;
import com.done.doneserialport.util.HexUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
 * Created by Done on 2017/10/26.
 *
 * @author by Done
 */

public class TcpClient implements Runnable {

    private static final String TAG = "TcpClient";

    private TcpSetting tcpSetting;

    private OnTcpClientListener onTcpClientListener;

    private Socket socket;

    private DataOutputStream out;
    private DataInputStream in;
    private InetAddress address;
    private boolean isRun;
    private boolean isActive;

    private static final int CALLBACK_OUTSIDE_RECEIVE = 0;
    private static final int CALLBACK_OUTSIDE_CONNECT = 1;
    private static final int CALLBACK_OUTSIDE_DISCONNECT = 2;

    private static final String BUNDLE_KEY_HEX_STRING = "BUNDLE_KEY_HEX_STRING";
    private static final String BUNDLE_KEY_SOURCE_DATA = "BUNDLE_KEY_SOURCE_DATA";

    private HandlerThread callbackHandlerThread;
    private Handler callbackHandler;
    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (onTcpClientListener == null) {
                return false;
            }
            switch (msg.what) {
                case CALLBACK_OUTSIDE_RECEIVE:
                    Bundle bundle = msg.getData();
                    String hexData = bundle.getString(BUNDLE_KEY_HEX_STRING);
                    byte[] sourceData = bundle.getByteArray(BUNDLE_KEY_SOURCE_DATA);
                    onTcpClientListener.onReceive(hexData, sourceData);
                    break;
                case CALLBACK_OUTSIDE_CONNECT:
                    onTcpClientListener.onConnect((TcpConstants.CLIENT_CODE) msg.obj);
                    break;
                case CALLBACK_OUTSIDE_DISCONNECT:
                    onTcpClientListener.onDisconnect((TcpConstants.CLIENT_CODE) msg.obj, address);
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * this method return the socket is connect
     *
     * @return if true is connected else is disconnected
     */
    public boolean isConnected() {
        boolean bRet = false;
        if (socket != null) {
            bRet = socket.isConnected();
        }
        return bRet;
    }

    /**
     * This instance is connecting dest host
     *
     * @param tcpSetting          your tcp client params
     * @param onTcpClientListener tcp status callback
     */
    public void connect(@NonNull TcpSetting tcpSetting, @NonNull OnTcpClientListener onTcpClientListener) {
        if (socket != null && socket.isConnected()) {
            onConnected(TcpConstants.CLIENT_CODE.CONNECT_HAS_CONNECTED);
            return;
        }
        this.tcpSetting = tcpSetting;
        this.onTcpClientListener = onTcpClientListener;
        callbackHandlerThread = new HandlerThread(TAG + "-HandlerThread");
        callbackHandlerThread.start();
        callbackHandler = new Handler(callbackHandlerThread.getLooper(), callback);
        TcpThreadFactory tcpThreadFactory = new TcpThreadFactory();
        threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), tcpThreadFactory);
        threadPoolExecutor.execute(this);
        isRun = false;
        isActive = false;
    }

    /**
     * disconnect your tcp client
     * if you want to reconnect host, you should use {@link #connect(TcpSetting, OnTcpClientListener)}
     */
    public void disconnect() {
        isActive = true;
        onDisconnect(TcpConstants.CLIENT_CODE.DISCONNECT_ACTIVE);
        release();
    }

    /**
     * send your data to host
     *
     * @param data your data
     * @param type if your data is hexStr,use {@link com.done.doneserialport.socket.constants.TcpConstants.SEND_DATA_TYPE#HEX}
     *             else user {@link com.done.doneserialport.socket.constants.TcpConstants.SEND_DATA_TYPE#STRING}
     * @return your send result
     */
    public boolean sendToServer(final String data, final TcpConstants.SEND_DATA_TYPE type) {
        try {
            TcpSender tcpSender = new TcpSender();
            tcpSender.setData(data, type);
            Future<Boolean> future = threadPoolExecutor.submit(tcpSender);
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean send(String data, TcpConstants.SEND_DATA_TYPE type) throws Exception {
        boolean bRet = false;
        byte[] sendBuffer = null;
        if (out == null) {
            return false;
        }
        switch (type) {
            case HEX:
                sendBuffer = HexUtil.hexStr2Bytes(data);
                break;
            case STRING:
                String tempHexStr = HexUtil.str2HexStr(data);
                sendBuffer = HexUtil.hexStr2Bytes(tempHexStr);
                break;
            default:
                break;
        }
        if (sendBuffer != null && sendBuffer.length > 0) {
            out.write(sendBuffer);
            out.flush();
            bRet = true;
        }
        return bRet;
    }

    @Override
    public void run() {
        try {

            socket = new Socket();
            socket.connect(new InetSocketAddress(tcpSetting.getHostIP(), tcpSetting.getPort()), tcpSetting.getConnectTimeout());
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            address = socket.getInetAddress();
            onConnected(TcpConstants.CLIENT_CODE.CONNECT_SUCCESS);
            isRun = true;
            startReceive();
        } catch (Exception e) {
            e.printStackTrace();
            isRun = false;
            if (e instanceof SocketTimeoutException) {
                onDisconnect(TcpConstants.CLIENT_CODE.CONNECT_TIMEOUT);
            } else {
                onDisconnect(TcpConstants.CLIENT_CODE.OTHER_ERROR);
            }
        }
        if (!isActive) {
            onDisconnect(TcpConstants.CLIENT_CODE.DISCONNECT_ERROR);
        }
        release();
    }

    /**
     * release TcpClient instance
     */
    private void release() {
        isRun = false;
        if (socket != null) {
            try {
                //stop receive data, but not stop send for say goodbye 4 times
                socket.shutdownInput();
                socket = null;
            } catch (IOException e) {
            }
        }
        if (in != null) {
            try {
                in.close();
                in = null;
            } catch (IOException e) {
            }
        }
        if (out != null) {
            try {
                out.close();
                out = null;
            } catch (IOException e) {
            }
        }
        if (threadPoolExecutor != null) {
            try {
                threadPoolExecutor.shutdownNow();
                threadPoolExecutor = null;
            } catch (Exception e) {
            }
        }
        callbackHandler.removeCallbacksAndMessages(null);
        callbackHandler.getLooper().quit();
        // use quit not quit safely
        callbackHandlerThread.quit();
    }

    /**
     * block receiving
     *
     * @throws Exception Accidental disconnection
     */
    private void startReceive() throws Exception {
        int readLen = -1;
        byte[] data = new byte[1024];
        byte[] copyData;
        while (isRun) {
            if ((readLen = in.read(data)) > -1) {
                copyData = new byte[readLen];
                System.arraycopy(data, 0, copyData, 0, readLen);
                onReceive(copyData);
            } else {
                break;
            }
        }
    }

    /**
     * Tcp Client instance's life-circle
     * --------you know what i say~
     *
     * @see #onConnected(TcpConstants.CLIENT_CODE)
     * @see #onDisconnect(TcpConstants.CLIENT_CODE)
     * @see #onReceive(byte[])
     **/
    private void onConnected(TcpConstants.CLIENT_CODE clientCode) {
        callbackHandler.obtainMessage(CALLBACK_OUTSIDE_CONNECT, clientCode)
                .sendToTarget();
    }

    private void onDisconnect(TcpConstants.CLIENT_CODE clientCode) {
        callbackHandler.obtainMessage(CALLBACK_OUTSIDE_DISCONNECT, clientCode)
                .sendToTarget();
    }

    private void onReceive(byte[] copyData) throws Exception {
        Message message = callbackHandler.obtainMessage(CALLBACK_OUTSIDE_RECEIVE);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_HEX_STRING, HexUtil.byte2HexStr(copyData));
        bundle.putByteArray(BUNDLE_KEY_SOURCE_DATA, copyData);
        message.setData(bundle);
        message.sendToTarget();
    }

    private class TcpSender implements Callable<Boolean> {

        String sendData;

        TcpConstants.SEND_DATA_TYPE type;

        TcpSender() {

        }

        void setData(String sendData, TcpConstants.SEND_DATA_TYPE type) {
            this.sendData = sendData;
            this.type = type;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                return send(sendData, type);
            } catch (Exception e) {
                SerialLogger.e(TAG, "tcp send error" + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }
}
