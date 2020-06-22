package com.alsc.alsc_wallet.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alsc.alsc_wallet.R;
import com.alsc.alsc_wallet.activity.BaseActivity;
import com.alsc.alsc_wallet.dialog.MyDialogFragment;
import com.alsc.alsc_wallet.fragment.cold.BtcTransferFragment;
import com.alsc.alsc_wallet.fragment.cold.BtcWalletFragment;
import com.alsc.alsc_wallet.utils.QRCodeUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BtcWalletAdapter extends BaseMultiItemQuickAdapter<BtcWalletFragment.BtcWalletItem, BaseViewHolder> {

    private Context mContext;

    public BtcWalletAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_btc_wallet_0);
        addItemType(1, R.layout.item_btc_wallet_1);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, BtcWalletFragment.BtcWalletItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.getView(R.id.llTransfer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).gotoPager(BtcTransferFragment.class);
                    }
                });
                helper.getView(R.id.llGetMoney).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showQrCodeDialog();
                    }
                });
                helper.getView(R.id.llFilter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFilterDialog(v);
                    }
                });
                break;
            case 1:
                if (getItemCount() == 2) {
                    helper.getView(R.id.llParent).setBackgroundResource(R.drawable.bg_wallet_white_round);
                } else {
                    if (helper.getAdapterPosition() == 1) {
                        helper.getView(R.id.llParent).setBackgroundResource(R.drawable.bg_wallet_white_round_top);
                    } else if (helper.getAdapterPosition() == getItemCount() - 1) {
                        helper.getView(R.id.llParent).setBackgroundResource(R.drawable.bg_wallet_white_round_bottom);
                    } else {
                        helper.getView(R.id.llParent).setBackgroundColor(Color.WHITE);
                    }
                }
                helper.setGone(R.id.line, helper.getAdapterPosition() == getItemCount() - 1);
                break;
        }
    }

    private Bitmap mQrBmp;

    private void showQrCodeDialog() {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_qrcode_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.tvCopy);
                if (mQrBmp != null) {
                    mQrBmp.recycle();
                }
                mQrBmp = QRCodeUtil.createQRImage(mContext, "1FyMtiZtiZtiZtiZkeo...aroZh4AVz", null);
                ((ImageView) view.findViewById(R.id.ivQrCode)).setImageBitmap(mQrBmp);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvCopy:
                        break;
                }
            }
        });
        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
        dialogFragment.setOnDismiss(new MyDialogFragment.IDismissListener() {
            @Override
            public void onDismiss() {
                if (mQrBmp != null) {
                    mQrBmp.recycle();
                }
                mQrBmp = null;
            }
        });
    }


    public void showFilterDialog(final View locationView) {
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.layout_filter_record_dialog);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.llParent, R.id.ll, R.id.tvAll, R.id.tvTransferIn, R.id.tvTransferOut);
                View llReport = view.findViewById(R.id.ll);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llReport.getLayoutParams();
                int[] location = new int[2];
                locationView.getLocationOnScreen(location);
                lp.topMargin = location[1] + view.getHeight();
                llReport.setLayoutParams(lp);
            }

            @Override
            public void onViewClick(int viewId) {
                switch (viewId) {
                    case R.id.tvAll:
                        break;
                    case R.id.tvTransferIn:
                        break;
                    case R.id.tvTransferOut:
                        break;
                }
            }
        });
        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
    }

}
