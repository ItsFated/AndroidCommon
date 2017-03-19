package com.im_hero.androidcommon.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.im_hero.adapter.IData;
import com.im_hero.adapter.ViewHolderCreator;

import java.util.List;

/**
 * @author ItsFated
 * @version 1.0
 */

public class ItemAdapterDemo implements IData<ItemAdapterDemo.AdapterDemoViewHolder> {


    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void bindViewHolder(AdapterDemoViewHolder holder, int position, List payloads) {

    }

    @Override
    public ViewHolderCreator<AdapterDemoViewHolder> getViewHolderCreator() {
        return null;
    }

    public static class AdapterDemoViewHolder extends RecyclerView.ViewHolder {
        public AdapterDemoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
