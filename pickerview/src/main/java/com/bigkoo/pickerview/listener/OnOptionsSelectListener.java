package com.bigkoo.pickerview.listener;

import android.view.View;

/**
 * Created by xiaosong on 2018/3/20.
 */

public interface OnOptionsSelectListener {

    void onOptionsSelect(int options1, int options2, int options3, int options4, View v);
    void onCancelSelect();
//    void onOptionsSelect(int options1, int options2, int options3, View v);

}
