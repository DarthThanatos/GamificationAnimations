package com.example.vobis.gamificationanimations.circleanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vobis.gamificationanimations.R;

public class LaunchActivity extends AppCompatActivity implements LaunchContract.View{

    private LaunchContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
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

    }
}
