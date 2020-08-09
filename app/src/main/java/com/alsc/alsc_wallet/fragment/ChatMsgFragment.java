package com.alsc.alsc_wallet.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.chat.ChatListFragment;
import com.alsc.alsc_wallet.fragment.chat.ContactFragment;

import java.util.ArrayList;

public class ChatMsgFragment extends BaseFragment {

    private ArrayList<BaseFragment> mBaseFragment;
    private Fragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_msg;
    }

    @Override
    protected void onViewCreated(View view) {
        initFragments();
        switchFragment(mBaseFragment.get(0));
        setViewsOnClickListener(R.id.llChatMsg, R.id.llContact);
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new ChatListFragment());
        mBaseFragment.add(new ContactFragment());
    }

    private void resetBtns(LinearLayout ll1, LinearLayout ll2) {
        ((TextView) ll1.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_07_bb_99));
        ll1.getChildAt(1).setVisibility(View.VISIBLE);
        ((TextView) ll2.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.color_00_00_00));
        ll2.getChildAt(1).setVisibility(View.INVISIBLE);
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
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.llChatMsg:
                switchFragment(mBaseFragment.get(0));
                resetBtns((LinearLayout) v, fv(R.id.llContact));
                break;
            case R.id.llContact:
                switchFragment(mBaseFragment.get(1));
                resetBtns((LinearLayout) v, fv(R.id.llChatMsg));
                break;
        }
    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }
}
