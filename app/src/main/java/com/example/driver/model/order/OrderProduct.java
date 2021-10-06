package com.example.driver.model.order;

/**
 * Created by Bhupesh Sen on 19-03-2021.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderProduct implements  Serializable {
    @SerializedName("vendor_selected_product")
    @Expose
    private String vendor_selected_product;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("orders_detail_id")
    @Expose
    private String ordersDetailId;
    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("variant_id")
    @Expose
    private Object variantId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productPhoto")
    @Expose
    private String productPhoto;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("subCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("SubsubCategoryName")
    @Expose
    private String subsubCategoryName;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("VendorName")
    @Expose
    private String vendorName;
    @SerializedName("OrderAttribute")
    @Expose
    private Object orderAttribute;
    @SerializedName("statusName")
    @Expose
    private String statusName;
    @SerializedName("statusColor")
    @Expose
    private String statusColor;

    public String getVendor_selected_product() {
        return vendor_selected_product;
    }

    public void setVendor_selected_product(String vendor_selected_product) {
        this.vendor_selected_product = vendor_selected_product;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Object getVariantId() {
        return variantId;
    }

    public void setVariantId(Object variantId) {
        this.variantId = variantId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
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

    public Object getOrderAttribute() {
        return orderAttribute;
    }

    public void setOrderAttribute(Object orderAttribute) {
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

}