package com.im_hero.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.Collection;
import java.util.List;

/**
 * 可以扩展的数据所应该实现的接口，注意：
 * <ul>
 *     <li>如果数据重写了 {@link Object#equals(Object)} 方法，在折叠时会通过 {@link List#removeAll(Collection)} 来移除子数据
 *     ，需要使用者注意相等的判断，以免移除了不需要移除的数据</li>
 * </ul>
 * @author Jason
 * @version 1.0
 */
public interface IExpandable<D extends IData, VH extends RecyclerView.ViewHolder>
        extends IData<VH> {
    /***
     * 展开层级的第一级别
     */
    int MIN_LEVEL = 0;

    /**
     * 展开层级的最大级别
     */
    int MAX_LEVEL = Integer.MAX_VALUE;

    /**
     * 设置是否已展开
     * @param expanded 展开状态
     */
    void setExpanded(boolean expanded);

    /**
     * @return 是否已展开
     */
    boolean isExpanded();

    /**
     * @return 获取此数据层级
     */
    int getExpansionLevel();

    /**
     * 获取子数据集
     */
    List<D> listSubItems();

}
