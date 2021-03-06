package com.alsc.chat.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alsc.chat.R;
import com.cao.commons.bean.chat.BasicMessage;
import com.cao.commons.bean.chat.MessageType;
import com.alsc.chat.http.OkHttpClientManager;
import com.alsc.chat.manager.MediaplayerManager;
import com.alsc.chat.utils.Constants;
import com.alsc.chat.utils.Utils;
import com.alsc.chat.view.ShowPicView;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.chs.filepicker.filepicker.util.OpenFile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ShowMessageFragment extends ChatBaseFragment {

    public static final int TYPE_SHOW_CHAT_MESSAGE = 0;
    public static final int TYPE_SHOW_IMAGE_URL = 1;

    private int mType;
    private BasicMessage mMessage;
    private String mUrl;


    private MapView mMapView;
    private AMap mAMap;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    private ArrayList<BasicMessage> mMsgList;
    private ArrayList<View> mViewList;
    private int mCurrentPos, mLastPos;

    private final int MAX_VIEW_SIZE = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(Constants.BUNDLE_EXTRA, TYPE_SHOW_CHAT_MESSAGE);
        if (mType == TYPE_SHOW_CHAT_MESSAGE) {
            mMessage = (BasicMessage) getArguments().getSerializable(Constants.BUNDLE_EXTRA_2);
            if (mMessage.getMsgType() == MessageType.TYPE_IMAGE.ordinal()
                    || mMessage.getMsgType() == MessageType.TYPE_VIDEO.ordinal()) {
                mMsgList = (ArrayList<BasicMessage>) getArguments().getSerializable(Constants.BUNDLE_EXTRA_3);
            }
        } else {
            mUrl = getArguments().getString(Constants.BUNDLE_EXTRA_2, "");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mMessage != null && mMessage.getMsgType() == MessageType.TYPE_LOCATION.ordinal()) {
            mMapView = view.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);// 此方法必须重写
            initMap();
            showLocationMsg();
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        int resId = 0;
        if (mType == TYPE_SHOW_IMAGE_URL) {
            resId = R.layout.item_message_media;
        } else {
            int msgType = mMessage.getMsgType();
            if (msgType == MessageType.TYPE_IMAGE.ordinal() || msgType == MessageType.TYPE_VIDEO.ordinal()) {
                resId = R.layout.layout_message_media;
            } else if (msgType == MessageType.TYPE_LOCATION.ordinal()) {
                resId = R.layout.layout_message_location;
            } else if (msgType == MessageType.TYPE_FILE.ordinal()) {
                resId = R.layout.layout_message_file;
            }
        }
        return resId;
    }

    @Override
    protected void onViewCreated(View view) {
        //   setTopStatusBarStyle(view);
        EventBus.getDefault().register(this);
        if (mType == TYPE_SHOW_IMAGE_URL) {
            showImage(view);
        } else {
            int msgType = mMessage.getMsgType();
            if (msgType == MessageType.TYPE_IMAGE.ordinal() || msgType == MessageType.TYPE_VIDEO.ordinal()) {
                showMedia(view);
            } else if (msgType == MessageType.TYPE_FILE.ordinal()) {
                showFileMsg(view);
            }
        }
    }

    private void showMedia(View view) {
        final ViewPager viewPager = view.findViewById(R.id.viewPager);
        mViewList = new ArrayList<>();
        for (int i = 0; i < MAX_VIEW_SIZE; ++i) {
            mViewList.add(LayoutInflater.from(getActivity()).inflate(R.layout.item_message_media, null));
        }
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mMsgList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View item = mViewList.get(position % MAX_VIEW_SIZE);
                container.addView(item);
                return item;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position % MAX_VIEW_SIZE));
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mCurrentPos = viewPager.getCurrentItem();
                if (state == ViewPager.SCROLL_STATE_IDLE && mLastPos != mCurrentPos) {
                    mLastPos = mCurrentPos;
                    if (mCurrentPos > 0) {
                        goneView(mViewList.get((mCurrentPos - 1) % MAX_VIEW_SIZE), mMsgList.get(mCurrentPos - 1));
                    }
                    if (mCurrentPos < mMsgList.size() - 1) {
                        goneView(mViewList.get((mCurrentPos + 1) % MAX_VIEW_SIZE), mMsgList.get(mCurrentPos + 1));
                    }
                    mMessage = mMsgList.get(mCurrentPos);
                    if (mMessage.getMsgType() == MessageType.TYPE_IMAGE.ordinal()) {
                        showImage(mViewList.get(mCurrentPos % MAX_VIEW_SIZE));
                    } else {
                        playVideo(mViewList.get(mCurrentPos % MAX_VIEW_SIZE));
                    }
                }
            }
        });
        mCurrentPos = 0;
        for (BasicMessage msg : mMsgList) {
            if (msg.getMessageId().equals(mMessage.getMessageId())) {
                break;
            }
            ++mCurrentPos;
        }
        viewPager.setCurrentItem(mCurrentPos);
        if (mMessage.getMsgType() == MessageType.TYPE_IMAGE.ordinal()) {
            showImage(mViewList.get(mCurrentPos % MAX_VIEW_SIZE));
        } else {
            playVideo(mViewList.get(mCurrentPos % MAX_VIEW_SIZE));
        }
        mLastPos = mCurrentPos;
        if (mCurrentPos > 0) {
            goneView(mViewList.get((mCurrentPos - 1) % MAX_VIEW_SIZE), mMsgList.get(mCurrentPos - 1));
        }
        if (mCurrentPos < mMsgList.size() - 1) {
            goneView(mViewList.get((mCurrentPos + 1) % MAX_VIEW_SIZE), mMsgList.get(mCurrentPos + 1));
        }
    }

    private void goneView(View view, BasicMessage msg) {
        VideoView videoView = view.findViewById(R.id.videoView);
        videoView.stopPlayback();
        videoView.setVisibility(View.GONE);
        view.findViewById(R.id.ivClose).setVisibility(View.GONE);
        ShowPicView showPicView = view.findViewById(R.id.ivShowPic);
        showPicView.setVisibility(View.VISIBLE);
        String fileName = null;
        if (!TextUtils.isEmpty(msg.getContent())) {
            try {
                JSONObject jsonObject = new JSONObject(msg.getContent());
                fileName = jsonObject.optString("fileName");
            } catch (Exception e) {

            }
        }
        String filePath = Utils.getSaveFilePath(mContext, fileName);
        Utils.loadImage(mContext, 0, new File(filePath), msg.getUrl(), showPicView);
    }

    private void showFileMsg(View view) {
        setTopStatusBarStyle(view);
        TextView tvLeft = view.findViewById(R.id.tvTitle);
        tvLeft.setText(R.string.chat_file);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_00_00_00));
        if (!TextUtils.isEmpty(mMessage.getContent())) {
            try {
                JSONObject jsonObject = new JSONObject(mMessage.getContent());
                String fileName = jsonObject.optString("fileName");
                setText(R.id.tvFileName, fileName);
                TextView tvFileOperator = view.findViewById(R.id.tvFileOperator);
                final File file = new File(Utils.getSaveFilePath(mContext, fileName));
                tvFileOperator.setVisibility(View.VISIBLE);
                if (!file.exists()) {
                    tvFileOperator.setText(getString(R.string.chat_download));
                    tvFileOperator.setTag(0);
                } else {
                    tvFileOperator.setText(getString(R.string.chat_open_file));
                    tvFileOperator.setTag(1);
                }
                tvFileOperator.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        if (tag == 0) {
                            v.setEnabled(false);
                            OkHttpClientManager.getInstance().downloadAsyn(mMessage.getUrl(), file, new OkHttpClientManager.HttpCallBack() {
                                @Override
                                public void successful() {
                                    if (getActivity() == null || getView() == null) {
                                        return;
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            v.setTag(1);
                                            v.setEnabled(true);
                                            tvFileOperator.setText(getString(R.string.chat_open_file));
                                        }
                                    });

                                }

                                public void progress(int progress) {

                                }

                                @Override
                                public void failed(Exception e) {
                                    if (getActivity() == null || getView() == null) {
                                        return;
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            v.setEnabled(true);
                                        }
                                    });
                                }
                            });
                        } else {
                            startActivity(Intent.createChooser(OpenFile.openFile(file.getAbsolutePath(), getActivity().getApplicationContext()), getString(R.string.chat_select_application)));
                        }
                    }
                });
            } catch (Exception e) {
            }
        }

    }

    private void showImage(View view) {
        ShowPicView picView = view.findViewById(R.id.ivShowPic);
        picView.setVisibility(View.VISIBLE);
        view.findViewById(R.id.videoView).setVisibility(View.GONE);
        view.findViewById(R.id.ivClose).setVisibility(View.GONE);
        picView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        if (mType == TYPE_SHOW_IMAGE_URL) {
            Utils.loadImage(mContext, 0, mUrl, picView);
            return;
        }
        String fileName = null;
        if (!TextUtils.isEmpty(mMessage.getContent())) {
            try {
                JSONObject jsonObject = new JSONObject(mMessage.getContent());
                fileName = jsonObject.optString("fileName");
            } catch (Exception e) {

            }
        }
        String filePath = Utils.getSaveFilePath(mContext, fileName);
        Utils.loadImage(mContext, 0, new File(filePath), mMessage.getUrl(), picView);
    }

    private void playVideo(View view) {
        view.findViewById(R.id.ivShowPic).setVisibility(View.GONE);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        ivClose.setVisibility(View.VISIBLE);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        VideoView videoView = view.findViewById(R.id.videoView);
        videoView.setVisibility(View.VISIBLE);
        String fileName = null;
        int width = 0, height = 0;
        if (!TextUtils.isEmpty(mMessage.getContent())) {
            try {
                JSONObject jsonObject = new JSONObject(mMessage.getContent());
                fileName = jsonObject.optString("fileName");
                width = jsonObject.optInt("width");
                height = jsonObject.optInt("height");
            } catch (Exception e) {

            }
        }
        String filePath = Utils.getSaveFilePath(mContext, fileName);
        File file = new File(filePath);
        if (file.exists()) {
            view.findViewById(R.id.progressBar).setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
            int screenW = getDisplaymetrics().widthPixels;
            int screenH = getDisplaymetrics().heightPixels;
            float ratio1 = width * 1.0f / height;
            float ratio2 = screenW * 1.0f / screenH;
            if (ratio1 > ratio2) {
                lp.width = screenW;
                lp.height = (int) (screenW / ratio1);
            } else {
                lp.width = (int) (screenH * ratio1);
                lp.height = screenH;
            }
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            videoView.setLayoutParams(lp);

            MediaController cotroller = new MediaController(getContext(), false);
            videoView.setVideoPath(filePath);
            videoView.setMediaController(cotroller);
            videoView.seekTo(0);
            videoView.requestFocus();
            videoView.start();
        } else {
            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            UiSettings settings = mAMap.getUiSettings();
            settings.setZoomControlsEnabled(false);
            mAMap.setLocationSource(new LocationSource() {
                @Override
                public void activate(OnLocationChangedListener onLocationChangedListener) {

                }

                @Override
                public void deactivate() {

                }
            });//设置了定位的监听,这里要实现LocationSource接口
            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(false);
            mAMap.setMyLocationEnabled(true);
            mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.strokeColor(Color.TRANSPARENT);
            myLocationStyle.radiusFillColor(Color.TRANSPARENT);
            myLocationStyle.myLocationIcon(null);
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                    .fromResource(R.drawable.chat_gps_d_1));
            mAMap.setMyLocationStyle(myLocationStyle);

            mAMap.moveCamera(CameraUpdateFactory.zoomTo(mAMap.getMaxZoomLevel() - 3));
        }
    }

    private void showLocationMsg() {
        setTopStatusBarStyle(getView());
        TextView tvLeft = fv(R.id.tvTitle);
        tvLeft.setText(R.string.chat_location_info);
        tvLeft.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_00_00_00));
        fv(R.id.topView).setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_ff_ff_ff));
        if (!TextUtils.isEmpty(mMessage.getContent())) {
            try {
                JSONObject jsonObject = new JSONObject(mMessage.getContent());
                double lat = jsonObject.optDouble("lat");
                double lon = jsonObject.optDouble("lon");
                setText(R.id.tvName, jsonObject.optString("title"));
                setText(R.id.tvAddress, jsonObject.optString("address"));
                LatLng pos = new LatLng(lat, lon);
                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(pos));
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(pos)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chat_gps_1)));
                mAMap.addMarker(markerOptions);
            } catch (Exception e) {

            }
        }
    }

    private void startLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            if (mLocationClient == null) {
                startLocation();
            } else {
                mLocationClient.startLocation();
            }
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }

    @Override
    public void updateUIText() {

    }

    @Override
    public void onClick(View v) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(HashMap<String, Object> map) {
        if (getView() != null && map != null) {
            if (map.containsKey(Constants.DOWNLOAD_FILE)) {
                String url = (String) map.get(Constants.DOWNLOAD_FILE);
                if (mMessage != null && mMessage.getMsgType() == MessageType.TYPE_VIDEO.ordinal() &&
                        mMessage.getUrl().equals(url)) {
                    getView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playVideo(mViewList.get(mCurrentPos % MAX_VIEW_SIZE));
                        }
                    }, 300);
                }
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        MediaplayerManager.getInstance().releaseMediaplayer();
    }
}
