package com.example.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.driver.adapter.MenuListAdapter;
import com.example.driver.adapter.order.OrderAdapter;
import com.example.driver.database.SqliteDatabase;
import com.example.driver.helper.Util;
import com.example.driver.model.LoginDetails;
import com.example.driver.model.order.Orders;
import com.example.driver.network.ApiService;
import com.example.driver.network.InternetConnection;
import com.example.driver.network.RestClient;
import com.example.driver.ui.LoginActivity;
import com.example.driver.ui.OrderDetails;
import com.example.driver.ui.home.MyOrder;
import com.example.driver.ui.home.PaymentRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements  MenuListAdapter.OnMeneuClickListnser ,OrderAdapter.OnMeneuClickListnser{
    FrameLayout frameLayout;
    public Toolbar toolbar;
    DrawerLayout drawer;
    Context mContext;
    MaterialTextView totalCollection;
    BottomNavigationView navView;
    ActionBarDrawerToggle toggle;
    String arrItems[] = new String[]{};
    String arrayItemTwo[] = new String[]{"bhs","bhs2","bhs3"};
    RecyclerView menuList,sideMenuList,orderListView;
    MenuListAdapter menuListAdapter;
    OrderAdapter orderAdapter;
    CoordinatorLayout llRoot;

    HomeActivity listenerContext;

    ArrayList<Orders> orderList ;
    LoginDetails login;
    String token;
    MaterialTextView total,tvNew,tvAccepted,tvName,tvMobile,tvTn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        listenerContext=this;
        login= new SqliteDatabase(this).getLogin();
        init();
        //String spliteStr[]=login.getName().split(" ");
        Log.e("sk",login.getSk());
        tvTn.setText(login.getName());
        tvName.setText(login.getName());
        tvMobile.setText(login.getNumber());
        toolbar.setTitle("Dashboard");
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        UpdateNotification();
                        // Log and toast
                        Log.e("msg_token_fmt", token);
                        Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private  void UpdateNotification(){
        if (InternetConnection.checkConnection(mContext)) {
            //   Util.showDialog("Please wait..",mContext);
            Map<String, String> params = new HashMap<>();
            params.put("user_id", login.getUser_id());
            params.put("notification_token", token);


            Log.e("vendor_id",params.toString());
            RestClient.post().updateNotification(ApiService.APP_DEVICE_ID,login.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {

                            Util.show(mContext,"Successfully updated your setting data");


                            //    Util.hideDialog();
                        } else {
                            //     Util.hideDialog();
                            Util.show(mContext, "something is wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //  Util.hideDialog();

                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {

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
    public void init(){
        sideMenuList=findViewById(R.id.sideMenuList);
        totalCollection=findViewById(R.id.totalCollection);
        llRoot=findViewById(R.id.llRoot);

        tvName=findViewById(R.id.tvName);
        total=findViewById(R.id.total);
        tvNew=findViewById(R.id.tvNew);
        tvAccepted=findViewById(R.id.tvAccepted);

        tvTn=findViewById(R.id.tvTn);
        tvMobile=findViewById(R.id.tvMobile);
        navView = findViewById(R.id.nav_view);
        orderList=new ArrayList<>();

        arrItems = (HomeActivity.this.getResources().getStringArray(R.array.arr_nav_service_items));

        // side menu layout adapter
        sideMenuList.setHasFixedSize(true);
        sideMenuList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        menuListAdapter = new MenuListAdapter(arrItems, this, mContext);
        sideMenuList.setAdapter(menuListAdapter);
//        menuList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        menuListAdapter = new MenuListAdapter(arrItems, this, mContext);
//        menuList.setAdapter(menuListAdapter);

        // near by list
       // orderListView.setHasFixedSize(true);
       // orderListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        drawer.setScrimColor(Color.TRANSPARENT);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

//                float slideX = drawerView.getWidth() * slideOffset;
//                content.setTranslationX(slideX);

                float scaleFactor = 80f;

                float slideX = drawerView.getWidth() * slideOffset;
                llRoot.setTranslationX(slideX);
                llRoot.setScaleX(1 - (slideOffset / scaleFactor));
                llRoot.setScaleY(1 - (slideOffset / scaleFactor));
            }
        };
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeId:

                    break;
                case R.id.order:
                    Intent mv2 = new Intent(HomeActivity.this, MyOrder.class);
                    startActivity(mv2);
                    ///loadFragment(serviceObj, "");
                    break;
                case R.id.history:

                    //loadFragment(categoryObj, "");

                    break;
                case R.id.settlement:

                    Intent mv3 = new Intent(HomeActivity.this, PaymentRequest.class);
                    startActivity(mv3);
                    break;


            }
            return true;

        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        findViewById(R.id.btnLogout).setOnClickListener(v->{
            Logout();
        });
        getHome();
        //getOrderListData();

    }



    private void getHome(){
        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();
            params.put("driverId", login.getUser_id());
            Log.e("datasubcateee", params.toString());
            // Calling JSON

            Call<String> call = RestClient.post().getHome("1234", login.getSk(), params);

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

                                totalCollection.setText(object.getString("totalCollection"));
                                total.setText(object.getJSONObject("totalOrder").getString("total"));
                                tvNew.setText(object.getJSONObject("totalOrder").getString("newOrder"));
                                tvAccepted.setText(object.getJSONObject("Accept").getString("Accept"));

//                                Type type = new TypeToken<ArrayList<Orders>>() {
//                                }.getType();
//                                orderList = gson.fromJson(object.getJSONArray("orderList").toString(), type);
//                                orderAdapter = new OrderAdapter(orderList, listenerContext, mContext);
//                                orderListView.setAdapter(orderAdapter);

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
    private void getOrderListData(){

        if (InternetConnection.checkConnection(mContext)) {


            Map<String, String> params = new HashMap<>();
            params.put("deliverboyId", login.getUser_id());
            Log.e("datasubcateee", params.toString());
            // Calling JSON

            Call<String> call = RestClient.post().getOrderList("1234", login.getSk(), params);

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


                                Type type = new TypeToken<ArrayList<Orders>>() {
                                }.getType();
                                orderList = gson.fromJson(object.getJSONArray("orderList").toString(), type);
                                orderAdapter = new OrderAdapter(orderList, listenerContext, mContext);
                                orderListView.setAdapter(orderAdapter);

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
    // Logout Function ///////////
    private void Logout() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    try {
                        new SqliteDatabase(mContext).removeLoginUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Do you want to logout from the app?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
//    public void loadFragment(Fragment fragment, String fragName) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
//    }

    @Override
    public void onOptionClick(String liveTest,int pos) {
        Toast.makeText(mContext, liveTest+"Can't find current address, "+pos, Toast.LENGTH_SHORT).show();

        //Log.e("post",String.valueOf(pos));
        switch (pos) {




        }

    }


    @Override
    public void onOrderClick(Orders data) {
        Intent mv=new Intent(HomeActivity.this, OrderDetails.class);
        mv.putExtra("orderData",data);
        startActivity(mv);

    }
}