package io.netflow.walletpro.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import io.netflow.walletpro.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class SelectHorTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    private int mSelectIndex;

    public SelectHorTypeAdapter(Context context) {
        super(R.layout.item_select_hor_type);
        mContext = context;
        mSelectIndex = 0;
    }

    public void setSelectIndex(int selectIndex) {
        mSelectIndex = selectIndex;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setText(R.id.tv, s)
                .setTextColor(R.id.tv, ContextCompat.getColor(mContext, mSelectIndex == helper.getAdapterPosition() ? R.color.color_07_bb_99 : R.color.color_00_00_00))
                .setVisible(R.id.line, mSelectIndex == helper.getAdapterPosition());
    }
}
