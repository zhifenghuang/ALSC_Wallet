package com.cao.commons.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cao.commons.R;
import com.cao.commons.model.CityModel;
import com.cao.commons.model.NavModel;
import com.cao.commons.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * selector Dialog选择Utils
 *
 * @author CJZ
 * @Time 2018/11/20
 */
public class DialogUtils {

    private static int currentIndex;
    private static List<CityModel> options1Items;
    private static ArrayList<ArrayList<String>> options2Items;
    private static ArrayList<ArrayList<ArrayList<String>>> options3Items;

    /**
     * Dialog本单位用户选择
     *
     * @param context 上下文
     * @param list    源数据
     * @param request 回调
     */
    public static void selectActivityType(Context context, final List<NavModel> list, DialogRequest request) {
        ArrayList<String> nameList = new ArrayList<>();
        for (NavModel model : list) {
            nameList.add(model.value);
        }
        selectorCondition(context, nameList, request);
    }

    public interface DialogRequest {
        void selected(int index);
    }

    /**
     * 公用的Dialog
     *
     * @param context
     * @param textView
     * @param listData
     */
    public static void select(Context context, final List<String> listData, final TextView textView) {
        currentIndex = 0;
        View outerView = LayoutInflater.from(context).inflate(R.layout.wheel_view, null);
        WheelView wheelView = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wheelView.setOffset(2);
        wheelView.setItems(listData);
        wheelView.setSeletion(0);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                currentIndex = selectedIndex - 2;
            }
        });

        new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.select_area))
                .setView(outerView)
                .setNegativeButton(context.getResources().getString(R.string.permissions_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(context.getResources().getString(R.string.permissions_determine), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (currentIndex == -1) {
                            currentIndex = 0;
                        }
                        textView.setText(listData.get(currentIndex));
                    }
                }).show();
    }

    /**
     * 省市区三级联动
     */
    public static void selectPCD(Context context, final DialogPCDRequest request) {
        getPCDData(context);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                request.selected(opt1tx, opt2tx, opt3tx);
            }

        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public interface DialogPCDRequest {
        void selected(String options1Item, String options2Item, String options3Item);
    }

    /**
     * 条件选择器
     */
    public static void selectorCondition(Context context, List<String> listData, final DialogRequest request) {
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                request.selected(options1);
            }
        })
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 1)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                    }
                })
                .build();

        pvOptions.setPicker(listData);//一级选择器*/
        pvOptions.show();
    }


    /**
     * 时间选择器(年月日时分秒)
     */
    public static void selectorTime(Context context, final TextView view) {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                view.setText(TimeUtils.converToString(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        pvTime.setDate(calendar);
        pvTime.show(view);
    }

    /**
     * 时间选择器(年月日时分)
     */
    public static void selectorTimeYMDHM(Context context, final TextView view) {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                view.setText(TimeUtils.converToStringYMDHM(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        pvTime.setDate(calendar);
        pvTime.show(view);
    }

    /**
     * 时间选择器(年月日)
     */
    public static void selectorTimeYMD(Context context, final TextView view) {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                view.setText(TimeUtils.converToStringYMD(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pvTime.setDate(calendar);
        pvTime.show(view);
    }


//    /**
//     * 选择图片、视频、音频dialog
//     */
//    public static void startPSToAvatarActivity(Activity activity, int chooseMode, int maxSelectNum, boolean isCrop, List<LocalMedia> selectList) {
//        PictureSelector.create(activity)
//                .openGallery(chooseMode)//图片
//                .theme(R.style.picture_default_style)//白色样式
//                .maxSelectNum(maxSelectNum)// 最大图片选择数量
//                .minSelectNum(1)// 最小选择数量
//                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
//                .previewImage(true)// 是否可预览图片
//                .enablePreviewAudio(true)
//                .previewVideo(true)
//                .isCamera(true)// 是否显示拍照按钮
//                .compress(true)// 是否压缩
//                .isGif(true)// 是否显示gif图片
//                .enableCrop(isCrop)// 是否裁剪 true or false
//                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
//                .imageFormat(PictureMimeType.PNG)
////                .setOutputCameraPath(OutputPath)// 自定义拍照保存路径
////                .cropCompressQuality(50)
//                .selectionMedia(selectList)// 是否传入已选图片
//                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
////                .minimumCompressSize(recharge_select)// 小于100kb的图片不压缩
//                .recordVideoSecond(20)
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//    }
//
//    /**
//     * 选择图片、视频、音频dialog
//     */
//    public static void startPSToAvatarActivity(Fragment fragment, int chooseMode, int maxSelectNum, boolean isCrop, List<LocalMedia> selectList) {
//        PictureSelector.create(fragment)
//                .openGallery(chooseMode)//图片
//                .theme(R.style.picture_default_style)//白色样式
//                .maxSelectNum(maxSelectNum)// 最大图片选择数量
//                .minSelectNum(1)// 最小选择数量
//                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
//                .previewImage(true)// 是否可预览图片
//                .enablePreviewAudio(true)
//                .previewVideo(true)
//                .isCamera(true)// 是否显示拍照按钮
//                .compress(true)// 是否压缩
//                .isGif(true)// 是否显示gif图片
//                .enableCrop(isCrop)// 是否裁剪 true or false
//                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
//                .imageFormat(PictureMimeType.PNG)
////                .setOutputCameraPath(OutputPath)// 自定义拍照保存路径
////                .cropCompressQuality(50)
//                .selectionMedia(selectList)// 是否传入已选图片
//                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
////                .minimumCompressSize(recharge_select)// 小于100kb的图片不压缩
//                .recordVideoSecond(20)
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//    }
//
//    /**
//     * 选择图片、视频、音频dialog
//     */
//    public static void startPictureSelectorActivity(Activity activity, int chooseMode, int maxSelectNum, List<LocalMedia> selectList) {
//        PictureSelector.create(activity)
//                .openGallery(chooseMode)//图片
//                .theme(R.style.picture_default_style)//白色样式
//                .maxSelectNum(maxSelectNum)// 最大图片选择数量
//                .minSelectNum(1)// 最小选择数量
//                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
//                .previewImage(true)// 是否可预览图片
//                .enablePreviewAudio(true)
//                .previewVideo(true)
//                .isCamera(true)// 是否显示拍照按钮
//                .compress(true)// 是否压缩
//                .isGif(true)// 是否显示gif图片
//                .enableCrop(true)// 是否裁剪 true or false
//                .withAspectRatio(70, 42)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
//                .imageFormat(PictureMimeType.PNG)
////                .setOutputCameraPath(OutputPath)// 自定义拍照保存路径
////                .cropCompressQuality(50)
////                .selectionMedia(selectList)// 是否传入已选图片
//                .previewEggs(true)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
////                .minimumCompressSize(recharge_select)// 小于100kb的图片不压缩
//                .recordVideoSecond(20)
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//    }
//
//
//    /**
//     * 选择图片、视频、音频dialog
//     */
//    public static void startVideoActivity(Activity activity) {
//        PictureSelector.create(activity)
//                .openCamera(PictureMimeType.ofVideo())
//                .recordVideoSecond(15)
//                .forResult(PictureConfig.REQUEST_CAMERA);
//    }

    /**
     * 二次确认框
     */
    public static void ConfirmationBox(Context context, String title, final ConfirmationBoxCallback callback) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setNegativeButton(context.getResources().getString(R.string.pickerview_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(context.getResources().getString(R.string.pickerview_submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.Confirm();
                    }
                }).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    public interface ConfirmationBoxCallback {
        void Confirm();
    }

    /**
     * 二次确认框
     */
    public static void ConfirmationToCustomButtonBox(Context context, String title, String negative, String positive, final ConfirmationToCustomButtonBoxCallback callback) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.Confirm1();
                    }
                })
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        callback.Confirm2();
                    }
                }).create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    public interface ConfirmationToCustomButtonBoxCallback {
        void Confirm1();

        void Confirm2();
    }


    //省市区数据初始化
    public static void getPCDData(Context context) {
        options1Items = new ArrayList<>();
        options2Items = new ArrayList<>();
        options3Items = new ArrayList<>();
        String jsonAssets = GsonUtils.getJsonAssets(context, "province.json");
        //获取assets目录下的json文件数据
        ArrayList<CityModel> cityModelList = GsonUtils.parseAssetsData(jsonAssets);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = cityModelList;

        for (int i = 0; i < cityModelList.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < cityModelList.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = cityModelList.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(cityModelList.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }
}
