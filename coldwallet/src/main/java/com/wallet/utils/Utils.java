package com.wallet.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.cao.commons.base.PoliceApplication;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.bean.cold.ColdHqBean;
import com.cao.commons.bean.cold.TradeInfoBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cao.commons.util.Network;
import com.cao.commons.util.log.Log;
import com.google.gson.Gson;
import com.cold.wallet.R;
import com.wallet.wallet.ColdWalletUtil;
import com.wallet.wallet.bean.ColdWallet;
import com.wallet.wallet.bean.ContractType;
import com.wallet.wallet.bean.EthDealDetails;
import com.wallet.wallet.bean.JnWallet;
import com.wallet.wallet.bean.WalletType;
import com.wallet.wallet.eth.EthColdWallet;
import com.wallet.wallet.util.HttpUtil;
import com.youth.banner.util.LogUtils;

import org.bitcoinj.core.UTXO;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Utils {
    private static DecimalFormat fnum = new DecimalFormat("##0.00");
    private static DecimalFormat fnum8 = new DecimalFormat("##0.00000000");
    public static int MESSAGE_WHAT_MONEY = 999;

    /**
     * 复制
     *
     * @param context context
     * @param content 内容
     */
    public static void copyData(Context context, String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    /**
     * 获取用户等级
     */
    public static String getUserLevel(Context context, int level) {
        String levelStr = "";
        if (level == 0) {
            levelStr = context.getString(R.string.user_level0);
        } else if (level == 1) {
            levelStr = context.getString(R.string.user_level1);
        } else if (level == 2) {
            levelStr = context.getString(R.string.user_level2);
        } else if (level == 3) {
            levelStr = context.getString(R.string.user_level3);
        } else if (level == 4) {
            levelStr = context.getString(R.string.user_level4);
        } else if (level == 5) {
            levelStr = context.getString(R.string.user_level5);
        } else if (level == 6) {
            levelStr = context.getString(R.string.user_level6);
        } else if (level == 7) {
            levelStr = context.getString(R.string.user_level7);
        } else if (level == 8) {
            levelStr = context.getString(R.string.user_level8);
        }
        return levelStr;
    }

    public static void getTotalMoney(ColdHqBean hpBean, Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String money = "0.00";
//                if (hpBean != null) 2{
//                    BigDecimal total = new BigDecimal("0");
//
//                    String walletContentMD = DataManager.getInstance().getColdUser().getWalletContentMD();
//                    Gson mGson = new Gson();
//                    ColdWallet mColdWallet = mGson.fromJson(walletContentMD, ColdWallet.class);
//                    for (JnWallet entity : mColdWallet.getJnWallets()) {
//                        String valueStr = Utils.getEqualMoney(hpBean, getWalletType(entity.getWalletType()));
//                        BigDecimal value = new BigDecimal(valueStr);
//                        BigDecimal mulValue = getMoneyDecimal(entity.getWalletType(), entity.getAddress());
//                        total = total.add(value.multiply(mulValue));
//                    }
//
//                    ArrayList<WalletDataBean> list = DatabaseOperate.getInstance().getWalletInfos();
//                    for (WalletDataBean bean : list) {
//                        String valueStr = Utils.getEqualMoney(hpBean, bean.getWalletType());
//                        BigDecimal value = new BigDecimal(valueStr);
//                        BigDecimal mulValue = getMoneyDecimal(bean.getWalletType(), bean.getAddress());
//                        total = total.add(value.multiply(mulValue));
//                    }
//                    money = fnum.format(total);
//                }

                Message message = handler.obtainMessage();
                message.what = MESSAGE_WHAT_MONEY;
                message.obj = money;
                handler.sendMessage(message);
            }
        }).start();
    }

