package com.example.vobis.gamificationanimations.listanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.vobis.gamificationanimations.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAnimationActivity extends AppCompatActivity implements ListAnimationContract.View {

    ListAnimationContract.Presenter presenter;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_animation);
        ButterKnife.bind(this);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new ListAnimationPresenter();
        presenter.attachView(this);
        presenter.initBusinessLogic();
    }


    @Override
    public void onDestroy(){
        if(presenter!=null) presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void displayFeedItems(List<FeedItem> feedItems) {
        ListAnimationViewAdapter listAnimationViewAdapter = new ListAnimationViewAdapter(this, feedItems);
        recyclerView.setAdapter(listAnimationViewAdapter);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
