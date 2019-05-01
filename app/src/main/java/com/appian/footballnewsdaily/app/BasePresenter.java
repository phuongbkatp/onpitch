package com.appian.footballnewsdaily.app;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V> {
    private WeakReference<V> mView;

    public void attachView(V view) {
        mView = new WeakReference<>(view);
    }

    public void detachView() {
        mView.clear();
    }

    protected V getView() {
        return mView.get();
    }
}