//    public static void getMoney(String mSymbol, String address, Handler handler) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String money = fnum8.format(getMoneyDecimal(mSymbol, address));
//                Message message = handler.obtainMessage();
//                message.what = MESSAGE_WHAT_MONEY;
//                message.obj = money;
//                handler.sendMessage(message);
//            }
//        }).start();
//    }

    public static void getMoney(String mSymbol, ArrayList<WalletDataBean> list, Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (WalletDataBean bean : list) {
                    String money = fnum8.format(getMoneyDecimal(mSymbol, bean.getAddress()));
                    bean.setCurrentPrice(money);
                }
                Message message = handler.obtainMessage();
                message.what = MESSAGE_WHAT_MONEY;
                message.obj = list;
                handler.sendMessage(message);
            }
        }).start();
    }

    private static BigDecimal getMoneyDecimal(WalletType mSymbol, String address) throws Exception {
//        if (WalletType.A13 == mSymbol) {
//            BigDecimal value = getTokenBalanceFromServer("A13", address);
//            return value == null ? ColdWalletUtil.getEthColdWallet().getTokenBalance(address, ContractType.A13.getUrl()) : value;
//        } else
        if (WalletType.ETH == mSymbol) {
            BigDecimal value = getTokenBalanceFromServer("ETH", address);
            Log.e("aaaaaaaaa", "value: " + value);
            return value == null ? ColdWalletUtil.getEthColdWallet().getBalance(address) : value;
        } else if (WalletType.BTC == mSymbol) {
            List<UTXO> list = ColdWalletUtil.getBtcColdWallet().getUnspentTransactionOutput1(address);
            return ColdWalletUtil.getBtcColdWallet().getBalance(list);
        } else if (WalletType.USDT_OMNI == mSymbol) {
            return ColdWalletUtil.getBtcColdWallet().getTokenBalance(address);
        } else if (WalletType.USDT_ERC20 == mSymbol) {
            BigDecimal value = getTokenBalanceFromServer("ERCUSDT", address);
            return value == null ? ColdWalletUtil.getEthColdWallet().getTokenBalance(address, ContractType.USDT_ERC20.getUrl()) : value;
        }

        return new BigDecimal("0");
    }

    /**
     * @param type
     * @param address
     * @throws Exception
     */
    private static BigDecimal getTokenBalanceFromServer(String type, String address) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", type);
            map.put("address", address);
            Log.e("aaaaaaaa", type + ": " + address);
            String response = HttpUtil.getInstance().post("http://47.242.161.117:8080/getTokenBlence", map);
            Log.e("aaaaaaaa", "response: " + response);
            JSONObject object = new JSONObject(response);
            if (object.optString("rspCode").equals("success")) {
                return new BigDecimal(object.optDouble("data"));
            }
        } catch (Exception e) {

        }
        return null;
    }

    private static BigDecimal getMoneyDecimal(String mSymbol, String address) {
        try {
            if ("A13".equals(mSymbol)) {
                return ColdWalletUtil.getEthColdWallet().getTokenBalance(address, ContractType.A13.getUrl());
            } else if ("ETH".equals(mSymbol)) {
                return ColdWalletUtil.getEthColdWallet().getBalance(address);
            } else if ("BTC".equals(mSymbol)) {
                List<UTXO> list = ColdWalletUtil.getBtcColdWallet().getUnspentTransactionOutput1(address);
                return ColdWalletUtil.getBtcColdWallet().getBalance(list);
            } else if ("USDT-OMNI".equals(mSymbol)) {
                return ColdWalletUtil.getBtcColdWallet().getTokenBalance(address);
            } else if ("USDT-ERC20".equals(mSymbol)) {
                return ColdWalletUtil.getEthColdWallet().getTokenBalance(address, ContractType.USDT_ERC20.getUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigDecimal("0");
    }

    public static String getEqualMoney(ColdHqBean bean, String mSymbol) {
        if ("A13".equals(mSymbol)) {
            return bean.getA13();
        } else if ("ETH".equals(mSymbol)) {
            return bean.getETH();
        } else if ("BTC".equals(mSymbol)) {
            return bean.getBTC();
        } else if ("USDT-OMNI".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
            return bean.getUSDT();
        }
        return "0.00";
    }


    public static String getTitleSum(String mSymbol) {
        if ("A13".equals(mSymbol)) {
            return "";
        } else if ("ETH".equals(mSymbol)) {
            return "Ethereum";
        } else if ("BTC".equals(mSymbol)) {
            return "Bitcoin";
        } else if ("LTC".equals(mSymbol)) {
            return "Litecoin";
        } else if ("XRP".equals(mSymbol)) {
            return "Ripple";
        } else if ("USDT".equals(mSymbol) || "USDT-OMNI".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
            return "TetherUS";
        }
        return "";
    }


    public static int getImageResource(String mSymbol) {
        if ("A13".equals(mSymbol)) {
            return R.mipmap.alsc_desk_logo;
        } else if ("ETH".equals(mSymbol)) {
            return R.mipmap.icon_eth;
        } else if ("BTC".equals(mSymbol)) {
            return R.mipmap.icon_btc;
        } else if ("USDT".equals(mSymbol) || "USDT-OMNI".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
            return R.mipmap.icon_usdt;
        } else if ("LTC".equals(mSymbol)) {
            return R.mipmap.icon_ltc;
        } else if ("XRP".equals(mSymbol)) {
            return R.mipmap.icon_xrp;
        }
        return 0;
    }

    public static int getImageResourceCold(String mSymbol) {
        if ("A13".equals(mSymbol)) {
            return R.mipmap.alsc_desk_logo;
        } else if ("ETH".equals(mSymbol)) {
            return R.mipmap.icon_square_eth;
        } else if ("BTC".equals(mSymbol)) {
            return R.mipmap.icon_square_btc;
        } else if ("USDT".equals(mSymbol) || "USDT-OMNI".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
            return R.mipmap.icon_square_usdt;
        }
        return 0;
    }

    public static int getImageResource1(String mSymbol) {
        if ("A13".equals(mSymbol)) {
            return R.mipmap.icon_alsc_1;
        } else if ("ETH".equals(mSymbol)) {
            return R.mipmap.icon_eth_1;
        } else if ("BTC".equals(mSymbol)) {
            return R.mipmap.icon_btc_1;
        } else if ("USDT".equals(mSymbol) || "USDT-OMNI".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
            return R.mipmap.icon_usdt_1;
        }
        return 0;
    }

    public static int getImageResource3(String mSymbol) {
        if ("A13".equals(mSymbol)) {
            return R.mipmap.icon_alsc_asset;
        } else if ("ETH".equals(mSymbol)) {
            return R.mipmap.icon_eth_3;
        } else if ("BTC".equals(mSymbol)) {
            return R.mipmap.icon_btc_3;
        } else if ("USDT".equals(mSymbol) || "USDT-OMNI".equals(mSymbol) || "USDT-ERC20".equals(mSymbol)) {
            return R.mipmap.icon_usdt_3;
        } else if ("LTC".equals(mSymbol)) {
            return R.mipmap.icon_ltc_3;
        } else if ("XRP".equals(mSymbol)) {
            return R.mipmap.icon_xrp_3;
        }
        return 0;
    }

    public static JnWallet getWallet(ColdWallet mColdWallet, String mSymbol) {
        for (JnWallet wallet : mColdWallet.getJnWallets()) {
            if (wallet.getWalletType() == getWalletType(mSymbol)) {
                return wallet;
            }
        }
        return null;
    }

    public static String getWalletName(ColdWallet mColdWallet, String mSymbol) {
        for (JnWallet wallet : mColdWallet.getJnWallets()) {
            if (wallet.getWalletType() == getWalletType(mSymbol)) {
                return wallet.getName();
            }
        }
        return "";
    }

    public static String getAddress(String mSymbol) {
        UserBean userBean = DataManager.getInstance().getColdUser();
        Gson mGson = new Gson();
        ColdWallet mColdWallet = mGson.fromJson(userBean.getWalletContentMD(), ColdWallet.class);
        for (JnWallet wallet : mColdWallet.getJnWallets()) {
            if (wallet.getWalletType() == getWalletType(mSymbol)) {
                return wallet.getAddress();
            }
        }
        return "";
    }

    public static String getAddress(ColdWallet mColdWallet, String mSymbol) {
        for (JnWallet wallet : mColdWallet.getJnWallets()) {
            if (wallet.getWalletType() == getWalletType(mSymbol)) {
                return wallet.getAddress();
            }
        }
        return "";
    }


    public static WalletType getWalletType(String mSymbol) {
        if ("ETH".equals(mSymbol)) {
            return WalletType.ETH;
        } else if ("USDT-OMNI".equals(mSymbol)) {
            return WalletType.USDT_OMNI;
        } else if ("USDT-ERC20".equals(mSymbol)) {
            return WalletType.USDT_ERC20;
        }
//        else if ("A13".equals(mSymbol)) {
//            return WalletType.A13;
//        }
        return WalletType.BTC;
    }

    public static String getWalletType(WalletType mSymbol) {
        if (WalletType.ETH == mSymbol) {
            return "ETH";
        } else if (WalletType.USDT_OMNI == mSymbol) {
            return "USDT-OMNI";
        } else if (WalletType.USDT_ERC20 == mSymbol) {
            return "USDT-ERC20";
        }
//        else if (WalletType.A13 == mSymbol) {
//            return "A13";
//        }
        return "BTC";
    }


    public static ArrayList<WalletDataBean> getAllWalletInfos(String mSymbol) {
        return DatabaseOperate.getInstance().getWalletInfos(mSymbol);
    }

    public static String getMnemonicCode(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (String str : list) {
                builder.append(str);
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    public static String getToastMessage(Context context, String e) {
        String msg;
        if (e.contains("Invalid password provided")) {
            msg = context.getString(R.string.invalid_password_provided);
        } else if (e.contains("gas * price + value") || e.contains("余额不足")) {
            msg = context.getString(R.string.balance_insufficient);
        } else if (e.contains("接口请求失败")) {
            msg = context.getString(R.string.http_service_error);
        } else {
            msg = context.getString(R.string.input_error);
        }
        return msg;
    }

    private static void saveTradeMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ColdWalletUtil.getEthColdWallet().setOnListeningTransaction(new EthColdWallet.ListeningTransaction() {
                        @Override
                        public void onError(Throwable throwable) {

                        }

                        @Override
                        public void onData(EthDealDetails ethDealDetails) {
                            if (ethDealDetails != null) {
                                TradeInfoBean infoBean = DatabaseOperate.getInstance().getTradeInfo(ethDealDetails.getHash());
                                if (infoBean == null) {
                                    infoBean = new TradeInfoBean();
                                    infoBean.setLoginAccount(DataManager.getInstance().getColdUser().getAccount());
                                    infoBean.setCreateTime(System.currentTimeMillis());
                                    infoBean.setHash(ethDealDetails.getHash());
                                }
                                infoBean.setFromAddress(ethDealDetails.getFrom());
                                infoBean.setToAddress(ethDealDetails.getTo());
                                infoBean.setValue(String.valueOf(ethDealDetails.getValue()));
                                infoBean.setGasPrice(String.valueOf(ethDealDetails.getGasPrice()));
                                infoBean.setBlockHash(ethDealDetails.getBlockHash());
                                infoBean.setNonce(ethDealDetails.getNonce());
                                infoBean.setResult(String.valueOf(ethDealDetails.isResult()));
                                infoBean.setBlockNumber(ethDealDetails.getBlockNumber());
                                infoBean.setGas(String.valueOf(ethDealDetails.getGas()));
                                infoBean.setTransIndex(ethDealDetails.getTransIndex());
                                DatabaseOperate.getInstance().insertOrUpdate(infoBean);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * ETH转账金额输入框限制最多可以输入18位
     * BTC转账金额输入框限制最多输入8位
     * USDT-ERC20转账金额输入框限制最多输入6位
     * USDT-OMNI转账金额输入框限制最多输入8位
     * A13转账金额输入框限制最多可以输入18位
     *
     * @param mSymbol
     * @return
     */
    public static int getTransferMaxLength(String mSymbol) {
        if ("A13".equals(mSymbol)) {
            return 18;
        } else if ("ETH".equals(mSymbol)) {
            return 18;
        } else if ("BTC".equals(mSymbol)) {
            return 8;
        } else if ("USDT-OMNI".equals(mSymbol)) {
            return 8;
        } else if ("USDT-ERC20".equals(mSymbol)) {
            return 6;
        } else if ("USDT".equals(mSymbol)) {
            return 8;
        }
        return 50;
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void refreshMoney(Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean netAvailable = Network.isConnected(PoliceApplication.getInstance());
                if (netAvailable) {
                    try {
                        UserBean userBean = DataManager.getInstance().getColdUser();
                        String walletContentMD = userBean.getWalletContentMD();
                        Gson mGson = new Gson();
                        ColdWallet mColdWallet = mGson.fromJson(walletContentMD, ColdWallet.class);
                        for (JnWallet entity : mColdWallet.getJnWallets()) {
                            try {
                                BigDecimal mulValue = getMoneyDecimal(entity.getWalletType(), entity.getAddress());
                                entity.setMoney(fnum8.format(mulValue));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(entity.getMoney())) {
                                entity.setMoney("0.00000000");
                            }
                            if (PoliceApplication.getColdHqBean() != null) {
                                String valueStr = Utils.getEqualMoney(PoliceApplication.getColdHqBean(), getWalletType(entity.getWalletType()));
                                entity.setPrice(MoneyUtil.moneyMul(valueStr, entity.getMoney()));
                            }
                        }
                        String walletContent = mGson.toJson(mColdWallet);
                        userBean.setWalletContentMD(walletContent);

                        DataManager.getInstance().saveColdUser(userBean);

                        ArrayList<WalletDataBean> list = DatabaseOperate.getInstance().getWalletInfos();
                        for (WalletDataBean bean : list) {
                            try {
                                BigDecimal mulValue = getMoneyDecimal(bean.getWalletType(), bean.getAddress());
                                bean.setMoney(fnum8.format(mulValue));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(bean.getMoney())) {
                                bean.setMoney("0.00000000");
                            }
                            if (PoliceApplication.getColdHqBean() != null) {
                                String valueStr = Utils.getEqualMoney(PoliceApplication.getColdHqBean(), bean.getWalletType());
                                bean.setPrice(MoneyUtil.moneyMul(valueStr, bean.getMoney()));
                            }
                            if (Network.isConnected(PoliceApplication.getInstance())) {
                                DatabaseOperate.getInstance().update(bean);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.e(e.getMessage());
                    }
                }

                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    public static String getTotalMoney() {
        String money = "0.00";
        UserBean userBean = DataManager.getInstance().getColdUser();
        String walletContentMD = userBean.getWalletContentMD();
        if (TextUtils.isEmpty(walletContentMD)) {
            return money;
        }
        Gson mGson = new Gson();
        ColdWallet mColdWallet = mGson.fromJson(walletContentMD, ColdWallet.class);
        for (JnWallet entity : mColdWallet.getJnWallets()) {
            money = MoneyUtil.moneyAdd(money, entity.getPrice());
        }

        ArrayList<WalletDataBean> list = DatabaseOperate.getInstance().getWalletInfos();
        for (WalletDataBean bean : list) {
            money = MoneyUtil.moneyAdd(money, bean.getPrice());
        }
        return money;
    }

    public static void changeAppLanguage(Resources resources, Locale language) {
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.locale = language;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static void changeAppLanguage(Context context, Locale language) {
        changeAppLanguage(context.getResources(), language);
    }

    public static void resetLanguage(Context context) {
        if (DataManager.getInstance().getLanguage() == 1) {
            changeAppLanguage(context.getResources(), Locale.US);
        } else if (DataManager.getInstance().getLanguage() == 2) {
            changeAppLanguage(context.getResources(), Locale.JAPAN);
        } else if (DataManager.getInstance().getLanguage() == 3) {
            changeAppLanguage(context.getResources(), Locale.KOREA);
        } else if (DataManager.getInstance().getLanguage() == 4) {
            changeAppLanguage(context.getResources(), new Locale("vi", "VN"));
        } else {
            changeAppLanguage(context.getResources(), Locale.SIMPLIFIED_CHINESE);
        }
    }

//    public static Context changeAppLanguage(Context context, String language) {
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////            //7.0及以上的方法
////            return createConfiguration(context, language);
////        } else {
//        updateConfiguration(context, language);
//        return context;
////        }
//    }


//    /**
//     * 7.0及以上的修改app语言的方法
//     *
//     * @param context  context
//     * @param language language
//     * @return context
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private static Context createConfiguration(Context context, String language) {
//        Resources resources = context.getResources();
//        Locale locale = new Locale(language);
//        Configuration configuration = resources.getConfiguration();
//        configuration.setLocale(locale);
//        LocaleList localeList = new LocaleList(locale);
//        LocaleList.setDefault(localeList);
//        configuration.setLocales(localeList);
//        return context.createConfigurationContext(configuration);
//    }
//
//    /**
//     * 7.0以下的修改app语言的方法
//     *
//     * @param context  context
//     * @param language language
//     */
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private static void updateConfiguration(Context context, String language) {
//        Resources resources = context.getResources();
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//        Configuration configuration = resources.getConfiguration();
//        configuration.setLocale(locale);
//        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//        resources.updateConfiguration(configuration, displayMetrics);
//    }

    /**
     * 获取扫描后的url，特殊处理im
     *
     * @param url
     * @return
     */
    public static String getScanUrl(String url) {
        if (url.startsWith("ethereum:")) {
            String result = url.replace("ethereum:", "");
            if (result.contains("?")) {
                String[] arr = result.split("\\?");
                return arr[0];
            }
        } else if (url.startsWith("bitcoin:")) {
            String result = url.replace("bitcoin:", "");
            if (result.contains("?")) {
                String[] arr = result.split("\\?");
                return arr[0];
            }
        }
        return url;
    }


}
