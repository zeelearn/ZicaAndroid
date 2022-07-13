package com.zeelearn.ekidzee.mlzs.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;
import com.zeelearn.ekidzee.mlzs.utils.NotificationUtil;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static String TAG = "ZEEFirebaseMessaging";


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG, "Firebase Token FOUND");
        Log.e(TAG, token);
        ZeePref.setFCMToken(getApplicationContext(),token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage != null) {
            Log.i(TAG, "Received From : " + remoteMessage.getFrom());
            if (remoteMessage.getNotification() != null) {
                Log.i(TAG, "Firebase Notification : " + remoteMessage.getNotification().getBody());
                String title = remoteMessage.getNotification().getTitle();
                if (TextUtils.isEmpty(title)) {
                    title = getResources().getString(R.string.app_name);
                }
                String nbody = remoteMessage.getNotification().getBody();
                try {
                    JSONObject obj = new JSONObject(nbody);
                    String type = obj.getString("type");

                    NotificationUtil.showNotification(getApplicationContext(), title, type, nbody.substring(0, 1).toUpperCase() + nbody.substring(1).toLowerCase(), "");

                } catch (Exception e) {
                    NotificationUtil.showNotification(getApplicationContext(), title, "", nbody.substring(0, 1).toUpperCase() + nbody.substring(1).toLowerCase(), "");
                }
            }

            if (remoteMessage.getData() != null) {
                Log.i(TAG, "Firebase Data Payload : " + remoteMessage.getData());
                handleDataPayloadMessage(new JSONObject(remoteMessage.getData()));
            }
        }
    }

    private void handleDataPayloadMessage(JSONObject data) {
        try {
            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.has("is_background") && data.getBoolean("is_background");
            String imageUrl = data.has("image") ? data.getString("image") : "";
            String timestamp = data.has("timestamp") ? data.getString("timestamp") : "";
            String type = data.has("type") ? data.getString("type") : "";
            String url = data.has("URL") ? data.getString("URL") : "";
            if (imageUrl.equals("")) {
                NotificationUtil.showNotification(getApplicationContext(), type, title, message, url);
            } else {
                NotificationUtil.showBigNotification(getApplicationContext(), imageUrl, type, getBitmapFromURL(imageUrl), title, message, isBackground, timestamp, url);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
