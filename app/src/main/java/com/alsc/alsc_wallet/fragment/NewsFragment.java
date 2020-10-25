package com.alsc.alsc_wallet.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.ArticleAdapter;
import com.alsc.chat.fragment.UserInfoFragment;
import com.cao.commons.bean.BaseListBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.common.activity.BaseActivity;
import com.cao.commons.bean.ArticleBean;
import com.common.fragment.BaseFragment;
import com.common.http.HttpMethods;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.common.utils.Utils;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment {

    private ArticleAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.ivMyAvatar);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        getAdapter().setNewInstance(DataManager.getInstance().getArticles());
    }

    private ArticleAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ArticleAdapter(getActivity());
        }
        return mAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        getNewsArticle();
    }

    @Override
    public void updateUIText() {
        UserBean myInfo = DataManager.getInstance().getUser();
        Utils.displayAvatar(getActivity(), R.drawable.chat_default_group_avatar, myInfo.getAvatarUrl(), fv(R.id.ivMyAvatar));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivMyAvatar:
                gotoPager(UserInfoFragment.class);
                break;
        }
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }

    private void getNewsArticle() {
        HttpMethods.getInstance().getNewsArticle(2, 1, 20, new HttpObserver(new SubscriberOnNextListener<BaseListBean<ArrayList<ArticleBean>>>() {
            @Override
            public void onNext(BaseListBean<ArrayList<ArticleBean>> bean, String msg) {
                if (getActivity() == null || getView() == null || bean.getList() == null) {
                    return;
                }
                DataManager.getInstance().saveArticles(bean.getList());
                getAdapter().setNewInstance(bean.getList());
            }
        }, getActivity(), false, (BaseActivity) getActivity()));
    }
}
