package com.example.vobis.gamificationanimations.retrofit;

/**
 * Created by Vobis on 2017-08-14
 */

import com.example.vobis.gamificationanimations.config.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitProvider {

    private AnimationsService animationsService;

    public RetrofitProvider() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        animationsService = retrofit.create(AnimationsService.class);
    }

    public AnimationsService getAnimationsService() {
        return animationsService;
    }
}