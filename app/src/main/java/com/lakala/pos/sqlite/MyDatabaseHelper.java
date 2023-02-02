package com.lakala.pos.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lakala.pos.utils.LogUtil;

/**
 * Created by jh03 on 2018/9/18.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper{

    public static final String CREATE_USER_INFO = "create table User_Info(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "name text,"+
            "password real," +
            "DataTime real,"+
            "type text,"+
            "Other text)";


    private Context mContext;

    //构造方法：第一个参数Context，第二个参数数据库名，第三个参数cursor允许我们在查询数据的时候返回一个自定义的光标位置，一般传入的都是null，第四个参数表示目前库的版本号（用于对库进行升级）
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory , int version){
        super(context,name ,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_USER_INFO);
        //创建成功
      //  Toast.makeText(mContext, "创建数据库 成功！", Toast.LENGTH_SHORT).show();
        LogUtil.i("MyDatabaseHelper","创建数据库 成功！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}