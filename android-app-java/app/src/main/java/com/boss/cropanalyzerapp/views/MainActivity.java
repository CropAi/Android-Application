package com.boss.cropanalyzerapp.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.boss.cropanalyzerapp.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 101;
    private static final int RESET_VIEWS = 102;
    private static final String IP = "http://192.168.1.4:3000/file_upload";
    private static final String CROP_LEAF_URL = "https://crop-leaf.herokuapp.com/file_upload";
    String image_path = "";
    String TAG = "MainActivity";
    Boolean userSelectedImage = false;

    ProgressDialog progressDialog;
    Button analyzeButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        clickListeners();
    }

    private void clickListeners() {
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, String.valueOf(userSelectedImage));
                if (userSelectedImage) {
                    // Make Network Request
                    getLeafCategory();
                } else {
                    Toast.makeText(MainActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, String.valueOf(userSelectedImage));
                if (userSelectedImage) {
                    // Make Network Request
                    getLeafCategory();
                } else {
                    Toast.makeText(MainActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", String.valueOf(userSelectedImage));
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, requesting permission
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        uploadImage();
                    }
                } else {
                    // system os is less than marshmallow
                    uploadImage();
                }
            }
        });
    }

    private void getLeafCategory() {
        Log.e(TAG, "Inside Leaf category");

        File file = new File(image_path);
        final String file_path = file.getAbsolutePath();
        // extension can be .png/.PNG/.jpg/.JPG/.jpeg/.JPEG
        String extension = file_path.substring(file_path.length() - 3);
        Log.e(TAG, extension);
        if (extension.equals("png") || extension.equals("PNG")) {
            makeRequest("png");
        } else if (extension.equals("jpg") || extension.equals("JPG")) {
            makeRequest("jpg");
        } else if (extension.equals("peg") || extension.equals("PEG")) {
            makeRequest("jpeg");
        } else {
            Toast.makeText(MainActivity.this, "Incorrect File Format", Toast.LENGTH_SHORT).show();
        }

    }

    private void makeRequest(String extension) {

        Log.e(TAG, "Inside make request");

        // Disable the button for multiple requests

        analyzeButton.setClickable(false);
        analyzeButton.setAlpha(.5f);
        imageView.setClickable(false);

        // make a loader on the main thread
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Analyzing");
        progressDialog.setProgress(30);//initially progress is 30 seconds
        progressDialog.setCancelable(false);
        progressDialog.show();

        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                progressDialog.setMessage(millisUntilFinished / 1000 + " SECONDS REMAINING");
            }

            public void onFinish() {
                progressDialog.setMessage("ALMOST THERE");
            }
        }.start();

        File file = new File(image_path);
        final String file_path = file.getAbsolutePath();

        RequestBody file_body = RequestBody.create(MediaType.parse("image/" + extension), file);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                .readTimeout(1, TimeUnit.MINUTES); // read timeout

        OkHttpClient client = builder.build();

        RequestBody request_body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                .build();

        Request request = new Request.Builder()
                .url(CROP_LEAF_URL)
                .post(request_body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // Hide Progress dialog
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                        resetViews();
                    }
                });
                Log.e(TAG, "ERROR:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Hide Progress dialog
                progressDialog.setMessage("DONE");
                progressDialog.dismiss();
                String jsonResponse = response.body().string();
                Log.e(TAG, "RESPONSE:" + jsonResponse);
                launchResultActivity(jsonResponse);
            }
        });
    }

    // Launch result activity
    private void launchResultActivity(String jsonResponse) {
        // start new activity and send JSON to this activity
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("response", jsonResponse);
        // we will reset the imageView in OnActivityResult
        startActivityForResult(intent, RESET_VIEWS);
    }

    public void bindViews() {
        analyzeButton = findViewById(R.id.buttonAnalyze);
        imageView = findViewById(R.id.imageView);
    }

    private void uploadImage() {
        //intent to upload image
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handling request of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadImage();
                } else {
                    //permission denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // handling result of pick image and going back from result activity to main activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_PICK_CODE: {
                    // Log.e(TAG, data.getData().getPath().split(":")[1]); -- this log statement is producing a bug, so commenting out
                    imageView.setImageURI(data.getData());
                    userSelectedImage = true;

                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Log.e(TAG, filePath.toString());
                    // content resolver helps us to get the access of different content providers
                    // content resolver helps us to get the access of different content providers
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePath, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndexedValue = cursor.getColumnIndex(filePath[0]);
                        String picturePath = cursor.getString(columnIndexedValue);
                        cursor.close();
                        image_path = picturePath;
                        Log.e(TAG, "PIC PATH :" + picturePath);
                        Glide.with(this).load(picturePath).into(imageView);
                    }
                    break;
                }
                case RESET_VIEWS: {
                    // reset the boolean variable and imageView
                    Log.e(TAG, "Back Inside Main Activity");
                    resetViews();
                    break;
                }
            }

        }
    }

    private void resetViews() {
        userSelectedImage = false;
        imageView.setImageResource(R.drawable.ic_leaf);
        // reset the upload button
        analyzeButton.setClickable(true);
        analyzeButton.setAlpha(1f);
        // reset the imageView
        imageView.setClickable(true);
    }
}