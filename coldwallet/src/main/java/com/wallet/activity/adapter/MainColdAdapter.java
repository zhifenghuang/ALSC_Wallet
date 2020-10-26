package com.wallet.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cao.commons.bean.cold.ColdHqBean;
import com.cao.commons.bean.cold.WalletDataBean;
import com.cold.wallet.R;
import com.wallet.activity.cold.ColdAssetsListActivity;
import com.wallet.activity.cold.ColdWalletAddActivity;
import com.wallet.utils.Utils;
import com.wallet.utils.StaticUtil;

import java.util.ArrayList;
import java.util.List;

public class  MainColdAdapter extends RecyclerView.Adapter {
    private final static int ITEM_TYPE_TOP = 1;
    private final static int ITEM_TYPE_HEAD = 2;

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<WalletDataBean> datas;
    private List<WalletDataBean> allDatas;
    private boolean isExpand = true;
    private String mSymbol;
    private ColdHqBean mColdHqBean;
    private boolean isEyeOpen = true;

    public MainColdAdapter(Context context, boolean isEyeOpen) {
        this.mContext = context;
        this.isEyeOpen = isEyeOpen;
        this.allDatas = new ArrayList<>();
        this.datas = new ArrayList<>();
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return ITEM_TYPE_TOP;
//        } else {
        return StaticUtil.ITEM_TYPE_NORMAL;
//        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case ITEM_TYPE_TOP:
//                return new TopViewHolder(layoutInflater.inflate(R.layout.item_cold_top, parent, false));
//            default:
//                return new MyViewHolder(layoutInflater.inflate(R.layout.item_cold_normal, parent, false));
//        }
        return new MyViewHolder(layoutInflater.inflate(R.layout.item_cold_normal, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            final WalletDataBean entity = datas.get(position);
            viewHolder.tv_title.setText(entity.getName());
            viewHolder.tv_address.setText(entity.getAddress());
            if (!TextUtils.isEmpty(entity.getPrice())) {
                viewHolder.tv_money.setText(entity.getMoney());
                viewHolder.iv_loading1.setImageBitmap(null);
            } else {
                viewHolder.tv_money.setText("");
                viewHolder.iv_loading1.setImageResource(R.drawable.ic_svstatus_loading);
                viewHolder.iv_loading1.startAnimation(createAnimation());
            }
            if (!TextUtils.isEmpty(entity.getPrice())) {
                viewHolder.tv_equal.setText("≈$" + entity.getPrice());
            } else {
                viewHolder.tv_equal.setText("");
            }
            if (isEyeOpen) {
                viewHolder.tv_money.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                viewHolder.tv_equal.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                viewHolder.tv_money.setTransformationMethod(PasswordTransformationMethod.getInstance());
                viewHolder.tv_equal.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (entity.getWalletType().trim().equals("USDT-OMNI")) {
//                        return;
//                    }
                    ColdAssetsListActivity.startActivity(mContext, entity);
                }
            });
        } else if (holder instanceof TopViewHolder) {
            TopViewHolder viewHolder = (TopViewHolder) holder;
            if (isExpand) {
                viewHolder.tv_expand.setText(mContext.getString(R.string.c_collapse));
                viewHolder.iv_arrow.setRotation(180f);
                viewHolder.ll_import.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_expand.setText(mContext.getString(R.string.c_expand));
                viewHolder.iv_arrow.setRotation(0f);
                viewHolder.ll_import.setVisibility(View.GONE);
            }

            viewHolder.ll_add_wallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ColdWalletAddActivity.startActivity(mContext);
                }
            });
            viewHolder.ll_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isExpand = !isExpand;
                    if (isExpand) {
                        viewHolder.tv_expand.setText(mContext.getString(R.string.c_collapse));
                        viewHolder.iv_arrow.setRotation(180f);
                        viewHolder.ll_import.setVisibility(View.VISIBLE);
                        datas.clear();
                        datas.addAll(allDatas);
                        notifyDataSetChanged();
                    } else {
                        viewHolder.tv_expand.setText(mContext.getString(R.string.c_expand));
                        viewHolder.iv_arrow.setRotation(0f);
                        viewHolder.ll_import.setVisibility(View.GONE);
                        datas.clear();
                        notifyDataSetChanged();
                    }
                }
            });
            viewHolder.ll_create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ColdAssetsListActivity.startActivity(mContext,"BTC");
                }
            });
            viewHolder.ll_import.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ColdAssetsListActivity.startActivity(mContext,"BTC");
                }
            });
        }
    }

    public void setmColdHqBean(ColdHqBean mColdHqBean) {
        this.mColdHqBean = mColdHqBean;
        notifyDataSetChanged();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == Utils.MESSAGE_WHAT_MONEY) {
                ArrayList<WalletDataBean> list1 = (ArrayList<WalletDataBean>) msg.obj;
                MainColdAdapter.this.allDatas = list1;
                if (isExpand) {
                    MainColdAdapter.this.datas.clear();
                    MainColdAdapter.this.datas.addAll(MainColdAdapter.this.allDatas);
                    notifyDataSetChanged();
                }
            }
        }
    };


    public void setData(String symbol, ArrayList<WalletDataBean> list) {
        this.datas.clear();
        this.allDatas.clear();
        this.mSymbol = symbol;
//        isExpand = false;
        notifyDataSetChanged();
        MainColdAdapter.this.allDatas = list;
        if (isExpand) {
            MainColdAdapter.this.datas.clear();
            MainColdAdapter.this.datas.addAll(MainColdAdapter.this.allDatas);
            notifyDataSetChanged();
        }
    }

    public void refreshData(String symbol, ArrayList<WalletDataBean> list) {
        this.datas.clear();
        this.allDatas.clear();
        this.mSymbol = symbol;
        notifyDataSetChanged();
        MainColdAdapter.this.allDatas = list;
        if (isExpand) {
            MainColdAdapter.this.datas.clear();
            MainColdAdapter.this.datas.addAll(MainColdAdapter.this.allDatas);
            notifyDataSetChanged();
        }
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
        MainColdAdapter.this.datas.clear();
        if (isExpand) {
            MainColdAdapter.this.datas.addAll(MainColdAdapter.this.allDatas);
        }
        notifyDataSetChanged();
    }

    public void clearAll() {
        datas.clear();
        notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        void onClick(String type, String name, int position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<WalletDataBean> list) {
        this.datas.clear();
        if (list != null) {
            this.datas.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setEyeOpen(boolean eyeOpen) {
        isEyeOpen = eyeOpen;
        notifyDataSetChanged();
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;

        public HeadViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_money;
        TextView tv_address;
        TextView tv_equal;
        ImageView iv_loading1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_equal = itemView.findViewById(R.id.tv_equal);
            iv_loading1 = itemView.findViewById(R.id.iv_loading1);
        }
    }

    class TopViewHolder extends RecyclerView.ViewHolder {
        TextView tv_expand;
        View ll_add_wallet, iv_arrow, ll_expand, ll_import, ll_create;

        public TopViewHolder(View itemView) {
            super(itemView);
            ll_add_wallet = itemView.findViewById(R.id.ll_add_wallet);
            tv_expand = itemView.findViewById(R.id.tv_expand);
            iv_arrow = itemView.findViewById(R.id.iv_arrow);
            ll_expand = itemView.findViewById(R.id.ll_expand);
            ll_import = itemView.findViewById(R.id.ll_import);
            ll_create = itemView.findViewById(R.id.ll_create);
        }
    }


    private RotateAnimation createAnimation(){
        RotateAnimation mRotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000L);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setRepeatCount(-1);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

        return mRotateAnimation;
    }
}