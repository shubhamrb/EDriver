package com.example.driver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bhupesh Sen on 02-02-2021.
 */
public class LoginDetails {


    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("masterCatId")
    @Expose
    private String masterCatId;
    @SerializedName("mastercatname")
    @Expose
    private String mastercatname;
    @SerializedName("sk")
    @Expose
    private String sk;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("pickup_point")
    @Expose
    private String pickupPoint;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;


    public LoginDetails(String user_id, String masterCatId, String mastercatname, String name, String email, String phone, String sk, String hash1, String hash2, String photo) {
        this.name = name;
        this.email = email;
        this.user_id = user_id;
        this.number = phone;
        this.masterCatId = masterCatId;
        this.mastercatname = mastercatname;
        this.sk = sk;
        //this.hash1 = hash1;
        //this.hash2 = hash2;
        this.photo = photo;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMasterCatId() {
        return masterCatId;
    }

    public void setMasterCatId(String masterCatId) {
        this.masterCatId = masterCatId;
    }

    public String getMastercatname() {
        return mastercatname;
    }

    public void setMastercatname(String mastercatname) {
        this.mastercatname = mastercatname;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
