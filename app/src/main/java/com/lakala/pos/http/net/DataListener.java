package com.lakala.pos.http.net;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2018/3/20
 */

public interface DataListener<T> {
    void onSuccess(T result);

    void onFailure(Throwable e, String s);
}
