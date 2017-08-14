package com.example.vobis.gamificationanimations.circleanimation;

/**
 * Created by Vobis on 2017-08-14
 */

interface LaunchContract {
    interface View{
        void displayProgressCircle(float progress);
        void shiftBackgroundAlpha(float progress);
        void displayProgressText(String txtToDisplay);
        void setInitialLoadingView();
        void setFinalLoadingView();
        void animateLoadedSuccessful();
    }

    interface Presenter{
        void attachView(View view);
        void detachView();
        void initBusinessLogic();

    }
}
