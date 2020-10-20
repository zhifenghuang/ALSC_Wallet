package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.message.SelectAtPeopleFragment;
import com.common.utils.Utils;
import com.common.view.FlowLayout;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SelectAtPeopleAdapter extends BaseMultiItemQuickAdapter<SelectAtPeopleFragment.SelectAtPeopleItem, BaseViewHolder> {

    private Context mContext;

    public SelectAtPeopleAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_select_at_people_0);
        addItemType(1, R.layout.item_select_at_people_1);
        addItemType(2, R.layout.item_select_at_people_2);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, SelectAtPeopleFragment.SelectAtPeopleItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                FlowLayout flowLayout = helper.getView(R.id.flowLayout);
                if (flowLayout.getChildCount() == 0) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, Utils.dip2px(mContext, 30));
                    lp.rightMargin = Utils.dip2px(mContext, 15);
                    lp.bottomMargin = Utils.dip2px(mContext, 15);
                    flowLayout.addView(LayoutInflater.from(mContext).inflate(R.layout.item_select_people_name, null), lp);
                    flowLayout.addView(LayoutInflater.from(mContext).inflate(R.layout.item_select_people_name, null), lp);
                }
                break;
            case 1:
                helper.setText(R.id.tvTitle, item.text);
                break;
            case 2:
                break;
        }
    }

}
