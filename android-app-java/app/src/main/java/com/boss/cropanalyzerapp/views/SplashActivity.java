package com.boss.cropanalyzerapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.boss.cropanalyzerapp.R;
import com.boss.cropanalyzerapp.onboarding.OnBoardingActivity;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.e(TAG, "Inside Splash");
        setUpSharedPreferences();
        checkFirstTimeStatus();
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences("first-time", Context.MODE_PRIVATE);
    }

    private void checkFirstTimeStatus() {
        boolean firstTime = sharedPreferences.getBoolean("firstTime", true);
        Log.e(TAG, String.valueOf(firstTime));
        if (firstTime) {
            // launch on boarding
            startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
            finish();
        } else {
            // launch main activity
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }
}
