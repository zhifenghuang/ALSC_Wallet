package com.alsc.chat.adapter;

import android.content.Context;

import com.alsc.chat.R;
import com.cao.commons.bean.chat.LabelBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

public class LabelAdapter extends BaseQuickAdapter<LabelBean, BaseViewHolder> {

    private Context mContext;


    public LabelAdapter(Context context) {
        super(R.layout.item_label);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, LabelBean item) {
        helper.setText(R.id.tvName, item.getName() + "(" + item.getContactCount() + ")");
    }

    public void deleteLabel(long tagId) {
        List<LabelBean> list = getData();
        for (LabelBean bean : list) {
            if (bean.getTagId() == tagId) {
                list.remove(bean);
                notifyDataSetChanged();
                return;
            }
        }
    }

    public void editLabel(LabelBean label) {
        List<LabelBean> list = getData();
        for (LabelBean bean : list) {
            if (bean.getTagId() == label.getTagId()) {
                bean.setContactCount(label.getContactCount());
                bean.setName(label.getName());
                notifyDataSetChanged();
                return;
            }
        }
    }
}
