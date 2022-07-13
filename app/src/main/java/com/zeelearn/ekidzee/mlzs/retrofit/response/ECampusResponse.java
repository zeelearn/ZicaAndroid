package com.zeelearn.ekidzee.mlzs.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ECampusResponse {

    @SerializedName("ResponseMessage")
    @Expose
    String response;

    @SerializedName("ResponseCode")
    @Expose
    int responseCode;

    @SerializedName("Data")
    @Expose
    Data data;

    public Data getData() {
        return data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
