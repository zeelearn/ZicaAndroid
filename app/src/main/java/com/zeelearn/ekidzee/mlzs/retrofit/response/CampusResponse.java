package com.zeelearn.ekidzee.mlzs.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CampusResponse {

    @SerializedName("ResponseMessage")
    @Expose
    List<CampusModel> modelList;

    public List<CampusModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<CampusModel> modelList) {
        this.modelList = modelList;
    }
}
