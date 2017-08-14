package com.example.vobis.gamificationanimations.retrofit;

import com.example.vobis.gamificationanimations.listanimation.FeedItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vobis on 2017-08-14
 */

public interface AnimationsService {

    @GET("category_posts")
    Observable<List<FeedItem>> getFeedItems(@Query("slug") String slug, @Query("count") int count);

}
