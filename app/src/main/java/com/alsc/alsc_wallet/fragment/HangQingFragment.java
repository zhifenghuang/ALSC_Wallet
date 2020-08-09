package com.alsc.alsc_wallet.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.adapter.HangQingAdapter;
import com.alsc.alsc_wallet.adapter.SelectHorTypeAdapter;
import com.alsc.alsc_wallet.bean.CoinHangQingBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

public class HangQingFragment extends BaseFragment {

    private SelectHorTypeAdapter mHorAdapter;
    private HangQingAdapter mHangQingAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hang_qing;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.horRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getHorAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getHorAdapter());
        for (int i = 0; i < 6; ++i) {
            getHorAdapter().addData(getString(getResources().getIdentifier("wallet_hq_type_" + i, "string", getActivity().getPackageName())));
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getHangQingAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getHangQingAdapter());
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_btc_icon_1, "BTC", "1.23万亿", "67903.61", "$9585.59", "+6.19%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_eth, "ETH", "1860.35亿", "1699.15", "$239.89", "+7.36%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_eos, "EOS", "650.19亿", "7.0784", "$0.9993", "0.00%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_ltc, "LTC", "100.69亿", "5.0678", "$0.8763", "-3.45%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_dash, "DASH", "80.12", "3.0145", "$0.563", "-4.17%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_zec, "ZEC", "60.87亿", "18.0678", "$2.8763", "-5.37%"));
    }

    private SelectHorTypeAdapter getHorAdapter() {
        if (mHorAdapter == null) {
            mHorAdapter = new SelectHorTypeAdapter(getActivity());
            mHorAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    mHorAdapter.setSelectIndex(position);
                }
            });
        }
        return mHorAdapter;
    }

    private HangQingAdapter getHangQingAdapter() {
        if (mHangQingAdapter == null) {
            mHangQingAdapter = new HangQingAdapter(getActivity());
        }
        return mHangQingAdapter;
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }


}
