package com.boss.cropanalyzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button upload_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upload_image = (Button)findViewById(R.id.buttonUpload);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindView();
            }
        });
    }
    public void bindView(){

    }
}
