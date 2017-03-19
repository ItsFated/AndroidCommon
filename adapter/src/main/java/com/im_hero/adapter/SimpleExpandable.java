package com.im_hero.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @author Jason
 * @version 1.0
 */
public abstract class SimpleExpandable<D extends IData, VH extends RecyclerView.ViewHolder> implements IExpandable<D, VH> {
    private ExpandableAdapter adapter;
    private int position;
    private boolean expanded;

    public SimpleExpandable(ExpandableAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void bindViewHolder(VH holder, int position, List payloads) {
        this.position = position;
    }

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

    public void toggle() {
        if (isExpanded()) {
            adapter.collapse(position);
        } else {
            adapter.expand(position);
        }
    }
}
