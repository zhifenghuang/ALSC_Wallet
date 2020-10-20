package com.cao.commons.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cao.commons.R;
import com.cao.commons.model.FormModel;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * 当前页面Activity
 *
 * @author CJZ
 * @Time 2019/6/11
 */
public class FormAdapter extends RecyclerArrayAdapter<String> {
    private List<FormModel> formList;

    public FormAdapter(Context context) {
        super(context);
    }

    public void setFormData(List<FormModel> formList) {
        this.formList = formList;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<String> {

        private final TextView tv_title;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_form_selector);
            tv_title = $(R.id.tv_title);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            for (FormModel model : formList) {
                if (TextUtils.equals(model.title, data)) {
                    tv_title.setBackground(getContext().getResources().getDrawable(R.drawable.edittext_solid_red));
                    tv_title.setTextColor(getContext().getResources().getColor(R.color.color_black));
                }
            }
            tv_title.setText(data);
        }
    }
}
