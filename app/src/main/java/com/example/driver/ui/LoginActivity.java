package com.example.driver.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.driver.HomeActivity;
import com.example.driver.R;
import com.example.driver.database.SqliteDatabase;
import com.example.driver.driverRegister;
import com.example.driver.helper.Util;
import com.example.driver.model.LoginDetails;
import com.example.driver.network.InternetConnection;
import com.example.driver.network.RestClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    TextInputEditText edtMobile;
    TextInputLayout edtPassword;
    MaterialButton submitBtn;
    MaterialTextView register;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        init();
    }
    public   void init(){
        edtMobile=findViewById(R.id.mobile);
        edtPassword=findViewById(R.id.password);

        submitBtn=findViewById(R.id.submitBtn);
        register=findViewById(R.id.register);
        register.setOnClickListener(v->{
            Intent mv=new Intent(LoginActivity.this, driverRegister.class);
            startActivity(mv);
        });
        submitBtn.setOnClickListener(v->{
            validate();
        });
    }

    private void validate(){
        if(edtMobile.getText().length()<=0){
            Util.show(this,"Please enter mobile");
            return;
        }
        if(edtPassword.getEditText().getText().length()<=0){
            Util.show(this,"Please enter password");
            return;
        }
        login();
    }

    public void login(){

        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();
            params.put("number", edtMobile.getText().toString());
            params.put("password", edtPassword.getEditText().getText().toString());
            params.put("type", "3");
            Log.e("data",params.toString());

            //
            Util.showDialog("Please wait..",mContext);
            // Calling JSON
            Call<LoginDetails> call = RestClient.post().userLogin("1234","1",params);

//            Call<String> call2 = RestClient.post().getOrderList("1234","1",params);

            // Enqueue Callback will be call when get response...
            call.enqueue(new Callback<LoginDetails>() {
                @Override
                public void onResponse(@NonNull Call<LoginDetails> call, @NonNull Response<LoginDetails> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        try {
                            LoginDetails obj = response.body();
                            Log.e("data", obj.getMessage());
                            if(obj.getStatus()==true){
                                Util.show(mContext,obj.getMessage());
                                new SqliteDatabase(mContext).addLogin(obj);
                                startActivity(new Intent(mContext, HomeActivity.class));
                                finish();
                                Util.hideDialog();
                            }else{
                                Util.show(mContext,obj.getMessage());
                                Util.hideDialog();
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                            Util.hideDialog();
                        }

                    } else {
                        try{
                            Toast.makeText(mContext, "error message"+response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        }catch(IOException e){
                            Toast.makeText(mContext, "error message"+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Util.hideDialog();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginDetails> call, @NonNull Throwable t) {
                    Toast.makeText(mContext, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                }

            });
        } else {

            Toast.makeText(mContext, R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }





}