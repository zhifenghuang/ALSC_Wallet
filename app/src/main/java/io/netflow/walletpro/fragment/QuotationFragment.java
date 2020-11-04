package io.netflow.walletpro.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.netflow.walletpro.R;
import io.netflow.walletpro.adapter.HangQingAdapter;
import com.cao.commons.bean.chat.UserBean;
import com.cao.commons.manager.DataManager;
import com.common.bean.CoinHangQingBean;
import com.common.fragment.BaseFragment;
import com.common.utils.Utils;

public class QuotationFragment extends BaseFragment {

    private HangQingAdapter mHangQingAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hang_qing;
    }

    @Override
    protected void onViewCreated(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getHangQingAdapter().onAttachedToRecyclerView(recyclerView);
        recyclerView.setAdapter(getHangQingAdapter());
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_btc, "BTC", "1.23万亿", "67903.61", "$9585.59", "+6.19%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_eth, "ETH", "1860.35亿", "1699.15", "$239.89", "+7.36%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_eos, "EOS", "650.19亿", "7.0784", "$0.9993", "0.00%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_ltc, "LTC", "100.69亿", "5.0678", "$0.8763", "-3.45%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_dash, "DASH", "80.12", "3.0145", "$0.563", "-4.17%"));
        getHangQingAdapter().addData(new CoinHangQingBean(
                R.drawable.wallet_zec, "ZEC", "60.87亿", "18.0678", "$2.8763", "-5.37%"));
    }

    private HangQingAdapter getHangQingAdapter() {
        if (mHangQingAdapter == null) {
            mHangQingAdapter = new HangQingAdapter(getActivity());
        }
        return mHangQingAdapter;
    }

    @Override
    public void updateUIText() {
        UserBean myInfo = DataManager.getInstance().getUser();
        Utils.displayAvatar(getActivity(), R.drawable.chat_default_group_avatar, myInfo.getAvatarUrl(), fv(R.id.ivMyAvatar));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean isNeedSetTopStyle() {
        return false;
    }


}
