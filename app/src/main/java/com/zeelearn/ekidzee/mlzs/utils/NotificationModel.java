package com.zeelearn.ekidzee.mlzs.utils;

import android.content.Intent;

public class NotificationModel {
    String message;
    Intent intent;
    String rawData;

    public NotificationModel() {
    }

    public NotificationModel(String message, Intent intent, String rawData) {
        this.message = message;
        this.intent = intent;
        this.rawData = rawData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
}
