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

public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelAdapter.ViewHolder> {
    private List<SummaryBean.Data> listdata;
    private SummaryBean.Data list;
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

    public PersonnelAdapter(Context context , SummaryBean.Data list) {
        this.mContext = context;
        this.list = list;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // 收款
        if (list.getREFUND().get(position).getType().equals("WECHAT ")){ // 微信
            holder.domestic_no_tv.setText(list.getREFUND().get(position).getNum());
            holder.domestic_amount_tv.setText(list.getREFUND().get(position).getAmount());

        } else {
            holder.overseas_no_tv.setText(list.getREFUND().get(position).getNum());
            holder.overseas_amount_tv.setText(list.getREFUND().get(position).getAmount());
        }


        // 退款
        if (list.getSUCCESS().get(position).getType().equals("WECHAT ")) { // 微信

            holder.refund_domestic_no_tv.setText(list.getSUCCESS().get(position).getNum());
            holder.refund_domestic_amount_tv.setText(list.getSUCCESS().get(position).getAmount());
        } else {

            holder.refund_overseas_no_tv.setText(list.getSUCCESS().get(position).getNum());
            holder.refund_overseas_amount_tv.setText(list.getSUCCESS().get(position).getAmount());
        }
    }

    @Override
    public int getItemCount() {
        if (list.getSUCCESS().size() > list.getREFUND().size()){
            return list.getSUCCESS().size();
        }
        return list.getREFUND().size();
    }


}