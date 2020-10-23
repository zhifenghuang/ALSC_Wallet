package com.wallet.activity.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cold.wallet.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ColdWalletVerifyAdapter extends RecyclerArrayAdapter<String> {
    private List<Integer> selected;
    public ColdWalletVerifyAdapter(Context context) {
        super(context);
        selected = new ArrayList<>();
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<String> {

        private final TextView tv_name;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_cold_wallet_verify);
            tv_name = $(R.id.tv_name);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            Integer position= getAdapterPosition();
            if (selected.contains(position)){
                tv_name.setBackgroundResource(R.drawable.corner_8051628c_10);
            }else {
                tv_name.setBackgroundResource(R.drawable.corner_51628c_10);
            }
            tv_name.setText(""+data);
        }
    }

    public List<Integer> getSelected() {
        return selected;
    }
}