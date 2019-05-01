package com.appian.footballnewsdaily.app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.appian.footballnewsdaily.data.app.Language;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Language.onAttach(newBase));
    }
}
