package com.im_hero.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * @author Jason
 * @version 1.0
 */
public abstract class SimpleExpandable<D extends IData<VH>, VH extends RecyclerView.ViewHolder> implements IExpandable<D, VH> {
    private boolean expanded;

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public int getExpansionLevel() {
        return 0;
    }
}
