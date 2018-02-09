package com.done.doneserialport.socket;

import com.done.doneserialport.socket.constants.TcpConstants;

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

public class TcpSetting {

    /**
     * host IP
     */
    private String hostIP;

    /**
     * host port
     */
    private int port;

    /**
     * set tcp connect timeout, default is {@link TcpConstants#DEFAULT_CONNECT_TIMEOUT}
     */
    private int connectTimeout = TcpConstants.DEFAULT_CONNECT_TIMEOUT;

    /**
     * set tcp send timeout, default is {@link TcpConstants#DEFAULT_SEND_TIMEOUT}
     */
    private int sendTimeout = TcpConstants.DEFAULT_SEND_TIMEOUT;

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectTimeout() {
        return connectTimeout == TcpConstants.DEFAULT_CONNECT_TIMEOUT ? TcpConstants.DEFAULT_CONNECT_TIMEOUT : connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSendTimeout() {
        return sendTimeout == TcpConstants.DEFAULT_SEND_TIMEOUT ? TcpConstants.DEFAULT_SEND_TIMEOUT : sendTimeout;
    }

    public void setSendTimeout(int sendTimeout) {
        this.sendTimeout = sendTimeout;
    }
}
