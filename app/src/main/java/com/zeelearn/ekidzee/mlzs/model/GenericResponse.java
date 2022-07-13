package com.zeelearn.ekidzee.mlzs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericResponse {
    int requestCode;
    @SerializedName("ResponseMessage")
    @Expose
    String message;

    public GenericResponse(int requestCode, String message) {
        this.requestCode = requestCode;
        this.message = message;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
