package com.im_hero.androidcommon.data;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.im_hero.adapter.ExpandableAdapter;
import com.im_hero.adapter.IData;
import com.im_hero.adapter.IExpandableViewHolder;
import com.im_hero.adapter.SimpleExpandable;
import com.im_hero.adapter.ViewHolderCreator;
import com.im_hero.androidcommon.R;
import com.im_hero.utils.Toaster;

import java.util.List;

/**
 * 展示 AdapterDemos
 * @author ItsFated
 * @version 1.0
 */
public class ItemAdapterDemos extends SimpleExpandable<IData, ItemAdapterDemos.AdapterDemosViewHolder> implements View.OnClickListener {
    public ItemAdapterDemos(ExpandableAdapter adapter) {
        super(adapter);
    }

    @Override
    public int getViewType() {
        return R.layout.item_expandable;
    }

    @Override
    public ViewHolderCreator<AdapterDemosViewHolder> getViewHolderCreator() {
        return new ViewHolderCreator<AdapterDemosViewHolder>() {
            @Override
            public AdapterDemosViewHolder createViewHolder(ViewGroup viewGroup, int viewType) {
                return new AdapterDemosViewHolder(layoutInflater.inflate(R.layout.item_expandable, viewGroup, false));
            }
        };
    }

    @Override
    public void bindViewHolder(AdapterDemosViewHolder holder, int position, List payloads) {
        super.bindViewHolder(holder, position, payloads);
        holder.itemView.setOnClickListener(this);
        if (isExpanded()) {
            holder.ivArrow.setRotation(90);
        } else {
            holder.ivArrow.setRotation(0);
        }
    }

    @Override
    public List<IData> listSubItems() {
        return MockData.getAdapterDemos();
    }

    @Override
    public void onClick(View v) {
        toggle();
    }

    static class AdapterDemosViewHolder extends RecyclerView.ViewHolder implements IExpandableViewHolder {
        ImageView ivArrow;

        public AdapterDemosViewHolder(View itemView) {
            super(itemView);
            ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
        }

        @Override
        public void onExpanding() {
            ObjectAnimator.ofFloat(ivArrow, "rotation", 0F, 90F).start();
            Toaster.showLong("展开");
        }

        @Override
        public void onCollapsing() {
            ObjectAnimator.ofFloat(ivArrow, "rotation", 90F, 0F).start();
            Toaster.showLong("折叠");
        }
    }
}
