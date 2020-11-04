package io.netflow.walletpro.adapter;

import android.content.Context;

import io.netflow.walletpro.R;
import io.netflow.walletpro.fragment.chat.ContactFragment;

import com.cao.commons.bean.chat.UserBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactAdapter extends BaseMultiItemQuickAdapter<ContactFragment.ContactItem, BaseViewHolder> {

    private Context mContext;
    private boolean mIsHadNewVerify;

    public ContactAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_contact_0);
        addItemType(1, R.layout.item_contact_1);
        mContext = context;
    }

    public void setNew(boolean isHadNewVerify) {
        mIsHadNewVerify = isHadNewVerify;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ContactFragment.ContactItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setImageResource(R.id.iv, item.iconResId)
                        .setText(R.id.tv, item.name)
                        .setVisible(R.id.ivNew, false)
                        .setGone(R.id.line, helper.getAdapterPosition() == 3);
                if (helper.getAdapterPosition() == 0 && mIsHadNewVerify) {
                    helper.setVisible(R.id.ivNew, true);
                }
                break;
            case 1:
                int pos = helper.getAdapterPosition();
                UserBean friend = item.getFriend();
                char c = friend.getPinyinName().charAt(0);
                if (pos == 4) {
                    helper.setText(R.id.tvLetter, String.valueOf(c))
                            .setGone(R.id.tvLetter, false);
                } else {
                    char prevC = getItem(pos - 1).name.charAt(0);
                    helper.setText(R.id.tvLetter, String.valueOf(c))
                            .setGone(R.id.tvLetter, prevC == c);
                }
                helper.setText(R.id.tvName, friend.getNickName());
                break;
        }
    }

}
