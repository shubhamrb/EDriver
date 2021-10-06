package com.example.driver.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.driver.HomeActivity;
import com.example.driver.R;
import com.example.driver.adapter.order.OrderCustomerAdapter;
import com.example.driver.adapter.order.OrderInstruction;
import com.example.driver.adapter.order.OrderLog;
import com.example.driver.adapter.order.OrdersitemAdapter;
import com.example.driver.database.SqliteDatabase;
import com.example.driver.helper.Util;
import com.example.driver.model.LoginDetails;
import com.example.driver.model.order.Instruction;
import com.example.driver.model.order.InstructionLog;
import com.example.driver.model.order.OrderParam;
import com.example.driver.model.order.OrderProduct;
import com.example.driver.model.order.Orders;
import com.example.driver.network.InternetConnection;
import com.example.driver.network.RestClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends AppCompatActivity implements OrderInstruction.OnMeneuClickListnser,OrdersitemAdapter.OnMeneuClickListnser,OrderLog.OnMeneuClickListnser{


    LoginDetails login;
    OrderParam orderDetails;
    RecyclerView itemItemCustomer, orderLogList, instructionList;

    ArrayList<OrderProduct> orderProductsList;
    ArrayList<InstructionLog> instructionLogs;
    ArrayList<Instruction> instructions;

    Context mContext;
    OrderDetails listenerContext;
    OrderInstruction instructionAdapter;
    OrderLog instructionLogAdapter;
    OrderCustomerAdapter orderCustomerAdapter;
    OrdersitemAdapter ordersitemAdapter;

    MaterialTextView deliveryAddress,tvCustomerName,orderNumber,orderDate,tvPrice;

    String customerNumber="",getSeverOtp="";
    TextInputEditText edtOtp;
    MaterialButton btnOtp;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        mContext=this;
        login = new SqliteDatabase(this).getLogin();
        orderDetails = (OrderParam) getIntent().getSerializableExtra("orderData");
        listenerContext=this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        deliveryAddress= findViewById(R.id.deliveryAddress);
        tvCustomerName= findViewById(R.id.tvCustomerName);
        orderNumber= findViewById(R.id.orderNumber);
        btnOtp=findViewById(R.id.btnOtp);
        edtOtp=findViewById(R.id.edtOtp);
        orderDate= findViewById(R.id.orderDate);
        tvPrice=findViewById(R.id.tvPrice);
        deliveryAddress.setText(orderDetails.getAddress());
        tvCustomerName.setText(orderDetails.getCustomerName());
        orderNumber.setText(orderDetails.getOrderNumber());
        orderDate.setText(orderDetails.getOrderDate());
        tvPrice.setText("₹ "+ orderDetails.getTotal());
        itemItemCustomer = findViewById(R.id.itemItemCustomer);
        orderLogList = findViewById(R.id.orderLogList);
        instructionList = findViewById(R.id.instructionList);

        itemItemCustomer.setHasFixedSize(true);
        itemItemCustomer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        orderLogList.setHasFixedSize(true);
        orderLogList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        instructionList.setHasFixedSize(true);
        instructionList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        btnOtp.setOnClickListener(v->{
            verifyOtp();
        });
        getOrderDetails();
    }

    private void getOrderDetails(){
        orderProductsList=new ArrayList<>();
        instructionLogs=new ArrayList<>();
        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();
            params.put("deliverId", login.getUser_id());
            params.put("orders_id",orderDetails.getOrdersId());
            params.put("token",orderDetails.getToken());
            Log.e("datasubcateee", params.toString());
            // Calling JSON

            Call<String> call = RestClient.post().orderViewDetails("1234", login.getSk(), params);

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
                                customerNumber=object.getJSONObject("customer").getString("mobile");
//

                                Type type1 = new TypeToken<ArrayList<OrderProduct>>() {
                                }.getType();


                                orderProductsList = gson.fromJson(object.getJSONArray("orderList").toString(), type1);
                                ordersitemAdapter = new OrdersitemAdapter("10",orderProductsList, listenerContext, mContext);
                                itemItemCustomer.setAdapter(ordersitemAdapter);
//
//
                                Type type2 = new TypeToken<ArrayList<InstructionLog>>() {
                                }.getType();
                                instructionLogs = gson.fromJson(object.getJSONArray("instructionOrderLog").toString(), type2);
                                instructionLogAdapter = new OrderLog(instructionLogs, listenerContext, mContext);
                                orderLogList.setAdapter(instructionLogAdapter);
//
//
//
//
//
                                Type type3 = new TypeToken<ArrayList<Instruction>>() {
                                }.getType();
                                instructions = gson.fromJson(object.getJSONArray("instruction").toString(), type3);
                                instructionAdapter = new OrderInstruction(instructions, listenerContext, mContext);
                                instructionList.setAdapter(instructionAdapter);
//



                            } else {
                                Util.show(mContext, object.getString("message"));

                            }


                        } catch (Exception e) {
                            Util.show(mContext, e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

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
    };





    public void verifyOtp() {
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("number", orderDetails.getCustomerName());
            Log.e("param",params.toString());
            Call<String> call = RestClient.post().getOtp("1234", login.getSk(), params);

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
                                getSeverOtp=object.getString("otp");

                                otpWindow();

                            } else {
                                Util.show(mContext, object.getString("message"));

                            }


                        } catch (Exception e) {
                            Util.show(mContext, e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(mContext, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                }

            });


        }
    }

    @SuppressLint("SetTextI18n")
    public void otpWindow() {

        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.order_verify, null);


        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_gredient_bg));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        ImageView closeImg=popupView.findViewById(R.id.closeImg);
        edtOtp = popupView.findViewById(R.id.edtOtp);
        edtOtp.setText(getSeverOtp);

        MaterialButton btnVerify = popupView.findViewById(R.id.otpVerify);

        closeImg.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        btnVerify.setOnClickListener(v -> {

            if(edtOtp.getText().toString().equalsIgnoreCase(getSeverOtp)){
                orderAccept();
            }

        });


    }
    public void orderAccept(){
        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();
            params.put("assigned_user_id", login.getUser_id());
        //    params.put("vendor_id", String.valueOf(orderDetails.getAssignedUserId()));
            params.put("orders_id",orderDetails.getOrdersId());


            // Calling JSON

            Call<String> call = RestClient.post().orderAccept("1234", login.getSk(), params);

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


                                startActivity(new Intent(mContext, HomeActivity.class));
                                finish();


                            } else {
                                Util.show(mContext, object.getString("message"));

                            }


                        } catch (Exception e) {
                            Util.show(mContext, e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

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
    public void orderAssign(){
        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();
            params.put("deliverId", login.getUser_id());
            params.put("orders_id",orderDetails.getOrdersId());
            params.put("token",orderDetails.getToken());


            // Calling JSON

            Call<String> call = RestClient.post().orderViewDetails("1234", login.getSk(), params);

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




                            } else {
                                Util.show(mContext, object.getString("message"));

                            }


                        } catch (Exception e) {
                            Util.show(mContext, e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

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

    @Override
    public void onOptionClick(OrderProduct data) {

    }

    @Override
    public void onOptionClick(InstructionLog data, int pos) {

    }

    @Override
    public void onOptionClick(Instruction data, int pos) {

    }
}