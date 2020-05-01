package com.boss.cropanalyzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bindViews();
    }

    private void bindViews() {
        textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("response"));
        // Parse this JSON and inflate views to show the results in a beautiful manner
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "Back Key Pressed");
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }
}
