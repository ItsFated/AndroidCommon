package com.im_hero.androidcommon.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.im_hero.androidcommon.R;

/**
 * 带有 Toolbar 的 Activity 的基类
 * @author Jason
 * @version 1.0
 */

public abstract class ToolbarActivity extends BaseActivity {
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
