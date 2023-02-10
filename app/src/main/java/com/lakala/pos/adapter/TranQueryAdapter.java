package com.lakala.pos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lakala.pos.R;
import com.lakala.pos.bean.TranQueryBean;
import java.util.ArrayList;
import java.util.List;

public class TranQueryAdapter extends BaseAdapter {

    private List<TranQueryBean.Records> data_list = new ArrayList<>();
    private Context mContext;
    ViewHolder viewHolder;

    public TranQueryAdapter(Context context, List<TranQueryBean.Records> data_list) {
        this.mContext = context;
        this.data_list = data_list;
    }

    public void onDateChange(List<TranQueryBean.Records> dataBeanList) {
        this.data_list = dataBeanList;
    }

    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tran_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tranCode.setText(data_list.get(position).getOrderNo());
        viewHolder.createTime.setText(data_list.get(position).getCreateTime());
        viewHolder.amount.setText(data_list.get(position).getAmount()+"");


        return convertView;
    }


    public static class ViewHolder {
        TextView amount, createTime, tranCode;

        ViewHolder(View view) {
            this.tranCode = view.findViewById(R.id.tv_tran_code);
            this.createTime = view.findViewById(R.id.tv_data);
            this.amount = view.findViewById(R.id.monye_tv);
        }
    }


}
