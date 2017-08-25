package com.example.vobis.gamificationanimations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.annimon.stream.Stream;
import com.example.vobis.gamificationanimations.circleanimation.LaunchActivity;
import com.example.vobis.gamificationanimations.homeanimation.HomeActivity;
import com.example.vobis.gamificationanimations.listanimation.ListAnimationActivity;
import com.example.vobis.gamificationanimations.websockets_example.WebSocketActivity;
import com.example.vobis.gamificationanimations.websockets_okhttp.WebsocketOkHttpActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> list = Arrays.asList("Yo", "Null", "Robo");
        Log.d(TAG, "Hello world");
        Stream<String> stream = Stream.of(list)
                .filter(value -> !value.equals("Null"));
        Log.d(TAG, "stream count: " + stream.count());
        stream.forEach(s -> Log.d(TAG, "-> " + s));
    }

    public void goToLaunchAnimation(View view) {
        Intent intent = new Intent(MainActivity.this, LaunchActivity.class);
        startActivity(intent);
    }

    public void goToListAnimation(View view) {
        Intent intent = new Intent(MainActivity.this, ListAnimationActivity.class);
        startActivity(intent);
    }

    public void goToHomeAnimation(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void goToWebsocket(View view) {
        Intent intent = new Intent(MainActivity.this, WebSocketActivity.class);
        startActivity(intent);
    }

    public void goToOkWebsocket(View view) {
        Intent intent = new Intent(MainActivity.this, WebsocketOkHttpActivity.class);
        startActivity(intent);
    }
}
