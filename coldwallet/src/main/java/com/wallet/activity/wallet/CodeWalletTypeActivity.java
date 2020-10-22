package com.wallet.activity.wallet;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.VersionBean;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cold.wallet.R;
import com.wallet.activity.MainColdActivity;
import com.cold.wallet.databinding.ActivityCodeWalletTypeBinding;
import com.wallet.event.LoginEvent;
import com.wallet.utils.DensityUtil;
import com.wallet.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

/**
 * 创建钱包类型
 */
public class CodeWalletTypeActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCodeWalletTypeBinding binding;
    private int switchType = -1;
    public PopupWindow popupWindow;

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_type);
        binding.setClickListener(this);
        binding.llBottom.setVisibility(View.GONE);
        binding.llRestore.setVisibility(View.GONE);
        resetLanguage();
        EventBus.getDefault().register(this);

        checkVersion();
    }

    private void checkVersion() {
//        HttpInterface.checkVersion(mContext, Tag, false, new HttpInfoRequest<VersionBean>() {
//            @Override
//            public void onSuccess(VersionBean model) {
//                if (mIsActivityFinish) {
//                    return;
//                }
//                if (model != null) {
//                    if (model.getType() > 0) {
//                        AppUpgradeDialog dialog = new AppUpgradeDialog(mContext, model);
//                        dialog.show();
//                    }
//                }
//            }
//
//            @Override
//            public void onError(int eCode) {
//
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_code_wallet) {
            switchBackground(0);
        } else if (id == R.id.ll_online_wallet) {
            switchBackground(1);
        } else if (id == R.id.btn_next) {
            jump2NextActivity();
        } else if (id == R.id.ll_restore) {
            RestoreWalletActivity.startActivity(mContext);
        } else if (id == R.id.ll_language) {
//            binding.ivLanguage.setImageResource(R.mipmap.icon_language_down);
            showPopupWindow();
        } else if (id == R.id.ll_bottom) {
//            LoginActivity.startActivity(mContext);
        }
    }

    private void jump2NextActivity() {
        if (switchType == 0) {
            ArrayList<UserBean> list = DatabaseOperate.getInstance().getColdUserInfos();
            if (list != null && list.size() > 0) {
                UserBean userBean = list.get(list.size() - 1);
                DataManager.getInstance().saveUser(userBean);
                MainColdActivity.startActivity(mContext);
                finish();
            } else {
                com.wallet.activity.wallet.CodeWalletMakeActivity.startActivity(mContext);
            }
        } else if (switchType == 1) {
//            OnlineWalletMakeActivity.startActivity(mContext);
        }
    }

    /**
     * 点击切换不同状态
     *
     * @param type 0  跨链钱包蓝色状态
     *             1  在线钱包黄色状态
     */
    private void switchBackground(int type) {
        binding.btnNext.setTextColor(getResources().getColor(R.color.white));
        switchType = type;
        if (type == 0) {
            binding.tvWalletType.setTextColor(getResources().getColor(R.color.blue));
            binding.tv1.setTextColor(getResources().getColor(R.color.white));
            binding.tv2.setTextColor(getResources().getColor(R.color.white));
            binding.tv3.setTextColor(getResources().getColor(R.color.text_color));
            binding.tv4.setTextColor(getResources().getColor(R.color.text_color));
            binding.llCodeWallet.setBackgroundResource(R.drawable.corner_blue_10);
            binding.llOnlineWallet.setBackgroundResource(R.drawable.corner_2e374c_10);
            binding.btnNext.setBackgroundResource(R.drawable.corner_blue_5);
            binding.ivCode.setImageResource(R.mipmap.icon_code_wallet_white);
            binding.ivOnline.setImageResource(R.mipmap.icon_code_wallet_blue);
            binding.llBottom.setVisibility(View.GONE);
            ArrayList<UserBean> list = DatabaseOperate.getInstance().getColdUserInfos();
            if (list != null && list.size() > 0) {
                binding.llRestore.setVisibility(View.GONE);
            } else {
                binding.llRestore.setVisibility(View.VISIBLE);
            }
        } else if (type == 1) {
            binding.tv1.setTextColor(getResources().getColor(R.color.text_color));
            binding.tv2.setTextColor(getResources().getColor(R.color.text_color));
            binding.tv3.setTextColor(getResources().getColor(R.color.white));
            binding.tv4.setTextColor(getResources().getColor(R.color.white));
            binding.tvWalletType.setTextColor(getResources().getColor(R.color.button_color_else));
            binding.llCodeWallet.setBackgroundResource(R.drawable.corner_2e374c_10);
            binding.llOnlineWallet.setBackgroundResource(R.drawable.corner_yellow_10);
            binding.btnNext.setBackgroundResource(R.drawable.corner_yellow_5);
            binding.ivCode.setImageResource(R.mipmap.icon_code_wallet_blue);
            binding.ivOnline.setImageResource(R.mipmap.icon_code_wallet_white);
            binding.llBottom.setVisibility(View.VISIBLE);
            binding.llRestore.setVisibility(View.GONE);
        }
    }

    private void resetLanguage() {
        int language = DataManager.getInstance().getLanguage();
        if (language == 0) {
            binding.tvLanguage.setText(getString(R.string.chat_language_0));
        } else if (language == 1) {
            binding.tvLanguage.setText(getString(R.string.chat_language_2));
        } else if (language == 2) {
            binding.tvLanguage.setText(getString(R.string.chat_language_3));
        } else if (language == 3) {
            binding.tvLanguage.setText(getString(R.string.chat_language_4));
        } else {
            binding.tvLanguage.setText(getString(R.string.chat_language_5));
        }
        binding.tvWalletType.setText(getString(R.string.wt_type_make));
        binding.tvWalletDes.setText(getString(R.string.wt_type_change));
        binding.tv1.setText(getString(R.string.wt_type_chain));
        binding.tv2.setText(getString(R.string.wt_type_chain_describe));
        binding.tv3.setText(getString(R.string.wt_type_online));
        binding.tv4.setText(getString(R.string.wt_type_online_describe));
        binding.btnNext.setText(getString(R.string.wt_type_next));
        binding.tv5.setText(getString(R.string.wt_type_has_alsc));
        binding.btnLogin.setText(getString(R.string.wt_type_login));
        binding.tv6.setText(getString(R.string.wt_btn_sure_add_cold));
        binding.tv7.setText(getString(R.string.wt_cold_wallet_restore));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent event) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 弹出发起群聊按钮
     */
    private void showPopupWindow() {
        // 构建一个popupwindow的布局
        if (popupWindow == null) {
            View popupView = getLayoutInflater().inflate(R.layout.popup_language, null);
            popupView.findViewById(R.id.ll_item1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    DataManager.getInstance().saveLanguage(1);
                    Utils.changeAppLanguage(com.wallet.activity.wallet.CodeWalletTypeActivity.this, Locale.US);
                    resetLanguage();
                }
            });
            popupView.findViewById(R.id.ll_item2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    DataManager.getInstance().saveLanguage(2);
                    Utils.changeAppLanguage(com.wallet.activity.wallet.CodeWalletTypeActivity.this, Locale.JAPAN);
                    resetLanguage();
                }
            });
            popupView.findViewById(R.id.ll_item3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    DataManager.getInstance().saveLanguage(3);
                    Utils.changeAppLanguage(com.wallet.activity.wallet.CodeWalletTypeActivity.this, Locale.KOREA);
                    resetLanguage();
                }
            });
            popupView.findViewById(R.id.ll_item4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    DataManager.getInstance().saveLanguage(4);
                    Utils.changeAppLanguage(com.wallet.activity.wallet.CodeWalletTypeActivity.this, new Locale("vi", "VN"));
                    resetLanguage();
                }
            });
            popupView.findViewById(R.id.ll_item5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    DataManager.getInstance().saveLanguage(0);
                    Utils.changeAppLanguage(com.wallet.activity.wallet.CodeWalletTypeActivity.this, Locale.SIMPLIFIED_CHINESE);
                    resetLanguage();
                }
            });

            // 创建PopupWindow对象，指定宽度和高度
            popupWindow = new PopupWindow(popupView, DensityUtil.dip2px(120), ViewGroup.LayoutParams.WRAP_CONTENT);
            // 设置动画
//            popupWindow.setAnimationStyle(R.style.popup_window_fade);
            // 设置背景颜色
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.color.transparent));
            // 设置可以获取焦点
            popupWindow.setFocusable(true);
            popupWindow.setTouchable(true);
            // 设置可以触摸弹出框以外的区域
            popupWindow.setOutsideTouchable(true);
            // 更新popupwindow的状态
            popupWindow.update();

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                    binding.ivLanguage.setImageResource(R.mipmap.icon_language_up);
                }
            });
        }
        backgroundAlpha(0.7f);
        popupWindow.showAsDropDown(binding.tvLanguage);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
