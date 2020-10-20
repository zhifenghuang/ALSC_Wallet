package com.jnsh.tool.base;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;


/**
 * @author rainking
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    public BaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }
}
