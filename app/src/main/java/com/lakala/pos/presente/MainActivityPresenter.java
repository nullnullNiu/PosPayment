package com.lakala.pos.presente;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.bean.CreateOrderResultBean;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.interfaces.IDeviceBindView;
import com.lakala.pos.interfaces.IHomeView;
import com.lakala.pos.ui.activity.TranQueryActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityPresenter extends BasePresenter<IHomeView> {




    /**
     * 根据订单状态查询订单
     */
    public void queryOrders(int pageNum) {
        if (noNetWork()) {
            return;
        }

        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("status", "-1");//-1 全部 0已收款/未开票 1已上送订单/已填报 2已开票 3已退单
//            object.put("deviceCode", Global.DEVICE_ID);//设备号
            object.put("deviceCode", "123");//设备号
            object.put("pageNum", pageNum);
            object.put("voucherNo", "");
            object.put("pageSize", 50);
            object.put("startDate", "");
            object.put("endDate", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.i("加载 pageNum: " + pageNum );
        modelAPI.queryOrders(object.toString(), new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("根据订单状态查询订单 接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();
                    TranQueryBean tranQueryBean = gson.fromJson(result, TranQueryBean.class);
                    getView().getQaCategoryList(tranQueryBean);
                } else {
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                LogUtil.e("error,throwable:" + e.getMessage() + ",message:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
            }
        });
    }



    /**
     * 创建订单
     */
    public void onCreateOrder(int term_type,String info) {
        if (noNetWork()) {
            return;
        }
        modelAPI.createOrder(info, new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("请求创建订单 接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
//                    Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();
                    CreateOrderResultBean bean = new Gson().fromJson(result,CreateOrderResultBean.class);
                    getView().getCreateQrderResult(term_type,bean);

                } else {
                    String msg = jsonObject.get("message").getAsString();
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                LogUtil.e("error,throwable:" + e.getMessage() + ",message:" + s);
                ToastUtil.showToast("服务端数据异常：" + s);
            }
        });
    }


}
