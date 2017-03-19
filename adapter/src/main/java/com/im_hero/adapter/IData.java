package com.im_hero.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * 数据类所应该实现的接口
 * @author Jason
 * @version 1.0
 */
public interface IData<VH extends RecyclerView.ViewHolder> {

    /**
     * 获取 ViewType ，这个 ViewType 和 ViewHolderCreator 应该要一一对应 {@link #getViewHolderCreator()}
     * @return viewType 视图类型
     */
    @LayoutRes
    int getViewType();

    /**
     * 参看{@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int, List)}
     */
    void bindViewHolder(VH holder, int position, List payloads);

    /**
     * 获取创建 ViewHolder 的创建者，这个ViewHolderCreator 应该要和 ViewType 一一对应 {@link #getViewType()}
     * @return ViewHolder 的创建者
     */
    ViewHolderCreator<VH> getViewHolderCreator();

}
