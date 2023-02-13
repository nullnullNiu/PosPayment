package com.lakala.pos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.lakala.pos.R;
import com.lakala.pos.bean.SummaryBean;


import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private List<SummaryBean.Data> list;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView domestic_card_tv, overseas_card_tv,domestic_no_tv,overseas_no_tv,domestic_amount_tv,overseas_amount_tv; //收款
        TextView refund_domestic_card_tv, refund_overseas_card_tv,refund_domestic_no_tv,refund_overseas_no_tv,refund_domestic_amount_tv,refund_overseas_amount_tv; //退款


        public ViewHolder(View view) {
            super(view);
            this.domestic_card_tv = view.findViewById(R.id.domestic_card_tv);
            this.overseas_card_tv = view.findViewById(R.id.overseas_card_tv);
            this.domestic_no_tv = view.findViewById(R.id.domestic_no_tv);
            this.overseas_no_tv = view.findViewById(R.id.overseas_no_tv);
            this.domestic_amount_tv = view.findViewById(R.id.domestic_amount_tv);
            this.overseas_amount_tv = view.findViewById(R.id.overseas_amount_tv);

            this.refund_domestic_card_tv = view.findViewById(R.id.refund_domestic_card_tv);
            this.refund_overseas_card_tv = view.findViewById(R.id.refund_overseas_card_tv);
            this.refund_domestic_no_tv = view.findViewById(R.id.refund_domestic_no_tv);
            this.refund_overseas_no_tv = view.findViewById(R.id.refund_overseas_no_tv);
            this.refund_domestic_amount_tv = view.findViewById(R.id.refund_domestic_amount_tv);
            this.refund_overseas_amount_tv = view.findViewById(R.id.refund_overseas_amount_tv);

        }
    }

    public SummaryAdapter(List<SummaryBean.Data> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.service_name.setText(list.get(position).getTitle());
        holder.fee_tv.setText(list.get(position).getFee());
        holder.time_tv.setText(list.get(position).getAdd_time());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}