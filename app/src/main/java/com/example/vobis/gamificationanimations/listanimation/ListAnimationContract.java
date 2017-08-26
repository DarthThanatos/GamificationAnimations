package com.example.vobis.gamificationanimations.listanimation;

import java.util.List;

/**
 * Created by Vobis on 2017-08-14
 */

interface ListAnimationContract {
    interface View{
        void showProgress();
        void hideProgress();
        void animateEntrance(List<FeedItem> feedItems);
    }

    interface Presenter{
        void attachView(View view);
        void detachView();
        void initBusinessLogic();
    }
}
