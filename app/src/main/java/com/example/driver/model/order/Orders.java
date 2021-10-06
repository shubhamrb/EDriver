package com.example.driver.model.order;

/**
 * Created by Bhupesh Sen on 18-03-2021.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Orders implements Serializable {


    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("subCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("SubsubCategoryName")
    @Expose
    private String subsubCategoryName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("checkbox")
    @Expose
    private String checkbox;
    @SerializedName("OrderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("orders_detail_id")
    @Expose
    private String ordersDetailId;
    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productPhoto")
    @Expose
    private String productPhoto;
    @SerializedName("admin_commision_type")
    @Expose
    private String adminCommisionType;
    @SerializedName("admin_commision")
    @Expose
    private String adminCommision;
    @SerializedName("OrderAttribute")
    @Expose
    private String orderAttribute;
    @SerializedName("statusName")
    @Expose
    private String statusName;
    @SerializedName("statusColor")
    @Expose
    private String statusColor;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("assigned_user_id")
    @Expose
    private Object assignedUserId;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("deliveryBoyName")
    @Expose
    private String deliveryBoyName;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("address")
    @Expose
    private String address;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubsubCategoryName() {
        return subsubCategoryName;
    }

    public void setSubsubCategoryName(String subsubCategoryName) {
        this.subsubCategoryName = subsubCategoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrdersDetailId() {
        return ordersDetailId;
    }

    public void setOrdersDetailId(String ordersDetailId) {
        this.ordersDetailId = ordersDetailId;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public String getAdminCommisionType() {
        return adminCommisionType;
    }

    public void setAdminCommisionType(String adminCommisionType) {
        this.adminCommisionType = adminCommisionType;
    }

    public String getAdminCommision() {
        return adminCommision;
    }

    public void setAdminCommision(String adminCommision) {
        this.adminCommision = adminCommision;
    }

    public String getOrderAttribute() {
        return orderAttribute;
    }

    public void setOrderAttribute(String orderAttribute) {
        this.orderAttribute = orderAttribute;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Object getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Object assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDeliveryBoyName() {
        return deliveryBoyName;
    }

    public void setDeliveryBoyName(String deliveryBoyName) {
        this.deliveryBoyName = deliveryBoyName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
