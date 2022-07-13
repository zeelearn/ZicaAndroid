package com.zeelearn.ekidzee.mlzs.model;

import android.text.TextUtils;

public class DigitalSupportModel {

    String SupportDate;
    String SupportName;
    String ClassName;
    String RowNo;
    String UploadType;
    String SubjectName;
    String ContentDescription;
    String Url;
    int TierId;
    int AppID;
    String DetailId;
    boolean IsVirtualRolloverl;
    String RolloverMsg;
    String RootPath;


    public DigitalSupportModel(String supportDate, String supportName, String className, String rowNo, String uploadType, String subjectName, String contentDescription, String url, int tierId, int appID, String detailId, boolean isVirtualRolloverl, String rolloverMsg, String rootPath) {
        SupportDate = supportDate;
        SupportName = supportName;
        ClassName = className;
        RowNo = rowNo;
        UploadType = uploadType;
        SubjectName = subjectName;
        ContentDescription = contentDescription;
        Url = url;
        TierId = tierId;
        AppID = appID;
        DetailId = detailId;
        IsVirtualRolloverl = isVirtualRolloverl;
        RolloverMsg = rolloverMsg;
        RootPath = rootPath;
    }

    public String getSupportDate() {
        return SupportDate;
    }

    public void setSupportDate(String supportDate) {
        SupportDate = supportDate;
    }

    public String getSupportName() {
        return SupportName;
    }

    public void setSupportName(String supportName) {
        SupportName = supportName;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getRowNo() {
        return RowNo;
    }

    public void setRowNo(String rowNo) {
        RowNo = rowNo;
    }

    public String getUploadType() {
        return UploadType;
    }

    public void setUploadType(String uploadType) {
        UploadType = uploadType;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getContentDescription() {
        return ContentDescription;
    }

    public void setContentDescription(String contentDescription) {
        ContentDescription = contentDescription;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getTierId() {
        return TierId;
    }

    public void setTierId(int tierId) {
        TierId = tierId;
    }

    public int getAppID() {
        return AppID;
    }

    public void setAppID(int appID) {
        AppID = appID;
    }

    public String getDetailId() {
        return DetailId;
    }

    public void setDetailId(String detailId) {
        DetailId = detailId;
    }

    public boolean isVirtualRolloverl() {
        return IsVirtualRolloverl;
    }

    public void setVirtualRolloverl(boolean virtualRolloverl) {
        IsVirtualRolloverl = virtualRolloverl;
    }

    public String getRolloverMsg() {
        return RolloverMsg;
    }

    public void setRolloverMsg(String rolloverMsg) {
        RolloverMsg = rolloverMsg;
    }

    public String getRootPath() {
        if(!TextUtils.isEmpty(RootPath) && RootPath.charAt(0)=='/'){
            RootPath = RootPath.substring(1);
        }
        return RootPath;
    }

    public void setRootPath(String rootPath) {
        RootPath = rootPath;
    }

    @Override
    public String toString() {
        return "DigitalSupportModel{" +
                "SupportName='" + SupportName + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", RootPath='" + RootPath + '\'' +
                ", SubjectName='" + SubjectName + '\'' +
                ", ContentDescription='" + ContentDescription + '\'' +
                ", Url='" + Url + '\'' +
                ", DetailId='" + DetailId + '\'' +
                '}';
    }
}
