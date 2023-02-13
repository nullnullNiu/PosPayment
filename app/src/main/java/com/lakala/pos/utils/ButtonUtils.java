package com.lakala.pos.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

/**
 *  避免按钮多次点击重复打开界面
 *
 */
public class ButtonUtils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 3000;//设置三秒
    private static final int MIN_CLICK_DELAY_SECOND = 500;//设置一秒
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    public static boolean isFastTimeClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_SECOND) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    public static boolean isCustomTimeClick(int time) {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= time) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return true 空，false非空
     */
    public static boolean isBlank(String str) {
        boolean b;
        if (str == null || "".equals(str) || "null".equals(str) || "NULL".equals(str)) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    /**
     * 调出打电话界面
     *
     * @param context context
     * @param phone   电话
     */
    public static void callPhone(Context context, String phone) {
        Uri data = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, data);
        context.startActivity(intent);
    }

    /**
     * 判断某个activity是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }


    /**
     * 判断某个界面是否在前台,返回true，为显示,否则不是
     */
    public static boolean isForeground(Activity context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());

            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {

        }
    }
}
