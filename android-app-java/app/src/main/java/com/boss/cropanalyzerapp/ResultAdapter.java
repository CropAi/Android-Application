package com.boss.cropanalyzerapp;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {
    String TAG = "ResultAdapter";
    Context context;
    ArrayList<String> arrayList;

    ResultAdapter(Context context, ArrayList<String> list) {
        this.arrayList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_row, parent, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        String string = arrayList.get(position);
        if (string.contains("https")) {
            Log.e(TAG, string);
            int start = string.indexOf("https");
            String name = string.substring(0, start).split(":")[0].trim();
            String link = string.substring(start);
            try {
                URL url = new URL(link);
                Log.e(TAG, String.valueOf(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();
            float dp = 6f;
            float fPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, metrics);
            int pixels = Math.round(fPixels);
            holder.textView.setTextSize(pixels);
            holder.textView.setClickable(true);
            holder.textView.setMovementMethod(LinkMovementMethod.getInstance());

            String source = "<a href='" + link + "'>" + name + " </a > ";
            Log.e(TAG, source);
            holder.textView.setText(HtmlCompat.fromHtml(source, 0));
        } else {
            holder.textView.setText(string);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ResultHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
