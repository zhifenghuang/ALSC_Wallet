package com.wallet.activity.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cao.commons.bean.cold.ColdAddressBean;
import com.cold.wallet.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wallet.utils.Utils;

public class AddressAdapter extends RecyclerArrayAdapter<ColdAddressBean> {

    private Context mContext;

    public AddressAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<ColdAddressBean> {

        private final TextView tv_name;
        private final TextView tv_message;
        private ImageView ivImage;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_address);
            tv_name = $(R.id.tv_name);
            tv_message = $(R.id.tv_message);
            ivImage = $(R.id.ivImage);
        }

        @Override
        public void setData(ColdAddressBean data) {
            super.setData(data);
            tv_name.setText(data.getName());
            tv_message.setText(data.getPath());
            ivImage.setImageResource(Utils.getImageResource3(data.getWalletType()));
        }
    }
}