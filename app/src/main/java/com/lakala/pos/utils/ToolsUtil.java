package com.lakala.pos.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Camera;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2019/12/21
 */

public class ToolsUtil {


    // 将秒转化成小时分钟秒
    public static String FormatMiss(long miss) {
        String hh = miss / 3600 > 9 ? miss / 3600 + "" : "0" + miss / 3600;
        String mm = (miss % 3600) / 60 > 9 ? (miss % 3600) / 60 + "" : "0" + (miss % 3600) / 60;
        String ss = (miss % 3600) % 60 > 9 ? (miss % 3600) % 60 + "" : "0" + (miss % 3600) % 60;
        return hh + ":" + mm + ":" + ss;
    }

    /**
     * 计费倍数获取
     *
     * @param second
     * @return
     */
    public static int getCharging(int second) {
        int charging = 0;
        charging = second / 3600;
        return charging + 1;
    }

    /**
     * 两点直线距离算法
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getDistance(double x1, double y1, double x2, double y2) {
        double d = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        return Math.sqrt(d);
    }

    /**
     * 格式化double数据--小数点后保留两位
     *
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.DOWN);
        return nf.format(d);
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime() / 1000;
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.parseLong(s));
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取年
     *
     * @return
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 将秒转换为小时，分，秒
     *
     * @param times
     * @return
     */
    public static String switchTiem(int times) {
        String result = null;
        long time = (long) times;
        long hour = time / 3600;
        long minute = time % 3600 / 60;
        long second = time % 60;
        result = hour + ":" + minute + ":" + second;
        return result;
    }

    /**
     * 将秒转换为分钟
     *
     * @param times
     * @return
     */
    public static String switchTiemMin(int times) {
        String result = null;
        long time = (long) times;
        long Min = time / 60;
        if (Min == 0) Min = 1;
        result = Min + "min";
        return result;
    }

    /**
     * 判断service是否在运行
     *
     * @param context
     * @param ServicePackageName
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServicePackageName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ServicePackageName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断相机是否可用
     *
     * @return true, 相机驱动可用，false,相机驱动不可用
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            mCamera.release();
            mCamera = null;
        }
        return canUse;
    }


    /**
     * 判断手机号是否符合规范
     *
     * @param phoneNo 输入的手机号
     * @return
     */
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }

            Pattern p = Pattern.compile("^1(3|4|5|6|7|8|9)\\d{9}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }


}
