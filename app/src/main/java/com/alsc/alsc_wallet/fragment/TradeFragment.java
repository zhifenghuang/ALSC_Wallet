package com.alsc.alsc_wallet.fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.BaseActivity;
import com.alsc.alsc_wallet.adapter.CoinSymbolAdapter;
import com.alsc.alsc_wallet.adapter.SelectMoneyTypeAdapter;
import com.alsc.alsc_wallet.adapter.SelectPayTypeAdapter;
import com.alsc.alsc_wallet.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.trade.BuyCoinFragment;
import com.alsc.alsc_wallet.fragment.trade.SellCoinFragment;
import com.alsc.alsc_wallet.fragment.trade.TradeOrderFragment;

import java.util.ArrayList;

public class TradeFragment extends BaseFragment {

    private CoinSymbolAdapter mAdapter;
    private BaseFragment mCurrentFragment;
    private ArrayList<BaseFragment> mBaseFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getAdapter());
        ArrayList<String> list = new ArrayList<>();
        list.add("USDT");
        list.add("BTC");
        list.add("ETH");
        getAdapter().addData(list);
        setViewsOnClickListener(R.id.ivFilter, R.id.ivRecord, R.id.llBuy, R.id.llSell);

        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new BuyCoinFragment());
        mBaseFragment.add(new SellCoinFragment());
        switchFragment(mBaseFragment.get(0));
    }

    private CoinSymbolAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new CoinSymbolAdapter(getActivity());
        }
        return mAdapter;
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivFilter:
                showFilterDialog();
                break;
            case R.id.ivRecord:
                gotoPager(TradeOrderFragment.class);
                break;
            case R.id.llBuy:
                switchFragment(mBaseFragment.get(0));
                resetBtns(fv(R.id.llSell), (LinearLayout) v);
                break;
            case R.id.llSell:
                switchFragment(mBaseFragment.get(1));
                resetBtns(fv(R.id.llBuy), (LinearLayout) v);
                break;
        }
    }

    private void resetBtns(LinearLayout ll1, LinearLayout ll2) {
        ((TextView) ll1.getChildAt(0)).setTextColor(ContextCompat.getColor(getActivity(), R.color.color_00_00_00));
        ((TextView) ll1.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        ll1.getChildAt(1).setVisibility(View.INVISIBLE);

        ((TextView) ll2.getChildAt(0)).setTextColor(ContextCompat.getColor(getActivity(), R.color.color_07_bb_99));
        ((TextView) ll2.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        ll2.getChildAt(1).setVisibility(View.VISIBLE);
    }

    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(BaseFragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.add(R.id.fl, to, to.toString()).commit();
            } else {
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                ft.show(to).commit();
            }
        }
        mCurrentFragment = to;
    }

    private void showFilterDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_filter_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.paddingView, R.id.tvReset, R.id.tvOk);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView1);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gridLayoutManager);
                SelectMoneyTypeAdapter adapter = new SelectMoneyTypeAdapter(getActivity());
                adapter.onAttachedToRecyclerView(recyclerView);
                recyclerView.setAdapter(adapter);
                ArrayList<TypeBean> list = new ArrayList<>();
                list.add(new TypeBean(R.drawable.wallet_cny, "CNY"));
                list.add(new TypeBean(R.drawable.wallet_myr, "MYR"));
                list.add(new TypeBean(R.drawable.wallet_usd, "USD"));
                list.add(new TypeBean(R.drawable.wallet_krw, "KRW"));
                list.add(new TypeBean(R.drawable.wallet_sgd, "SGD"));
                list.add(new TypeBean(R.drawable.wallet_thb, "THB"));
                list.add(new TypeBean(R.drawable.wallet_twd, "TWD"));
                list.add(new TypeBean(R.drawable.wallet_vnd, "VND"));
                list.add(new TypeBean(R.drawable.wallet_idr, "IDR"));
                adapter.addData(list);

                recyclerView = view.findViewById(R.id.recyclerView2);
                gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(gridLayoutManager);
                SelectPayTypeAdapter adapter2 = new SelectPayTypeAdapter(getActivity());
                adapter.onAttachedToRecyclerView(recyclerView);
                recyclerView.setAdapter(adapter2);
                list = new ArrayList<>();
                list.add(new TypeBean(0, getString(R.string.wallet_pay_type_0)));
                list.add(new TypeBean(0, getString(R.string.wallet_pay_type_1)));
                list.add(new TypeBean(0, getString(R.string.wallet_pay_type_2)));
                list.add(new TypeBean(0, getString(R.string.wallet_pay_type_3)));
                list.add(new TypeBean(0, getString(R.string.wallet_pay_type_4)));
                adapter2.addData(list);
            }

            @Override
            public void onViewClick(int viewId) {

            }
        });
        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
    }

    public static class TypeBean {
        public int icon;
        public String name;

        public TypeBean(int icon, String name) {
            this.icon = icon;
            this.name = name;
        }

    }
}
