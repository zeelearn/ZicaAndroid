package com.zeelearn.ekidzee.mlzs.retrofit.request;

public class LogRequest {

    String LogType;
    String EntityType;
    String EntityId;
    String ErrorDesc;
    String UserId;
    String VisitLink;
    String Remarks;

    public LogRequest(String logType, String entityType, String entityId, String errorDesc, String userId, String visitLink, String remarks) {
        LogType = logType;
        EntityType = entityType;
        EntityId = entityId;
        ErrorDesc = errorDesc;
        UserId = userId;
        VisitLink = visitLink;
        Remarks = remarks;
    }

    public String getLogType() {
        return LogType;
    }

    public void setLogType(String logType) {
        LogType = logType;
    }

    public String getEntityType() {
        return EntityType;
    }

    public void setEntityType(String entityType) {
        EntityType = entityType;
    }

    public String getEntityId() {
        return EntityId;
    }

    public void setEntityId(String entityId) {
        EntityId = entityId;
    }

    public String getErrorDesc() {
        return ErrorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        ErrorDesc = errorDesc;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getVisitLink() {
        return VisitLink;
    }

    public void setVisitLink(String visitLink) {
        VisitLink = visitLink;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }
}
