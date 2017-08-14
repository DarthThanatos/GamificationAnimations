package com.example.vobis.gamificationanimations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.vobis.gamificationanimations.circleanimation.LaunchActivity;
import com.example.vobis.gamificationanimations.homeanimation.HomeActivity;
import com.example.vobis.gamificationanimations.listanimation.ListAnimationActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Log.d(TAG, "yo");
    }
}
