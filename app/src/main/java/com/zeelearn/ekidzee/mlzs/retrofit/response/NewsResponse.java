package com.zeelearn.ekidzee.mlzs.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zeelearn.news.retrofit.response.tips.ContentResponse;

import java.util.List;

public class NewsResponse {


    int ResponseCode;
    String ResponseMessage;
    @SerializedName("Data")
    @Expose
    List<ContentResponse> modelList;

    //NewsData newsData;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        ResponseMessage = responseMessage;
    }

    public List<ContentResponse> getModelList() {
        return modelList;
    }

    public void setModelList(List<ContentResponse> modelList) {
        this.modelList = modelList;
    }

    /*public NewsData getNewsData() {
        return newsData;
    }

    public void setNewsData(NewsData newsData) {
        this.newsData = newsData;
    }*/
}
