package com.zeelearn.ekidzee.mlzs;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.zee.zeedoc.ZeeDocApp;
import com.zeelearn.ekidzee.mlzs.utils.ForceUpdateChecker;


import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = MyApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        ZeeDocApp.initialize(this);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // set in-app defaults
        /*Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, 8);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
                "https://play.google.com/store/apps/details?id=com.sembozdemir.renstagram");

        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);*/
        firebaseRemoteConfig.fetch(60) // fetch every minutes
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "remote config is fetched.");
                            firebaseRemoteConfig.activateFetched();
                        }
                    }
                });

    }

    public void setUserId(String userId) {
        mFirebaseAnalytics.setUserId(userId);
    }

    public void registerTopic(String topicName) {
        //mFirebaseAnalytics.setUserProperty("user_activity", eventname);
        FirebaseMessaging.getInstance().subscribeToTopic(topicName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("ZEEFirebaseMessaging","Topic registered "+topicName);;
                //Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveEvent(String usertype) {
        mFirebaseAnalytics.setUserProperty("user_activity", usertype);
    }

    public void saveEvent(String filename, String module) {
        Bundle params = new Bundle();
        params.putString("file_name", filename);
        params.putString("module", module);
        mFirebaseAnalytics.logEvent(module, params);
    }

    public void saveCampusEvent(String filename, String type) {
        Bundle params = new Bundle();
        params.putString("file_name", filename);
        params.putString("type", type);
        mFirebaseAnalytics.logEvent("eCampus_VIEW", params);
    }

}
