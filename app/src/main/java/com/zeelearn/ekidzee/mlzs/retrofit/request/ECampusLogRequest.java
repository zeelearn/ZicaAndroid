package com.zeelearn.ekidzee.mlzs.retrofit.request;

public class ECampusLogRequest {
    int userId;
    String userType;
    int appId;
    String userIpAddress;
    String appType;
    String url;

    public ECampusLogRequest(int userId, String userType, int appId, String userIpAddress, String appType, String url) {
        this.userId = userId;
        this.userType = userType;
        this.appId = appId;
        this.userIpAddress = userIpAddress;
        this.appType = appType;
        this.url = url;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getUserIpAddress() {
        return userIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        this.userIpAddress = userIpAddress;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
