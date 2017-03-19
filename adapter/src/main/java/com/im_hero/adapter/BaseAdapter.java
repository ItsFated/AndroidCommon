package com.im_hero.adapter;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 数据适配器的基类
 * @author Jason
 * @version 1.0
 */
@SuppressWarnings("unchecked")
    public class BaseAdapter<D extends IData, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     *  用于创建 ViewHolder 的创建者
     */
    private final SparseArray<ViewHolderCreator<VH>> mViewHolderCreators;

    /**
     * 显示数据，这里使用 protected 修饰是为了方便子类使用，谨慎使用
     */
    protected List<D> mData;

    /**
     * 创建基础适配器
     * @param data 数据
     */
    public BaseAdapter(List<D> data){
        this.mData = data == null ? new ArrayList<D>() : data;
        this.mViewHolderCreators = new SparseArray<>();
    }

    /**
     * 创建基础适配器
     * @param data 数据
     * @param viewHolderCreatorCount ViewHolderCreators 容器的初始化大小
     */
    public BaseAdapter(List<D> data, int viewHolderCreatorCount) {
        this.mData = data == null ? new ArrayList<D>() : data;
        this.mViewHolderCreators = new SparseArray<>(viewHolderCreatorCount);
    }

    @CallSuper
    @Override
    public int getItemViewType(int position) {
        D datum = mData.get(position);
        int type = datum.getViewType();
        if (mViewHolderCreators.get(type) == null) {
            mViewHolderCreators.put(type, datum.getViewHolderCreator());
        }
        return type;
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return mViewHolderCreators.get(viewType).create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        mData.get(position).bindViewHolder(holder, position, Collections.EMPTY_LIST);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        mData.get(position).bindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 获取制定位置的数据，以判断下标范围，不会数组越界
     */
    public <T extends IData> T getData(int position) {
        if (position < 0 || position >= mData.size()) return null;
        return (T) mData.get(position);
    }

    /**
     * 添加单个数据到末尾
     * @return 是否添加成功
     */
    public boolean append(D d){
        if (d != null) {
            final int position = mData.size();
            if (mData.add(d)) {
                notifyItemInserted(position);
                return true;
            }
        }
        return false;
    }

    /**
     * 添加数据集合到末尾
     * @return 是否添加成功
     */
    public boolean appendAll(Collection<D> d){
        if (d != null) {
            final int position = mData.size();
            if (mData.addAll(position, d)) {
                notifyItemRangeInserted(position, d.size());
                return true;
            }
        }
        return false;
    }

    /**
     * 移除一项数据
     * @return 是否移除成功
     */
    public boolean remove(D d) {
        if (d != null) {
            final int position = mData.indexOf(d);
            if (position >= 0) {
                mData.remove(position);
                notifyItemRemoved(position);
            }
            return true;
        }
        return false;
    }
}
