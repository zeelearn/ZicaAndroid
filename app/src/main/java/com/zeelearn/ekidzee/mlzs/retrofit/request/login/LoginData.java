package com.zeelearn.ekidzee.mlzs.retrofit.request.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginData {

    @SerializedName("tblUser")
    @Expose
    List<UserInfoModel> userInfoModels;

    @SerializedName("tblMenu")
    @Expose
    List<MenuRequest> menuList;

    public LoginData() {
    }

    public LoginData(List<UserInfoModel> userInfoModels, List<MenuRequest> menuList) {
        this.userInfoModels = userInfoModels;
        this.menuList = menuList;
    }

    public List<UserInfoModel> getUserInfoModels() {
        return userInfoModels;
    }

    public void setUserInfoModels(List<UserInfoModel> userInfoModels) {
        this.userInfoModels = userInfoModels;
    }

    public List<MenuRequest> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuRequest> menuList) {
        this.menuList = menuList;
    }
}
