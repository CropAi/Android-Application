package com.boss.cropanalyzerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OnBoardAdapter  extends RecyclerView.Adapter<OnBoardAdapter.OnboardingViewHolder> {
    private List<OnBoardItems> onBoardingItems;
    public OnBoardAdapter(List<OnBoardItems> onBoardingItems){
        this.onBoardingItems = onBoardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_onboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onBoardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onBoardingItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDesc;
        private ImageView imagOnBoarding;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDescription);
            imagOnBoarding = itemView.findViewById(R.id.onboard_img);
        }

        public void setOnboardingData(OnBoardItems onboardingData){
            textTitle.setText(onboardingData.getTitle());
            textDesc.setText(onboardingData.getDescription());
            imagOnBoarding.setImageResource(onboardingData.getImgRes());
        }
    }
}