package com.zeelearn.ekidzee.mlzs.retrofit.request;

public class CampusRequest {

    int User_id;
    int App_Id;

    public CampusRequest(int user_id,int appId) {
        this.App_Id = appId;
        User_id = user_id;
    }



    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }
}
