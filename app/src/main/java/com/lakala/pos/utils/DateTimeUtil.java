package com.lakala.pos.utils;

import android.annotation.SuppressLint;
import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
    
 // 时间格式
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMssSSS = "yyyyMMddHHmmssSSS";//有毫秒时间
    
    /**
     * 格式化时间，将YYYYMMDDHHSS格式化为 YYYY/MM/DD HH:SS
     * 
     * @return String 格式：YYYYMMDDHHSS
     *            格式： YYYY/MM/DD HH:SS
     * @return
     * @createtor: Administrator
     * @date:2014-2-19 下午04:07:13
     */
    public static String formatTime(String time) {
        if (time.length() != 14) {
            return time;
        }

        return time.substring(0, 4) + "/" + time.substring(4, 6) + "/"
                + time.substring(6, 8) + " " + time.substring(8, 10) + ":"
                + time.substring(10, 12)+":"+time.substring(12,14);
    }

    /**
     * 将毫秒转换成yyyy-MM-dd格式日期
     * @param time 毫秒时间
     * @return 年-月-日
     */
    @SuppressLint("SimpleDateFormat") 
    public static String formatMillisecondDate(long time) {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(time);
        String strDate=format.format(date);
        return strDate;
    }
    
    /**
     * 将毫秒转换成HH:mm:ss格式时间
     * @param time 毫秒时间
     * @return 时:分:秒
     */
    @SuppressLint("SimpleDateFormat") 
    public static String formatMillisecondTimeSecond(long time){
        String strTime="";
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        Date date=new Date(time);
        strTime=format.format(date);
        return strTime;
    }

    
    /**
     * 将毫秒转换成HH:mm格式时间
     * @param time 毫秒时间
     * @return 时:分:秒
     */
    @SuppressLint("SimpleDateFormat") 
    public static String formatMillisecondTime(long time){
        String strTime="";
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        Date date=new Date(time);
        strTime=format.format(date);
        return strTime;
    }
    
    /**
     * 设置系统时间
     * @param year
     * @param month 0~11
     * @param day
     */
    public static void setCurrentDateTime(int year,int month,int day){
        Calendar c = Calendar.getInstance();
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, day);
        long when = c.getTimeInMillis();
 
        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }
    }
    
    /**
     * 设置系统时间
     * @param year
     * @param month 0~11
     * @param day
     * @param minute
     * @param hour
     */
    public static void setCurrentDateTime(int year,int month,int day,int hour,int minute){
        Calendar c = Calendar.getInstance();
        
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month-1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.HOUR, hour);
        long when = c.getTimeInMillis();
 
        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }
    }

    /**
     * 设置系统时间
     * @param hour
     * @param minute
     */
    public static void setCurrentDateTime(int hour,int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        long when = c.getTimeInMillis();
 
        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }
    }
    
    /**
     * 获取当前的系统时间字符串
     * 
     * @param dateFormat
     *            日期格式，例如格式yyyyMMddhhmmss
     * @return
     */
    @SuppressLint("SimpleDateFormat") 
    public static String getCurrentDate(String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(new Date());
    }
}
