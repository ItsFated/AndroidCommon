package com.im_hero.androidcommon.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity 的基类
 * @author Jason
 * @version 1.0
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
    }

    protected abstract int getContentViewResId();
}
