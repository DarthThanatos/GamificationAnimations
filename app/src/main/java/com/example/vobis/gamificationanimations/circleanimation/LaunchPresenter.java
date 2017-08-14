package com.example.vobis.gamificationanimations.circleanimation;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Vobis on 2017-08-14
 */

class LaunchPresenter implements LaunchContract.Presenter {

    private LaunchContract.View view;

    private Disposable launchingHolderCountDownDisposable;
    private static final long ANIMATION_TIME = (5 * 1000 / 10);

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
        view.setInitialLoadingView();
        launchingHolderCountDownDisposable = Observable.interval(10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .forEachWhile(this::countDown , Throwable::printStackTrace, this::onEnd);
    }

    @Override
    public void performAPICheck() {

    }

    private boolean countDown(Long aLong) {
        float interpolatedTime = (float)aLong/ANIMATION_TIME;
        view.displayProgressCircle(interpolatedTime);
        int val = (int)(interpolatedTime * 100);
        String txtToDisplay = (val < 10 ? "0" : "") +  Integer.toString(val);
        view.displayProgressText(txtToDisplay);
        view.shiftBackgroundAlpha(interpolatedTime);
        return aLong < ANIMATION_TIME;
    }

    private void onEnd() {
        view.setFinalLoadingView();
        view.animateLoadedSuccessful();
        dispose(launchingHolderCountDownDisposable);
    }
}
