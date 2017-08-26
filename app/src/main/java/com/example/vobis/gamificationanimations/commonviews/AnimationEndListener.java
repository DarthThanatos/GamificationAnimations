package com.example.vobis.gamificationanimations.commonviews;

import android.view.animation.Animation;

/**
 * Created by Vobis on 2017-08-26
 */

public abstract class AnimationEndListener implements Animation.AnimationListener {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        onEnd(animation);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    protected abstract void onEnd(Animation animation);
}
