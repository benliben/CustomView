package com.android.benben.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Time      2017/6/9 14:28 .
 * Author   : LiYuanXiong.
 * Content  :
 */

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 一。先重写onMeasure，实现测量子View大小及设定ViewGroup的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*将所有的子View进行测量，这会触发每个view的onMeasure函数
        * 注意要与measureChild区分，measureChild是对单个view进行测量*/

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        if (childCount == 0) {//如果没有子view，当前viewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0, 0);
        } else {
            if (widthMode == MeasureSpec.AT_MOST && heightModel == MeasureSpec.AT_MOST) {
                /*如果宽高都是包裹内容，我们将高度设置为所有子View的高度相加，宽度为子View中最大的宽度*/
                int height = getTotleHeight();
                int width = getMaxChildWidth();
                setMeasuredDimension(width, height);
            } else if (heightModel == MeasureSpec.AT_MOST) {
                /*如果只有高度是包裹内容，宽度为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和*/
                int height = getTotleHeight();
                setMeasuredDimension(widthSize, height);

            } else if (widthMode == MeasureSpec.AT_MOST) {
                /*如果只有宽度是包裹内容，宽度设置为View中宽度的最大的值，高度设置为ViewGroup自己的测量值*/
                int width = getMaxChildWidth();
                setMeasuredDimension(width, heightSize);
            }
        }
    }


    /**
     * 获取子view中宽度最大的值
     *
     * @return 最大值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth) {
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }


    /**
     * 将所有子View的高度相加
     *
     * @return 总和
     */
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();
        }
        return height;
    }
    /**
     * 摆放子View
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = 0;
        /*将子View逐个摆放*/
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            /*摆放子view，参数分别是子View矩形区域的左上右下*/
            child.layout(0, curHeight, width, curHeight + height);
            curHeight += height;
        }
    }


}
