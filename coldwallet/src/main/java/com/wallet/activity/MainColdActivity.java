package com.wallet.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.base.PoliceApplication;
import com.cao.commons.bean.ChangeLanguageEvent;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.cold.ColdHqBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cao.commons.util.log.Log;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityMainColdBinding;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.wallet.activity.adapter.MainColdAdapter;
import com.wallet.activity.cold.AddressActivity;
import com.wallet.activity.cold.ColdAssetsListActivity;
import com.wallet.activity.cold.ColdWalletAddActivity;
import com.wallet.wallet.bean.JnWallet;
import com.wallet.widget.dialog.RiskDialog;
import com.wallet.event.ChangeWalletNameEvent;
import com.wallet.event.WalletAddEvent;
import com.wallet.event.WalletAllEvent;
import com.wallet.retrofit.ColdInterface;
import com.wallet.retrofit.HttpInfoRequest;
import com.wallet.utils.Utils;
import com.wallet.wallet.bean.ColdWallet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 冷钱包主页
 */
public class MainColdActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMainColdBinding binding;
    private MainColdAdapter mAdapter;
    //BTC ETH USDT omni USDT etc A13
    private String[] walletNames = new String[]{"BTC", "ETH", "USDT-OMNI", "USDT-ERC20"};//, "A13"};
    private String currentWallet = "BTC";
    private ColdWallet mColdWallet;
    private boolean isEyeOpen = true;
    private boolean isFirst = true;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainColdActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, boolean isFromRegister) {
        Intent intent = new Intent(context, MainColdActivity.class);
        intent.putExtra("isFromRegister", isFromRegister);
        context.startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (binding != null) {
                        setCurrentData(false);
                        if (handler != null) {
                            handler.sendEmptyMessageDelayed(2, 10 * 1000);
                        }
                    }
                    break;
                case 1:
                    setCurrentData(true);
                    break;
                case 2://刷新币
                    Utils.refreshMoney(handler);
                    break;
                case 3://刷新行情
                    getColdHq();
                    break;

            }
        }
    };

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_cold);
        binding.setClickListener(this);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .titleBar(R.id.ll_top)
                .init();

        isEyeOpen = DataManager.getInstance().getColdEye();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainColdAdapter(mContext, isEyeOpen);
        binding.recyclerView.setAdapter(mAdapter);
        if (!isEyeOpen) {
            changeEye();
        }

        initWallet();

        handler.sendEmptyMessage(1);
        handler.sendEmptyMessage(3);
        if (getIntent() != null) {
            boolean isFromRegister = getIntent().getBooleanExtra("isFromRegister", false);
            if (isFromRegister) {
                showAdDialog();
            }
        }
    }

    private void initWallet() {
        ArrayList<UserBean> list = DatabaseOperate.getInstance().getColdUserInfos();
        if (list != null && list.size() > 0) {
            UserBean userBean = list.get(list.size() - 1);
            Gson mGson = new Gson();
            mColdWallet = mGson.fromJson(userBean.getWalletContentMD(), ColdWallet.class);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        changeExpandStatus(false);
        setCurrentData(false);
    }

    @Override
    protected void setListener() {
        super.setListener();
//        binding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY == 0) {
//                    binding.flTop.setAlpha(0);
//                } else if (Math.abs(scrollY) < 200) {
//                    float scale = Math.abs(scrollY) * 1.0f / 200;
//                    binding.flTop.setAlpha(scale);
//                } else {
//                    binding.flTop.setAlpha(1.0f);
//                }
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_eye) {
            isEyeOpen = !isEyeOpen;
            DataManager.getInstance().saveColdEye(isEyeOpen);
            changeEye();
        } else if (id == R.id.fl_avatar) {
            showMyMoreDialog();
        } else if (id == R.id.fl_address) {
            AddressActivity.startActivity(mContext);
        } else if (id == R.id.ll_add_wallet) {
            ColdWalletAddActivity.startActivity(mContext);
        } else if (id == R.id.ll_expand) {
            mAdapter.setExpand(!mAdapter.isExpand());
            changeExpandStatus(mAdapter.isExpand());
        } else if (id == R.id.ll_wallet) {
            JnWallet jnWallet = Utils.getWallet(mColdWallet, currentWallet);
//                if (jnWallet.getWalletType().getType().trim().equals("USDT-OMNI")) {
//                    return;
//                }
            jnWallet.setMnemonicCode(mColdWallet.getMnemonicCode());
            ColdAssetsListActivity.startActivity(mContext,
                    binding.tvWallet.getText().toString(), jnWallet);
        } else if (id == R.id.ll_item1) {
            currentWallet = binding.tvWalletName1.getText().toString();
            changeExpandStatus(false);
            setCurrentData(true);
        } else if (id == R.id.ll_item2) {
            currentWallet = binding.tvWalletName2.getText().toString();
            changeExpandStatus(false);
            setCurrentData(true);
        } else if (id == R.id.ll_item3) {
            currentWallet = binding.tvWalletName3.getText().toString();
            changeExpandStatus(false);
            setCurrentData(true);
        } else if (id == R.id.ll_item4) {
            currentWallet = binding.tvWalletName4.getText().toString();
            changeExpandStatus(false);
            setCurrentData(true);
        }
    }

    private void changeExpandStatus(boolean isExpand) {
        if (isExpand) {
            binding.tvExpand.setText(mContext.getString(R.string.c_collapse));
            binding.ivArrow.setRotation(180f);
        } else {
            binding.tvExpand.setText(mContext.getString(R.string.c_expand));
            binding.ivArrow.setRotation(0f);
        }
    }

    private void changeEye() {
        if (isEyeOpen) {
            binding.tvMoney.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletHaveMoney.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletHaveEqual.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletMoney1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletMoney2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletMoney3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletMoney4.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletEqual1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletEqual2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletEqual3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.tvWalletEqual4.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            binding.tvMoney.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletHaveMoney.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletHaveEqual.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletMoney1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletMoney2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletMoney3.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletMoney4.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletEqual1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletEqual2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletEqual3.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.tvWalletEqual4.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mAdapter.setEyeOpen(isEyeOpen);
    }

    private synchronized void setCurrentData(boolean b) {
        try {
            ArrayList<UserBean> list = DatabaseOperate.getInstance().getColdUserInfos();
            if (list == null || list.size() == 0) {
                return;
            }
            UserBean userBean = list.get(list.size() - 1);
            Gson mGson = new Gson();
            ColdWallet coldWallet = mGson.fromJson(userBean.getWalletContentMD(), ColdWallet.class);

            binding.tvWallet.setText(currentWallet);
            binding.tvWalletSummary.setText(Utils.getTitleSum(currentWallet));
            binding.tvWalletHave.setText(Utils.getWalletName(coldWallet, currentWallet));
            JnWallet jnWallet = Utils.getWallet(coldWallet, currentWallet);
            if (jnWallet != null) {
                binding.tvWalletHaveAddress.setText("" + jnWallet.getAddress());
                Log.e("aaaaaaaaa", jnWallet.getAddress() + ", " + jnWallet.getWalletType());
                if (!TextUtils.isEmpty(jnWallet.getMoney())) {
                    binding.tvWalletHaveMoney.setText("" + jnWallet.getMoney());
                    binding.ivHaveLoading1.clearAnimation();
                    binding.ivHaveLoading1.setImageBitmap(null);
                } else {
                    binding.tvWalletHaveMoney.setText("");
                    binding.ivHaveLoading1.setImageResource(R.drawable.ic_svstatus_loading);
                    binding.ivHaveLoading1.startAnimation(createAnimation());
                }
                if (!TextUtils.isEmpty(jnWallet.getPrice())) {
                    binding.tvWalletHaveEqual.setText("≈$" + jnWallet.getPrice());
                } else {
                    binding.tvWalletHaveEqual.setText("");
                }
            }
            ArrayList<WalletDataBean> beans = Utils.getAllWalletInfos(currentWallet);
            mAdapter.clearAll();
            if (beans.size() == 0) {
                binding.llExpand.setVisibility(View.GONE);
            } else {
                binding.llExpand.setVisibility(View.VISIBLE);
                if (b) {
                    mAdapter.setData(currentWallet, beans);
                } else {
                    mAdapter.refreshData(currentWallet, beans);
                }
            }
            int index = 0;
            for (int i = 0; i < walletNames.length; i++) {
                if (currentWallet.equals(walletNames[i])) {
                    continue;
                }
                index++;
                if (index == 1) {
                    binding.ivImage1.setImageResource(Utils.getImageResourceCold(walletNames[i]));
                    binding.tvWalletName1.setText(walletNames[i]);
                    JnWallet wallet = Utils.getWallet(coldWallet, walletNames[i]);
                    if (wallet != null) {
                        binding.tvWalletPath1.setText("" + wallet.getAddress());
                        if (!TextUtils.isEmpty(wallet.getMoney())) {
                            binding.tvWalletMoney1.setText("" + wallet.getMoney());
                            binding.ivMoneyLoading1.clearAnimation();
                            binding.ivMoneyLoading1.setImageBitmap(null);
                        } else {
                            binding.tvWalletMoney1.setText("");
                            binding.ivMoneyLoading1.setImageResource(R.drawable.ic_svstatus_loading);
                            binding.ivMoneyLoading1.startAnimation(createAnimation());
                        }
                        if (!TextUtils.isEmpty(wallet.getPrice())) {
                            binding.tvWalletEqual1.setText("≈$" + wallet.getPrice());
                        } else {
                            binding.tvWalletEqual1.setText("");
                        }
                    }
                } else if (index == 2) {
                    binding.ivImage2.setImageResource(Utils.getImageResourceCold(walletNames[i]));
                    binding.tvWalletName2.setText(walletNames[i]);
                    JnWallet wallet = Utils.getWallet(coldWallet, walletNames[i]);
                    if (wallet != null) {
                        binding.tvWalletPath2.setText("" + wallet.getAddress());
                        if (!TextUtils.isEmpty(wallet.getMoney())) {
                            binding.tvWalletMoney2.setText("" + wallet.getMoney());
                            binding.ivMoneyLoading2.clearAnimation();
                            binding.ivMoneyLoading2.setImageBitmap(null);
                        } else {
                            binding.tvWalletMoney2.setText("");
                            binding.ivMoneyLoading2.setImageResource(R.drawable.ic_svstatus_loading);
                            binding.ivMoneyLoading2.startAnimation(createAnimation());
                        }
                        if (!TextUtils.isEmpty(wallet.getPrice())) {
                            binding.tvWalletEqual2.setText("≈$" + wallet.getPrice());
                        } else {
                            binding.tvWalletEqual2.setText("");
                        }
                    }
                } else if (index == 3) {
                    binding.ivImage3.setImageResource(Utils.getImageResourceCold(walletNames[i]));
                    binding.tvWalletName3.setText(walletNames[i]);
                    JnWallet wallet = Utils.getWallet(coldWallet, walletNames[i]);
                    if (wallet != null) {
                        binding.tvWalletPath3.setText("" + wallet.getAddress());
                        if (!TextUtils.isEmpty(wallet.getMoney())) {
                            binding.tvWalletMoney3.setText("" + wallet.getMoney());
                            binding.ivMoneyLoading3.clearAnimation();
                            binding.ivMoneyLoading3.setImageBitmap(null);
                        } else {
                            binding.tvWalletMoney3.setText("");
                            binding.ivMoneyLoading3.setImageResource(R.drawable.ic_svstatus_loading);
                            binding.ivMoneyLoading3.startAnimation(createAnimation());
                        }
                        if (!TextUtils.isEmpty(wallet.getPrice())) {
                            binding.tvWalletEqual3.setText("≈$" + wallet.getPrice());
                        } else {
                            binding.tvWalletEqual3.setText("");
                        }
                    }
                } else if (index == 4) {
                    binding.ivImage4.setImageResource(Utils.getImageResourceCold(walletNames[i]));
                    binding.tvWalletName4.setText(walletNames[i]);
                    JnWallet wallet = Utils.getWallet(coldWallet, walletNames[i]);
                    if (wallet != null) {
                        binding.tvWalletPath4.setText("" + wallet.getAddress());
                        if (!TextUtils.isEmpty(wallet.getMoney())) {
                            binding.tvWalletMoney4.setText("" + wallet.getMoney());
                            binding.ivMoneyLoading4.clearAnimation();
                            binding.ivMoneyLoading4.setImageBitmap(null);
                        } else {
                            binding.tvWalletMoney4.setText("");
                            binding.ivMoneyLoading4.setImageResource(R.drawable.ic_svstatus_loading);
                            binding.ivMoneyLoading4.startAnimation(createAnimation());
                        }
                        if (!TextUtils.isEmpty(wallet.getPrice())) {
                            binding.tvWalletEqual4.setText("≈$" + wallet.getPrice());
                        } else {
                            binding.tvWalletEqual4.setText("");
                        }
                    }
                }
            }

            binding.tvMoney.setText(Utils.getTotalMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getColdHq() {
        String[] strings = new String[]{"BTC", "ETH", "USDT", "A13"};
        ColdInterface.getColdHq(mContext, Tag, new HttpInfoRequest<ColdHqBean>() {
            @Override
            public void onSuccess(ColdHqBean model) {
                if (model != null) {
                    PoliceApplication.setColdHqBean(model);
                    handler.sendEmptyMessageDelayed(3, 60 * 1000);
                    if (!handler.hasMessages(2) && isFirst) {
                        isFirst = false;
                        handler.sendEmptyMessage(2);
                    }
                }
            }

            @Override
            public void onError(int eCode) {

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAllEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WalletAddEvent event) {
        showAdDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangeLanguageEvent event) {
        binding.tvTitle.setText(getString(R.string.total_asset));
        binding.tvTitle1.setText(getString(R.string.total_assets_add));
        changeExpandStatus(mAdapter.isExpand());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangeWalletNameEvent event) {
        initWallet();
    }

    public void showMyMoreDialog() {

    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeMessages(0);
            handler.removeMessages(1);
            handler.removeMessages(2);
            handler.removeMessages(3);
            handler = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    private boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            Toast.makeText(this, getString(R.string.quit_app), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    private RotateAnimation createAnimation() {
        RotateAnimation mRotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000L);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

        return mRotateAnimation;
    }

    private void showAdDialog() {
        RiskDialog dialog = new RiskDialog(mContext);
        dialog.show();
    }
}
