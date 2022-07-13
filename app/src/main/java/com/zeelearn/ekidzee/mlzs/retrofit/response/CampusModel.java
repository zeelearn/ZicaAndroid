package com.zeelearn.ekidzee.mlzs.retrofit.response;

public class CampusModel {

    String SupportDate;
    String SupportName;
    String ClassName;
    int RowNo;
    String UploadType;
    String SubjectName;
    String ContentDescription;
    String Url;
    int TierId;
    int AppID;

    public CampusModel() {
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

    public int getRowNo() {
        return RowNo;
    }

    public void setRowNo(int rowNo) {
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
}
