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

import com.lakala.pos.R;
import com.lakala.pos.http.ModelAPI;
import com.lakala.pos.http.net.IScanningApi;
import com.lakala.pos.ui.activity.TranQueryActivity;
import com.lakala.pos.ui.activity.TransDetailsActivity;
import com.lakala.pos.utils.LogUtil;


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


}
