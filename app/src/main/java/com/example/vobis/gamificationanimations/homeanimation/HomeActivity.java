package com.example.vobis.gamificationanimations.homeanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vobis.gamificationanimations.R;
import com.example.vobis.gamificationanimations.commonviews.ActivitySwitcher;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onResume(){
        ActivitySwitcher.animationIn(findViewById(R.id.container), getWindowManager());
        super.onResume();
    }
}
