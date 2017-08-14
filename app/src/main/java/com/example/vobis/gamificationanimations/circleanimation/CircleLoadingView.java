package com.example.vobis.gamificationanimations.circleanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Vobis on 2017-08-14.
 */

public class CircleLoadingView extends View {

    private float progress = 0;

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void displayProgress(float progress){
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){

    }

}
