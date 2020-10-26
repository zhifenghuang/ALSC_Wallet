package com.wallet.activity.cold;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.cao.commons.base.BaseActivity;
import com.cao.commons.base.PoliceApplication;
import com.cao.commons.bean.chat.CaptureEvent;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.cold.EthgasBean;
import com.cao.commons.bean.cold.TradeInfoBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cold.wallet.databinding.ActivityColdAssetsTransferBinding;
import com.google.gson.Gson;
import com.cold.wallet.R;
import com.wallet.activity.my.CaptureActivity;
import com.wallet.widget.dialog.ContributeColdDialog;
import com.wallet.event.CheckCPwdEvent;
import com.wallet.event.WalletAllEvent;
import com.wallet.retrofit.ColdInterface;
import com.wallet.utils.MoneyUtil;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.BtcFee;
import com.wallet.wallet.bean.BtcTransaction;
import com.wallet.wallet.bean.ColdWallet;
import com.wallet.wallet.bean.ContractType;
import com.wallet.wallet.bean.EthSignature;
import com.wallet.wallet.bean.JnWallet;
import com.wallet.wallet.bean.WalletType;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.UTXO;
import org.bitcoinj.script.Script;
import org.bouncycastle.util.encoders.Hex;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 转账 冷钱包
 */
public class ColdAssetsTransferActivity extends BaseActivity implements View.OnClickListener {
    private final static int REQUEST_CODE_ADDRESS = 100;
    private final static BigDecimal ETH_GAS_MIN_PRICE = new BigDecimal(0.00012);
    private final static BigDecimal ETH_GAS_MAX_PRICE = new BigDecimal(0.006);

    private ActivityColdAssetsTransferBinding binding;
    private String mSymbol, mReceiveUrl;
    private JnWallet mJnWallet;
    private ContributeColdDialog mDialog;
    private String mAddress;
    private WalletDataBean mWalletDataBean;
    private int chooseErc = 3;
    private String mBalance = "";
    private float mTransferFee;
    /**
     * eth的费用，用来检查旷工费
     */
    private String ethGasPrice;
    private List<UTXO> mUtxoList;
    private String publicKey;
    private BtcFee mBtcFee;

    public static void startActivity(Context context, String symbol, String receiveUrl) {
        Intent intent = new Intent(context, ColdAssetsTransferActivity.class);
        intent.putExtra("symbol", symbol);
        intent.putExtra("receiveUrl", receiveUrl);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String symbol, JnWallet wallet) {
        Intent intent = new Intent(context, ColdAssetsTransferActivity.class);
        intent.putExtra("symbol", symbol);
        intent.putExtra("wallet", wallet);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, WalletDataBean wallet) {
        Intent intent = new Intent(context, ColdAssetsTransferActivity.class);
        intent.putExtra("walletData", wallet);
        context.startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    String message = (String) msg.obj;
                    ToastUtil.toast(message);
                    dismissDialog();
                    if (message.equals(getString(R.string.alsc_transfer_success))) {
                        EventBus.getDefault().post(new WalletAllEvent());
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cold_assets_transfer);
        binding.setClickListener(this);
        EventBus.getDefault().register(this);
        mSymbol = getIntent().getStringExtra("symbol");
        mReceiveUrl = getIntent().getStringExtra("receiveUrl");
        mJnWallet = (JnWallet) getIntent().getSerializableExtra("wallet");
        mWalletDataBean = (WalletDataBean) getIntent().getSerializableExtra("walletData");
        if (mJnWallet != null) {
            mAddress = mJnWallet.getAddress();
            publicKey = mJnWallet.getPublicKey();
        } else if (mWalletDataBean != null) {
            mAddress = mWalletDataBean.getAddress();
            mSymbol = mWalletDataBean.getWalletType();
            publicKey = mWalletDataBean.getPublicKey();
        } else {
            String walletContentMD = DataManager.getInstance().getColdUser().getWalletContentMD();
            Gson mGson = new Gson();
            ColdWallet mColdWallet = mGson.fromJson(walletContentMD, ColdWallet.class);
            mJnWallet = Utils.getWallet(mColdWallet, mSymbol);
            mAddress = mJnWallet.getAddress();
            publicKey = mJnWallet.getPublicKey();
        }

        binding.tvToolbarTitle.setText(mSymbol + getString(R.string.my_transfer));

        binding.tvTitleAlsc1.setText(mSymbol + " " + getString(R.string.my_balance) + "：");
        binding.tvTitleAlsc3.setText(mSymbol);
        binding.etReceiveUrl.setHint(getString(R.string.my_input) + mSymbol + getString(R.string.my_address));
        if (!TextUtils.isEmpty(mReceiveUrl)) {
            binding.etReceiveUrl.setText(mReceiveUrl);
        }
        binding.tvSendUrl.setText(mAddress);
        binding.llItem1.setVisibility(View.GONE);
        binding.llItem2.setVisibility(View.VISIBLE);

        initMoney();
        getUtxo();

    }

