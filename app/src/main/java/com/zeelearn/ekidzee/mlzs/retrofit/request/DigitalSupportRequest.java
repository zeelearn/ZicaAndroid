package com.zeelearn.ekidzee.mlzs.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DigitalSupportRequest {

    @SerializedName("User_id")
    @Expose
    int User_id;
    @SerializedName("App_Id")
    @Expose
    int App_Id;
    @SerializedName("digitalCategoryId")
    @Expose
    int digitalCategoryId;

    @SerializedName("KeySupport")
    @Expose
    String supportPath;

    public DigitalSupportRequest(int user_id, int app_Id, int digitalCategoryId) {
        User_id = user_id;
        App_Id = app_Id;
        this.digitalCategoryId = digitalCategoryId;
    }

    public String getSupportPath() {
        return supportPath;
    }

    public void setSupportPath(String supportPath) {
        this.supportPath = supportPath;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public int getApp_Id() {
        return App_Id;
    }

    public void setApp_Id(int app_Id) {
        App_Id = app_Id;
    }

    public int getDigitalCategoryId() {
        return digitalCategoryId;
    }

    public void setDigitalCategoryId(int digitalCategoryId) {
        this.digitalCategoryId = digitalCategoryId;
    }
}
