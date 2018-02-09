package com.done.doneserialport.serial;

import android.util.Log;

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

public class SerialLogger {

    public static boolean isDebug = true;

    public static void v(String tag, String message) {
        if (isDebug) {
            Log.v(tag, "[t_id:" + Thread.currentThread().getId() + ",t_name:" + Thread.currentThread().getName() + "]☛" + message);
        }
    }

    public static void d(String tag, String message) {
        if (isDebug) {
            Log.d(tag, "[t_id:" + Thread.currentThread().getId() + ",t_name:" + Thread.currentThread().getName() + "]☛" + message);
        }
    }

    public static void i(String tag, String message) {
        if (isDebug) {
            Log.i(tag, "[t_id:" + Thread.currentThread().getId() + ",t_name:" + Thread.currentThread().getName() + "]☛" + message);
        }
    }

    public static void w(String tag, String message) {
        if (isDebug) {
            Log.w(tag, "[t_id:" + Thread.currentThread().getId() + ",t_name:" + Thread.currentThread().getName() + "]☛" + message);
        }
    }

    public static void e(String tag, String message) {
        if (isDebug) {
            Log.e(tag, "[t_id:" + Thread.currentThread().getId() + ",t_name:" + Thread.currentThread().getName() + "]☛" + message);
        }
    }
}
