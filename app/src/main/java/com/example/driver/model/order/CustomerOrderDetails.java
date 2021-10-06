package com.example.driver.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bhupesh Sen on 02-05-2021.
 */
public class CustomerOrderDetails implements Serializable {


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

    @SerializedName("view")
    @Expose
    private Boolean viewList;

    @SerializedName("orderIds")
    @Expose
    private ArrayList<String> orderIds = null;

    @SerializedName("instruction")
    @Expose
    private ArrayList<String> instruction = null;

    @SerializedName("orderDetails")
    @Expose
    private OrderParam orderDetails;

    @SerializedName("productDetails")
    @Expose
    private ArrayList<Orders> productDetails = null;

    public OrderParam  getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderParam orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ArrayList<Orders> getProductDetails() {
        return productDetails;
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

    public void setProductDetails(ArrayList<Orders> productDetails) {
        this.productDetails = productDetails;
    }

    public ArrayList<String> getInstruction() {
        return instruction;
    }

    public Boolean getViewList() {
        return viewList;
    }

    public void setViewList(Boolean viewList) {
        this.viewList = viewList;
    }

    public void setInstruction(ArrayList<String> instruction) {
        this.instruction = instruction;
    }

    public ArrayList<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(ArrayList<String> orderIds) {
        this.orderIds = orderIds;
    }
}
