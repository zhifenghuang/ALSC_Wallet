//package com.cao.commons.util.baidu;
//
//import android.content.Context;
//
//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.core.PoiInfo;
//import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
//import com.baidu.mapapi.search.poi.PoiDetailResult;
//import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
//import com.baidu.mapapi.search.poi.PoiIndoorResult;
//import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
//import com.baidu.mapapi.search.poi.PoiResult;
//import com.baidu.mapapi.search.poi.PoiSearch;
//import com.baidu.mapapi.search.poi.PoiSortType;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.List;
//
//
///**
// * Created by caojianzhen on 2018/6/12.
// */
//public class LocationManager {
//
//    public MyLocationListenner myListener = new MyLocationListenner();
//    public BDLocation nowLocation;
//    private Object objLock = new Object();
//    private final LocationClient mLocClient;
//    private final PoiSearch search;
//    private int pageNum = 1;
//
//    public LocationManager(Context context) {
//        search = PoiSearch.newInstance();
//        // 定位初始化
//        mLocClient = new LocationClient(context);
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        //设置是否需要地址信息，默认为无地址
//        option.setIsNeedAddress(true);
//        option.setIsNeedLocationDescribe(true);
//        option.setIsNeedLocationPoiList(true);
//        option.setAddrType("all");// 返回的定位结果包含地址信息
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(10000);
//        mLocClient.setLocOption(option);
//    }
//
//    /**
//     * 设置翻页
//     */
//    public void setPageNum(int pageNum) {
//        this.pageNum = pageNum;
//    }
//
//    /**
//     * 定位SDK监听函数
//     */
//    public class MyLocationListenner extends BDAbstractLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            nowLocation = location;
//            // map view 销毁后不在处理新接收的位置
//            if (location == null) {
//                return;
//            }
//            EventBus.getDefault().post(location);
//            searchNeayBy(location, 5000, pageNum);
//        }
//    }
//
//    public void start() {
//        synchronized (objLock) {
//            if (mLocClient != null) {
//                mLocClient.start();
//            }
//        }
//    }
//
//    public void stop() {
//        synchronized (objLock) {
//            if (mLocClient != null && mLocClient.isStarted()) {
//                mLocClient.stop();
//            }
//        }
//    }
//
//
//    /**
//     * 搜索周边地理位置
//     * by hankkin at:2015-11-01 22:54:49
//     */
//    private void searchNeayBy(BDLocation location, int radius, int pageNum) {
//        search.setOnGetPoiSearchResultListener(resultListener);
//        PoiNearbySearchOption option = new PoiNearbySearchOption();
//        option.keyword("小区");
//        option.sortType(PoiSortType.distance_from_near_to_far);
//        option.location(new LatLng(location.getLatitude(), location.getLongitude()));
//        option.radius(radius);
//
//        option.pageCapacity(20);
//        option.pageNum(pageNum);//搜索一页
//        search.searchNearby(option);
//    }
//
//    //POI检索的监听对象
//    OnGetPoiSearchResultListener resultListener = new OnGetPoiSearchResultListener() {
//        //获得POI的检索结果，一般检索数据都是在这里获取
//        @Override
//        public void onGetPoiResult(PoiResult poiResult) {
//            //如果搜索到的结果不为空，并且没有错误
//            if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
//                List<PoiInfo> allPoi = poiResult.getAllPoi();
//                EventBus.getDefault().post(allPoi);
//                stop();
//            }
//        }
//
//        @Override
//        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//
//        }
//
//        //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
//        //详细检索一般用于单个地点的搜索，比如搜索一大堆信息后，选择其中一个地点再使用详细检索
//        @Override
//        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailResult) {
//        }
//
//        //获得POI室内检索结果
//        @Override
//        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
//        }
//    };
//}
