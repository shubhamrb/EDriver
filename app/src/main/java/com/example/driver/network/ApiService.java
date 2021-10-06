package com.example.driver.network;




import com.example.driver.model.LoginDetails;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static com.example.driver.network.Constants.GET_ORDERS;


public interface ApiService {

    String APP_DEVICE_ID = "1234";
    //  String BASE_URL = "http://192.168.43.56/top10/api/v1/deliver-boy/";
   // String IMG_PRODUCT_URL = "http://192.168.43.56/top10/upload/product/";
    String MAIN_BASE_URL ="http://top10india.in/";
    String BASE_URL = MAIN_BASE_URL+"api/v1/deliver-boy/";
 // String BASE_URL = "http://top10india.in/api/v1/deliver-boy/";
    String IMG_PRODUCT_URL = "http://top10india.in/upload/product/";

    String AppType = "( LIVE )";//LIVE
    //login
    @FormUrlEncoded
    @POST(Constants.GET_LOGIN)
    Call<LoginDetails> userLogin(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);

    //get order list by delivery boy id
    @FormUrlEncoded
    @POST(Constants.GET_ORDERS)
    Call<String> getOrderList(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    //get order list by delivery boy id
    @FormUrlEncoded
    @POST(Constants.GET_ORDER_VIEW_DETAILS)
    Call<String> orderViewDetails(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    //get order list by delivery boy id
    @FormUrlEncoded
    @POST(Constants.GET_OTP)
    Call<String> getOtp(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);

    //get order list by delivery boy id
    @FormUrlEncoded
    @POST(Constants.ORDER_ACCEPT)
    Call<String> orderAccept(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);

    @FormUrlEncoded
    @POST(Constants.GET_ORDER)
    Call<String> getOrder(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.UPDATE_NOTIFICATION)
    Call<String> updateNotification(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_HOME)
    Call<String> getHome(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.REGISTER)
    Call<String> register(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.Add_Payment_Request)
    Call<String> addPaymentRequest(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.Get_Payment_Request)
    Call<String> getPaymentRequest(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


    @FormUrlEncoded
    @POST(Constants.GET_WALLET)
    Call<String> getWallet(@Header("DID") String did, @Header("SK") String SK, @FieldMap Map<String, String> uData);


}

