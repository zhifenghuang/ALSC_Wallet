package io.netflow.walletpro.adapter;

import android.content.Context;


import io.netflow.walletpro.R;
import io.netflow.walletpro.fragment.message.ArticleDetailFragment;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ArticleDetailAdapter extends BaseMultiItemQuickAdapter<ArticleDetailFragment.ArticleDetailItem, BaseViewHolder> {

    private Context mContext;

    public ArticleDetailAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_article_detail_0);
        addItemType(1, R.layout.item_article_detail_1);
        addItemType(2, R.layout.item_article_detail_2);
        addItemType(3, R.layout.item_article_detail_3);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ArticleDetailFragment.ArticleDetailItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                break;
            case 1:
                break;
        }
    }

}
