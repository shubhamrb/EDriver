package com.example.driver.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Bhupesh Sen on 19-03-2021.
 */
public class InstructionLog implements Serializable {

    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("orders_log_id")
    @Expose
    private String ordersLogId;
    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("orders_detail_id")
    @Expose
    private String ordersDetailId;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("logDate")
    @Expose
    private String logDate;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("ProductName")
    @Expose
    private String productName;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getOrdersLogId() {
        return ordersLogId;
    }

    public void setOrdersLogId(String ordersLogId) {
        this.ordersLogId = ordersLogId;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getOrdersDetailId() {
        return ordersDetailId;
    }

    public void setOrdersDetailId(String ordersDetailId) {
        this.ordersDetailId = ordersDetailId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
