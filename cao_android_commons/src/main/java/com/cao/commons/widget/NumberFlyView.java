package com.cao.commons.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.youngfeng.snake.util.Utils;

import java.util.ArrayList;
import java.util.Random;

public class NumberFlyView extends RelativeLayout {

    private ArrayList<FlyEmo> mFlyViews;
    private boolean mFlag;
    private Matrix mMatrix;
    private float mSpeed;

    class FlyEmo {
        View numberView;
        float currentX;
        float currentY;
        float currentScale;
    }

    public NumberFlyView(Context context, AttributeSet attr) {
        super(context, attr);
        mFlag = false;
        mSpeed = Utils.dp2px(context, 3);
    }

    public boolean addFlyView(View numberView) {
        if (numberView == null) {
            return false;
        }
        if (getVisibility() == View.GONE) {
            setVisibility(View.VISIBLE);
        }
        if (mFlyViews == null) {
            mFlyViews = new ArrayList<>();
        }
        Random random = new Random();
        FlyEmo flyView = new FlyEmo();
        flyView.numberView = numberView;
        flyView.currentX = getWidth() / 10 + random.nextInt(getWidth() * 4 / 5);
        flyView.currentY = getHeight() / 5 + random.nextInt(getHeight() * 7 / 10);
        flyView.currentScale = 0.1f;
        mFlyViews.add(flyView);
        if (!mFlag) {
            startThread();
        }
        return true;
    }

    private synchronized void startThread() {
        if (mFlag) {
            return;
        }
        mFlag = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (mFlag) {

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        mFlag = false;
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (mflyEmos == null || mflyEmos.size() == 0) {
//            mFlag = false;
//            return;
//        }
//        if (!mFlag) {
//            startThread();
//        }
//        FlyEmo flyEmo;
//        for (int i = 0; i < mflyEmos.size(); ) {
//            flyEmo = mflyEmos.get(i);
//            if (flyEmo.emo == null || flyEmo.currentY <= 0) {
//                mflyEmos.remove(i);
//                continue;
//            }
//            ++i;
//            if (mMatrix == null) {
//                mMatrix = new Matrix();
//            }
//            mMatrix.reset();
//            if (flyEmo.currentScale < 1.0f) {
//                flyEmo.currentScale += 0.05f;
//                mMatrix.postScale(flyEmo.currentScale, flyEmo.currentScale);
//                mMatrix.postTranslate(
//                        flyEmo.currentX + 0.5f * flyEmo.emo.getWidth()
//                                * (1 - flyEmo.currentScale), flyEmo.currentY
//                                + 0.5f * flyEmo.emo.getHeight()
//                                * (1 - flyEmo.currentScale));
//                canvas.drawBitmap(flyEmo.emo, mMatrix, null);
//            } else {
//                if (mSpeed <= 1.0f) {
//                    mSpeed = Utils.dip2px(getContext(), 5);
//                }
//                flyEmo.currentY = flyEmo.currentY - mSpeed;
//                mMatrix.postTranslate(flyEmo.currentX, flyEmo.currentY);
//                mMatrix.postScale(flyEmo.currentScale, flyEmo.currentScale);
//                canvas.drawBitmap(flyEmo.emo, mMatrix, null);
//            }
//        }
//    }

    public void stopNumberFly() {
        if (mFlyViews != null) {
            mFlyViews.clear();
        }
        mFlag = false;
    }

}
