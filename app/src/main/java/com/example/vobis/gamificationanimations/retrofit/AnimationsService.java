package com.example.vobis.gamificationanimations.retrofit;

import com.example.vobis.gamificationanimations.listanimation.FeedContainer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vobis on 2017-08-14
 */

public interface AnimationsService {

    @GET("/")
    Observable<FeedContainer> getFeedItems(@Query("json") String json, @Query("slug") String slug, @Query("count") int count);

}
