package com.lakala.pos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lakala.pos.R;
import com.lakala.pos.bean.BossInfoBean;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.ui.activity.TranQueryActivity;

import java.util.List;

public class BossPersonnelAdapter extends RecyclerView.Adapter<BossPersonnelAdapter.ViewHolder> {
    private List<BossInfoBean.Data> listdata;

    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.boss_tv);
        }
    }

    public BossPersonnelAdapter(Context context , List<BossInfoBean.Data> list) {
        this.mContext = context;
        this.listdata = list;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personnel_view, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String name = listdata.get(position).getBossName();
        String phone = listdata.get(position).getBossPhone();
        holder.name.setText(name);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(name,phone);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }




    public interface OnItemClickListion {
        void onItemClick(String name,String phone);
    }
    private OnItemClickListion mOnItemClickListener;

    public void setOnItemClickListener (OnItemClickListion  mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}