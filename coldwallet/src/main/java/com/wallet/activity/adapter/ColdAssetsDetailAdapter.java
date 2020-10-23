package com.wallet.activity.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cao.commons.bean.user.TransferListBean;
import com.cold.wallet.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class ColdAssetsDetailAdapter extends RecyclerArrayAdapter<TransferListBean> {

    private Context mContext;
    private String symbolStr;
    private int type;

    public ColdAssetsDetailAdapter(Context context, String symbolStr, int type) {
        super(context);
        this.mContext = context;
        this.symbolStr = symbolStr;
        this.type = type;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    public void setData() {
        notifyDataSetChanged();
    }


    public class ViewHolder extends BaseViewHolder<TransferListBean> {

        private final ImageView iv_image;
        private final TextView tv_title;
        private final TextView tv_time;
        private final TextView tv_money;
        private final TextView tv_status;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_assets_detail_cold);
            tv_title = $(R.id.tv_title);
            tv_time = $(R.id.tv_time);
            iv_image = $(R.id.iv_image);
            tv_money = $(R.id.tv_money);
            tv_status = $(R.id.tv_status);
        }

        @Override
        public void setData(TransferListBean data) {
            super.setData(data);

            tv_time.setText(data.getAdd_time());

            if (data.getStatus()==0){//0 等待确认 1确认中  2已完成  5交易失败
                tv_status.setText(mContext.getString(R.string.status_cold0));
            }else if (data.getStatus()==1){
                tv_status.setText(mContext.getString(R.string.status_cold1));
            }else if (data.getStatus()==2){
                tv_status.setText(mContext.getString(R.string.status_cold2));
            }else if (data.getStatus()==5){
                tv_status.setText(mContext.getString(R.string.status_cold3));
            }else {
                tv_status.setText("");
            }
            tv_money.setTextColor(mContext.getResources().getColor(R.color.black));
            iv_image.setImageResource(R.mipmap.icon_send_up_black);
            boolean moneyIn = true;
            StringBuilder builder = new StringBuilder();
            if (type == 0) {//全部的时候 1.转账  2充币
                if (data.getTag() == 2) {
                    moneyIn = true;
                } else {
                    moneyIn = false;
                }
                if (data.getStatus() == 1){
                    tv_money.setTextColor(mContext.getResources().getColor(R.color.black));
                    iv_image.setImageResource(R.mipmap.icon_send_down_black);
                }
            } else if (type == 1) {
                moneyIn = false;
            } else if (type == 2) {
                moneyIn = true;
                tv_money.setTextColor(mContext.getResources().getColor(R.color.black));
                iv_image.setImageResource(R.mipmap.icon_send_down_black);
            }
            if (moneyIn) {//1充币 2提币
                builder.append("+");
                if (data.getStatus() == 2) {
                    iv_image.setImageResource(R.mipmap.icon_send_down2);
                    tv_money.setTextColor(mContext.getResources().getColor(R.color.color_22dc9a));
                }
            } else {
                builder.append("-");
                if (data.getStatus() == 2) {
                    iv_image.setImageResource(R.mipmap.icon_send_up2);
                    tv_money.setTextColor(mContext.getResources().getColor(R.color.color_ff5442));
                }
            }
            builder.append(data.getAmount());
            builder.append(" ");
            builder.append(symbolStr);
            tv_money.setText(builder.toString());

            if ("A13".equals(symbolStr) && type==3){
                //来源 1黄金矿工  2红包
                if (data.getType()==1){
                    tv_title.setText(mContext.getString(R.string.alsc_transfer_type1));
                    iv_image.setImageResource(R.mipmap.icon_asset_detail1);
                }else if (data.getType()==2){
                    tv_title.setText(mContext.getString(R.string.alsc_transfer_type2));
                    iv_image.setImageResource(R.mipmap.icon_asset_detail2);
                }else {
                    tv_title.setText(mContext.getString(R.string.alsc_transfer_type3));
                    iv_image.setImageResource(R.mipmap.icon_asset_detail3);
                }
            }else {
                tv_title.setText("JHSIOJIHJSDI...H");
            }
        }
    }
}
