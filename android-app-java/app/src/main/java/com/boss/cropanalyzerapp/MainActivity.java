package com.boss.cropanalyzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //declaring variables for all the views
    Button button;
    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }
    
    private void bindView(){
        button = findViewById(R.id.button);
        imageView=findViewById(R.id.image_view);
}
