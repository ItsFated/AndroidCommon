package com.im_hero.adapter;

/**
 * 如果在 展开或者折叠 的时候需要动画显示（或者让视图呈现不一样的状态），可以让 ViewHolder 实现此接口，在对应方法中开始动画
 * @author Jason
 * @version 1.0
 */
public interface IExpandableViewHolder {
    /**
     * 展开
     */
    void onExpanding();

    /**
     * 展开
     */
    void onCollapsing();
}
