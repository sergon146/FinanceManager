package com.myst3ry.financemanager.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.myst3ry.financemanager.R;

public final class DiagramView extends View {

    private long mCurrentMoney;
    private long mTotalMoney;

    private final Paint mCurrentPaint = new Paint();
    private final Paint mTotalPaint = new Paint();

    public DiagramView(Context context) {
        super(context);
        prepareCustomView();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        prepareCustomView();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prepareCustomView();
    }

    @SuppressWarnings("unused")
    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        prepareCustomView();
    }

    private void prepareCustomView() {
        mCurrentPaint.setColor(getResources().getColor(R.color.color_primary_dark));
        mTotalPaint.setColor(getResources().getColor(R.color.color_primary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDiagram(canvas);
    }


    public void update(final long currentMoney, final long totalMoney) {
        this.mCurrentMoney = currentMoney;
        this.mTotalMoney = totalMoney;
        invalidate();
    }

    public void drawDiagram(final Canvas canvas) {
        if (mCurrentMoney + mTotalMoney == 0) {
            return;
        }

        final int space = 5;
        final int size = Math.min(getHeight(), getWidth()) - space * 2;
        final float currentAngle = 360.f * mCurrentMoney / (mCurrentMoney + mTotalMoney);
        final float totalAngle = 360.f * mTotalMoney / (mCurrentMoney + mTotalMoney);
        final int xMargin = (getWidth() - size) / 2;
        final int yMargin = (getHeight() - size) / 2;
        canvas.drawArc(xMargin - space, yMargin, getWidth() - xMargin - space,
                getHeight() - yMargin, 180 - currentAngle / 2, currentAngle, true, mCurrentPaint);
        canvas.drawArc(xMargin + space, yMargin, getWidth() - xMargin + space,
                getHeight() - yMargin, 360 - totalAngle / 2, totalAngle, true, mTotalPaint);
    }
}