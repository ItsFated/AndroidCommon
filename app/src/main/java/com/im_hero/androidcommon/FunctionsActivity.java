package com.im_hero.androidcommon;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;

import com.im_hero.adapter.ExpandableAdapter;
import com.im_hero.adapter.IData;
import com.im_hero.androidcommon.common.ToolbarActivity;
import com.im_hero.androidcommon.data.ItemAdapterDemos;

import java.util.ArrayList;

public class FunctionsActivity extends ToolbarActivity {
    private RecyclerView rvFunctionsMenu;
    private ExpandableAdapter functionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_functions;
    }

    private void initView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        rvFunctionsMenu = (RecyclerView) findViewById(R.id.rvFunctionsMenu);
        ArrayList<IData> functions = new ArrayList<>(1);
        functionAdapter = new ExpandableAdapter(functions, rvFunctionsMenu, 1, true, true);
        for (int i = 0; i < 100; i++) {
            functions.add(new ItemAdapterDemos(functionAdapter));
        }
    }
}
