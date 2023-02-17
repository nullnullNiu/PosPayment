package com.lakala.pos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.lakala.pos.R;
import com.lakala.pos.bean.UserInfoBean;
import com.lakala.pos.utils.LogUtil;
import java.util.List;

public class PersonnelAdapter extends RecyclerView.Adapter<PersonnelAdapter.ViewHolder> {
    private List<UserInfoBean> listdata;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.boss_tv);
        }
    }

    public PersonnelAdapter(Context context ,  List<UserInfoBean> list) {
        this.mContext = context;
        this.listdata = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personnel_view, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        LogUtil.i(listdata.get(position).getLoginName());
        String name = listdata.get(position).getLoginName();
        String pwd = listdata.get(position).getPassword();
        holder.name.setText(name);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onPersItemClick(name,pwd);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public interface OnItemClickListion {
        void onPersItemClick(String name,String pwd);
    }
    private OnItemClickListion mOnItemClickListener;

    public void setOnItemClickListener (OnItemClickListion mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }



}