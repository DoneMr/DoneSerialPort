package com.done.doneserialport.socket.interfaces;

import com.done.doneserialport.socket.constants.TcpConstants;

import java.net.InetAddress;

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

public interface OnTcpClientListener {
    /**
     * when tcp is connected callback
     *
     * @param code {@link com.done.doneserialport.socket.constants.TcpConstants.CLIENT_CODE}
     */
    void onConnect(TcpConstants.CLIENT_CODE code);

    /**
     * when tcp is disconnected callback
     *
     * @param code    {@link com.done.doneserialport.socket.constants.TcpConstants.CLIENT_CODE}
     * @param address socket host address
     */
    void onDisconnect(TcpConstants.CLIENT_CODE code, InetAddress address);

    /**
     * when tcp is received some data callback
     *
     * @param hexStr     java use this param
     * @param sourceData this is source data for tcp
     */
    void onReceive(String hexStr, byte[] sourceData);
}
