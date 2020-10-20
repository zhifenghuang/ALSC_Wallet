package com.cao.commons.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.text.Html;
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
public class ReleaseSuccessDialog extends Dialog implements View.OnClickListener,DialogInterface.OnDismissListener{

    private View mView;
    private Context mContext;
    private Button back_home;
    private TextView tv_title;
    private TextView tv_content;
    private OnReleaseSuccessDialogListener listener;

    public static ReleaseSuccessDialog builder(Context context) {
        return new ReleaseSuccessDialog(context);
    }

    public ReleaseSuccessDialog setContent( String msg){
        tv_content.setText(Html.fromHtml(msg));
        return this;
    }

    public ReleaseSuccessDialog setTitle( String msg){
        tv_title.setText(msg);
        return this;
    }

    public ReleaseSuccessDialog setListener(OnReleaseSuccessDialogListener listener){
        this.listener = listener;
        return this;
    }

    public ReleaseSuccessDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.release_success_dialog,null);
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
        tv_title = mView.findViewById(R.id.tv_title);
        tv_content = mView.findViewById(R.id.tv_content);
    }

    private void setListener(){
        back_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back_home) {
            listener.cancleOnClick(ReleaseSuccessDialog.this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        listener.onDismiss();
    }

    public interface OnReleaseSuccessDialogListener {
        void cancleOnClick(ReleaseSuccessDialog dialog);
        void onDismiss();
    }
}
