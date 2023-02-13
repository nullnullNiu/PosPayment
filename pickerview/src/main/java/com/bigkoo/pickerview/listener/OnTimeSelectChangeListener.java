package com.bigkoo.pickerview.listener;

import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.view.WheelTime;

import java.util.Date;

/**
 * Created by xiaosong on 2018/3/20.
 */

public interface OnTimeSelectChangeListener {

    void onTimeSelectChanged(Date date);

    void cancelClick();


//    void sendDefaultFocus(Button btnSubmit, Button btnCancel, View yearView, View monthView, View dayView);

//    void sendDefaultFocus(Button btnSubmit, Button btnCancel, View yearView, View monthView, View dayView);
}
