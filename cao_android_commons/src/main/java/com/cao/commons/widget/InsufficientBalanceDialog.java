package com.cao.commons.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cao.commons.R;


/**
 * InsufficientBalanceDialog 余额不足
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-6-13
 */
public class InsufficientBalanceDialog extends Dialog implements View.OnClickListener,DialogInterface.OnDismissListener{

    private View mView;
    private Context mContext;
    /** 标题、内容*/
    private TextView tv_title;
    private TextView tv_msg_1;
    private ImageView img_logo;
    /** 取消、确定*/
    private ImageView img_cancel;
    private Button btn_back_home;
    private OnInsufficientBalanceDialogListener listener;

    public static InsufficientBalanceDialog builder(Context context) {
        return new InsufficientBalanceDialog(context);
    }



    public InsufficientBalanceDialog setContent(String msg){
//        tv_msg_1.setText(Html.fromHtml(msg));
        tv_msg_1.setText(msg);
        return this;
    }

    public InsufficientBalanceDialog setTitle(String msg){
        tv_title.setText(msg);
        return this;
    }

    public InsufficientBalanceDialog setButton(String msg,Drawable drawable){
        btn_back_home.setText(msg);
        btn_back_home.setBackground(drawable);
        return this;
    }

    public InsufficientBalanceDialog setVisibilityCha(int visibility){
        img_cancel.setVisibility(visibility);
        return this;
    }
    public InsufficientBalanceDialog setVisibilityLogo(int visibility){
        img_logo.setVisibility(visibility);
        return this;
    }

    public InsufficientBalanceDialog setListener(OnInsufficientBalanceDialogListener listener){
        this.listener = listener;
        return this;
    }

    public InsufficientBalanceDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.insufficient_balance_dialog,null);
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
        tv_msg_1 =  mView.findViewById(R.id.tv_msg_1);
        img_cancel =  mView.findViewById(R.id.img_cancel);
        img_logo =  mView.findViewById(R.id.img_logo);
        btn_back_home =  mView.findViewById(R.id.btn_back_home);
    }

    private void setListener(){
        btn_back_home.setOnClickListener(this);
        img_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_back_home) {
            listener.confirmOnClick(InsufficientBalanceDialog.this);
        }else if(i == R.id.img_cancel){
            listener.cancleOnClick(InsufficientBalanceDialog.this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        listener.onDismiss();
    }

    public interface OnInsufficientBalanceDialogListener {
        void confirmOnClick(InsufficientBalanceDialog dialog);
        void cancleOnClick(InsufficientBalanceDialog dialog);
        void onDismiss();
    }
}
