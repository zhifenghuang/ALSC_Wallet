package com.alsc.alsc_wallet.adapter;

import android.content.Context;

import com.alsc.alsc_wallet.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cao.commons.bean.ArticleBean;
import com.common.utils.Utils;

import org.jetbrains.annotations.NotNull;

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private Context mContext;

    public ArticleAdapter(Context context) {
        super(R.layout.item_article);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ArticleBean s) {
        helper.setText(R.id.tvName, s.getTitle())
                .setText(R.id.tvTime, s.getCreate_time());
        Utils.displayAvatar(mContext, 0, s.getThumb(), helper.getView(R.id.ivCover));
    }
}
