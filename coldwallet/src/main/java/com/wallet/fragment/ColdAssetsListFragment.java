package com.wallet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cao.commons.base.BaseFragment;
import com.cao.commons.bean.cold.TradeInfoBean;
import com.cao.commons.bean.user.TransferListBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.util.log.Log;
import com.cold.wallet.R;
import com.wallet.activity.adapter.ColdAssetsDetailAdapter;
import com.wallet.activity.cold.ColdAssetsDetailActivity;
import com.cold.wallet.databinding.FragmentEasyRecyclerviewBinding;
import com.wallet.event.TransferEvent;
import com.wallet.event.WalletAddEvent;
import com.wallet.event.WalletAllEvent;
import com.wallet.retrofit.ColdInterface;
import com.wallet.retrofit.HttpInfoRequest;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ColdAssetsListFragment extends BaseFragment {
    private ColdAssetsDetailAdapter adapter;
    private FragmentEasyRecyclerviewBinding binding;
    private int symbol, type;
    private int page = 1;
    private String symbolStr;
    private String mAddress;

    public static ColdAssetsListFragment newInstance(Bundle bundle) {
        ColdAssetsListFragment fragment = new ColdAssetsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_easy_recyclerview, viewGroup, false);
        symbolStr = getArguments().getString("symbol");
        mAddress = getArguments().getString("address");
        type = getArguments().getInt("type", 0);
        symbol = getIndexByType(symbolStr);
        return binding.getRoot();
    }

    private int getIndexByType(String str) {
        if ("A13".equals(str)) {
            return 1;
        } else if ("ETH".equals(str)) {
            return 2;
        } else if ("BTC".equals(str)) {
            return 5;
        } else if ("USDT".equals(str)) {
            return 3;
        }
        return 0;
    }

    @Override
    protected void onFragmentFirstVisible() {
        EventBus.getDefault().register(this);
        adapter = new ColdAssetsDetailAdapter(getActivity(), symbolStr, type);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                binding.recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getData(page);
                    }
                }, 1000);
            }

            @Override
            public void onMoreClick() {
            }
        });
        adapter.setError(R.layout.view_error_foot, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        adapter.setNoMore(R.layout.view_nomore_black);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = adapter.getAllData().get(position).getId();
                ColdAssetsDetailActivity.startActivity(mContext, id, mAddress);
            }
        });

        ArrayList<TradeInfoBean> list = DatabaseOperate.getInstance().getAllTradeInfo();
        Log.e("ColdAssetsListFragment", "trade size = " + list.size());
        getData(1);
    }

    private void getData(int pages) {
        ColdInterface.getTradeList(mAddress, 10, pages,symbolStr,type, mContext, Tag, new HttpInfoRequest<List<TransferListBean>>() {
            @Override
            public void onSuccess(List<TransferListBean> model) {
                if (pages == 1) {
                    adapter.clear();
//                    if (model== null || model.size() == 0) {
//                        binding.recyclerView.setEmptyView(R.layout.view_nodata3_layout);
//                        binding.recyclerView.showEmpty();
//                    }
                }
                adapter.addAll(model);
            }

            @Override
            public void onError(int eCode) {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TransferEvent event) {
        page = 1;
        getData(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAllEvent event) {
        page = 1;
        getData(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAddEvent event) {
        page = 1;
        getData(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
