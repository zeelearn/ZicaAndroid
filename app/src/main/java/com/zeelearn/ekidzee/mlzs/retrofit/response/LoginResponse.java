package com.zeelearn.ekidzee.mlzs.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zeelearn.ekidzee.mlzs.retrofit.request.login.LoginData;

public class LoginResponse {

    @SerializedName("ResponseCode")
    @Expose
    int responseCode;

    @SerializedName("ResponseMessage")
    @Expose
    String responseMessage;

    @SerializedName("Data")
    @Expose
    LoginData data;

    public LoginResponse() {
    }

    public LoginResponse(int responseCode, String responseMessage, LoginData data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
