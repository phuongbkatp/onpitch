package com.appian.footballnewsdaily.app.followsetting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.appian.footballnewsdaily.R;
import com.appian.footballnewsdaily.app.BaseActivity;
import com.appian.footballnewsdaily.app.MainActivity;
import com.appian.footballnewsdaily.service.notification.NotificationFactory;
import com.appian.footballnewsdaily.util.Utils;

import static com.appian.footballnewsdaily.Constant.EXTRA_KEY_FOLLOW_SETTING;
import static com.appian.footballnewsdaily.Constant.EXTRA_KEY_FOLLOW_SETTING_TEAM;

public class FollowActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_activity);

        Intent intent = getIntent();
        String follow_category = intent.getStringExtra(EXTRA_KEY_FOLLOW_SETTING);

        FragmentManager fm = getSupportFragmentManager();

        if (follow_category.equals(EXTRA_KEY_FOLLOW_SETTING_TEAM)) {
            fm.beginTransaction().replace(R.id.fragment_container, FollowSettingFragment.newInstance(1)).commit();
        } else {
            fm.beginTransaction().replace(R.id.fragment_container, FollowSettingFragment.newInstance(0)).commit();
        }

        View btnBackArrow = findViewById(R.id.img_back_arrow);
        btnBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
