package com.im_hero.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author Jason
 * @version 1.0
 */

public abstract class ViewHolderCreator<VH extends  RecyclerView.ViewHolder> {
    protected static LayoutInflater layoutInflater;

    final VH create(ViewGroup viewGroup, int viewType){
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return createViewHolder(viewGroup, viewType);
    }

    public abstract VH createViewHolder(ViewGroup viewGroup, int viewType);
}
