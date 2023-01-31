package com.lakala.pos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;


import com.lakala.pos.ui.MyApplication;

import java.util.Set;

public class PreferencesUtils {

    public static final void setPreference(String key, String value) {
        Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static final void setPreference(String key, int value) {
        Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * long類型
     *
     * @param key
     * @param value
     */
    public static final void setPreferenceLoging(String key, long value) {
        Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取long
     *
     * @param key
     * @param defaultvalue
     * @return
     */
    public static final long getPreferenceLoging(String key, long defaultvalue) {
        return getPreferences().getLong(key, defaultvalue);
    }


    /**
     * 设置用户是否登录过
     *
     * @param key
     * @param value
     */
    public static final void setPreferenceLoging(String key, boolean value) {
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 用户是否是调解员
     *
     * @param key
     * @param value
     */
    public static final void setPreferenceIsMediator(String key, boolean value) {
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 设置用户个人喜好
     *
     * @param key
     * @param value
     */
    public static final void setPreferenceLike(String key, boolean value) {
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取用户登录记录
     *
     * @param key
     * @param defaultvalue
     * @return
     */
    public static final boolean getPreferenceLoging(String key, boolean defaultvalue) {
        return getPreferences().getBoolean(key, defaultvalue);
    }



    public static final void setPreference(String key, Set<String> value) {
        Editor editor = getPreferences().edit();
        editor.putStringSet(key, value);
        editor.apply();
        editor.clear();
    }

    public static final Integer getPreferenceInt(String key, int defaultvalue) {
        return getPreferences().getInt(key, defaultvalue);
    }

    public static final String getPreferenceString(String key, String defaultvalue) {
        return getPreferences().getString(key, defaultvalue);
    }

    public static final boolean getPreferenceBoolean(String key, boolean defaultvalue) {
        return getPreferences().getBoolean(key, defaultvalue);
    }

    public static final void clear() {
        getPreferences().edit().clear().commit();
    }

    private static final SharedPreferences getPreferences() {
        SharedPreferences pre = MyApplication.getContext()
                .getSharedPreferences(
                        "POS_PAY",
                        Build.VERSION.SDK_INT > 10 ? Context.MODE_MULTI_PROCESS
                                : Context.MODE_PRIVATE);
        return pre;
    }


    /**
     * 设置用户是否静音
     *
     * @param key
     * @param value
     */
    public static final void setPreferenceVioce(String key, boolean value) {
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取用户静音
     *
     * @param key
     * @param defaultvalue
     * @return
     */
    public static final boolean getPreferenceVoice(String key, boolean defaultvalue) {
        return getPreferences().getBoolean(key, defaultvalue);
    }


    /**
     * 删除保存的 key值数据
     *
     * @param key
     * @return
     */
    public static final void deletalPerferceDtat(String key){
        getPreferences().edit().remove(key).apply();
    }

}
