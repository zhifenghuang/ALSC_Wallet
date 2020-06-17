package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.trade.MoneyAccountFragment;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MoneyAccountAdapter extends BaseMultiItemQuickAdapter<MoneyAccountFragment.AccountItem, BaseViewHolder> {

    private Context mContext;

    public MoneyAccountAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_money_account_0);
        addItemType(1, R.layout.item_money_account_1);
        addItemType(2, R.layout.item_money_account_2);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, MoneyAccountFragment.AccountItem accountItem) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.getView(R.id.llBuy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetBtns(helper.getView(R.id.llSell), (LinearLayout) v);
                        List<MoneyAccountFragment.AccountItem> list=getData();
                        list.clear();
                        list.add(new MoneyAccountFragment.AccountItem(0));
                        list.add(new MoneyAccountFragment.AccountItem(1));
                        notifyDataSetChanged();
                    }
                });
                helper.getView(R.id.llSell).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetBtns(helper.getView(R.id.llBuy), (LinearLayout) v);
                        List<MoneyAccountFragment.AccountItem> list=getData();
                        list.clear();
                        list.add(new MoneyAccountFragment.AccountItem(0));
                        list.add(new MoneyAccountFragment.AccountItem(2));
                        list.add(new MoneyAccountFragment.AccountItem(2));
                        notifyDataSetChanged();
                    }
                });
                break;
            case 1:
                break;
        }
    }

    private void resetBtns(LinearLayout ll1, LinearLayout ll2) {
        ((TextView) ll1.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_66_66_66));
        ll1.getChildAt(1).setVisibility(View.INVISIBLE);

        ((TextView) ll2.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        ll2.getChildAt(1).setVisibility(View.VISIBLE);
    }


}
