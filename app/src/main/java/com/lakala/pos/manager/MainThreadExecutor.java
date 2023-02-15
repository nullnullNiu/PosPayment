package com.lakala.pos.manager;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @author: xucongyun
 * @e-mail: xucongyun@gmail.com
 * @date: 2021/4/26
 * @description : 主线程线程池使用
 */
public class MainThreadExecutor implements Executor {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
