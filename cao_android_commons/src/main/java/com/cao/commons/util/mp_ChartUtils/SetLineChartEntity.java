package com.cao.commons.util.mp_ChartUtils;

import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

//import com.cao.commons.R;
//import com.cao.commons.base.PoliceApplication;
//import com.cao.commons.util.ColorUtils;
//import com.github.mikephil.charting.charts.BarLineChartBase;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.github.mikephil.charting.utils.ColorTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 小武 on 2017/9/23.
// * 折线图
// */
//
//public class SetLineChartEntity extends BaseChartAttribute {
//
//    public SetLineChartEntity(BarLineChartBase lineChartBase, List<Entry>[] entries) {
//        this(lineChartBase, entries, null, null);
//    }
//
//    /**
//     * 参数 3(X 轴的名称)  4 (图例名称)  如果不显示 就调用上面两个方法
//     */
//    public SetLineChartEntity(BarLineChartBase lineChartBase, List<Entry>[] entries, List<String> xName, List<String> legendName) {
//        super(lineChartBase, entries, xName, legendName);
//    }
//
//    @Override
//    protected void setChartData() {
//        List<ILineDataSet> lineDataSets = new ArrayList<>();
//        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
//            for (int i = 0; i < mEntries.length; i++)
//                ((LineDataSet) mChart.getData().getDataSetByIndex(i)).setValues(mEntries[i]);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            for (int i = 0; i < mEntries.length; i++) {
//                LineDataSet lineDataSet = new LineDataSet(mEntries[i], getLabel(i));
//                if (mEntries.length > 1) {
//                    lineDataSet.setColor(ColorUtils.randomColor());
//                } else
//                    lineDataSet.setColors(ColorTemplate.getHoloBlue());
//                lineDataSet.setLineWidth(2f);           // 设置线宽
//                lineDataSet.setCircleRadius(3f);        // 设置点大小（半径）
//                lineDataSet.setDrawCircleHole(false);    // true 为空心点  false 为实心点
//                lineDataSets.add(lineDataSet);
//            }
//
//            LineData lineData = new LineData(lineDataSets);
//            mChart.setData(lineData);
//        }
//    }
//
//
//    /**
//     * 设置单条数据颜色虚线（第几条数据）
//     */
//    public void setFormLineDashEffect(int i) {
//        LineDataSet sets = (LineDataSet) mChart.getData().getDataSetByIndex(i);
//        sets.enableDashedLine(10f, 5f, 0f);
//        sets.enableDashedHighlightLine(10f, 5f, 0f);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置单条数据多条颜色虚线（第几条数据）
//     */
//    public void setEnableDashedLine(int i) {
//        LineDataSet sets = (LineDataSet) mChart.getData().getDataSetByIndex(i);
//        sets.enableDashedLine(10f, 10f, 0f);
//        sets.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        sets.setCircleColors(ColorTemplate.VORDIPLOM_COLORS);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置数据被填充 默认颜色 （第几条数据）
//     */
//    public void setDrawFilled(int i) {
//        setDrawFilled(true, i, 0);
//    }
//
//
//    /**
//     * 设置数据被填充 自定义颜色  （是否填充， 第几条数据，drawable资源文件）
//     */
//    public void setDrawFilled(boolean isFill, int i, @DrawableRes int id) {
//        LineDataSet data = (LineDataSet) mChart.getData().getDataSetByIndex(i);
//        data.setDrawFilled(isFill); // 是否数据被填充
//        Drawable drawable;
//        if (id == 0) {
//            drawable = ContextCompat.getDrawable(PoliceApplication.newInstance(), R.drawable.fade_red);
//        } else {
//            drawable = ContextCompat.getDrawable(PoliceApplication.newInstance(), id);
//        }
//        data.setFillDrawable(drawable);
//        mChart.invalidate();
//    }
//
//    /**
//     * true 为空心点  false 为实心点
//     */
//    public void setDrawCircleHole(Boolean isSolid) {
//        LineDataSet data = (LineDataSet) mChart.getData().getDataSets();
//        data.setDrawCircleHole(isSolid);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置图表上数据值的颜色
//     */
//    public void setChartValueColor(int chartValueColor) {
//        LineData data = (LineData) mChart.getData();
//        data.setValueTextColor(chartValueColor);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置所有标签颜色
//     */
//    public void setChartLabelColor(int labelColor) {
//        mChart.getXAxis().setTextColor(labelColor);
//        mChart.getAxisLeft().setTextColor(labelColor);
//        mChart.getLegend().setTextColor(labelColor);
//        LineData data = (LineData) mChart.getData();
//        data.setValueTextColor(labelColor);
//        mChart.invalidate();
//    }
//}
