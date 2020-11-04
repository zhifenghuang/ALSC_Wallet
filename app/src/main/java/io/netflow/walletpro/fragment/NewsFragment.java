package io.netflow.walletpro.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.netflow.walletpro.R;
import io.netflow.walletpro.adapter.ArticleAdapter;
import com.alsc.chat.fragment.UserInfoFragment;
import com.cao.commons.bean.BaseListBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.activity.BaseActivity;
import com.cao.commons.bean.ArticleBean;
import com.common.fragment.BaseFragment;
import com.common.http.HttpMethods;
import com.common.http.HttpObserver;
import com.common.http.SubscriberOnNextListener;
import com.common.http.ThirdPartyHttpMethods;
import com.common.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

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
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("wallet_address", "1HfYppSw3EtWoGT8kC9EtbWV2dg1qqYdBY");
                    map.put("wallet_type", "BTC");
                    map.put("wallet_from", "imtoken");
                    ThirdPartyHttpMethods.getInstance().bindWallet(map, new HttpObserver(new SubscriberOnNextListener() {
                        @Override
                        public void onNext(Object o, String msg) {

                        }
                    }, getActivity(), false, (BaseActivity) getActivity()));
                }
            });
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
