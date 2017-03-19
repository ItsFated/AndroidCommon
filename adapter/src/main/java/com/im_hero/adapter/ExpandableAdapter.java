package com.im_hero.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 可展开数据的适配器，请在创建的时候设置，
 * 执行过程中设置可能会出问题
 * @author Jason
 * @version 1.0
 */
public class ExpandableAdapter extends BaseAdapter<IData, RecyclerView.ViewHolder> {
    /** 此 Adapter 的拥有者 */
    private RecyclerView rvOwner;
    /** 是否展开为空的数据项 */
    private final boolean expandEmpty;
    /** 当展开第二个数据项时，自动折叠上次展开的数据项 */
    private final boolean autoCollapseOnExpand;
    /** 在展开或折叠时调用 {@link IExpandableViewHolder} 接口的对应方法 */
    private boolean notifyExpandableViewHolder = true;


    /**
     * 附带一些默认值的构造器
     * @param data 可以只有部分是可扩展的数据
     * @param rvOwner 目标 RecyclerView，不用再调用 {@link RecyclerView#setAdapter(RecyclerView.Adapter)}，构造方法中已经设置
     */
    public ExpandableAdapter(List<IData> data, RecyclerView rvOwner) {
        super(data);
        this.rvOwner = Objects.requireNonNull(rvOwner);
        rvOwner.setAdapter(this);
        expandEmpty = false;
        autoCollapseOnExpand = false;
    }

    /**
     * 具有所有参数的构造器
     * @param data 可以只有部分是可扩展的数据
     * @param rvOwner 此适配器的拥有者
     * @param viewHolderCreatorCount 视图创建者的个数
     * @param expandEmpty 是否展开没有子数据的项
     * @param autoCollapseOnExpand 在展开时折叠上次展开的项（{@link #expandAll() 将无法使用}）
     */
    public ExpandableAdapter(List<IData> data, RecyclerView rvOwner, int viewHolderCreatorCount, boolean expandEmpty, boolean autoCollapseOnExpand) {
        super(data, viewHolderCreatorCount);
        this.rvOwner = Objects.requireNonNull(rvOwner);
        rvOwner.setAdapter(this);
        this.expandEmpty = expandEmpty;
        this.autoCollapseOnExpand = autoCollapseOnExpand;
    }

//    /**
//     * 是否需要展开子数据为零或者为空的可展开数据，默认不展开
//     * @param expandEmpty 真为展开，假为不展开
//     */
//    public ExpandableAdapter setExpandEmpty(boolean expandEmpty){
//        this.expandEmpty = expandEmpty;
//        return this;
//    }
//
//    /**
//     * 自动折叠上次展开的项
//     * @param autoCollapseOnExpand 是否开启此功能
//     */
//    public ExpandableAdapter setAutoCollapseOnExpand(boolean autoCollapseOnExpand) {
//        this.autoCollapseOnExpand = autoCollapseOnExpand;
//        return this;
//    }

    /**
     * 在展开或折叠时调用 {@link IExpandableViewHolder} 接口的对应方法
     * @param notifyExpandableViewHolder 如果需要在展开或折叠时播放动画建议开启，如果不需要可以关闭，加快展开和折叠的速度
     */
    public ExpandableAdapter setNotifyExpandableViewHolder(boolean notifyExpandableViewHolder) {
        this.notifyExpandableViewHolder = notifyExpandableViewHolder;
        return this;
    }

    /**
     * 找到数据在数据集合中的位置
     * @param o 集合中的数据
     * @return 下标位置
     */
    public int indexOfData(IData o){
        return mData.indexOf(o);
    }

    /**
     * 展开
     * @return 子数据的个数
     */
    public int expand(int position) {
        return doExpandOrCollapse(true, position);
    }

    /**
     * 展开
     * @return 子数据的个数
     */
    public int expand(IExpandable expandable) {
        return doExpandOrCollapse(true, mData.indexOf(expandable));
    }

    /**
     * 折叠
     * @return 子数据的个数
     */
    public int collapse(int position) {
        return doExpandOrCollapse(false, position);
    }

    /**
     * 展开
     * @return 子数据的个数
     */
    public int collapse(IExpandable expandable) {
        return doExpandOrCollapse(false, mData.indexOf(expandable));
    }

