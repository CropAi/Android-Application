package com.boss.cropanalyzerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    TextView textView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bindViews();
        try {
            parsingData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parsingData() throws JSONException {
        // String responseData = "{\"category\":\"Pepper Bell Bacterial Spot\",\"Symptoms\":{\"1\":\"Leaf spots that appear on the lower surface of older leaves as small, pimples and on the upper leaf surface as small water-soaked spots are a symptom of bacterial spot.\",\"2\":\"Eventually, the spots develop gray to tan centers with darker borders. Lesions enlarge during warm, humid weather. Leaves may then turn yellow, then brown and drop. Lesions may also develop on stems.\",\"3\":\"Lesions may also develop on stems. Fruits develop small, raised rough spots that do not affect eating quality.\"},\"Treatment\":{\"1\":\"Treat seeds by soaking them for 2 minutes in a 10% chlorine bleach solution (1 part bleach; 9 parts water). Thoroughly rinse seeds and dry them before planting.\",\"2\":\" Mulch plants deeply with a thick organic material like newspaper covered with straw or grass clippings.\",\"3\":\"Spray every 10-14 days with fixed copper (organic fungicide) to slow down the spread of infection.\",\"4\":\"Rotate peppers to a different location if infections are severe and cover the soil with black plastic mulch or black landscape fabric prior to planting. \"},\"Recommended_Product\":{\"1\":\"Neem Oil : https://www.planetnatural.com/product/organic-neem-oil/\",\"2\":\"Garden Dust : https://www.planetnatural.com/product/garden-dust/\"}}\n";
        String responseData = getIntent().getStringExtra("response");
        Log.e(TAG, responseData);
        // Parse this JSON and inflate views to show the results in a beautiful manner
        JSONObject jsonObject = new JSONObject(responseData);
        String category = jsonObject.getString("category");
        String symptoms = jsonObject.getString("Symptoms");
        String treatment = jsonObject.getString("Treatment");
        String products = jsonObject.getString("Recommended_Product");
        Log.e(TAG, category);
        Log.e(TAG, symptoms);
        Log.e(TAG, treatment);
        Log.e(TAG, products);
        // set the title of toolbar
        textView.setText(category);
    }

    private void bindViews() {
        toolbar = findViewById(R.id.myToolbar);
        textView = toolbar.findViewById(R.id.textViewTitle);
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "Back Key Pressed");
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }
}
