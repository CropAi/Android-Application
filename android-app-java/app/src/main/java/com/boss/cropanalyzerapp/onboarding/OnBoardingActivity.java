package com.boss.cropanalyzerapp.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.boss.cropanalyzerapp.R;
import com.boss.cropanalyzerapp.views.MainActivity;

public class OnBoardingActivity extends AppCompatActivity implements OnBoardingOneFragment.OnFirstPageClick, OnBoardingSecondFragment.OnSecondPageClick, OnBoardingThreeFragment.OnThirdPageClick {
    private static final String TAG = "OnBoardingActivity";
    ViewPager viewPager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        bindViews();
        setUpSharedPreferences();
        Log.e(TAG, "Inside OnBoarding");
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences("first-time", Context.MODE_PRIVATE);
    }

    private void bindViews() {
        viewPager = findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
    }

    @Override
    public void onOption1Next() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onOption2Back() {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onOption2Next() {
        viewPager.setCurrentItem(2);
    }

    @Override
    public void onOption3Back() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onOption3Done() {
        sharedPreferences.edit().putBoolean("firstTime", false).apply();
        startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
        finish();
    }
}

// splash -> onBoard -> Main (1st time users)
// splash -> Main (>1 time users)