    private void getUtxo() {
        String type = "";
        if ("ETH".equals(mSymbol) || "USDT-ERC20".equals(mSymbol) || "A13".equals(mSymbol)) {
            type = "ETH";
        } else {
            type = "BTC";
        }
        String fee = Utils.getEqualMoney(PoliceApplication.getColdHqBean(), type);
        mTransferFee = Float.parseFloat(fee);
        if ("BTC".equals(mSymbol) || "USDT-OMNI".equals(mSymbol)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mUtxoList = ColdWalletUtil.getBtcColdWallet().getUnspentTransactionOutput1(mAddress);
                        mBtcFee = ColdWalletUtil.getBtcColdWallet().getBtcFee();
                    } catch (Exception e) {
                        e.printStackTrace();
                        mUtxoList = new ArrayList<>();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mBtcFee != null) {
                                int maxValue = (int) (mBtcFee.getFastestFee() - mBtcFee.getHourFee());
                                if (TextUtils.isEmpty(binding.tvConvert.getText().toString().trim())) {
                                    int currentValue = (int) (mBtcFee.getHalfHourFee() - mBtcFee.getHourFee());
                                    binding.seekbar.setProgress(currentValue);
                                    try {
                                        if (mUtxoList!=null && mUtxoList.size()>0) {
                                            binding.tvConvert.setText(ColdWalletUtil.getBtcColdWallet().getBtcPrecisionPrice(mUtxoList, currentValue, mTransferFee, "$"));
                                        } else {
                                            binding.tvConvert.setText(ColdWalletUtil.getBtcColdWallet().getBtcPrecisionPrice(createSpecialUtxo(), currentValue, mTransferFee, "$"));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (maxValue == 0) {
                                    maxValue = 21;
                                }
                                binding.seekbar.setMax(maxValue);
                            }
                        }
                    });
                }
            }).start();

        } else {
            if (PoliceApplication.getColdHqBean() != null) {
                int gWei = getLastValue().intValue();
                binding.seekbar.setProgress(gWei);
                try {
                    if ("A13".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
                        binding.tvConvert.setText(ColdWalletUtil.getEthColdWallet().getAlscPrecisionPrice(gWei, mTransferFee, "$"));
                    } else if ("ETH".equals(mSymbol)) {
                        binding.tvConvert.setText(ColdWalletUtil.getEthColdWallet().getEthPrecisionPrice(gWei, mTransferFee, "$"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private void initMoney() {
        if (mJnWallet != null) {
            UserBean userBean = DataManager.getInstance().getColdUser();
            Gson mGson = new Gson();
            ColdWallet coldWallet = mGson.fromJson(userBean.getWalletContentMD(), ColdWallet.class);
            JnWallet jnWallet = Utils.getWallet(coldWallet, mSymbol);
            mBalance = "" + jnWallet.getMoney();
        } else if (mWalletDataBean != null) {
            WalletDataBean walletDataBean = DatabaseOperate.getInstance().getWalletInfo(mWalletDataBean.getId());
            mBalance = "" + walletDataBean.getMoney();
        }
        binding.tvMoney.setText(mBalance);
        String type = "";
        if ("ETH".equals(mSymbol) || "USDT-ERC20".equals(mSymbol) || "A13".equals(mSymbol)) {
            type = "ETH";
        } else {
            type = "BTC";
        }

    }

    private List<UTXO> createSpecialUtxo(){
        UTXO utxo = new UTXO(Sha256Hash.wrap("849b26ca347d0c4d33b10909d7781d2af84690439dd078d7af565bd0a8e2cc89"),
                Long.valueOf("0"), Coin.valueOf(Long.valueOf("1000")),
                0, false, new Script(Hex.decode("76a91479911778660c48cdceab4799088ae8729b522f9888ac")));
        List<UTXO> utxos = new ArrayList<>();
        utxos.add(utxo);
        return utxos;
    }
    @Override
    protected void setListener() {
        super.setListener();
        binding.etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Utils.getTransferMaxLength(mSymbol)) {
        }});
        binding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!TextUtils.isEmpty(s)) {
//                    if ("A13".equals(mSymbol)) {
//                        try {
//                            double money = Double.parseDouble(s.toString());
//                            if (money > 10) {
//                                money = money - 10;
//                            } else {
//                                money = 0;
//                            }
//                            String moneyStr = String.valueOf(money);
//                            if (moneyStr.endsWith(".0")) {
//                                moneyStr += "0";
//                            }
//                            binding.tvRest.setText(moneyStr);
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    binding.tvRest.setText("");
//                }
            }
        });
        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int gWei = progress;
                try {
                    if (mTransferFee != 0) {
                        if ("A13".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
                            if (progress <= 2) {
                                gWei = 2;
                            }
                            binding.tvConvert.setText(ColdWalletUtil.getEthColdWallet().getAlscPrecisionPrice(gWei, mTransferFee, "$"));
                        } else if ("ETH".equals(mSymbol)) {
                            if (progress <= 2) {
                                gWei = 2;
                            }
                            binding.tvConvert.setText(ColdWalletUtil.getEthColdWallet().getEthPrecisionPrice(gWei, mTransferFee, "$"));
                        } else if ("BTC".equals(mSymbol) || "USDT-OMNI".equals(mSymbol)) {
                            if (mBtcFee != null && mUtxoList != null) {
                                long gWeii = progress + mBtcFee.getHourFee();
                                if (mUtxoList!=null && mUtxoList.size()>0) {
                                    binding.tvConvert.setText(ColdWalletUtil.getBtcColdWallet().getBtcPrecisionPrice(mUtxoList, gWeii, mTransferFee, "$"));
                                } else {
                                    binding.tvConvert.setText(ColdWalletUtil.getBtcColdWallet().getBtcPrecisionPrice(createSpecialUtxo(), gWeii, mTransferFee, "$"));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private BigDecimal getLastValue() {
        BigDecimal mSafeLow;
        try {
            mSafeLow = ColdWalletUtil.getEthColdWallet().latelyCurrentGasPrice();
        } catch (Exception e) {
            e.printStackTrace();
            mSafeLow = new BigDecimal(10);
        }
        return mSafeLow;
    }


    private void initSeekBarData(EthgasBean model) {
        BigDecimal fastest = new BigDecimal(model.getFastest()).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("10"));
        BigDecimal average = new BigDecimal(model.getAverage()).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("10"));
        BigDecimal mSafeLow = new BigDecimal(model.getSafeLow()).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal("10"));
        int maxValue = fastest.subtract(mSafeLow).intValue();
        if (maxValue > 0) {
            binding.seekbar.setMax(maxValue);

        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next) {
            next();
        } else if (id == R.id.fl_scan) {
            jumpScan();
        } else if (id == R.id.tv_all_money) {
            binding.etContent.setText(mBalance);
        } else if (id == R.id.iv_address) {
            AddressActivity.startActivityForResult(this, mSymbol, REQUEST_CODE_ADDRESS);
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void next() {
        String moneyStr = binding.etContent.getText().toString();
        String receiveUrl = binding.etReceiveUrl.getText().toString();
        if (TextUtils.isEmpty(moneyStr)) {
            ToastUtil.toast(getString(R.string.input_transfer_money));
            return;
        }
        if (TextUtils.isEmpty(receiveUrl)) {
            ToastUtil.toast(getString(R.string.input_transfer_receive_url));
            return;
        }
        if (mDialog == null) {
            mDialog = new ContributeColdDialog(mContext);
        }
        mDialog.showDialog(Tag);
    }

    private void transfer() {
        String money = binding.etContent.getText().toString();
        String receiveUrl = binding.etReceiveUrl.getText().toString();
        int progress = binding.seekbar.getProgress();
        if ("ETH".equals(mSymbol) || "USDT-ERC20".equals(mSymbol) || "A13".equals(mSymbol)) {
            if (!ColdWalletUtil.getEthColdWallet().isValidateAddress(receiveUrl)) {
                ToastUtil.toast(getString(R.string.input_receive_url_failure));
                return;
            }
            String ethgas = "";
            int gWei = progress;
            if (progress < 2) {
                gWei = 2;
            }
            try {
                if ("A13".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
                    ethgas = ColdWalletUtil.getEthColdWallet().getEthPrecision(gWei);
                } else if ("ETH".equals(mSymbol)) {
                    ethgas = ColdWalletUtil.getEthColdWallet().getAlscPrecision(gWei);
                }
                if (!TextUtils.isEmpty(ethgas) && !TextUtils.isEmpty(ethGasPrice)) {
                    if (ethgas.contains(" ETHER")) {
                        ethgas = ethgas.replace(" ETHER", "");
                    }
                    if (MoneyUtil.moneyComp(ethGasPrice, ethgas) < 0) {
                        ToastUtil.toast(getString(R.string.user_balance_eth_not_enough));
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("BTC".equals(mSymbol) || "USDT-OMNI".equals(mSymbol)) {
            if (mUtxoList == null || mBtcFee == null) {
                return;
            }
        }

        showWithStatus();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage(100);
                String fromAddress = "", privateKey = "";
                if (mJnWallet != null) {
                    fromAddress = mJnWallet.getAddress();
                    privateKey = mJnWallet.getPrivateKey();
                } else if (mWalletDataBean != null) {
                    fromAddress = mWalletDataBean.getAddress();
                    privateKey = mWalletDataBean.getPrivateKey();
                }


                String fees = "";
                int gWei = progress;
                try {
                    if ("A13".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
                        if (progress <= 2) {
                            gWei = 2;
                        }
                        fees = ColdWalletUtil.getEthColdWallet().getAlscPrecision(gWei);
                    } else if ("ETH".equals(mSymbol)) {
                        if (progress <= 2) {
                            gWei = 2;
                        }
                        fees = ColdWalletUtil.getEthColdWallet().getEthPrecision(gWei);
                    } else if ("BTC".equals(mSymbol) || "USDT-OMNI".equals(mSymbol)) {
                        if (progress <= 3) {
                            gWei = 3;
                        }
                        if (mUtxoList != null) {
                            fees = "" + ColdWalletUtil.getBtcColdWallet().getFee(mUtxoList, (long) gWei);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String gasPriceParam = String.valueOf(gWei);

                if ("ETH".equals(mSymbol) || "USDT-ERC20".equals(mSymbol) || "A13".equals(mSymbol)) {
                    try {
                        EthSignature txhash = null;
                        if ("ETH".equals(mSymbol)) {
                            txhash = ColdWalletUtil.getEthColdWallet().sendEthTransfer(privateKey, fromAddress, receiveUrl, gasPriceParam, money);
                        } else if ("USDT-ERC20".equals(mSymbol)) {
                            txhash = ColdWalletUtil.getEthColdWallet().sendTokenTransfer(privateKey, fromAddress, receiveUrl, gasPriceParam, money, ContractType.USDT_ERC20.getUrl());
                        } else if ("A13".equals(mSymbol)) {
                            txhash = ColdWalletUtil.getEthColdWallet().sendTokenTransfer(privateKey, fromAddress, receiveUrl, gasPriceParam, money, ContractType.A13.getUrl());
                        }

                        if (txhash != null) {
                            if (!TextUtils.isEmpty(txhash.getTxhash())) {
                                ColdInterface.createETH(fromAddress, receiveUrl, txhash.getTxhash(), mSymbol, money, fees, mContext, "");
                                TradeInfoBean infoBean = new TradeInfoBean();
                                infoBean.setWalletType(mSymbol);
                                infoBean.setSendType(1);
                                infoBean.setLoginAccount(DataManager.getInstance().getColdUser().getAccount());
                                infoBean.setCreateTime(System.currentTimeMillis());
                                infoBean.setHash(txhash.getTxhash());
                                infoBean.setFromAddress(fromAddress);
                                infoBean.setToAddress(receiveUrl);
                                infoBean.setValue(money);
                                infoBean.setGasPrice(gasPriceParam);
                                DatabaseOperate.getInstance().insertOrUpdate(infoBean);
                                message.obj = getString(R.string.alsc_transfer_success);
                            } else {
                                message.obj = getString(R.string.alsc_transfer_failure);
                            }
                        } else {
                            message.obj = getString(R.string.alsc_transfer_failure);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        message.obj = Utils.getToastMessage(mContext, e.toString());
                    }
                } else if ("BTC".equals(mSymbol)) {
                    if (mUtxoList.size() == 0) {
                        message.obj = getString(R.string.balance_insufficient);
                        return;
                    }
                    try {
                        if (!ColdWalletUtil.getBtcColdWallet().isValidateAddress(receiveUrl, WalletType.BTC)) {
                            message.obj = getString(R.string.input_receive_url_failure);
                        } else {
                            long fee = progress + mBtcFee.getHourFee();
                            BtcTransaction txhash = ColdWalletUtil.getBtcColdWallet().sendBtcTransfer(fromAddress, receiveUrl, fee, privateKey, money,
                                    mUtxoList);

                            if (txhash != null && !TextUtils.isEmpty(txhash.getTxHash())) {
                                ColdInterface.createBTC(fromAddress, receiveUrl, txhash.getTxHash(), money, MoneyUtil.moneydiv8(fees,"100000000"), mContext, "");
                                TradeInfoBean infoBean = new TradeInfoBean();
                                infoBean.setWalletType(mSymbol);
                                infoBean.setSendType(1);
                                infoBean.setLoginAccount(DataManager.getInstance().getColdUser().getAccount());
                                infoBean.setCreateTime(System.currentTimeMillis());
                                infoBean.setHash(txhash.getTxHash());
                                infoBean.setFromAddress(fromAddress);
                                infoBean.setToAddress(receiveUrl);
                                infoBean.setValue(money);
                                infoBean.setGasPrice(gasPriceParam);
                                DatabaseOperate.getInstance().insertOrUpdate(infoBean);
                                message.obj = getString(R.string.alsc_transfer_success);
                            } else {
                                message.obj = getString(R.string.alsc_transfer_failure);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        message.obj = Utils.getToastMessage(mContext, e.toString());
                    }
                } else if ("USDT-OMNI".equals(mSymbol)) {
                    if (mUtxoList == null) {
                        return;
                    }
                    if (mUtxoList.size() == 0) {
                        message.obj = getString(R.string.balance_insufficient);
                        return;
                    }
                    try {
                        if (!ColdWalletUtil.getBtcColdWallet().isValidateAddress(receiveUrl, WalletType.USDT_OMNI)) {
                            message.obj = getString(R.string.input_receive_url_failure);
                        } else {
                            long fee = progress + mBtcFee.getHourFee();
                            BtcTransaction txhash = ColdWalletUtil.getBtcColdWallet().sendTokenTransfer(fromAddress, receiveUrl, fee, privateKey, money,
                                    mUtxoList);

                            if (txhash != null && !TextUtils.isEmpty(txhash.getTxHash())) {
                                ColdInterface.createOMNI(fromAddress, receiveUrl, txhash.getTxHash(), money, MoneyUtil.moneydiv8(fees,"100000000"), mContext, "");
                                TradeInfoBean infoBean = new TradeInfoBean();
                                infoBean.setWalletType(mSymbol);
                                infoBean.setSendType(1);
                                infoBean.setLoginAccount(DataManager.getInstance().getColdUser().getAccount());
                                infoBean.setCreateTime(System.currentTimeMillis());
                                infoBean.setHash(txhash.getTxHash());
                                infoBean.setFromAddress(fromAddress);
                                infoBean.setToAddress(receiveUrl);
                                infoBean.setValue(money);
                                infoBean.setGasPrice(gasPriceParam);
                                DatabaseOperate.getInstance().insertOrUpdate(infoBean);
                                message.obj = getString(R.string.alsc_transfer_success);
                            } else {
                                message.obj = getString(R.string.alsc_transfer_failure);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        message.obj = Utils.getToastMessage(mContext, e.toString());
                    }
                } else {
                    message.obj = getString(R.string.alsc_transfer_failure);
                }
                handler.sendMessage(message);
            }
        }).start();
    }

    private void jumpScan() {
        // 先判断是否有权限。
        PermissionUtils.permission(PermissionConstants.CAMERA).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                // 有权限，直接do anything.
                CaptureActivity.startActivity(mContext);
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                //申请失败需要重新申请
                if (!permissionsDeniedForever.isEmpty()) {
                    showOpenAppSettingDialog();
                    return;
                }
            }
        }).request();

    }

    /**
     * 系统设置权限
     */
    private void showOpenAppSettingDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(ActivityUtils.getTopActivity())
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok, ((dialog, which) -> {
                    PermissionUtils.launchAppDetailsSettings();
                    dialog.dismiss();
                })).setOnCancelListener((dialog -> {
            dialog.dismiss();
        })).setCancelable(false)
                .create()
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADDRESS:
                if (resultCode == RESULT_OK && data != null) {
                    String receiveUrl = data.getStringExtra("address");
                    binding.etReceiveUrl.setText(receiveUrl);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CaptureEvent event) {
        if (event != null && !TextUtils.isEmpty(event.getUrl())) {
            binding.etReceiveUrl.setText(Utils.getScanUrl(event.getUrl()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CheckCPwdEvent event) {
        if (event != null && !TextUtils.isEmpty(event.getTag()) && Tag.equals(event.getTag())) {
            transfer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
