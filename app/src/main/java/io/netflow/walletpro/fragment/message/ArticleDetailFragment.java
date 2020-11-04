package io.netflow.walletpro.fragment.message;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.netflow.walletpro.R;
import io.netflow.walletpro.adapter.ArticleDetailAdapter;
import com.common.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

public class ArticleDetailFragment extends BaseFragment {

    private ArticleDetailAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_detail;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, getString(R.string.wallet_article));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<ArticleDetailItem> list = new ArrayList<>();
        list.add(new ArticleDetailItem(0));
        list.add(new ArticleDetailItem(1));
        list.add(new ArticleDetailItem(1));
        list.add(new ArticleDetailItem(1));
        list.add(new ArticleDetailItem(1));
        list.add(new ArticleDetailItem(2));
        list.add(new ArticleDetailItem(3));
        list.add(new ArticleDetailItem(3));
        list.add(new ArticleDetailItem(3));
        list.add(new ArticleDetailItem(3));
        getAdapter().setNewInstance(list);
    }


    private ArticleDetailAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ArticleDetailAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    if (position > 0) {
                        gotoPager(ViewpointDetailFragment.class);
                    }
                }
            });
        }
        return mAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    public static class ArticleDetailItem implements MultiItemEntity {

        public int itemType;

        public ArticleDetailItem(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }

}
