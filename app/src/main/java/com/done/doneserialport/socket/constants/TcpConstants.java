package com.done.doneserialport.socket.constants;

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

public class TcpConstants {

    /**
     * default connect timeout is 10s
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;

    /**
     * default send timeout is 10s
     */
    public static final int DEFAULT_SEND_TIMEOUT = 10 * 1000;

    public enum SEND_DATA_TYPE {
        HEX,
        STRING
    }

    /**
     * when tcp is connecting callback use these codes
     */
    public enum CLIENT_CODE {
        /**
         * tcp connect success
         */
        CONNECT_SUCCESS(0, "tcp is connected"),

        /**
         * tcp connect error when no catch the exception
         */
        OTHER_ERROR(1, "tcp connect error when no catch the exception"),

        /**
         * tcp connect timeout
         */
        CONNECT_TIMEOUT(2, "tcp connect timeout"),

        /**
         * tcp is disconnected, if the network is accessing, sever maybe expel you
         */
        DISCONNECT_ERROR(3, "tcp is disconnected, if the network is accessing, sever maybe expel you!"),

        /**
         * user disconnect tcp socket of user's own initiative
         */
        DISCONNECT_ACTIVE(4, "user disconnect tcp socket of user's own initiative"),

        /**
         * tcp has connected
         */
        CONNECT_HAS_CONNECTED(5, "tcp has connected");

        int code;
        String message;

        CLIENT_CODE(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

}
