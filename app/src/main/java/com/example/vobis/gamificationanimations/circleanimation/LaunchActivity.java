package com.example.vobis.gamificationanimations.circleanimation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vobis.gamificationanimations.R;
import com.example.vobis.gamificationanimations.config.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchActivity extends AppCompatActivity implements LaunchContract.View{

    private LaunchContract.Presenter presenter;
    @BindView(R.id.loading_circle) CircleLoadingView circleLoadingView;
    @BindView(R.id.progress_display_tv) TextView progressDisplayTV;
    @BindView(R.id.launch_container) RelativeLayout launchContainer;
    @BindView(R.id.background_loaded) ImageView backgroundLoaded;
    @BindView(R.id.init_loading_center) RelativeLayout initLoadingCenter;
    @BindView(R.id.final_loading_center) ImageView finalLoadingCenter;
    @BindView(R.id.smaller_background_circle) CircleInBackGround smallerBackgroundCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        initPresenter();

    }

    private void initPresenter(){
        presenter = new LaunchPresenter();
        presenter.attachView(this);
        presenter.initBusinessLogic();
    }

    @Override
    public void onDestroy(){
        if(presenter != null) presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void displayProgressCircle(float progress) {
        circleLoadingView.displayProgress(progress);
    }



    @Override
    public void shiftBackgroundAlpha(float progress) {
        backgroundLoaded.setAlpha(progress);
    }

    @Override
    public void displayProgressText(String txtToDisplay) {
        progressDisplayTV.setText(txtToDisplay);
    }

    @Override
    public void setInitialLoadingView() {
        initLoadingCenter.setVisibility(View.VISIBLE);
        finalLoadingCenter.setVisibility(View.GONE);
    }

    @Override
    public void setFinalLoadingView() {
        initLoadingCenter.setVisibility(View.GONE);
        finalLoadingCenter.setVisibility(View.VISIBLE);
    }

    private void animateSquareViewGrowth(View view, int startSize,  int endSize, boolean addEndListener){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator growingAnimator = ValueAnimator.ofInt(startSize, endSize);
        growingAnimator.setDuration(Config.GROWING_ANIMATION_TIME);
        growingAnimator.setInterpolator(new LinearInterpolator());
        growingAnimator.addUpdateListener(valueAnimator -> {
            params.width = (int) valueAnimator.getAnimatedValue();
            params.height = (int) valueAnimator.getAnimatedValue();
            view.setLayoutParams(params);
        });
        if(addEndListener){
            growingAnimator.addListener(new AnimationEndListener() {
                @Override
                public void onEnd(Animator animator) {
                    presenter.performAPICheck();
                    finish();
                }
            });
        }
        growingAnimator.start();
    }

    @Override
    public void animateLoadedSuccessful() {
        ValueAnimator unfadingAnimator = ObjectAnimator.ofFloat(finalLoadingCenter, "alpha", 0, 1);
        unfadingAnimator.setDuration(Config.UNFADING_ANIMATION_TIME);
        unfadingAnimator.setInterpolator(new LinearInterpolator());
        unfadingAnimator.addListener(new AnimationEndListener() {

            @Override
            public  void onEnd(Animator animator) {
                int biggerCircleWidth = (int) getResources().getDimension(R.dimen.bigger_circle_width);
                int startCircleSize = (int) getResources().getDimension(R.dimen.smaller_circle_width);
                int endCircleSize = startCircleSize + (int) (biggerCircleWidth - startCircleSize) / 4;
                int startFinalCenterSize = (int) getResources().getDimension(R.dimen.loading_center_width);
                int endFinalCenterSize = startFinalCenterSize + (int) (endCircleSize/(Math.sqrt(2)) - startFinalCenterSize) / 4;

                animateSquareViewGrowth(smallerBackgroundCircle, startCircleSize, endCircleSize, false);
                animateSquareViewGrowth(circleLoadingView, startCircleSize, endCircleSize, false);
                animateSquareViewGrowth(finalLoadingCenter, startFinalCenterSize, endFinalCenterSize, true);
            }
        });

        unfadingAnimator.start();
    }
}
