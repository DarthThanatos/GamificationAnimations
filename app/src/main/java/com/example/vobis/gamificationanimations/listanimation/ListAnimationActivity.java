package com.example.vobis.gamificationanimations.listanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.vobis.gamificationanimations.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAnimationActivity extends AppCompatActivity implements ListAnimationContract.View {

    private static final String TAG = ListAnimationActivity.class.getSimpleName();
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
        ((ListAnimationViewAdapter)recyclerView.getAdapter()).disposeAll();
        super.onDestroy();
    }

    @Override
    public void displayFeedItems(List<FeedItem> feedItems) {
        ListAnimationViewAdapter listAnimationViewAdapter = new ListAnimationViewAdapter(this, feedItems);
        recyclerView.addOnScrollListener(new OnScrollDirectionChangeListener(listAnimationViewAdapter));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private class OnScrollDirectionChangeListener extends RecyclerView.OnScrollListener {
        private ListAnimationViewAdapter listAnimationViewAdapter;

        OnScrollDirectionChangeListener(ListAnimationViewAdapter listAnimationViewAdapter){
            this.listAnimationViewAdapter = listAnimationViewAdapter;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            listAnimationViewAdapter.reactOnScrollDirectionChange(dy);
        }
    }
}
