package com.example.driver.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 02-05-2021.
 */
public class OrderParam implements Serializable {




    @SerializedName("vendorId")
    @Expose
    private String vendorId;

    @SerializedName("shop_name")
    @Expose
    private String shop_name;

    @SerializedName("vendorNumber")
    @Expose
    private String vendorNumber;

    @SerializedName("shopaddress")
    @Expose
    private String shopaddress;

    @SerializedName("shopLatitude")
    @Expose
    private String shopLatitude;

    @SerializedName("shopLongitude")
    @Expose
    private String shopLongitude;

    @SerializedName("customerLatitude")
    @Expose
    private String customerLatitude;

    @SerializedName("customerLongitude")
    @Expose
    private String customerLongitude;


    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("totalPrice")
    @Expose
    private String totalPrice;
    @SerializedName("paymode")
    @Expose
    private String paymode;

    @SerializedName("VendorName")
    @Expose
    private String VendorName;
    @SerializedName("CustomerMobile")
    @Expose
    private String customerMobile;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("payment_mode_id")
    @Expose
    private String paymentModeId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("assign_user_id")
    @Expose
    private Object assignUserId;
    @SerializedName("deliveryBoyName")
    @Expose
    private Object deliveryBoyName;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(String shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public String getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(String shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public String getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(String customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public String getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(String customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(String paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Object getAssignUserId() {
        return assignUserId;
    }

    public void setAssignUserId(Object assignUserId) {
        this.assignUserId = assignUserId;
    }

    public Object getDeliveryBoyName() {
        return deliveryBoyName;
    }

    public void setDeliveryBoyName(Object deliveryBoyName) {
        this.deliveryBoyName = deliveryBoyName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }
}
