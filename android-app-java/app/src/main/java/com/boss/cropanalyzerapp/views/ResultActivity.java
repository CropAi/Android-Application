package com.boss.cropanalyzerapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.boss.cropanalyzerapp.decorators.DividerItemDecorator;
import com.boss.cropanalyzerapp.R;
import com.boss.cropanalyzerapp.adapters.ResultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";
    TextView textView;
    Toolbar toolbar;
    ArrayList<String> symptoms, treatments, products;
    RecyclerView recyclerViewSymptoms, recyclerViewTreatments, recyclerViewProducts;
    RecyclerView.ItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bindViews();
        try {
            parsingData();
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void parsingData() throws JSONException {
        // String responseData = "{\"category\":\"Pepper Bell Bacterial Spot\",\"Symptoms\":{\"1\":\"Leaf spots that appear on the lower surface of older leaves as small, pimples and on the upper leaf surface as small water-soaked spots are a symptom of bacterial spot.\",\"2\":\"Eventually, the spots develop gray to tan centers with darker borders. Lesions enlarge during warm, humid weather. Leaves may then turn yellow, then brown and drop. Lesions may also develop on stems.\",\"3\":\"Lesions may also develop on stems. Fruits develop small, raised rough spots that do not affect eating quality.\"},\"Treatment\":{\"1\":\"Treat seeds by soaking them for 2 minutes in a 10% chlorine bleach solution (1 part bleach; 9 parts water). Thoroughly rinse seeds and dry them before planting.\",\"2\":\" Mulch plants deeply with a thick organic material like newspaper covered with straw or grass clippings.\",\"3\":\"Spray every 10-14 days with fixed copper (organic fungicide) to slow down the spread of infection.\",\"4\":\"Rotate peppers to a different location if infections are severe and cover the soil with black plastic mulch or black landscape fabric prior to planting. \"},\"Recommended_Product\":{\"1\":\"Neem Oil : https://www.planetnatural.com/product/organic-neem-oil/\",\"2\":\"Garden Dust : https://www.planetnatural.com/product/garden-dust/\"}}\n";
        String responseData = getIntent().getStringExtra("response");
        // Parse this JSON and inflate views to show the results in a beautiful manner
        JSONObject jsonObject = new JSONObject(responseData);
        // set the title of toolbar
        String category = jsonObject.getString("category");
        textView.setText(category);
        // populate symptoms arrayList using these objects
        JSONObject symptomsObject = jsonObject.getJSONObject("Symptoms");
        populateList(symptomsObject, symptoms);
        // populate treatments arrayList using these objects
        JSONObject treatmentsObject = jsonObject.getJSONObject("Treatment");
        populateList(treatmentsObject, treatments);
        // populate products arrayList using these objects
        JSONObject productsObject = jsonObject.getJSONObject("Recommended_Product");
        populateList(productsObject, products);
        // use these list to set up adapter and fill the recycler views
        initRecyclerView();
    }

    private void initRecyclerView() {
        ResultAdapter resultAdapterSymptoms = new ResultAdapter(this, symptoms);
        ResultAdapter resultAdapterTreatments = new ResultAdapter(this, treatments);
        ResultAdapter resultAdapterProducts = new ResultAdapter(this, products);
        recyclerViewSymptoms.setAdapter(resultAdapterSymptoms);
        recyclerViewTreatments.setAdapter(resultAdapterTreatments);
        recyclerViewProducts.setAdapter(resultAdapterProducts);
    }

    private void populateList(JSONObject jsonObject, ArrayList<String> list) throws JSONException {
        int key = 1;
        while (true) {
            if (jsonObject.isNull(String.valueOf(key))) {
                break;
            }
            // get the value corresponding to valid key
            String str = jsonObject.getString(String.valueOf(key));
            // Add it to the input arrayList
            list.add(str);
            // increment the key
            key += 1;
        }
    }

    private void bindViews() {
        toolbar = findViewById(R.id.myToolbar);
        textView = toolbar.findViewById(R.id.textViewTitle);
        setSupportActionBar(toolbar);
        recyclerViewSymptoms = findViewById(R.id.recyclerViewSymptoms);
        recyclerViewTreatments = findViewById(R.id.recyclerViewTreatments);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewSymptoms.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewTreatments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dividerItemDecoration = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerViewSymptoms.addItemDecoration(dividerItemDecoration);
        recyclerViewTreatments.addItemDecoration(dividerItemDecoration);
        recyclerViewProducts.addItemDecoration(dividerItemDecoration);
        symptoms = new ArrayList<>();
        treatments = new ArrayList<>();
        products = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "Back Key Pressed");
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }
}
