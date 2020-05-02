package com.boss.cropanalyzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;
import java.util.Map;


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
//        textView.setText(getIntent().getStringExtra("response"));
        String result = getIntent().getStringExtra("response");
        // Parse this JSON and inflate views to show the results in a beautiful manner
        try {
                JSONObject data = new JSONObject(result);

                //getting category
                String category = (String) data.get("category");
                textView.setText("Category : " + category + "\n");
                textView.append("Symptoms : " + "\n");
                //getting symptoms
                Map symptoms = ((Map) data.get("Symptoms"));

                //getting symptoms Map
                Iterator<Map.Entry> itr_sym = symptoms.entrySet().iterator();
                while (itr_sym.hasNext())
                {
                    Map.Entry pair = itr_sym.next();
                    String symptom = pair.getKey() + ":" + pair.getValue() + "\n";
                    textView.append(symptom + "\n");
                }

                textView.append("Treatment : " + "\n");
                //getting treatment
                Map treatment = ((Map) data.get("Treatment"));

                //getting treatment map
                Iterator<Map.Entry> itr_trt = treatment.entrySet().iterator();
                while (itr_trt.hasNext())
                {
                    Map.Entry pair = itr_trt.next();
                    String treat = pair.getKey() + ":" + pair.getValue() + "\n";
                    textView.append(treat + "\n");
                }

                textView.append("Recommended_Product : " + "\n");
                //getting recommended product
                Map recommend = ((Map) data.get("Recommended_Product"));

                //getting recommend map
                Iterator<Map.Entry> itr_rec = recommend.entrySet().iterator();
                while(itr_rec.hasNext())
                {
                    Map.Entry pair = itr_rec.next();
                    String rec_pro = pair.getKey() + ":" + pair.getValue() + "\n";
                    textView.append(rec_pro + "\n");
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "Back Key Pressed");
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }
}
