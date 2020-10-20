package com.alsc.alsc_wallet.fragment;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.message.FollowedFragment;
import com.alsc.alsc_wallet.fragment.message.HotArticleFragment;
import com.alsc.alsc_wallet.fragment.message.UserInfoFragment;
import com.common.fragment.BaseFragment;

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
        setViewsOnClickListener(R.id.ivMyAvatar, R.id.ivSearch);
        initFragments();
        switchFragment(mBaseFragment.get(0));
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new HotArticleFragment());
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
        }
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
