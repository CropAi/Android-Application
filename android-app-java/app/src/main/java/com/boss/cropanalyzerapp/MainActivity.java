package com.boss.cropanalyzerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button uploadbtn;

    private static int IMAGE_PICK_CODE = 1000;
    private static int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewByID(R.id.imageView1);
        uploadbtn = (Button)findViewByID(R.id.buttonUpload);

        uploadbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //checking runtime permission

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
                    //system os is less than marshmallow
                    uploadImage();
                }
            }
        });
    }

    private void uploadImage(){
        //intent to upload image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handling request of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    uploadImage();
                }
                else{
                    //permission denied
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handling result og upload image
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            String TAG = "Image Location";
            Log.e(TAG, String.valueOf(data.getData())); // logging the path of image's location
            imageView.setImageURI(data.getData());
        }
    }
}
