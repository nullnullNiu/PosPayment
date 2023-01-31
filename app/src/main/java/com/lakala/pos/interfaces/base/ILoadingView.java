package com.lakala.pos.interfaces.base;

/**
 * Author：TobiasLee
 * E-mail：tobiaslee0810@126.com
 * Date：2018/3/19
 */

public interface ILoadingView<T> extends IView<T> {
    void showLoading();

    void hideLoading();
}
