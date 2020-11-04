package io.netflow.walletpro.fragment;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.common.activity.BaseActivity;
import com.common.fragment.BaseFragment;
import com.google.gson.Gson;
import com.wallet.activity.cold.AddressActivity;
import com.wallet.activity.cold.ColdWalletAddActivity;
import com.wallet.activity.wallet.RestoreWalletActivity;
import com.wallet.event.LoginEvent;
import com.wallet.fragment.MainColdFragment;
import com.wallet.retrofit.ColdInterface;
import com.wallet.utils.ToastUtil;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.ColdWallet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.netflow.walletpro.R;
import io.netflow.walletpro.activity.MainActivity;
import io.netflow.walletpro.adapter.ColdWalletAdapter;
import io.netflow.walletpro.adapter.ImportWalletAddressAdapter;

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

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        UserBean userBean = DataManager.getInstance().getColdUser();
        if (userBean != null && !TextUtils.isEmpty(userBean.getWalletContentMD())) {
            setViewGone(R.id.llUnLogin);
            setViewVisible(R.id.llLogined);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.llLogined, MainColdFragment.newInstance(), "MainColdFragment").commit();
        } else {
            setViewGone(R.id.ivAddress, R.id.ivAddAddress);
            setViewVisible(R.id.llUnLogin);
            setViewGone(R.id.llLogined);
        }
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
        ((BaseActivity) getActivity()).showLoading();
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
                            setViewVisible(R.id.llLogined, R.id.ivAddress, R.id.ivAddAddress);
                            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                            ft.add(R.id.llLogined, MainColdFragment.newInstance(), "MainColdFragment").commit();
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
                            ((BaseActivity) getActivity()).hideLoading();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        UserBean userBean = DataManager.getInstance().getColdUser();
        if (userBean != null && !TextUtils.isEmpty(userBean.getWalletContentMD())) {
            setViewGone(R.id.llUnLogin);
            setViewVisible(R.id.llLogined);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.llLogined, MainColdFragment.newInstance(), "MainColdFragment").commit();
        } else {
            setViewGone(R.id.ivAddress, R.id.ivAddAddress);
            setViewVisible(R.id.llUnLogin);
            setViewGone(R.id.llLogined);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
