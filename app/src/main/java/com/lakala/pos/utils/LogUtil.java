package com.lakala.pos.utils;

import android.util.Log;

//Logcat统一管理类
public class LogUtil {

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "Logcat";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, getContent(msg));
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, getContent(msg));
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, getContent(msg));
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, getContent(msg));
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getContent(msg));
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getContent(msg));
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getContent(msg));
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, getContent(msg));
    }

   // int place, Object... args
    private static String getContent(String msg) {

        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace(), 4);
            return sourceLinks + String.format(msg, "");
        } catch (Throwable throwable) {
            return msg;
        }
    }

    private static String getNameFromTrace(StackTraceElement[] traceElements, int place) {
        StringBuilder taskName = new StringBuilder();
        if (traceElements != null && traceElements.length > place) {
            StackTraceElement traceElement = traceElements[place];
            taskName.append(traceElement.getMethodName());
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")");
        }
        return taskName.toString();
    }

}