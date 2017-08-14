package com.example.vobis.gamificationanimations.circleanimation;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Vobis on 2017-08-14.
 */

class LaunchPresenter implements LaunchContract.Presenter {

    private LaunchContract.View view;

    private Disposable launchingHolderCountDownDisposable;
    private static long ANIMATION_TIME = (5 * 1000 / 10);

    @Override
    public void attachView(LaunchContract.View view) {
        this.view = view;
    }


    private void dispose(Disposable trash){
        if(trash!= null && !trash.isDisposed())
            trash.dispose();
    }

    @Override
    public void detachView() {
        view = null;
        dispose(launchingHolderCountDownDisposable);
    }

    @Override
    public void initBusinessLogic() {
        Log.d(TAG, "started countdown");
        launchingHolderCountDownDisposable = Observable.interval(10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .forEachWhile(this::countDown , Throwable::printStackTrace, this::onEnd);
    }

    private boolean countDown(Long aLong) {
        Log.d(TAG, "Got " + aLong);
        view.displayProgressCircle((float)aLong/ANIMATION_TIME);
        return aLong < ANIMATION_TIME;
    }

    private void onEnd() {
       Log.d(TAG, "completed countdown and disposed timer");
       dispose(launchingHolderCountDownDisposable);
    }
}
