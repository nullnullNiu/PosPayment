package com.lakala.pos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lakala.pos.R;
import com.lakala.pos.bean.EnterpriseInfoBean;
import com.lakala.pos.utils.LogUtil;

import java.util.List;

public class SpinnerNameAdapter extends BaseAdapter {

//    List list;
//    EnterpriseInfoBean.Data data;
    ViewHolder viewHolder;
    private List<EnterpriseInfoBean.Data> data;
    private Context context;
    private int type;

    public SpinnerNameAdapter(Context context,  List<EnterpriseInfoBean.Data> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_spinner_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(data.get(position).getName());
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i("名称  "+data.get(position).getName());
                LogUtil.i("税号  "+data.get(position).getTaxId());
                LogUtil.i("地址  "+data.get(position).getLocation());
                mOnItemClickListener.onItemClick(data.get(position).getName(),data.get(position).getTaxId(),data.get(position).getLocation());
            }
        });

        return convertView;
    }


    public static class ViewHolder {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            name = itemView.findViewById(R.id.name_tv);
        }
    }

    public interface OnItemClickListion {
        void onItemClick(String selectName,String selectTaxId,String selectLocation);
    }
    private OnItemClickListion mOnItemClickListener;

    public void setOnItemClickListener (OnItemClickListion  mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
