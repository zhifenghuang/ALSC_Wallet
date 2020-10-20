package com.cao.commons.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cao.commons.R;


/**
 * CommonDialog
 *
 * @author Rock
 * @version v1.0
 * @Time 2018-6-13
 */
public class CommonDialog extends Dialog implements View.OnClickListener,DialogInterface.OnDismissListener{

    private View mView;
    private Context mContext;
    /** 标题、内容*/
    private TextView tv_title;
    private TextView tv_msg_1;
    /** 取消、确定*/
    private TextView tv_dismiss;
    private OnCommonDialogListener listener;

    public static CommonDialog builder(Context context) {
        return new CommonDialog(context);
    }



    public CommonDialog setContent(String msg){
        tv_msg_1.setText(msg);
        return this;
    }

    public CommonDialog setTitle(String msg){
        tv_title.setText(msg);
        return this;
    }

    public CommonDialog setListener(OnCommonDialogListener listener){
        this.listener = listener;
        return this;
    }

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.common_dialog,null);
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
        tv_title = mView.findViewById(R.id.tv_title);
        tv_msg_1 =  mView.findViewById(R.id.tv_msg_1);
        tv_dismiss =  mView.findViewById(R.id.tv_dismiss);
    }

    private void setListener(){
        tv_dismiss.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_dismiss) {
            listener.cancleOnClick(CommonDialog.this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        listener.onDismiss();
    }

    public interface OnCommonDialogListener {
        void cancleOnClick(CommonDialog dialog);
        void onDismiss();
    }
}
