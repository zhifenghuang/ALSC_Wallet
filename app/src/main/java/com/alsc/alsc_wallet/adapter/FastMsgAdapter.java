package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.BaseActivity;
import com.alsc.alsc_wallet.fragment.message.UserInfoFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FastMsgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public FastMsgAdapter(Context context) {
        super(R.layout.item_fast_msg);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String s) {
        helper.setVisible(R.id.line, helper.getAdapterPosition() > 0);
    }
}
