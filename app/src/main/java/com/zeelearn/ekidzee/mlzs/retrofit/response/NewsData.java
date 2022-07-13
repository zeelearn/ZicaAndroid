package com.zeelearn.ekidzee.mlzs.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zeelearn.news.retrofit.response.tips.ContentResponse;

import java.util.List;

public class NewsData {

    @SerializedName("tblTipUser")
    @Expose
    List<ContentResponse> modelList;

    public List<ContentResponse> getModelList() {
        return modelList;
    }

    public void setModelList(List<ContentResponse> modelList) {
        this.modelList = modelList;
    }
}