    /**
     * 展开或折叠
     * @param isExpand 真为展开，假为折叠
     * @param position 数据位置
     * @return 子数据的个数，相同状态转换返回 0（展开的状态下展开，返回 0）
     */
    @SuppressWarnings("unchecked")
    private int doExpandOrCollapse(boolean isExpand, int position) {
        IData<RecyclerView.ViewHolder> datum = mData.get(position);
        if (datum instanceof IExpandable) {
            IExpandable<IData<RecyclerView.ViewHolder>, RecyclerView.ViewHolder> expandable = (IExpandable) datum;
            // 已经是目标状态（展开的状态下展开，返回 0）
            if(isExpand == expandable.isExpanded()) return 0;

            // 在展开之前先关闭其他所有同等级的
            if (isExpand && autoCollapseOnExpand) {
                collapseAll(expandable.getExpansionLevel());
                position = mData.indexOf(expandable);
            }

            Collection<IData<RecyclerView.ViewHolder>> subItems = expandable.listSubItems();
            final int size = subItems == null ? 0 : subItems.size();

            // 如果 ViewHolder 实现了 IExpandableViewHolder 则调用对应方法
            IExpandableViewHolder expandableViewHolder;
            if (notifyExpandableViewHolder) {
                RecyclerView.ViewHolder viewHolder = rvOwner.findViewHolderForAdapterPosition(position);
                if (viewHolder instanceof IExpandableViewHolder) {
                    expandableViewHolder = (IExpandableViewHolder) viewHolder;
                } else {
                    expandableViewHolder = null;
                }
            } else {
                expandableViewHolder = null;
            }

            if (size > 0) {             // 展开
                int positionStart = position + 1;
                if (isExpand) {
                    if (mData.addAll(positionStart, subItems)) {
                        expandable.setExpanded(true);
                        if (expandableViewHolder != null) {
                            expandableViewHolder.onExpanding();
                        }
                        notifyItemRangeInserted(positionStart, size);
                        // TODO: 2017/2/21 展开的时候如果需要自动滑动到顶部
                        return size;
                    }
                } else {
                    if (mData.removeAll(subItems)) {
                        expandable.setExpanded(false);
                        if (expandableViewHolder != null) {
                            expandableViewHolder.onCollapsing();
                        }
                        notifyItemRangeRemoved(positionStart, size);
                        return size;
                    }
                }
            } else if(expandEmpty) {    // 展开子数据为空的数据
                expandable.setExpanded(isExpand);
                if (expandableViewHolder != null) {
                    if (isExpand){
                        expandableViewHolder.onExpanding();
                    } else {
                        expandableViewHolder.onCollapsing();
                    }
                }
                return 0;
            }
        }
        return 0;
    }

    /**
     * 递归折叠
     */
    @SuppressWarnings("unchecked")
    public void recursiveCollapse(IExpandable expandable){
        if (expandable.isExpanded()) {
            collapseAll(expandable.listSubItems());
            collapse(expandable);
        }
    }

    /**
     * 递归折叠
     */
    private void collapseAll(List<IData> items) {
        if (items == null) {
            return;
        }
        IData item;
        IExpandable iExpandable;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            if (item instanceof IExpandable) {
                iExpandable = (IExpandable) item;
                if (iExpandable.isExpanded()) {
                    recursiveCollapse(iExpandable);
                }
            }
        }
    }

    /**
     * 折叠所有指定层级的项
     * @param level 层级
     */
    public void collapseAll(int level) {
        IData item;
        IExpandable iExpandable;
        for (int i = 0; i < mData.size(); i++) {
            item = mData.get(i);
            if (item instanceof IExpandable) {
                iExpandable = (IExpandable) item;
                if (iExpandable.getExpansionLevel() == level && iExpandable.isExpanded()) {
                    recursiveCollapse(iExpandable);
                }
            }
        }
    }

    /**
     * 折叠所有项
     */
    public void collapseAll(){
        collapseAll(mData);
    }

    /**
     * 递归展开
     */
    @SuppressWarnings("unchecked")
    public void recursiveExpand(IExpandable expandable){
        if (!expandable.isExpanded()) {
            expandAll(expandable.listSubItems());
            expand(expandable);
        }
    }

    /**
     * 递归展开
     */
    private void expandAll(List<IData> items) {
        // 如果开启了 展开时自动折叠上次展开项功能，直接返回
        if (autoCollapseOnExpand || items == null) {
            return;
        }
        IData item;
        IExpandable iExpandable;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            if (item instanceof IExpandable) {
                iExpandable = (IExpandable) item;
                if (!iExpandable.isExpanded()) {
                    recursiveExpand(iExpandable);
                }
            }
        }
    }

    /**
     * 展开所有项
     */
    public void expandAll(){
        expandAll(mData);
    }
}
