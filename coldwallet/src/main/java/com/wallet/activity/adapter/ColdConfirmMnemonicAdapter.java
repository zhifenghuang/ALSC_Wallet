package com.wallet.activity.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cold.wallet.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ColdConfirmMnemonicAdapter extends RecyclerArrayAdapter<String> {
    private List<Integer> selectList;
    private Context mContext;

    public ColdConfirmMnemonicAdapter(Context context) {
        super(context);
        mContext = context;
        selectList = new ArrayList<>();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<String> {

        private final TextView tv_name;


        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_cold_confirm_muemonic);
            tv_name = $(R.id.tv_name);
        }

        @Override
        public void setData(final String data) {
            super.setData(data);
            Integer integer = getAdapterPosition();
            if (selectList.contains(integer)) {
                tv_name.setTextColor(mContext.getResources().getColor(R.color.color_1d2637_50));
                tv_name.setBackgroundResource(R.drawable.corner_hollow_807c8798_4);
            } else {
                tv_name.setTextColor(mContext.getResources().getColor(R.color.color_1d_26_37));
                tv_name.setBackgroundResource(R.drawable.corner_hollow_7c8798_4);
            }
            tv_name.setText("" + data);
        }
    }

    public List<Integer> getSelectList() {
        return selectList;
    }
}