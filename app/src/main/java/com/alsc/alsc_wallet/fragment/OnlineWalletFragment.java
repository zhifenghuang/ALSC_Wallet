package com.alsc.alsc_wallet.fragment;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.MainActivity;
import com.alsc.alsc_wallet.adapter.HotWalletAdapter;
import com.cao.commons.bean.AssetsBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.alsc.alsc_wallet.fragment.online.WalletDetailFragment;
import com.common.fragment.BaseFragment;
import com.wallet.activity.MainColdActivity;

public class OnlineWalletFragment extends BaseFragment {

    private HotWalletAdapter mAdapter;

    private AssetsBean mAssetBean;
    private AssetsBean.ItemBean mSelectItem;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_wallet;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.rl, R.id.tvColdWallet);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        mAssetBean = DataManager.getInstance().getMyAssets();
        if (mAssetBean == null) {
            ((MainActivity) getActivity()).getMyAssets();
        } else {
            resetView();
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rl:
                gotoPager(WalletDetailFragment.class);
                break;
            case R.id.tvColdWallet:
//                ((MainActivity) getActivity()).setWalletType(1);
//                UserBean userBean = DataManager.getInstance().getUser();
//                if (userBean != null && !TextUtils.isEmpty(userBean.getWalletContentMD())) {
//                    MainColdActivity.startActivity(getActivity());
//                } else {
                ((MainActivity) getActivity()).setWalletType(1);
                //               }
                break;
        }
    }

    private HotWalletAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new HotWalletAdapter(getActivity());
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    mSelectItem = mAdapter.getItem(position);
                    resetSelectView();
                }
            });
        }
        return mAdapter;
    }

    public void setAssetBean(AssetsBean bean) {
        mAssetBean = bean;
        if (getView() == null) {
            return;
        }
        resetView();
    }

    private void resetView() {
        setText(R.id.tvTotal, mAssetBean.getTotal());
        getAdapter().setNewInstance(mAssetBean.getList());
        if (mAssetBean.getList() == null && mAssetBean.getList().isEmpty()) {
            return;
        }
        mSelectItem = mAssetBean.getList().get(0);
        resetSelectView();
    }

    private void resetSelectView() {
        setText(R.id.tvCoinName, mSelectItem.getName());
        setText(R.id.tvCoinFullName, mSelectItem.getAllname());
        setText(R.id.tvValue, mSelectItem.getTotal());
        setText(R.id.tvTotalCoin, "≈ $" + mSelectItem.getTousdt());
        setText(R.id.tvAddress, mSelectItem.getAddress_wallet());
        String name = mSelectItem.getName().toLowerCase();
        if (name.contains("-")) {
            name = name.split("-")[0];
        }
        try {
            int drawableId = getResources().getIdentifier("wallet_" + name, "drawable", getActivity().getPackageName());
            setImage(R.id.ivCoinIcon, drawableId);
        } catch (Exception e) {

        }
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
