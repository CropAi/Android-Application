package com.boss.cropanalyzerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnBoardActivity extends AppCompatActivity {

    private OnBoardAdapter onBoardAdp;
    private LinearLayout layoutIndicator;
    private MaterialButton nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        layoutIndicator = (LinearLayout)findViewById(R.id.linearIndicator);
        nextButton = (MaterialButton)findViewById(R.id.btnAction);
        setupOnboardingItems();
        final ViewPager2 onBoardingViewPager = findViewById(R.id.viewPager);
        onBoardingViewPager.setAdapter(onBoardAdp);

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onBoardingViewPager.getCurrentItem()+1<onBoardAdp.getItemCount()){
                    onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem()+1);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private void setupOnboardingItems(){
        List<OnBoardItems> onBoardList = new ArrayList<OnBoardItems>();
        OnBoardItems itempayonline1 = new OnBoardItems(R.drawable.arrows,"Upload","Upload Image for Analysis");
        OnBoardItems itempayonline2 = new OnBoardItems(R.drawable.magnifier,"Predict","Initialize Prediction to get results");
        OnBoardItems itempayonline3 = new OnBoardItems(R.drawable.business,"Results","Generate report and suggestion");
        onBoardList.add(itempayonline1);
        onBoardList.add(itempayonline2);
        onBoardList.add(itempayonline3);

        onBoardAdp = new OnBoardAdapter(onBoardList);
    }
}
