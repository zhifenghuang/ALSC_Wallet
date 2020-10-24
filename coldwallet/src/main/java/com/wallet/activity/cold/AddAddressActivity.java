package com.wallet.activity.cold;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.cao.commons.base.BaseActivity;
import com.cao.commons.bean.chat.CaptureEvent;
import com.cao.commons.bean.cold.ColdAddressBean;
import com.cao.commons.db.DatabaseOperate;
import com.cao.commons.manager.DataManager;
import com.cold.wallet.R;
import com.cold.wallet.databinding.ActivityAddAddressBinding;
import com.wallet.activity.my.CaptureActivity;
import com.wallet.widget.dialog.AddressDialog;
import com.wallet.widget.dialog.OnItemClickListener;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAddAddressBinding binding;
    private ColdAddressBean mAddressBean;
    private AddressDialog mAddressDialog;
    private String mSymbol;

    public static void startActivity(Activity context, int requestCode) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);
        binding.setClickListener(this);
        EventBus.getDefault().register(this);
        mAddressBean = new ColdAddressBean();
        binding.etUserAccount.addTextChangedListener(textWatcher);
        binding.etUserName.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(binding.etUserAccount.getText()) && !TextUtils.isEmpty(binding.etUserName.getText())) {
                binding.btnNext.setBackgroundResource(R.drawable.corner_07bb99_4);
            } else {
                binding.btnNext.setBackgroundResource(R.drawable.corner_d2d2d2_4);
            }
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_scan) {
            jumpScan();
        } else if (id == R.id.btn_next) {
            next();
        } else if (id == R.id.tv_change_title) {
            showDialog();
        } else if (id == R.id.fl_back) {
            finish();
        }
    }

    private void next() {
        String name = binding.etUserName.getText().toString().trim();
        String account = binding.etUserAccount.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.toast(getString(R.string.address_name));
            return;
        }

        if (TextUtils.isEmpty(account)) {
            ToastUtil.toast(getString(R.string.address_account));
            return;
        }
        if (TextUtils.isEmpty(mSymbol)) {
            ToastUtil.toast(getString(R.string.choose_wallet_type));
            return;
        }
        mAddressBean.setLoginAccount(DataManager.getInstance().getUser().getAccount());
        mAddressBean.setName(name);
        mAddressBean.setRemarks("");
        mAddressBean.setPath(account);
        mAddressBean.setWalletType(mSymbol);
        mAddressBean.setCreateTime(System.currentTimeMillis());
        DatabaseOperate.getInstance().insertWithNoPrimaryKey(mAddressBean);
        finish();
    }

    private void showDialog() {
        if (mAddressDialog == null) {
            mAddressDialog = new AddressDialog(mContext);
        }
        mAddressDialog.show();
        mAddressDialog.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(String content) {
                mSymbol = content;
                binding.tvChangeTitle.setText(content);
                binding.ivImage.setImageResource(Utils.getImageResource3(mSymbol));
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CaptureEvent event) {
        if (event != null && !TextUtils.isEmpty(event.getUrl())) {
            binding.etUserAccount.setText(Utils.getScanUrl(event.getUrl()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
