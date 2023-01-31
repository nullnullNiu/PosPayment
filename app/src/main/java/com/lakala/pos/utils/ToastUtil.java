package com.lakala.pos.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.lakala.pos.ui.MyApplication;


public class ToastUtil {

    private static Toast toast;
    private static boolean isDebug = true;

    public synchronized static void showToast(String text) {
        showToast(MyApplication.context(), text, Toast.LENGTH_LONG);
    }
    public synchronized static void showToast(String text,boolean isCenter) {
        showToast(MyApplication.context(), text, Toast.LENGTH_LONG,isCenter);
    }
    public synchronized static void showToast(Context context, String text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);//如果不为空，直接改变当前Toast的文本内容
        }
        toast.show();
    }
    public synchronized static void showToast(Context context, String text, int duration,boolean isCenter) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);//如果不为空，直接改变当前Toast的文本内容
        }
        if(isCenter) {
            toast.setGravity( Gravity.CENTER, 0, 0);
        }
        toast.show();
    }

    public synchronized static void showDebugToast(String text) {
        if (isDebug) {
            toast = Toast.makeText(MyApplication.context(), text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

}
