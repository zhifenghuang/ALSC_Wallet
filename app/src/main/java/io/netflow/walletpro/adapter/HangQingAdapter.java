package io.netflow.walletpro.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.bean.CoinHangQingBean;
import io.netflow.walletpro.R;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class HangQingAdapter extends BaseQuickAdapter<CoinHangQingBean, BaseViewHolder> {

    private Context mContext;

    public HangQingAdapter(Context context) {
        super(R.layout.item_hangqing);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, CoinHangQingBean item) {
        helper.setText(R.id.tvNo, "0" + (helper.getAdapterPosition() + 1))
                .setImageResource(R.id.ivCoinIcon, item.getResId())
                .setText(R.id.tvName, item.getName())
                .setText(R.id.tvTotalValue, item.getTotalValue())
                .setText(R.id.tvCNYPrice, item.getCnyPrice())
                .setText(R.id.tvPrice, item.getPrice())
                .setText(R.id.tvRose, item.getRose());

        if (item.getRose().startsWith("+")) {
            helper.setTextColor(R.id.tvRose, ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        } else if (item.getRose().startsWith("-")) {
            helper.setTextColor(R.id.tvRose, ContextCompat.getColor(mContext, R.color.color_ed_37_35));
        } else {
            helper.setTextColor(R.id.tvRose, ContextCompat.getColor(mContext, R.color.color_65_65_65));
        }
    }

}
