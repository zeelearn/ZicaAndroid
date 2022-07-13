package com.zeelearn.ekidzee.mlzs.retrofit.response;


import com.zeelearn.ekidzee.mlzs.model.DigitalSupportModel;

import java.util.List;

public class DigitalSupportResponse {

    boolean status;
    List<DigitalSupportModel> mContentList;

    public DigitalSupportResponse() {
    }

    public DigitalSupportResponse(boolean status, List<DigitalSupportModel> mContentList) {
        this.status = status;
        this.mContentList = mContentList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<DigitalSupportModel> getmContentList() {
        return mContentList;
    }

    public void setmContentList(List<DigitalSupportModel> mContentList) {
        this.mContentList = mContentList;
    }
}
