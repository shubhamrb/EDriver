package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.driver.database.SqliteDatabase;
import com.example.driver.helper.CameraUtils;
import com.example.driver.helper.ImageGalleryCode;
import com.example.driver.helper.Util;
import com.example.driver.model.LoginDetails;
import com.example.driver.network.ApiService;
import com.example.driver.network.InternetConnection;
import com.example.driver.network.RestClient;
import com.example.driver.ui.LoginActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class driverRegister extends AppCompatActivity {
    TextInputEditText edtName, edtEmail, mobile ;
    MaterialButton submitBtn;
    TextInputLayout password, tvConfirmPassword;
    ImageGalleryCode imageGalleryCode;
    String startDay, endDay, selectImgType, drivinglicensestr="no",image1 = "no", image2 = "no", image3 = "no";
    Context mContext;
    public static final int BITMAP_SAMPLE_SIZE = 0;
    AppCompatImageView selectedImageOne, drivinglicense,selectedImageTwo,selectedImagethree;
    static Bitmap bitmapString, bitmapString2, bitmapString3, bitmapString4;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    public static final int PICK_IMAGE_CHOOSER_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       this.mContext=this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        init();
    }

    private void init() {

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        mobile = findViewById(R.id.mobile);
        selectedImagethree=findViewById(R.id.selectedImagethree);
        password = findViewById(R.id.password);
        tvConfirmPassword = findViewById(R.id.tvConfirmPassword);
        submitBtn = findViewById(R.id.submitBtn);
        selectedImageOne = findViewById(R.id.selectedImageOne);
        selectedImageTwo = findViewById(R.id.selectedImageTwo);
        drivinglicense= findViewById(R.id.drivinglicense);
        submitBtn.setOnClickListener(v -> {


            if(edtName.getText().length()==0){
                Util.show(mContext,"please enter name");
                return;
            }
            if (edtEmail.getText().length() == 0) {
                Util.show(mContext, "please enter email");
                return;
            }
            if(mobile.getText().length()==0 || mobile.getText().length()<10){
                Util.show(mContext,"please enter valid mobile");
                return;
            }
            if(tvConfirmPassword.getEditText().toString().length()==0){
                Util.show(mContext,"please enter confirm password");
                return;
            }
            if(password.getEditText().toString().length()==0){
                Util.show(mContext,"please enter password");
                return;
            }
            if(!password.getEditText().getText().toString().equalsIgnoreCase(tvConfirmPassword.getEditText().getText().toString())){
                Util.show(mContext,"please check your password");
                return;
            }
            userRegister();
        });

        selectedImageOne.setOnClickListener(v -> {
            selectImgType = "1";
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        selectedImagethree.setOnClickListener(v -> {
            selectImgType = "3";
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
        selectedImageTwo.setOnClickListener(v -> {
            selectImgType = "2";
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

        drivinglicense.setOnClickListener(v -> {
            selectImgType = "4";
            ImagePicker.Companion.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                }
                break;
            }

        }

        if (resultCode == RESULT_OK) {
            //Image Uri will not be null for RESULT_OK


            //You can get File object from intent

            //Uri fileUri = data;
            Log.e("dgfdgfd", selectImgType);

            int type = Integer.parseInt(selectImgType);
            Uri compressUri = imageGalleryCode.getPickImageResultUri(mContext, data);
            File file = ImagePicker.Companion.getFile(data);
            Bitmap bitmapString = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, file.getAbsolutePath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapString.compress(Bitmap.CompressFormat.JPEG, 45, baos);
            if (type == 1) {
                selectedImageOne.setImageResource(0);
                byte[] imageBytes = baos.toByteArray();
                selectedImageOne.setImageBitmap(bitmapString);
                image1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                selectImgType = "";
            } else if (type == 2) {
                selectedImageTwo.setImageResource(0);
                byte[] imageBytes = baos.toByteArray();
                selectedImageTwo.setImageBitmap(bitmapString);
                image2 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                selectImgType = "";
            }else if (type == 3) {
                selectedImagethree.setImageResource(0);
                byte[] imageBytes = baos.toByteArray();
                selectedImagethree.setImageBitmap(bitmapString);
                image3 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                selectImgType = "";
            }else if (type == 4) {
                drivinglicense.setImageResource(0);
                byte[] imageBytes = baos.toByteArray();
                drivinglicense.setImageBitmap(bitmapString);
                drivinglicensestr = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                selectImgType = "";
            }


            //You can get File object from intent

            //You can also get File Path from intent
            //String filePath = ImagePicker.Companion.getFilePath(data);


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mContext, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }


    }


    private void userRegister() {
        if (InternetConnection.checkConnection(mContext)) {
             Util.showDialog("Please wait..",mContext);
            Map<String, String> params = new HashMap<>();
            params.put("name", edtName.getText().toString());
            params.put("number", mobile.getText().toString());
            params.put("email", edtEmail.getText().toString());
            params.put("password", tvConfirmPassword.getEditText().getText().toString());
            params.put("pencard", image1);
            params.put("photo", image3);
            params.put("type_id", "3");
            params.put("adharcard", image2);
            params.put("license", drivinglicensestr);
            Log.e("vendor_id", params.toString());
            RestClient.post().register(ApiService.APP_DEVICE_ID, "", params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message", object.getString("message"));

                        if (object.getBoolean("status")) {

                            Util.show(mContext, "Successfully resgiter your setting data");
Intent mv=new Intent(driverRegister.this, LoginActivity.class);
startActivity(mv);

                               Util.hideDialog();
                        } else {
                                 Util.hideDialog();
                            Util.show(mContext, object.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //  Util.hideDialog();
                        Util.hideDialog();
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                    Util.hideDialog();
                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //   Util.hideDialog();
                }
            });

        }
    }
}