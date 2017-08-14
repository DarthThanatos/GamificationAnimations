package com.example.vobis.gamificationanimations.circleanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Vobis on 2017-08-14.
 */

public class CircleLoadingView extends View {

    private static final int START_ANGLE_POINT = -90;
    private static final String TAG = CircleLoadingView.class.getSimpleName();

    private Paint paint, linesPaint;
    private RectF rect;

    private float angle, oldAngle, newAngle = 360;
    private final int strokeWidth = 10;

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.WHITE);
        linesPaint = new Paint();
        linesPaint.setColor(Color.WHITE);

        rect = new RectF();
    }

    public void displayProgress(float interpolatedTime){
        angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        rect.set(
                strokeWidth,
                getHeight()/2 - getWidth()/2 + strokeWidth,
                getWidth() - strokeWidth,
                getHeight()/2 + getWidth()/2 - strokeWidth
        );
//        canvas.drawLine(0, 0, getWidth() + correction, 0, linesPaint);
//        canvas.drawLine(getWidth() + correction, 0, getWidth() + correction, getHeight() + correction, linesPaint);
//        canvas.drawLine(getWidth() + correction, getHeight() + correction, 0, getHeight() + correction, linesPaint);
//        canvas.drawLine(0, getHeight() + correction, 0, 0, linesPaint);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

}
