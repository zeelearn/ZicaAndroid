package com.zeelearn.ekidzee.mlzs.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipsRequest {

    @SerializedName("UserId")
    @Expose
    String userId;
    @SerializedName("UserType")
    @Expose
    String userType;
    @SerializedName("PageNo")
    @Expose
    int pageIndex;
    @SerializedName("PageSize")
    @Expose
    int pageSize;
    @SerializedName("ContentType")
    @Expose
    String contentType;

    public TipsRequest(String userId, String userType, int pageIndex, int pageSize, String contentType) {
        this.userId = userId;
        this.userType = userType;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.contentType = contentType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
