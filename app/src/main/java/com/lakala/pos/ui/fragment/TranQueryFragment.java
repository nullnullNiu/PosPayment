package com.lakala.pos.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import com.lakala.pos.adapter.TranQueryAdapter;
import com.lakala.pos.bean.TranQueryBean;
import com.lakala.pos.http.ModelAPI;
import com.lakala.pos.http.net.DataListener;
import com.lakala.pos.http.net.IScanningApi;
import com.lakala.pos.ui.activity.TransDetailsActivity;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.NetworkUtlis;
import com.lakala.pos.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TranQueryFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {


    private int state;
    IScanningApi modelAPI = new ModelAPI();

    View footerView, footerLoadView;
    private int totalItemCount; // 列表适配器中的项目数
    private int lastVisibleItem; // 数据集最后一项的索引
    private boolean isScrollLoad = true; // 滑动时是否要加载数据

    private int pageNum = 1;
    private int pageSiz = 10;

    TextView tvEmpty;
    ListView listView;

    TranQueryAdapter tranQueryAdapter;
    private List<TranQueryBean.Records> dataBeanList = new ArrayList<>();

    public static TranQueryFragment getInstance(int id) {
        TranQueryFragment sf = new TranQueryFragment();
        sf.state = id;
        return sf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tran_query_list, null);
        listView = v.findViewById(R.id.list_view);
        tvEmpty = v.findViewById(R.id.tv_empty);
        listView.setVerticalScrollBarEnabled(false);
        listView.setFastScrollEnabled(false);
        footerView = inflater.inflate(R.layout.view_footer, null);
        footerLoadView = inflater.inflate(R.layout.view_footer_load, null);

        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消点击阴影效果
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            pageNum = 1;
            dataBeanList.clear();
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
            object.put("status", state);//0已收款/未开票 1已上送订单/已填报 2已开票 3已退单
            object.put("pageNum", pageNum);
            object.put("pageSize", pageSiz);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtil.i("加载 status :" + state + "     pageNum: " + pageNum);
        modelAPI.queryOrders(object.toString(), new DataListener<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("根据订单状态查询订单 接口返回： " + result);
                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                int code = jsonObject.get("code").getAsInt();
                if (code == 0) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create();
                    TranQueryBean tranQueryBean = gson.fromJson(result, TranQueryBean.class);
                    getQaCategoryList(tranQueryBean);

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


    private void getQaCategoryList(TranQueryBean tranQueryBean) {
        tvEmpty.setVisibility(View.GONE);
        LogUtil.e("size ===========" + tranQueryBean.getData().getRecords().size());
        listView.removeFooterView(footerLoadView);

        LogUtil.i("pageNum:  " + pageNum + "      totalItemCount:  " + totalItemCount);
        if (pageNum > 1 && totalItemCount < 10) {
            listView.addFooterView(footerView);
            tranQueryAdapter.notifyDataSetChanged();
            isScrollLoad = false;
            return;
        }

        LogUtil.i("pageNum:  " + pageNum + "      totalItemCount:  " + totalItemCount);
        if (null != tranQueryBean && null != tranQueryBean.getData() && 0 < tranQueryBean.getData().getRecords().size()) {

            dataBeanList.addAll(tranQueryBean.getData().getRecords());

            if (tranQueryAdapter == null) {
                tranQueryAdapter = new TranQueryAdapter(getActivity(), dataBeanList);
            }

            listView.setAdapter(tranQueryAdapter);

            listView.setSelection(0);

            tranQueryAdapter.notifyDataSetChanged();


        } else {
            LogUtil.i("暂无交易信息");
            tvEmpty.setVisibility(View.VISIBLE);
//            ToastUtil.showToast("暂无交易信息");

        }

    }


    /**
     * 滑动状态改变时被调用
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        LogUtil.i("数据集最后一项的索引:  " + lastVisibleItem + "      列表适配器中的项目数:  " + totalItemCount + " isScroll=" + isScrollLoad);

        if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE && isScrollLoad) {
            listView.addFooterView(footerLoadView);
            pageNum++;
            LogUtil.i("加载 第 " + pageNum + "  页。。。");
            queryOrders();
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


    public boolean noNetWork() {
        if (!NetworkUtlis.isNetworkAvailable()) {
            ToastUtil.showToast("网络异常请检查网络连接");
            return true;
        }
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pageNum = 1;
        LogUtil.e("===onDestroy===");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.e("订单号 = " + dataBeanList.get(position).getOrderNo());
        Intent intent = new Intent(getActivity(), TransDetailsActivity.class);
        intent.putExtra("orderNo", dataBeanList.get(position).getOrderNo());
        startActivity(intent);
    }
}
