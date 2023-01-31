package com.lakala.pos.presente;



import com.lakala.pos.http.ModelAPI;
import com.lakala.pos.http.net.IScanningApi;
import com.lakala.pos.utils.LogUtil;
import com.lakala.pos.utils.NetworkUtlis;
import com.lakala.pos.utils.ToastUtil;

import java.lang.ref.WeakReference;



public abstract class BasePresenter<T> {
    IScanningApi modelAPI = new ModelAPI();
    /**
     * 当内存不足释放内存，view的弱引用
     */
    protected WeakReference<T> mViewRef;

    /**
     * 绑定 P with V
     *
     * @param view
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    /**
     * 解除绑定 P with V
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
            LogUtil.i("已经GC");
        }
    }

    /**
     * 获取View的方法
     *
     * @return 当前关联的View
     */
    public T getView() {
        if (mViewRef == null) return null;
        return mViewRef.get();//加入mViewRef没有被回收，引用mViewRef对象
    }


    public boolean noNetWork(){
        if (!NetworkUtlis.isNetworkAvailable()) {
            ToastUtil.showToast("网络异常请检查网络连接");
            return true;
        }
        return false;
    }

}
