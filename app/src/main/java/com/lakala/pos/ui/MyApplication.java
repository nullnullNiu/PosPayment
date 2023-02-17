package com.lakala.pos.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import androidx.multidex.MultiDex;
import com.lakala.pos.manager.ThreadPoolManager;
import com.lakala.pos.sqlite.MyDatabaseHelper;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyApplication extends Application {
    static Context context;
    public static SQLiteDatabase db;
    public static MyDatabaseHelper myDatabaseHelper;
    private List<Activity> mActivityList = new CopyOnWriteArrayList<Activity>();

    public static Context context() {
        return context;
    }

    public static Application getContext() {
        return (Application) context;
    }


    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public String getActivity() {
        if (mActivityList != null && mActivityList.size() != 0) {
            String className = mActivityList.get(mActivityList.size() - 1).getLocalClassName().toString();
            return className;
        }
        return null;
    }

    public void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        onInfoDatabase(); //子线程中创建数据库
        // 初始化MultiDex
        MultiDex.install(this);
    }


    public void onInfoDatabase() {
        ThreadPoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                myDatabaseHelper = new MyDatabaseHelper(context, "user.db", null, 1);
                //创建或打开现有的数据库
                db = myDatabaseHelper.getWritableDatabase();
            }
        });
    }

}
