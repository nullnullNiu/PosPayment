package com.lakala.pos.sqlite;


import android.content.ContentValues;

import com.lakala.pos.ui.MyApplication;
import com.lakala.pos.utils.LogUtil;


public class SaveDataToDatabase {

    private volatile static SaveDataToDatabase mInstance;
    private SaveDataToDatabase() {
    }
    public static SaveDataToDatabase getInstance() {
        if (mInstance == null) {
            synchronized (SaveDataToDatabase.class) {
                if (mInstance == null) {
                    mInstance = new SaveDataToDatabase();
                }
            }
        }
        return mInstance;
    }

    public void onSaveData(int type, String name,String pwd) {
            LogUtil.i("数据库存储，用户信息" + type + "   " + name + "   " + pwd);
            LogUtil.i("===========================================数据库存储，用户信息========================================" + "[" + Thread.currentThread().getName() + "线程--");
            try {
                ContentValues values = new ContentValues();
                values.put("type", type);
                values.put("name",name);
                values.put("password",pwd);

                //insert（）方法中第一个参数是表名，第二个参数是表示给表中未指定数据的自动赋值为NULL。第三个参数是一个ContentValues对象
                MyApplication.db.insert("User_Info",null,values);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.i("数据库存入数据失败！"+e.getMessage());
            }
        }


    }
