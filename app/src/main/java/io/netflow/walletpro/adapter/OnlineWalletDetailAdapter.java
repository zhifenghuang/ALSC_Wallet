package io.netflow.walletpro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import io.netflow.walletpro.R;
import com.common.activity.BaseActivity;
import com.common.dialog.MyDialogFragment;
import io.netflow.walletpro.fragment.online.TransferFragment;
import io.netflow.walletpro.fragment.online.WalletDetailFragment;
import com.common.utils.QRCodeUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OnlineWalletDetailAdapter extends BaseMultiItemQuickAdapter<WalletDetailFragment.BtcWalletItem, BaseViewHolder> {

    private Context mContext;

    public OnlineWalletDetailAdapter(Context context) {
        super(new ArrayList<>());
        addItemType(0, R.layout.item_online_wallet_detail);
        addItemType(1, R.layout.item_online_wallet_detail_1);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, WalletDetailFragment.BtcWalletItem item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.getView(R.id.llTransfer).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).gotoPager(TransferFragment.class);
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
