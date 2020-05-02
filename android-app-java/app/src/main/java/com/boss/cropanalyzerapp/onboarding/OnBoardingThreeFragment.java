package com.boss.cropanalyzerapp.onboarding;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boss.cropanalyzerapp.R;

public class OnBoardingThreeFragment extends Fragment {
    TextView textViewBack;
    TextView textViewDone;
    OnThirdPageClick onThirdPageClick;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onThirdPageClick = (OnThirdPageClick) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    private void bindView(View view) {
        textViewBack = view.findViewById(R.id.textViewBack);
        textViewDone = view.findViewById(R.id.textViewDone);
        clickListeners();
    }

    private void clickListeners() {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onThirdPageClick.onOption3Back();
            }
        });
        textViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onThirdPageClick.onOption3Done();
            }
        });
    }


    interface OnThirdPageClick {
        void onOption3Back();

        void onOption3Done();
    }
}
