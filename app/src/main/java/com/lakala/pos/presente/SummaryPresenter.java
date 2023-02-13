package com.lakala.pos.presente;

import android.content.Context;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.LoginInfo;
import com.lakala.pos.bean.SummaryBean;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.ISummaryView;
import com.lakala.pos.interfaces.ITransView;
import com.lakala.pos.utils.ButtonUtils;
import com.lakala.pos.utils.DateUtils;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import java.util.Date;

public class SummaryPresenter extends BasePresenter<ISummaryView> {

    public TimePickerView mDatePicker = null;
    /**
     * 日期选择器
     *
     * @param context
     */

    public void onDateSelection(Context context, int type) {

        if (null != mDatePicker && mDatePicker.isShowing) {
            mDatePicker.dismiss();
        }
        mDatePicker = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                LogUtil.e("获取到的时间是====" + DateUtils.date2String(date, DateUtils.yyyyMMdd.get()));
                if (ButtonUtils.isFastTimeClick()) {
                    final String selectTime = DateUtils.date2String(date, DateUtils.yyyyMMdd.get());
                    getView().getDate(selectTime, type);
                }
            }
        }).setOutSideCancelable(false).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        LogUtil.i("onTimeSelectChanged");
                    }

                    @Override
                    public void cancelClick() {
                        LogUtil.i("cancelClick");
                    }
                })
                .setTitleText("日期选择")
                .build();

        mDatePicker.show();
    }


    /**
     * 交易汇总
     */
    public void onCensus(String info) {
        if (noNetWork()) {
            return;
        }
        modelAPI.census(info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求交易汇总返回信息： " + result);

                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    SummaryBean summaryBean = new Gson().fromJson(result,SummaryBean.class);
                    getView().onCensusResult(summaryBean);
                } else {
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                LogUtil.e("error,throwable:" + e.getMessage() + ",msg:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
            }
        });


    }




}
