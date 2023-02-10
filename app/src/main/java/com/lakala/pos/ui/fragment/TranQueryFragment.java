package com.lakala.pos.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lakala.pos.R;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.http.ModelAPI;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.http.net.IScanningApi;
import com.lakala.pos.presente.TransPresenter;
import com.lakala.pos.ui.activity.TranQueryActivity;
import com.lakala.pos.ui.activity.TransDetailsActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.NetworkUtlis;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class TranQueryFragment extends Fragment implements AbsListView.OnScrollListener {

    ListView list_view;
    private int state;
    private int lineNum = 2;
    IScanningApi modelAPI = new ModelAPI();


    private int pageNum = 1;

    View footerView, footerLoadView;
    private int totalItemCount; // 列表适配器中的项目数
    private int lastVisibleItem; // 数据集最后一项的索引
    private boolean isScrollLoad = true; // 滑动时是否要加载数据


    LinearLayout trans_list_layout;

    public static TranQueryFragment getInstance(int id) {
        TranQueryFragment sf = new TranQueryFragment();
        sf.state = id;
        return sf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tran_query_list, null);
        list_view = v.findViewById(R.id.list_view);
        list_view.setVerticalScrollBarEnabled(false);
        list_view.setFastScrollEnabled(false);
        footerView = inflater.inflate(R.layout.view_footer, null);
        footerLoadView = inflater.inflate(R.layout.view_footer_load, null);

        trans_list_layout = v.findViewById(R.id.trans_list_layout);
        trans_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TransDetailsActivity.class));
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            queryOrders();
        }
    }


    /**
     * 根据订单状态查询订单
     */
    public void queryOrders() {
        if (noNetWork()) {
            return;
        }
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("status",state);//0已收款/未开票 1已上送订单/已填报 2已开票 3已退单
            object.put("pageNum",1);
            object.put("pageSize",10);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        modelAPI.queryOrders(object.toString(), new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("根据订单状态查询订单 接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {

                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    TranQueryBean tranQueryBean = gson.fromJson(result,TranQueryBean.class);

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
     * 滑动状态改变时被调用
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        LogUtil.i("数据集最后一项的索引:  " + lastVisibleItem + "      列表适配器中的项目数:  " + totalItemCount + " isScroll=" + isScrollLoad);
        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE && isScrollLoad) {
            pageNum++;
            LogUtil.i("加载 第 " + pageNum + "  页。。。");
//            getMediatelist(state, pageNum, true);
        }
    }

    /**
     * 滑动时被调用
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }



    public boolean noNetWork(){
        if (!NetworkUtlis.isNetworkAvailable()) {
            ToastUtil.showToast("网络异常请检查网络连接");
            return true;
        }
        return false;
    }


}
