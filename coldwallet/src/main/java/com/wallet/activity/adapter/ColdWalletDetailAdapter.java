package com.wallet.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cold.wallet.R;
import com.common.activity.BaseActivity;
import com.common.dialog.MyDialogFragment;
import com.common.utils.QRCodeUtil;
import com.wallet.activity.cold.ColdAssetsListActivity;
import com.wallet.utils.ToastUtil;
import com.wallet.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 描述结果
 *
 * @author xyx on 2020/10/26 0026
 * @e-mail 384744573@qq.com
 * @see [相关类/方法](可选)
 */
public class ColdWalletDetailAdapter extends BaseMultiItemQuickAdapter<ColdAssetsListActivity.BtcWalletItem, BaseViewHolder> {

    private Context mContext;
    private String mAddress;

    public ColdWalletDetailAdapter(Context context,String address) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_cold_wallet_detail);
        addItemType(1, R.layout.item_cold_wallet_detail_1);
        mContext = context;
        mAddress = address;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ColdAssetsListActivity.BtcWalletItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.getView(R.id.llTransfer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                    helper.getView(R.id.llParent).setBackgroundResource(R.drawable.shape_ffffff_8);
                } else {
                    if (helper.getAdapterPosition() == 1) {
                        helper.getView(R.id.llParent).setBackgroundResource(R.drawable.shape_ffffff_8_top);
                    } else if (helper.getAdapterPosition() == getItemCount() - 1) {
                        helper.getView(R.id.llParent).setBackgroundResource(R.drawable.shape_ffffff_8_bottom);
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
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.dialog_my_qrcode);
        dialogFragment.setOnMyDialogListener(new MyDialogFragment.OnMyDialogListener() {
            @Override
            public void initView(View view) {
                dialogFragment.setDialogViewsOnClickListener(view, R.id.tvCopy);
                if (mQrBmp != null) {
                    mQrBmp.recycle();
                }
                mQrBmp = QRCodeUtil.createQRImage(mContext, mAddress, null);
                ((ImageView) view.findViewById(R.id.ivQrCode)).setImageBitmap(mQrBmp);
            }

            @Override
            public void onViewClick(int viewId) {
                if (viewId == R.id.tvCopy) {
                    Utils.copyData(mContext, mAddress);
                    ToastUtil.toast(mContext.getString(R.string.user_copy_success));
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
        final MyDialogFragment dialogFragment = new MyDialogFragment(R.layout.dialog_filter_record);
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
                if (viewId == R.id.tvAll) {

                } else if (viewId == R.id.tvTransferIn) {

                } else if (viewId == R.id.tvTransferOut) {

                }
            }
        });
        dialogFragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "MyDialogFragment");
    }

}
