package com.example.vobis.gamificationanimations.circleanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vobis.gamificationanimations.R;

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
    public void shiftBackgroundsAlphas(float progress) {
//        launchContainer.getBackground().setAlpha((int) (255 * (1 - progress)));
        backgroundLoaded.setAlpha(255 * progress);
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

    @Override
    public void animateLoadedSuccessful() {

    }
}
