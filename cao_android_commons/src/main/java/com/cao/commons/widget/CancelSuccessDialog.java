package com.cao.commons.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cao.commons.R;


/**
 * ReleaseSuccessDialog
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-6-13
 */
public class CancelSuccessDialog extends Dialog implements View.OnClickListener,DialogInterface.OnDismissListener{

    private View mView;
    private Context mContext;
    private Button back_home;
    private TextView tv_my_ticket;
    private OnCancelSuccessDialogListener listener;

    public static CancelSuccessDialog builder(Context context) {
        return new CancelSuccessDialog(context);
    }

    public CancelSuccessDialog setListener(OnCancelSuccessDialogListener listener){
        this.listener = listener;
        return this;
    }

    public CancelSuccessDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.cancel_success_dialog,null);
        this.mContext = context;
        init();
    }

    private void init(){
        setContentView(mView);
        setCancelable(false);
        findViewByIds();
        setListener();
    }

    private void findViewByIds(){
        back_home = mView.findViewById(R.id.back_home);
        tv_my_ticket = mView.findViewById(R.id.tv_my_ticket);
    }

    private void setListener(){
        back_home.setOnClickListener(this);
        tv_my_ticket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_home) {
            listener.cancleOnClick(CancelSuccessDialog.this);
        }else if(i == R.id.tv_my_ticket){
            listener.confirmOnClick(CancelSuccessDialog.this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        listener.onDismiss();
    }

    public interface OnCancelSuccessDialogListener {
        void cancleOnClick(CancelSuccessDialog dialog);
        void confirmOnClick(CancelSuccessDialog dialog);
        void onDismiss();
    }
}
