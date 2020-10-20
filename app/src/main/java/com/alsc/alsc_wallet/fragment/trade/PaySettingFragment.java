package com.alsc.alsc_wallet.fragment.trade;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.alsc.alsc_wallet.R;
import com.common.fragment.BaseFragment;

import java.util.ArrayList;

public class PaySettingFragment extends BaseFragment {

    private ArrayList<BaseFragment> mBaseFragment;
    private BaseFragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pay_setting;
    }

    @Override
    protected void onViewCreated(View view) {
        setText(R.id.tvTitle, R.string.wallet_pay_setting);
        setViewsOnClickListener(R.id.tvBankCard, R.id.tvAliPay, R.id.tvWePay, R.id.tvPayPal);
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new BankPaySettingFragment());
        mBaseFragment.add(new AliPaySettingFragment());
        mBaseFragment.add(new WePaySettingFragment());
        mBaseFragment.add(new PayPalSettingFragment());
        switchFragment(mBaseFragment.get(0));
    }


    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvBankCard:
                switchFragment(mBaseFragment.get(0));
                resetBtns((TextView) v, fv(R.id.tvAliPay), fv(R.id.tvWePay), fv(R.id.tvPayPal));
                break;
            case R.id.tvAliPay:
                switchFragment(mBaseFragment.get(1));
                resetBtns((TextView) v, fv(R.id.tvBankCard), fv(R.id.tvWePay), fv(R.id.tvPayPal));
                break;
            case R.id.tvWePay:
                switchFragment(mBaseFragment.get(2));
                resetBtns((TextView) v, fv(R.id.tvAliPay), fv(R.id.tvBankCard), fv(R.id.tvPayPal));
                break;
            case R.id.tvPayPal:
                switchFragment(mBaseFragment.get(3));
                resetBtns((TextView) v, fv(R.id.tvAliPay), fv(R.id.tvWePay), fv(R.id.tvBankCard));
                break;
        }
    }

    private void resetBtns(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        tv2.setTextColor(ContextCompat.getColor(mContext, R.color.color_b2_b2_b2));
        tv3.setTextColor(ContextCompat.getColor(mContext, R.color.color_b2_b2_b2));
        tv4.setTextColor(ContextCompat.getColor(mContext, R.color.color_b2_b2_b2));
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


}
