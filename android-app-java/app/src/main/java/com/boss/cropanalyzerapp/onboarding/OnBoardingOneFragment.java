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

public class OnBoardingOneFragment extends Fragment {
    TextView textViewNext;
    OnFirstPageClick onFirstPageClick;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFirstPageClick = (OnFirstPageClick) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    private void bindView(View view) {
        textViewNext = view.findViewById(R.id.textViewNext);
        clickListeners();
    }

    private void clickListeners() {
        textViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFirstPageClick.onOption1Next();
            }
        });
    }

    interface OnFirstPageClick {
        void onOption1Next();
    }

}
