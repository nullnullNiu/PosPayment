package com.lakala.pos.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyApplication extends Application{
    static Context context;
    private List<Activity> mActivityList = new CopyOnWriteArrayList<Activity>();
    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

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

}
