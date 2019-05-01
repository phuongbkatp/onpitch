package com.appian.footballnewsdaily.app;

import android.support.v4.app.Fragment;

public interface StateFragment {
    void onCreated(Fragment fragment);
    void onDestroyed(Fragment fragment);
}
