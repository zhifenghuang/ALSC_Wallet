package com.alsc.alsc_wallet.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.message.FastMsgFragment;
import com.alsc.alsc_wallet.fragment.message.FollowedFragment;
import com.alsc.alsc_wallet.fragment.message.HotArticleFragment;
import com.alsc.alsc_wallet.fragment.message.QuanziFragment;
import com.alsc.alsc_wallet.fragment.message.UserInfoFragment;

import java.util.ArrayList;

public class MessageFragment extends BaseFragment {

    private ArrayList<BaseFragment> mBaseFragment;
    private Fragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void onViewCreated(View view) {
        setViewsOnClickListener(R.id.ivMyAvatar, R.id.ivSearch, R.id.llHotMsg, R.id.llFastMsg, R.id.llQuanMsg);
        initFragments();
        switchFragment(mBaseFragment.get(0));
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new HotArticleFragment());
        mBaseFragment.add(new FastMsgFragment());
        mBaseFragment.add(new QuanziFragment());
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivMyAvatar:
                gotoPager(UserInfoFragment.class);
                break;
            case R.id.ivSearch:
                gotoPager(FollowedFragment.class);
                break;
            case R.id.llHotMsg:
                switchFragment(mBaseFragment.get(0));
                resetBtns((LinearLayout) v, fv(R.id.llFastMsg), fv(R.id.llQuanMsg));
                break;
            case R.id.llFastMsg:
                switchFragment(mBaseFragment.get(1));
                resetBtns((LinearLayout) v, fv(R.id.llHotMsg), fv(R.id.llQuanMsg));
                break;
            case R.id.llQuanMsg:
                switchFragment(mBaseFragment.get(2));
                resetBtns((LinearLayout) v, fv(R.id.llHotMsg), fv(R.id.llFastMsg));
                break;
        }
    }

    private void resetBtns(LinearLayout ll1, LinearLayout ll2, LinearLayout ll3) {
        ((TextView) ll1.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        ll1.getChildAt(1).setVisibility(View.VISIBLE);
        ((TextView) ll2.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        ll2.getChildAt(1).setVisibility(View.INVISIBLE);
        ((TextView) ll3.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        ll3.getChildAt(1).setVisibility(View.INVISIBLE);
    }

    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment to) {
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

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
