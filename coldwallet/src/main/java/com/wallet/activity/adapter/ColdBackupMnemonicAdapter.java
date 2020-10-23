package com.wallet.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cold.wallet.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class ColdBackupMnemonicAdapter extends RecyclerArrayAdapter<String> {

    private OnClickDeleteListener mListener;

    public ColdBackupMnemonicAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    public void setOnClickDeleteListener(OnClickDeleteListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends BaseViewHolder<String> {

        private final TextView tv_name;
        private final ImageView iv_delete;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_cold_backup_muemonic);
            tv_name = $(R.id.tv_name);
            iv_delete = $(R.id.iv_delete);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            tv_name.setText(data);
            if (mListener != null && !TextUtils.isEmpty(data)) {
                tv_name.setBackgroundResource(R.drawable.corner_hollow_7c8798_4);
                iv_delete.setVisibility(View.VISIBLE);
            } else {
                tv_name.setBackground(null);
                iv_delete.setVisibility(View.GONE);
            }
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.delete(data);
                    }
                }
            });
        }
    }

    public static interface OnClickDeleteListener {
        public void delete(String data);
    }
}