package com.lakala.pos.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<View>  pagelist;


    public ViewPagerAdapter(List<View> list) {
        this.pagelist = list;
    }

    @Override
    public int getCount() {
        return pagelist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//
//        new Thread( ) {
//            @Override
//            public void run() {
//
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                super.run();
//            }
//        }.start();

        container.addView(pagelist.get(position));
        return pagelist.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(pagelist.get(position));
    }
}
