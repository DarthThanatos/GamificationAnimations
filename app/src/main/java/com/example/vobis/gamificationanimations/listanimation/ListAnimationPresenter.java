package com.example.vobis.gamificationanimations.listanimation;

import android.util.Log;

import com.annimon.stream.Stream;
import com.example.vobis.gamificationanimations.retrofit.RetrofitProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Vobis on 2017-08-14
 */

public class ListAnimationPresenter implements ListAnimationContract.Presenter {

    private static final String TAG = ListAnimationPresenter.class.getSimpleName();
    private ListAnimationContract.View view;
    private Disposable networkingDisposable;


    private void dispose(Disposable trash){
        if(trash!= null && !trash.isDisposed())
            trash.dispose();
    }

    @Override
    public void attachView(ListAnimationContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        dispose(networkingDisposable);
    }

    @Override
    public void initBusinessLogic() {
        view.showProgress();
        networkingDisposable = new RetrofitProvider()
                .getAnimationsService()
                .getFeedItems("get_category_posts","news", 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::feedView, Throwable::printStackTrace);
    }

    private void feedView(FeedContainer feedContainer) {
        Stream.of(feedContainer.getPosts()).forEach(feedItem -> Log.d(TAG, feedItem.toString()));
        view.hideProgress();
        view.displayFeedItems(feedContainer.getPosts());
    }
}
