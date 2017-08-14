package com.example.vobis.gamificationanimations.circleanimation;

/**
 * Created by Vobis on 2017-08-14.
 */

public class LaunchPresenter implements LaunchContract.Presenter {

    private LaunchContract.View view;

    @Override
    public void attachView(LaunchContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void initBusinessLogic() {

    }
}
