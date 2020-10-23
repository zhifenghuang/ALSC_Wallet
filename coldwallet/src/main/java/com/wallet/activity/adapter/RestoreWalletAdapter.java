package com.wallet.activity.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cold.wallet.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class RestoreWalletAdapter extends RecyclerArrayAdapter<String> {
    private Context mContext;

    public RestoreWalletAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<String> {

        private final TextView tv_name;
   //     private final View iv_delete;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_cold_confirm_muemonic);
            tv_name = $(R.id.tv_name);
//            iv_delete = $(R.id.iv_delete);
//            iv_delete.setVisibility(View.VISIBLE);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            tv_name.setText("" + data);
        }
    }

}