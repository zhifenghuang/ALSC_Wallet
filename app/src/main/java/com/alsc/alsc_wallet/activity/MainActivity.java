package com.alsc.alsc_wallet.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.fragment.BaseFragment;
import com.alsc.alsc_wallet.fragment.ChatMsgFragment;
import com.alsc.alsc_wallet.fragment.HangQingFragment;
import com.alsc.alsc_wallet.fragment.MessageFragment;
import com.alsc.alsc_wallet.fragment.TradeFragment;
import com.alsc.alsc_wallet.fragment.ZiChanFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BaseFragment> mBaseFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initViews();
    }

    private void initFragments() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new ChatMsgFragment());
        mBaseFragment.add(new MessageFragment());
        mBaseFragment.add(new TradeFragment());
        mBaseFragment.add(new HangQingFragment());
        mBaseFragment.add(new ZiChanFragment());
    }

    private void initViews() {
        switchFragment(mBaseFragment.get(0));
        resetBottomBar(0);
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        View itemView;
        for (int i = 0; i < count; ++i) {
            itemView = llBottom.getChildAt(i);
            itemView.setTag(i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (int) view.getTag();
                    switchFragment(mBaseFragment.get(tag));
                    resetBottomBar(tag);
                }
            });
        }
    }

    private void resetBottomBar(int currentPos) {
        LinearLayout llBottom = findViewById(R.id.llBottom);
        int count = llBottom.getChildCount();
        ViewGroup itemView;
        for (int i = 0; i < count; ++i) {
            itemView = (ViewGroup) llBottom.getChildAt(i);
            (((ImageView) itemView.getChildAt(0))).setImageResource(getResIdByIndex(i, currentPos == i));
            (((TextView) itemView.getChildAt(1))).
                    setTextColor(ContextCompat.getColor(this, currentPos == i ? R.color.color_07_bb_99 : R.color.color_a0_ac_c0));
        }
    }

    private int getResIdByIndex(int index, boolean isCheck) {
        int id = 0;
        switch (index) {
            case 0:
                id = isCheck ? R.drawable.wallet_chat_select : R.drawable.wallet_chat_unselect;
                break;
            case 1:
                id = isCheck ? R.drawable.wallet_msg_select : R.drawable.wallet_msg_unselect;
                break;
            case 2:
                id = isCheck ? R.drawable.wallet_trade_select : R.drawable.wallet_trade_unselect;
                break;
            case 3:
                id = isCheck ? R.drawable.wallet_hq_select : R.drawable.wallet_hq_unselect;
                break;
            case 4:
                id = isCheck ? R.drawable.wallet_zc_select : R.drawable.wallet_zc_unselect;
                break;
        }
        return id;
    }

    /**
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment to) {
        if (mCurrentFragment != to) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
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
