package com.example.driver.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver.BuildConfig;
import com.example.driver.HomeActivity;
import com.example.driver.R;
import com.example.driver.adapter.DroupdownMenuAdapter;
import com.example.driver.adapter.order.OrdersDetailsAdapter;
import com.example.driver.database.SqliteDatabase;
import com.example.driver.helper.Util;
import com.example.driver.model.DroupDownModel;
import com.example.driver.model.LoginDetails;
import com.example.driver.model.order.CustomerOrderDetails;
import com.example.driver.model.order.OrderParam;
import com.example.driver.model.order.Orders;
import com.example.driver.network.InternetConnection;
import com.example.driver.network.RestClient;
import com.example.driver.service.AddressIntentService;
import com.example.driver.ui.LoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrder extends AppCompatActivity implements DroupdownMenuAdapter.OnMeneuClickListnser, OrdersDetailsAdapter.OnMeneuClickListnser {
    ArrayList<CustomerOrderDetails> orders;
    RecyclerView orderList, menuList;
    OrdersDetailsAdapter ordersAdapter;
    LinearLayoutCompat acceptLayout;
    TextInputEditText edtOtp;
    ProgressBar progressBar;
    boolean GpsStatus;
    OrderParam orderDetails;
    MyOrder listner;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    ArrayList<String> vendorOrderList;
    String orderCurrentStatus = "6";
    ArrayList<DroupDownModel> statusList;
    LocationManager locationManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private LocationCallback locationCallback;
    String latitudeStr = "null", longitudeStr = "null", currentAddcmp = "";
    PopupWindow popupWindow;
    MaterialTextView tvNewOrder, tvAccepted, tvDelivered, tvOntheway;
    MaterialTextView tvError;
    private static AlertDialog alertDialog;
    MaterialTextView tvTotal, tvPT;
    ArrayList<Orders> selectedProductData;
    Context mContext;
    CustomerOrderDetails currentCustomerOrderDetails;
    LoginDetails loginDetails;
    ArrayList<String> selectedIdes;
    ArrayList<String> orderIdes;
    ArrayList<String> tokensList;
    MyOrder listnerContext;
    AppCompatImageView imgClose;
    MaterialTextView tvCurrentLocation;
    MaterialButton tvCancel, tvAccept;
    private Toolbar toolbar;
    String currentStatus = "1", getSeverOtp = "";
    private static Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_order_id);
        loginDetails = new SqliteDatabase(this).getLogin();
        this.mContext = this;
        this.listner = this;
        this.listnerContext = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        addDropdown();

    }

    public void init() {
        requestCameraPermission();
        getCurrentLocation();
        CheckGpsStatus();
        vendorOrderList = new ArrayList<>();
        statusList = new ArrayList<>();
        orders = new ArrayList<>();
        orderIdes = new ArrayList<>();
        selectedIdes = new ArrayList<>();
        tokensList = new ArrayList<>();
        selectedProductData = new ArrayList<>();
        tvCurrentLocation = findViewById(R.id.tvCurrentLocation);
        tvNewOrder = findViewById(R.id.tvNewOrder);
        tvAccepted = findViewById(R.id.tvAccepted);
        tvDelivered = findViewById(R.id.tvDelivered);
        tvPT = findViewById(R.id.tvPT);
        imgClose = findViewById(R.id.imgClose);
        tvOntheway = findViewById(R.id.tvOntheway);
        tvCancel = findViewById(R.id.tvCancel);
        tvAccept = findViewById(R.id.tvAccept);
        acceptLayout = findViewById(R.id.acceptLayout);
        orderList = findViewById(R.id.orderList);

        tvTotal = findViewById(R.id.tvTotal);
        tvError = findViewById(R.id.tvError);
        progressBar = findViewById(R.id.progressBar);

        tvNewOrder.setOnClickListener(v -> {
            tvAccept.setVisibility(View.VISIBLE);
            currentStatus = "1";
            orderCurrentStatus = "6";
            getOrder("6");
            tvAccept.setText("Accept");
            tvNewOrder.setTextColor(Color.WHITE);
            tvNewOrder.setBackgroundResource(R.drawable.red_square_gradient);

            tvDelivered.setTextColor(Color.BLACK);
            tvDelivered.setBackground(null);

            tvAccepted.setTextColor(Color.BLACK);
            tvAccepted.setBackground(null);
            tvOntheway.setTextColor(Color.BLACK);
            tvOntheway.setBackground(null);


        });

        tvAccepted.setOnClickListener(v -> {
            currentStatus = "2";
            orderCurrentStatus = "12";
            tvCancel.setVisibility(View.GONE);
            tvAccept.setVisibility(View.VISIBLE);
            getOrder("12");
            tvAccept.setText("Pick Your Order");
            tvAccepted.setTextColor(Color.WHITE);
            tvAccepted.setBackgroundResource(R.drawable.red_square_gradient);

            tvDelivered.setTextColor(Color.BLACK);
            tvDelivered.setBackground(null);

            tvNewOrder.setTextColor(Color.BLACK);
            tvNewOrder.setBackground(null);

            tvOntheway.setTextColor(Color.BLACK);
            tvOntheway.setBackground(null);
        });

        tvDelivered.setOnClickListener(v -> {
            tvCancel.setVisibility(View.GONE);
            tvAccept.setVisibility(View.GONE);
            currentStatus = "4";
            orderCurrentStatus = "5";
            getOrder("5");

            tvDelivered.setTextColor(Color.WHITE);
            tvDelivered.setBackgroundResource(R.drawable.red_square_gradient);

            tvNewOrder.setTextColor(Color.BLACK);
            tvNewOrder.setBackground(null);

            tvAccepted.setTextColor(Color.BLACK);
            tvAccepted.setBackground(null);

            tvOntheway.setTextColor(Color.BLACK);
            tvOntheway.setBackground(null);
        });

        tvOntheway.setOnClickListener(v -> {
            tvCancel.setVisibility(View.GONE);
            currentStatus = "3";
            tvAccept.setVisibility(View.VISIBLE);
            orderCurrentStatus = "7";
            getOrder("7");
            tvAccept.setText("Delivered To Customer");
            tvOntheway.setTextColor(Color.WHITE);
            tvOntheway.setBackgroundResource(R.drawable.red_square_gradient);

            tvNewOrder.setTextColor(Color.BLACK);
            tvNewOrder.setBackground(null);

            tvAccepted.setTextColor(Color.BLACK);
            tvAccepted.setBackground(null);

            tvDelivered.setTextColor(Color.BLACK);
            tvDelivered.setBackground(null);
        });

        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        getOrder("6");

        imgClose.setOnClickListener(v -> {
            acceptLayout.setVisibility(View.GONE);
        });
        tvCancel.setVisibility(View.VISIBLE);

        tvCancel.setOnClickListener(view -> {
            if (selectedIdes.size() < 1) {
                Util.show(mContext, "please Select Minimum one product ");
                return;
            }
            if (currentStatus.equalsIgnoreCase("1")) {
                tvCancel.setVisibility(View.VISIBLE);
                orderAcceptedByVendor("Accepted", "4");
            }
        });
        tvAccept.setOnClickListener(v -> {
            Log.e("size", String.valueOf(selectedIdes.size()));
            if (selectedIdes.size() < 1) {
                Util.show(mContext, "please Select Minimum one product ");
                return;
            }
            if (currentStatus.equalsIgnoreCase("1")) {
                tvCancel.setVisibility(View.VISIBLE);
                orderAcceptedByVendor("Accepted", "12");
            } else if (currentStatus.equalsIgnoreCase("2")) {


                //                if(vendorOrderList.size()>2){
//                    Util.show(mContext, "You con't pic up multi vendor order at same time ");
//                    return;
//                }
                getDistance();
            } else if (currentStatus.equalsIgnoreCase("3")) {

                if (orderIdes.size() > 1) {
                    Util.show(mContext, "You Can not delivered more then one order at a time please select the product of any one order  ");
                    return;
                }
                orderAcceptedByVendor("delivered", "5");
            }


        });

    }

    public void addDropdown() {
        DroupDownModel droupDownModel = new DroupDownModel();
        droupDownModel.setDescription("On Hold");
        droupDownModel.setId("9");
        droupDownModel.setName("On Hold");
        statusList.add(droupDownModel);
        DroupDownModel droupDownModel1 = new DroupDownModel();
        droupDownModel1.setDescription("Delivered");
        droupDownModel1.setId("8");
        droupDownModel1.setName("Delivered");
        statusList.add(droupDownModel1);

        DroupDownModel droupDownModel2 = new DroupDownModel();
        droupDownModel2.setDescription("Cancel");
        droupDownModel2.setId("10");
        droupDownModel2.setName("Cancel");
        statusList.add(droupDownModel2);
    }

    private void orderAcceptedByVendor(String msg, String status) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    try {

                        if (status.equalsIgnoreCase("5")) {

                            otpWindow();

                        } else {
                            orderAccept(msg, status);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);

        builder.setMessage("Want to " + msg + " This Order?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }

    private void getOrder(String status) {
        acceptLayout.setVisibility(View.GONE);
        orders = new ArrayList<>();
        orderList.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText("Please Wait...");
        tvTotal.setText("");
        if (InternetConnection.checkConnection(mContext)) {
            Util.showDialog("Please wait..", mContext);

            Map<String, String> params = new HashMap<>();
            params.put("deliverboyId", loginDetails.getUser_id());
            params.put("status", status);
            // Calling JSON
            Log.e("params", params.toString());
            Call<String> call = RestClient.post().getOrder("1234", loginDetails.getSk(), params);

            // Enqueue Callback will be call when get response...
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        try {


                            Log.e("datares", response.body());
                            Gson gson = new Gson();

                            JSONObject object = new JSONObject(response.body());
                            if (object.getBoolean("status")) {

                                Util.hideDialog();

                                Type type = new TypeToken<ArrayList<CustomerOrderDetails>>() {
                                }.getType();
                                orders = gson.fromJson(object.getJSONArray("orderList").toString(), type);

                                if (orders.size() > 0) {
                                    orderList.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    tvError.setVisibility(View.GONE);
                                    tvTotal.setText("" + orders.size());
                                    ordersAdapter = new OrdersDetailsAdapter(orders, listnerContext, mContext, status);
                                    orderList.setAdapter(ordersAdapter);
                                    ordersAdapter.notifyDataSetChanged();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    tvError.setVisibility(View.VISIBLE);
                                    tvError.setText(object.getString("message"));
                                    orders.clear();
                                    ordersAdapter.notifyDataSetChanged();
                                }

                            } else {
                                Util.hideDialog();
                                Util.show(mContext, object.getString("message"));

                            }


                        } catch (Exception e) {
                            Util.hideDialog();
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
                        tvError.setVisibility(View.GONE);
                        tvError.setText("Please Wait...");
                        tvTotal.setText("");
                    } else {
                        Util.hideDialog();
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
                    Util.hideDialog();
                    Toast.makeText(mContext, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                }

            });
        } else {

            Toast.makeText(mContext, R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void orderAccept(CustomerOrderDetails data, int pos) {


    }


    @Override
    public void selectProduct(ArrayList<CustomerOrderDetails> listData) {

        selectedIdes = new ArrayList<>();
        tokensList = new ArrayList<>();
        orderIdes = new ArrayList<>();
        vendorOrderList = new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {


            for (int p = 0; p < listData.get(i).getProductDetails().size(); p++) {
                Log.e("ordecheck" + p, listData.get(i).getProductDetails().get(p).getCheckbox() + "=" + listData.get(i).getProductDetails().get(p).getProductName());
                if (listData.get(i).getProductDetails().get(p).getCheckbox().equalsIgnoreCase("1")) {
                    currentCustomerOrderDetails = listData.get(i);
                    if (!vendorOrderList.contains(listData.get(i).getOrderDetails().getVendorId())) {
                        vendorOrderList.add(listData.get(i).getOrderDetails().getOrdersId());
                    }
                    if (!orderIdes.contains(listData.get(i).getOrderDetails().getOrderNumber())) {
                        orderIdes.add(listData.get(i).getOrderDetails().getOrderNumber());
                    }


                    orderDetails = listData.get(i).getOrderDetails();
                    selectedIdes.add(listData.get(i).getProductDetails().get(p).getOrdersDetailId());
                    Log.e("selectedIdes", String.valueOf(selectedIdes.size()));
                    if (!tokensList.contains(listData.get(i).getProductDetails().get(p).getVendorId()))
                        tokensList.add(listData.get(i).getProductDetails().get(p).getVendorId());
                    if (!tokensList.contains(listData.get(i).getProductDetails().get(p).getCustomerId()))
                        tokensList.add(listData.get(i).getProductDetails().get(p).getCustomerId());

                }
            }
        }
        acceptLayout.setVisibility(View.VISIBLE);
        tvPT.setText(String.valueOf(selectedIdes.size()));
    }

    @Override
    public void Call(CustomerOrderDetails data, String type) {
        if (checkPermission(Manifest.permission.CALL_PHONE)) {


            if (type.equalsIgnoreCase("vendor")) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + data.getOrderDetails().getVendorNumber()));//change the number.
                startActivity(callIntent);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + data.getOrderDetails().getCustomerMobile()));//change the number.
                startActivity(callIntent);
            }

        } else {
            Util.show(mContext, "Permission Call Phone denied");
        }
        if (checkPermission(Manifest.permission.CALL_PHONE)) {
            //Cug.setEnabled(true);
        } else {
            //dial.setEnabled(false);
            ActivityCompat.requestPermissions(MyOrder.this, new String[]{Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }


    }

    @Override
    public void NavigateOnMap(CustomerOrderDetails data, String type) {
        if (type.equalsIgnoreCase("vendor")) {

            String uri = "http://maps.google.com/maps?saddr=" + latitudeStr + "," + longitudeStr + "&daddr=" + data.getShopLatitude() + "," + data.getShopLongitude();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        } else {

            String uri = "http://maps.google.com/maps?saddr=" + latitudeStr + "," + longitudeStr + "&daddr=" + data.getCustomerLatitude() + "," + data.getCustomerLongitude();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("SetTextI18n")
    public void otpWindow() {
        CustomerOrderDetails data = currentCustomerOrderDetails;
        View popupView = null;


        popupView = LayoutInflater.from(mContext).inflate(R.layout.fragment_layout_page9, null);
        //Util.showDropDown(statusList,"Please Select Status",mContext,listner);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white_gredient_bg));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        MaterialTextView tvOrder = popupView.findViewById(R.id.tvOrder);
        MaterialTextView vendorName = popupView.findViewById(R.id.vendorName);
        TextView payMode = popupView.findViewById(R.id.payMode);
        TextView toAmt = popupView.findViewById(R.id.toAmt);
        TextView instructionList = popupView.findViewById(R.id.instructionList);
        TextView tvCustomerName = popupView.findViewById(R.id.tvCustomerName);
        TextView tvCustomerMobile = popupView.findViewById(R.id.tvCustomerMobile);
        TextView tvCustomerAddress = popupView.findViewById(R.id.tvCustomerAddress);
        AppCompatCheckBox tvCheckBox = popupView.findViewById(R.id.tvCheckBox);
        TextView tvShopAddress = popupView.findViewById(R.id.tvShopAddress);
        TextView tvVendorNo = popupView.findViewById(R.id.tvVendorNo);
        TextView tvShopName = popupView.findViewById(R.id.tvShopName);
        AppCompatImageView closeImg = popupView.findViewById(R.id.tvClose);
        TextInputLayout enterAmount = popupView.findViewById(R.id.enterAmt);
        TextInputLayout enterOtp = popupView.findViewById(R.id.enterOtp);
        OrderParam param = data.getOrderDetails();
        tvShopAddress.setText(data.getOrderDetails().getShopaddress());
        tvShopName.setText(data.getOrderDetails().getShop_name());
        tvVendorNo.setText(data.getOrderDetails().getVendorNumber());
        if (param.getPaymentModeId().equalsIgnoreCase("5")) {
            enterAmount.setVisibility(View.VISIBLE);
        } else {
            enterAmount.setVisibility(View.GONE);
        }
        MaterialButton btnDelivered = popupView.findViewById(R.id.btnDelivered);
        tvOrder.setText(param.getOrderNumber());
        payMode.setText(param.getPaymode());
        toAmt.setText(param.getTotal());
        if (data.getInstruction().size() == 0) {
            instructionList.setText("No Instruction found");
        } else {
            instructionList.setText(data.getInstruction().toString());
        }

        tvCustomerName.setText(param.getCustomerName());
        tvCustomerMobile.setText(param.getCustomerMobile());
        vendorName.setText(param.getVendorName());
        tvCustomerAddress.setText(param.getAddress());
        closeImg.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        btnDelivered.setOnClickListener(v -> {

            if (enterOtp.getEditText().getText().length() < 4) {
                Util.show(this, "Please enter otp");
                return;

            }

            if (!tvCheckBox.isChecked()) {
                Util.show(this, "Please click on check box");
                return;
            }

            if (enterAmount.getEditText().length() <= 0 && param.getPaymentModeId().equalsIgnoreCase("5")) {
                Util.show(this, "Please enter amount");
                return;

            }
            if(param.getPaymentModeId().equalsIgnoreCase("5")){
                int entAmt = Integer.parseInt(enterAmount.getEditText().getText().toString());
                int getTotalAmt = Integer.parseInt(param.getTotalPrice());
                if (entAmt > getTotalAmt || entAmt < getTotalAmt) {
                    Util.show(this, "Please enter valid amount");
                    return;
                }
            }


            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        orderAccept("Order is delivered", "5");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            };
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
            builder.setMessage("Do you want to logout from the app?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();


        });
        //        edtOtp = popupView.findViewById(R.id.edtOtp);
//        edtOtp.setText(getSeverOtp);
//
//        MaterialButton btnVerify = popupView.findViewById(R.id.otpVerify);
//
//        closeImg.setOnClickListener(v -> {
//            popupWindow.dismiss();
//        });
//        btnVerify.setOnClickListener(v -> {
//
//            if(edtOtp.getText().toString().equalsIgnoreCase(getSeverOtp)){
//
//            }
//
//        });


    }

    public void orderAccept(String msg, String status) {
        if (InternetConnection.checkConnection(mContext)) {


            Util.showDialog("Please wait..", mContext);
            Map<String, String> params = new HashMap<>();
            params.put("orders_detail_id", selectedIdes.toString().replace("[", "").replace("]", "").replace(" ", ""));
            params.put("status", status);
            params.put("tokens", tokensList.toString().replace("[", "").replace("]", "").replace(" ", ""));
            params.put("remark", msg);
            params.put("userId", loginDetails.getUser_id());
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
                                if (status.equalsIgnoreCase("5")) {
                                    popupWindow.dismiss();
                                }
                                //
                                getOrder(orderCurrentStatus);
                            } else {
                                if (status.equalsIgnoreCase("5")) {
                                    popupWindow.dismiss();
                                }
                                Util.show(mContext, object.getString("message"));
                                Util.hideDialog();
                                //      popupWindow.dismiss();
                            }


                        } catch (Exception e) {
                            Util.show(mContext, e.getMessage());
                            e.printStackTrace();
                            Util.hideDialog();
                            if (status.equalsIgnoreCase("5")) {
                                popupWindow.dismiss();
                            }
                            //   popupWindow.dismiss();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        if (status.equalsIgnoreCase("5")) {
                            popupWindow.dismiss();
                        }
                        Util.hideDialog();
                        //popupWindow.dismiss();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onOptionClick(DroupDownModel data) {
        Util.hideDropDown();
        orderAccept(data.getDescription(), data.getId());
    }


    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(mContext, "Address not found, ", Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");
            String current_latitude = resultData.getString("latitude");
            String current_longitude = resultData.getString("longitude");
            if (resultCode == 2) {

                latitudeStr = current_latitude;
                longitudeStr = current_longitude;
                // Toast.makeText(mContext, currentAddcmp, Toast.LENGTH_SHORT).show();
                currentAddcmp = currentAdd;
                tvCurrentLocation.setText(currentAdd);
                //checkAlert();
//                if(myAnim!=null){
//                    myAnim.cancel();
//                }

            }
        }
    }


    public void CheckGpsStatus() {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (GpsStatus == true) {
            GpsStatus = true;
            // tvcurrentLocation.setText("GPS Is Enabled");
        } else {
            GpsStatus = false;
        }
    }

    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {


                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.cancel();
                        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                        startActivity(i);
                        finish();
                    }
                }).show();
    }

    private void getCurrentLocation() {
        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }

            ;
        };
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((AppCompatActivity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(500);
            locationRequest.setFastestInterval(500);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.myLooper());
        }
    }

    private void getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(mContext, "Can't find current address, ", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(mContext, AddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        mContext.startService(intent);

    }

    @Override
    public void onResume() {

        startLocationUpdates();
        super.onResume();
    }

    @Override
    public void onPause() {

        fusedLocationClient.removeLocationUpdates(locationCallback);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getDistance() {


        try {


            double getDistance = distance(Double.parseDouble(orderDetails.getShopLatitude()), Double.parseDouble(orderDetails.getShopLongitude()), Double.parseDouble(latitudeStr), Double.parseDouble(longitudeStr));
            milesTokm(getDistance);

            orderAcceptedByVendor("Pick Up The Order", "7");

            double metterD = Math.round(getDistance * 1000);

            Log.e("location", String.valueOf(metterD) + "lat" + Double.parseDouble(orderDetails.getShopLatitude()) + "long" + Double.parseDouble(orderDetails.getShopLongitude()) + Double.parseDouble(latitudeStr) + Double.parseDouble(longitudeStr));
            if (metterD <= 500) {
                //alertDialog.dismiss();
                //alertDialog.cancel();
            } else {
                //alertDialog.dismiss();
                //alertDialog.cancel();
                // getUpdateLocation("","");
                //   currentLocationtv.setText("out of range ");
                Toast.makeText(mContext, "Your are not near by vendor shop", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double milesTokm(double distanceInMiles) {
        return distanceInMiles * 1.60934;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}