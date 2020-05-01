package com.boss.cropanalyzerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static boolean IMAGE_CHECK = false;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 101;
    String TAG = "MainActivity";
    String image_path = "";

    Button analyzeButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IMAGE_CHECK) {
                    Toast.makeText(getApplicationContext(), "No File Selected", Toast.LENGTH_SHORT).show();
                } else {

                    // Make network request here and launch result activity on getting response
                    // pass the response in intent to the result activity
                    Intent intent = new Intent(MainActivity.this , ResultActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void bindView() {
        analyzeButton = findViewById(R.id.buttonAnalyze);
        imageView = findViewById(R.id.imageView);
    }

    private void uploadImage() {
        //intent to upload image
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_CODE);
        IMAGE_CHECK = true;
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

    //handling result of pick image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Log.e(TAG, data.getData().toString());
            //  We will pass the Uri , Get Path of the image
            imageView.setImageURI(data.getData());
            image_path = getUrlPath(data.getData());
        }
    }

    public String getUrlPath(Uri path) {
        String picturePath = "";
        Uri selectedImage = path;
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(selectedImage, filePath, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndexedValue = cursor.getColumnIndex(filePath[0]);
            picturePath = cursor.getString(columnIndexedValue);
            cursor.close();
        }
        Log.e(TAG, picturePath);
        return picturePath;
    }
}
