package com.myst3ry.financemanager.ui.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Auto fit Grid RecyclerView
 *
 * @author marcochin
 * Copied: https://github.com/marcochin/Popular-Movies/blob/master/app/src/main/java/com/mcochin/popularmovies/custom/AutoFitGridRecyclerView.java
 */
public class AutoFitGridRecyclerView extends RecyclerView {
    private GridLayoutManager manager;
    private int columnWidth = -1;

    public AutoFitGridRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoFitGridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoFitGridRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            // list the attributes we want to fetch
            // columnWidth is what GridView uses, so we use it too
            int[] attrsArray = {
                    android.R.attr.columnWidth
            };

            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);

            //retrieve the value of the 0 index, which is columnWidth
            columnWidth = array.getDimensionPixelSize(0, -1);
            array.recycle();
        }

        manager = new GridLayoutManager(context, 1);
        setLayoutManager(manager);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        if (columnWidth > 0) {
            //The spanCount will always be at least 1
            int spanCount = Math.max(1, getMeasuredWidth() / columnWidth);
            manager.setSpanCount(spanCount);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}