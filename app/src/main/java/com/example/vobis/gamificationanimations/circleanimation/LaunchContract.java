package com.example.vobis.gamificationanimations.circleanimation;

/**
 * Created by Vobis on 2017-08-14.
 */

interface LaunchContract {
    interface View{
        void displayProgressCircle(float progress);
    }

    interface Presenter{
        void attachView(View view);
        void detachView();
        void initBusinessLogic();

    }
}
