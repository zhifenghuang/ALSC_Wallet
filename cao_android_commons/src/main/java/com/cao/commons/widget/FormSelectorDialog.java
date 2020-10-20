package com.cao.commons.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

//import com.cao.commons.R;
//import com.cao.commons.model.FormModel;
//import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
//import com.library.flowlayout.FlowLayoutManager;
//import com.library.flowlayout.SpaceItemDecoration;
//
//import java.util.List;
//
//
///**
// * FormSelectorDialog
// *
// * @author Rock
// * @version v1.0
// * @Time 2018-6-13
// */
//public class FormSelectorDialog extends Dialog implements View.OnClickListener, DialogInterface.OnDismissListener {
//
//    private View mView;
//    private Context mContext;
//    private Button back_home;
//    private OnReleaseSuccessDialogListener listener;
//    private RecyclerView rvForm;
//
//    public static FormSelectorDialog builder(Context context) {
//        return new FormSelectorDialog(context);
//    }
//
//    public FormSelectorDialog setRvData(final List<String> list, List<FormModel> formList) {
//        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
//        rvForm.addItemDecoration(new SpaceItemDecoration(10));
//        rvForm.setLayoutManager(flowLayoutManager);
//        FormAdapter formAdapter = new FormAdapter(getContext());
//        rvForm.setAdapter(formAdapter);
//        formAdapter.setFormData(formList);
//        formAdapter.addAll(list);
//        formAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                String selectItem = list.get(position);
//                listener.selectItem(FormSelectorDialog.this, selectItem);
//            }
//        });
//        return this;
//    }
//
//    public FormSelectorDialog setListener(OnReleaseSuccessDialogListener listener) {
//        this.listener = listener;
//        return this;
//    }
//
//    public FormSelectorDialog(@NonNull Context context) {
//        super(context, R.style.custom_dialog);
//        mView = LayoutInflater.from(context).inflate(R.layout.form_selector_dialog, null);
//        this.mContext = context;
//        init();
//    }
//
//    private void init() {
//        setContentView(mView);
//        setCancelable(false);
//        findViewByIds();
//        setListener();
//        Window window = this.getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(params);
//    }
//
//    private void findViewByIds() {
//        back_home = mView.findViewById(R.id.back_home);
//        rvForm = mView.findViewById(R.id.rvForm);
//    }
//
//    private void setListener() {
//        back_home.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.back_home) {
//            listener.cancleOnClick(FormSelectorDialog.this);
//        }
//    }
//
//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        listener.onDismiss();
//    }
//
//    public interface OnReleaseSuccessDialogListener {
//        void cancleOnClick(FormSelectorDialog dialog);
//
//        void selectItem(FormSelectorDialog dialog, String selectItem);
//
//        void onDismiss();
//    }
//}
