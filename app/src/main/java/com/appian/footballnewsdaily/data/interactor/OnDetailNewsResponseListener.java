package com.appian.footballnewsdaily.data.interactor;

public interface OnDetailNewsResponseListener<T> {
    void onSuccess(T data);

    void onFailure(String error);
}
