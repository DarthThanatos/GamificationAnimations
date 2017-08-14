package com.example.vobis.gamificationanimations.circleanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Vobis on 2017-08-14
 */

public class CircleInBackGround extends View {

    private Paint paint, linesPaint;
    private RectF rect;

    private final int strokeWidth = 5;

    public CircleInBackGround(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.GRAY);
        linesPaint = new Paint();
        linesPaint.setColor(Color.WHITE);

        rect = new RectF();
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
        canvas.drawArc(rect, 0, 360, false, paint);
    }

}
