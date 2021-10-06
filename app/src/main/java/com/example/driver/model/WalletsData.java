package com.example.driver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WalletsData implements Serializable {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("WalletTxnID")
    @Expose
    private String walletTxnID;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pay_type")
    @Expose
    private String payType;
    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("add_date")
    @Expose
    private String addDate;
    @SerializedName("deliveryBoyName")
    @Expose
    private String deliveryBoyName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWalletTxnID() {
        return walletTxnID;
    }

    public void setWalletTxnID(String walletTxnID) {
        this.walletTxnID = walletTxnID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getDeliveryBoyName() {
        return deliveryBoyName;
    }

    public void setDeliveryBoyName(String deliveryBoyName) {
        this.deliveryBoyName = deliveryBoyName;
    }

}