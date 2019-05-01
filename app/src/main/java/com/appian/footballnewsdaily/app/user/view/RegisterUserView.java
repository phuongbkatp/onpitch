package com.appian.footballnewsdaily.app.user.view;

import com.appnet.android.football.fbvn.data.SignInAccount;

public interface RegisterUserView {
    void registerSuccess(SignInAccount data);
    void registerFail();
}
