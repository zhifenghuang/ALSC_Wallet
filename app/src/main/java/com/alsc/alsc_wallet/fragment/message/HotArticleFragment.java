package com.alsc.alsc_wallet.fragment.message;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.HotArticle0Adapter;
import com.alsc.alsc_wallet.adapter.HotArticle1Adapter;
import com.alsc.alsc_wallet.adapter.HotArticle2Adapter;
import com.common.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;


public class HotArticleFragment extends BaseFragment implements OnItemClickListener {

    private HotArticle0Adapter mAdapter0;
    private HotArticle1Adapter mAdapter1;
    private HotArticle2Adapter mAdapter2;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_article;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter0 = new HotArticle0Adapter(getActivity());
        mAdapter0.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter0);
        mAdapter0.addData("1");
        mAdapter0.addData("2");
        mAdapter0.addData("1");
        mAdapter0.addData("2");
        mAdapter0.setOnItemClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView1);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter1 = new HotArticle1Adapter(getActivity());
        mAdapter1.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter1);
        mAdapter1.addData("1");
        mAdapter1.addData("2");
        mAdapter1.addData("1");
        mAdapter1.addData("2");
        mAdapter1.setOnItemClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView2);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter2 = new HotArticle2Adapter(getActivity());
        mAdapter2.onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter2);
        mAdapter2.addData("1");
        mAdapter2.addData("2");
        mAdapter2.addData("1");
        mAdapter2.addData("2");
        mAdapter2.addData("1");
        mAdapter2.addData("2");
        mAdapter2.addData("1");
        mAdapter2.addData("2");
        mAdapter2.setOnItemClickListener(this);

    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        gotoPager(ArticleDetailFragment.class);
    }
}
