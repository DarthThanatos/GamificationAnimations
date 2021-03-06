package com.example.vobis.gamificationanimations.config;

/**
 * Created by Vobis on 2017-08-14
 */

public class Config {
    public static final String ENDPOINT = "http://stacktips.com";
    public static final int NORMAL_CLOSURE_STATUS = 1000;
    public static final int EVERY_TEN_MILLIS = 10;
    private static final int TIME_UNIT = 1000;
    public static final int GROWING_ANIMATION_TIME = TIME_UNIT;
    public static final int UNFADING_ANIMATION_TIME = TIME_UNIT;
    public static final int ENTRANCE_ANIMATION_TIME = (int) (0.2 * TIME_UNIT);
}
