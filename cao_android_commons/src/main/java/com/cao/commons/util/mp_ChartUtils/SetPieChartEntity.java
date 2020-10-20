package com.cao.commons.util.mp_ChartUtils;

//import android.graphics.Color;
//
//import com.cao.commons.util.CollectionUtils;
//import com.cao.commons.util.ColorUtils;
//import com.github.mikephil.charting.animation.Easing;
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.data.PieEntry;
//import com.github.mikephil.charting.interfaces.datasets.IDataSet;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by 小武 on 2017/9/24.
// * 饼状图
// */
//
//public class SetPieChartEntity {
//
//
//    private int allTextColor = Color.WHITE;   // 默认全部字体的颜色
//    private PieChart mChart;
//    protected List<PieEntry> mEntries;       // 数据
//    ArrayList<Integer> colors;              // 环块上对应数据的颜色
//    private Legend l;
//
//    public void setAllTextColor(int allTextColor) {
//        this.allTextColor = allTextColor;
//        setChartLabelColor(allTextColor);
//    }
//
//    public SetPieChartEntity(PieChart barChart, List<PieEntry> entries) {
//        this(barChart, entries, null);
//    }
//
//    public SetPieChartEntity(PieChart barChart, List<PieEntry> entries, ArrayList<Integer> colors) {
//        this.mChart = barChart;
//        this.mEntries = entries;
//        this.colors = colors;
//        initAttribute();
//        setChartData();
//    }
//
//
//    private void setChartData() {
//        PieDataSet dataSet = new PieDataSet(mEntries, "");
//        dataSet.setSliceSpace(2f);  //  圆形上每个数据之间的间隙
//        dataSet.setSelectionShift(6f); // 点击某个数据块放大的值
//        dataSet.setDrawValues(true);
//
//        // 如果没有设置颜色  就显示默认的颜色
//        if (CollectionUtils.isNullOrEmpty(colors)) {
//            ArrayList<Integer> colorDefault = new ArrayList<Integer>();
//            for (int i = 0; i < mEntries.size(); i++) {
//                colorDefault.add(ColorUtils.randomColor());
//            }
//            dataSet.setColors(colorDefault);
//        } else {
//            dataSet.setColors(colors);
//        }
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(9f);
//        data.setValueTextColor(allTextColor);
//        mChart.setData(data);
//
//        // undo all highlights
//        mChart.highlightValues(null);
//
//        mChart.invalidate();
//
//    }
//
//    /**
//     * 设置是否显示在环块上的数据值
//     *
//     * @param isDrawValues
//     */
//    public void setDrawValues(Boolean isDrawValues) {
//        for (IDataSet<?> set : mChart.getData().getDataSets())
//            set.setDrawValues(isDrawValues);
//
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置是否显示在环块上的name
//     *
//     * @param isDrawEntryLabels
//     */
//    public void setDrawEntryLabels(Boolean isDrawEntryLabels) {
//        mChart.setDrawEntryLabels(isDrawEntryLabels);
//        mChart.invalidate();
//    }
//
//    //在piechart内的值是否绘制成百分比值
//    public void setUsePercentValues(Boolean usePercentValues) {
//        mChart.setUsePercentValues(usePercentValues);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置环块无间隙 无阴影
//     */
//    public void setTransparentCircle() {
//        PieDataSet dataSet = (PieDataSet) mChart.getData().getDataSet();
//        dataSet.setSliceSpace(0f);  //  圆形上每个数据之间的间隙
//        dataSet.setSelectionShift(6f); // 点击某个数据块放大的值
//        //设置小于或等于跟中间空心圆的值一样 就不会有透明的想阴影的圆
//        mChart.setTransparentCircleRadius(50f);
//        mChart.invalidate();
//    }
//
//    // 空心半径占比全图的百分比
//    public void setHoleRadius(float holeRadius) {
//        mChart.setHoleRadius(holeRadius);
//        mChart.invalidate();
//    }
//
//    /**
//     * // 设置图表在整块布局上的偏移量(在图表视图周围)
//     *
//     * @param left
//     * @param top
//     * @param right
//     * @param bottom
//     */
//    public void setExtraOffsets(float left, float top, float right, float bottom) {
//        mChart.setExtraOffsets(left, top, right, bottom);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置用折线显示数据值
//     */
//    public void setValueLine() {
//        PieDataSet dataSet = (PieDataSet) mChart.getData().getDataSet();
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.3f);
//        dataSet.setValueLinePart2Length(0.4f);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setValueTextColor(allTextColor);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置图列块和 折线上的值 颜色
//     */
//    public void setChartLabelColor(int allTextColor) {
//        mChart.getLegend().setTextColor(allTextColor);
//        mChart.getData().setValueTextColor(allTextColor);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置空心圆中的文本是否显示
//     */
//    public void setDrawCenterText(Boolean isCenterText) {
//        setDrawCenterText(isCenterText, "");
//    }
//
//    /**
//     * 设置空心圆中的文本是否显示和值
//     */
//    public void setDrawCenterText(Boolean isCenterText, String text) {
//        mChart.setCenterText(text);
//        // 是否显示空心的文本
//        mChart.setDrawCenterText(isCenterText);
//        mChart.setCenterTextSize(20);
//        mChart.invalidate();
//    }
//
//    /**
//     * 设置列块居中左边
//     */
//    public void setLegendLeft() {
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//    }
//
//    /**
//     * 设置禁用比例块
//     */
//    public void setLegend() {
//        l.setEnabled(false);//设置禁用比例块
//    }
//
//    /**
//     * 设置基本属性
//     */
//    protected void initAttribute() {
//
//        //在piechart内的值是否绘制成百分比值
//        mChart.setUsePercentValues(false);
//        // 是否显示图表右角的文字
//        mChart.getDescription().setEnabled(false);
//        //  设置在PieChart中心显示的文本字符串
//        mChart.setCenterText("");
//        // 是否显示空心的文本
//        mChart.setDrawCenterText(false);
//        // 设置是否是空心的饼状图
//        mChart.setDrawHoleEnabled(true);
//        // 设置空心的颜色
//        mChart.setHoleColor(Color.WHITE);
//        // 设置透明圈应有的颜色。
//        mChart.setTransparentCircleColor(Color.WHITE);
//        // 空心半径占比全图的百分比
//        mChart.setHoleRadius(50f);
//        // 设置在空心圆旁边的透明圆的半径
//        mChart.setTransparentCircleRadius(0);
//        // 设置在空心圆旁边的透明度 透明度为0 =完全透明，
//        mChart.setTransparentCircleAlpha(110);
//        //饼图上怎么只显示百分比
//        mChart.setDrawSliceText(true);
//
//        // 设置旋转度设定一个偏移量
//        mChart.setRotationAngle(0);
//        // 是否通过触摸使图表旋转
//        mChart.setRotationEnabled(true);
//        mChart.setHighlightPerTapEnabled(true);
//        // 设置图表转动的速度
//        mChart.setDragDecelerationFrictionCoef(0.95f);
//        // 设置图表在整块布局上的偏移量(在图表视图周围)
//        mChart.setExtraOffsets(5.f, 10.f, 5.f, 10.f);
//
//        // 设置环块上的标签name的颜色
//        mChart.setEntryLabelColor(Color.BLACK);
//        // 设置环块上的标签name的大小
//        mChart.setEntryLabelTextSize(10f);
//        // 设置环块上的标签name是否显示
//        mChart.setDrawEntryLabels(false);
//
//        mChart.animateY(2000, Easing.EasingOption.EaseInOutQuad);
//
//        l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setForm(Legend.LegendForm.SQUARE); //设置图例的形状
//        l.setFormSize(9f);         //设置图例的大小
//        l.setTextSize(8f);         // 设置图例文字大小
//        l.setWordWrapEnabled(true);
//        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);  //设置图例自动换行
//        l.setDrawInside(false);     // 设置图例是否在图表内或外部绘制
//        l.setXEntrySpace(0f);       //设置每个图例块X轴之间距（setOrientation = HORIZONTAL有效）
//        l.setYEntrySpace(5f);       //设置每个图例块Y轴之间距（setOrientation = VERTICAL 有效）
//        l.setXOffset(20f);          //设置整个比例块X轴偏移量
//        l.setYOffset(-5f);           //设置整个比例块Y轴偏移量
//
//    }
//
//}
