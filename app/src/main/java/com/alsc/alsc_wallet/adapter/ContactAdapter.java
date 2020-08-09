package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.chat.ContactFragment;
import com.alsc.alsc_wallet.fragment.message.ArticleDetailFragment;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactAdapter extends BaseMultiItemQuickAdapter<ContactFragment.ContactItem, BaseViewHolder> {

    private Context mContext;

    public ContactAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_contact_0);
        addItemType(1, R.layout.item_contact_1);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ContactFragment.ContactItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setImageResource(R.id.iv, item.iconResId)
                        .setText(R.id.tv, item.name)
                        .setGone(R.id.line, helper.getAdapterPosition() == 3);
                break;
            case 1:
                int pos = helper.getAdapterPosition();
                char c = item.name.charAt(0);
                if (pos == 0) {
                    helper.setText(R.id.tvLetter, String.valueOf(c))
                            .setGone(R.id.tvLetter, false);
                } else {
                    char prevC = getItem(pos - 1).name.charAt(0);
                    helper.setText(R.id.tvLetter, String.valueOf(c))
                            .setGone(R.id.tvLetter, prevC == c);
                }
                helper.setText(R.id.tvName, item.name);
                break;
        }
    }

}
