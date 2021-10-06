package com.example.driver.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driver.R;
import com.example.driver.database.SqliteDatabase;
import com.example.driver.helper.Util;
import com.example.driver.model.LoginDetails;
import com.example.driver.model.order.CustomerOrderDetails;
import com.example.driver.model.order.OrderParam;
import com.example.driver.network.InternetConnection;
import com.example.driver.network.RestClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderView extends AppCompatActivity {
    CustomerOrderDetails data;
    Context mContext;
    LoginDetails loginDetails;
    TextView tvShopMobile,tvShopName,tvShopAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        loginDetails = new SqliteDatabase(this).getLogin();
        data= (CustomerOrderDetails) getIntent().getSerializableExtra("data");
        init();
    }

    void  init(){

    }
//    void init(){
//        tvShopName=findViewById(R.id.tvShopName);
//        tvShopAddress=findViewById(R.id.tvShopAddress);
//        tvShopMobile=findViewById(R.id.tvShopMobile);
//        MaterialTextView tvOrder= findViewById(R.id.tvOrder);
//        MaterialTextView vendorName= findViewById(R.id.vendorName);
//        TextView payMode= findViewById(R.id.payMode);
//        TextView toAmt= findViewById(R.id.toAmt);
//        TextView instructionList= findViewById(R.id.instructionList);
//        TextView tvCustomerName= findViewById(R.id.tvCustomerName);
//        TextView tvCustomerMobile=findViewById(R.id.tvCustomerMobile);
//        TextView tvCustomerAddress=findViewById(R.id.tvCustomerAddress);
//        AppCompatImageView closeImg=findViewById(R.id.tvClose);
//
//        TextInputLayout enterAmount=findViewById(R.id.enterAmt);
//        TextInputLayout  enterOtp=findViewById(R.id.enterOtp);
//
//        OrderParam param=data.getOrderDetails();
//
//        if(param.getPaymentModeId().equalsIgnoreCase("5")) { enterAmount.setVisibility(View.VISIBLE); }else{ enterAmount.setVisibility(View.GONE);}
//        MaterialButton btnDelivered=findViewById(R.id.btnDelivered);
//        tvOrder.setText(param.getOrderNumber());
//        payMode.setText(param.getPaymode());
//        toAmt.setText(param.getTotal());
//        if(data.getInstruction().size()==0){
//            instructionList.setText("No Instruction found");
//        }else{
//            instructionList.setText(data.getInstruction().toString());
//        }
//
//        tvCustomerName.setText(param.getCustomerName());
//        tvCustomerMobile.setText(param.getCustomerMobile());
//        vendorName.setText(param.getVendorName());
//        tvCustomerAddress.setText(param.getAddress());
//        tvShopName.setText(param.getShop_name());
//        tvShopAddress.setText(param.getShopaddress());
//        tvShopMobile.setText(param.getVendorNumber());
//
//        btnDelivered.setOnClickListener(v->{
//
//            if(enterOtp.getEditText().getText().length()<4){
//                Util.show(this,"Please enter otp");
//                return;
//
//            }
//
//            if(enterAmount.getEditText().length()<=0){
//                Util.show(this,"Please enter amount");
//                return;
//
//            }
//            int entAmt=Integer.parseInt(enterAmount.getEditText().getText().toString());
//            int getTotalAmt=Integer.parseInt(param.getTotalPrice());
//            if(entAmt>getTotalAmt || entAmt<getTotalAmt){
//                Util.show(this,"Please enter valid amount");
//                return;
//
//            }
//
//
//            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE:
//                        orderAccept(data);
//                        break;
//
//                    case DialogInterface                                                                                             .BUTTON_NEGATIVE:
//                        //No button clicked
//                        break;
//                }
//            };
//            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
//            builder.setMessage("Do you want to logout from the app?").setPositiveButton("Yes", dialogClickListener)
//                    .setNegativeButton("No", dialogClickListener).show();
//
//
//        });
//    }


    public void orderAccept(CustomerOrderDetails data) {
        if (InternetConnection.checkConnection(mContext)) {

            OrderParam orderParam = data.getOrderDetails();
            Util.showDialog("Please wait..", mContext);
            Map<String, String> params = new HashMap<>();
            String jsonString = new Gson().toJson(data.getOrderIds());
            params.put("orders_detail_id", jsonString.replace("[", "").replace("]", "").replace("\"", ""));
            //    params.put("vendor_id", String.valueOf(orderDetails.getAssignedUserId()));
            params.put("remark", " is successfully delivered");
            params.put("orders_id", orderParam.getOrdersId());
            params.put("tokens", data.getOrderDetails().getCustomerId());
            params.put("status", "8");
            Log.e("g", params.toString());
            // Calling JSON

            Call<String> call = RestClient.post().orderAccept("1234", loginDetails.getSk(), params);

            // Enqueue Callback will be call when get response...
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;


                        try {


                            Gson gson = new Gson();
                            JSONObject object = new JSONObject(response.body());

                            if (object.getBoolean("status")) {


                                Util.show(mContext, object.getString("message"));
                                Util.hideDialog();

                            } else {
                                Util.show(mContext, object.getString("message"));
                                Util.hideDialog();

                            }


                        } catch (Exception e) {
                            Util.show(mContext, e.getMessage());
                            e.printStackTrace();
                            Util.hideDialog();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Util.hideDialog();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(mContext, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                }

            });
        } else {

            Toast.makeText(mContext, R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }

}