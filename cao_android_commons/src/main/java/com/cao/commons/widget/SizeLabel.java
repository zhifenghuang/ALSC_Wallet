package com.cao.commons.widget;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import com.cao.commons.base.PoliceApplication;

import org.xml.sax.XMLReader;

/**
 * 自定义设置字体大小
 *
 * @author CJZ
 * @Time 2018/11/26
 */
public class SizeLabel implements Html.TagHandler {

    private Context context;
    private int size;
    private int startIndex = 0;
    private int stopIndex = 0;

    public SizeLabel(int size) {
        this.size = size;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals("size")) {
            if (opening) {
                startIndex = output.length();
            } else {
                stopIndex = output.length();
                output.setSpan(new AbsoluteSizeSpan(dip2px(size)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public static int dip2px(float dpValue) {
        final float scale = PoliceApplication.newInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
