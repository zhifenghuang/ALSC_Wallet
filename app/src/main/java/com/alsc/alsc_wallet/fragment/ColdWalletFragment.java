package com.alsc.alsc_wallet.fragment;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.MainActivity;
import com.alsc.alsc_wallet.adapter.ColdWalletAdapter;
import com.alsc.alsc_wallet.adapter.ImportWalletAddressAdapter;
import com.alsc.chat.activity.ChatBaseActivity;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.common.fragment.BaseFragment;
import com.common.bean.CoinSymbolBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.wallet.activity.MainColdActivity;
import com.wallet.activity.cold.AddressActivity;
import com.wallet.activity.cold.ColdWalletAddActivity;
import com.wallet.activity.wallet.ColdWalletBackupActivity;
import com.wallet.activity.wallet.RestoreWalletActivity;
import com.wallet.fragment.MainColdFragment;
import com.wallet.retrofit.ColdInterface;
import com.wallet.utils.ToastUtil;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.ColdWallet;

public class ColdWalletFragment extends BaseFragment {

    private ColdWalletAdapter mAdapter;

    private ImportWalletAddressAdapter mAdapter1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cold_wallet;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.tvOnlineWallet, R.id.tvColdWallet, R.id.tvRestore, R.id.tvCreate, R.id.ivAddAddress, R.id.ivAddress);
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        getAdapter().onAttachedToRecyclerView(recyclerView);
//        recyclerView.setAdapter(getAdapter());
//        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eth, "ETH", "1FyMFyFyFy......h4FyAVz"));
//        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_usdt, "USDT", "1FyMFyFyFy......h4FyAVz"));
//        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_eos, "EOS", "1FyMFyFyFy......h4FyAVz"));
//        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_ltc, "LTC", "1FyMFyFyFy......h4FyAVz"));
//        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_dash, "DASH", "1FyMFyFyFy......h4FyAVz"));
//        getAdapter().addData(new CoinSymbolBean(R.drawable.wallet_zec, "ZEC", "1FyMFyFyFy......h4FyAVz"));
//
//        recyclerView = view.findViewById(R.id.recyclerView1);
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        getAdapter1().onAttachedToRecyclerView(recyclerView);
//        recyclerView.setAdapter(getAdapter1());
//        getAdapter1().addData("");
//        getAdapter1().addData("");

        UserBean userBean = DataManager.getInstance().getColdUser();
        if (userBean != null && !TextUtils.isEmpty(userBean.getWalletContentMD())) {
            setViewGone(R.id.llUnLogin);
            setViewVisible(R.id.llLogined);
        } else {
            setViewVisible(R.id.llUnLogin);
            setViewGone(R.id.llLogined);
        }

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.llLogined, MainColdFragment.newInstance(), "MainColdFragment").commit();
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivAddress:
                gotoPager(AddressActivity.class);
                break;
            case R.id.ivAddAddress:
                gotoPager(ColdWalletAddActivity.class);
                break;
            case R.id.tvOnlineWallet:
                ((MainActivity) getActivity()).setWalletType(0);
                break;
            case R.id.tvColdWallet:
                ((MainActivity) getActivity()).setWalletType(1);
                break;
            case R.id.tvRestore:
                gotoPager(RestoreWalletActivity.class);
                break;
            case R.id.tvCreate:
                create();
                break;
        }
    }

    private void create() {
        String userName = getTextById(R.id.etName);
        String pass = getTextById(R.id.etPassword);
        String passRetry = getTextById(R.id.etPasswordRetry);
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.toast(getString(R.string.wc_code_input_account));
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            ToastUtil.toast(getString(R.string.wc_code_input_wallet_pwd));
            return;
        }
        if (!pass.equals(passRetry)) {
            ToastUtil.toast(getString(R.string.create_wallet_pwd_confirm_input_tips));
            return;
        }
        if (pass.length() < 8) {
            ToastUtil.toast(getString(R.string.saving_wallet_password_length));
            return;
        }

        setViewEnable(R.id.tvCreate, false);
        ((ChatBaseActivity) getActivity()).showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserBean userBean = new UserBean();
                    userBean.setAccount(userName);
                    userBean.setPassword(pass);
                    userBean.setWalletType(1);
                    userBean.setUserId(-1);
                    ColdWallet wallet = ColdWalletUtil.createColdWallet(userName, pass);
                    String walletContent = getGson().toJson(wallet);
                    userBean.setWalletContentMD(walletContent);
                    userBean.setCreateTime(System.currentTimeMillis());
                    DataManager.getInstance().saveColdUser(userBean);
                    ColdInterface.requestColdCreates(ColdInterface.getRequstWallet(wallet));

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setViewGone(R.id.llUnLogin);
                            setViewVisible(R.id.llLogined);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.toast(getString(R.string.saving_wallet_failure));
                        }
                    });
                } finally {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setViewEnable(R.id.tvCreate, true);
                            ((ChatBaseActivity) getActivity()).hideLoading();
                        }
                    });
                }
            }
        }).start();


    }

    private ColdWalletAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new ColdWalletAdapter(getActivity());
        }
        return mAdapter;
    }

    private ImportWalletAddressAdapter getAdapter1() {
        if (mAdapter1 == null) {
            mAdapter1 = new ImportWalletAddressAdapter(getActivity());
            mAdapter1.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                }
            });
        }
        return mAdapter1;
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }

    private Gson mGson;

    private Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }
}
