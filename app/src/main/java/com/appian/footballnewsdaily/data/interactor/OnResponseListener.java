package com.appian.footballnewsdaily.data.interactor;

public interface OnResponseListener<T> {
    void onSuccess(T data);

    void onFailure(String error);
}
