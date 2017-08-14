package com.example.vobis.gamificationanimations.circleanimation;


import android.animation.Animator;

/**
 * Created by Vobis on 2017-08-14
 */

public abstract class AnimationEndListener implements Animator.AnimatorListener {

    public abstract void onEnd(Animator animator);

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        onEnd(animator);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
