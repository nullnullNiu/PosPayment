package com.lakala.pos.http.factory;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.lakala.pos.ui.MyApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date： 9:58
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截的cookie保存在originalResponse中
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                String Cookie = header.substring(0, header.indexOf(";"));//截取@之前的字符串
                Log.e("TAG", "拦截的cookie是：" + Cookie);
                cookies.add(Cookie);
            }
            //保存的sharepreference文件名为cookieData
            SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("cookieData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookies);
            editor.commit();
        }
        return originalResponse;
    }
}
