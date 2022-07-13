package com.zeelearn.ekidzee.mlzs.retrofit.request;

public class ECampusRequest {

    int UserId;

    public ECampusRequest(int userId) {
        UserId = userId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
