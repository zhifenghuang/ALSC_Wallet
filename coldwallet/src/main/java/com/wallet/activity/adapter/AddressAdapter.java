package com.wallet.activity.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cao.commons.bean.cold.ColdAddressBean;
import com.cold.wallet.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

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
        private final TextView tv_remark;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_address);
            tv_name = $(R.id.tv_name);
            tv_message = $(R.id.tv_message);
            tv_remark = $(R.id.tv_remark);
        }

        @Override
        public void setData(ColdAddressBean data) {
            super.setData(data);
            tv_name.setText(data.getName());
            tv_message.setText(data.getWalletType() + "：" + data.getPath());
            tv_remark.setText(mContext.getString(R.string.add_contacts_note) + "：" + data.getRemarks());
        }
    }
}