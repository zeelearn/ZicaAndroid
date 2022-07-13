package com.zeelearn.ekidzee.mlzs.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.SplashActivity;

public class NotificationUtil {

    public static final String TAG = NotificationUtil.class.getCanonicalName();
    public static final String CHANNEL_ID = "zica";
    public static final String CHANNEL_NAME = "zica";
    public static final int REQ_CODE = 1005;
    public static final String KEY_ACTION = "action";


    /**
     * TEACHER Induction action
     */
    public static final String ACTION_NEWS = "news";

    /**
     * TIPS action
     */
    public static final String ACTION_PLACEMENT = "placement";


    /**
     * TEACHER Induction action
     */
    public static final String ACTION_PROMO = "promo";


    private static NotificationModel getNotificatinModel(Context context, String type) {
        NotificationModel notificationModel = new NotificationModel();
        Intent intent = new Intent(context, SplashActivity.class);
        try {
            intent.putExtra("action",type);
            Log.d(TAG, "Action  found  " + intent.getAction());

        } catch (Exception e) {
            Log.d(TAG, "get Exceptiopn " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        notificationModel.setIntent(intent);
        return notificationModel;
    }

    public static void showNotification(Context context, String type, String title, String message, String url) {
        NotificationModel model = getNotificatinModel(context, type);
        Intent intent = model.getIntent();
        //message = model.getMessage();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notification_message", model.getMessage());
        intent.putExtra("activity", "MenuActivity");
        intent.putExtra("Title", title);
        intent.putExtra("Message", message);
        intent.putExtra("URL", url);
        intent.putExtra("image", url);
        intent.putExtra("type", type);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;

        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                REQ_CODE,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


    public static void showBigNotification(Context context, String imgurl, String type, Bitmap bitmap, String title, String message, boolean isb, String ts, String url) {

        Log.d(TAG, "showBignotification");

        NotificationModel model = getNotificatinModel(context, type);
        Intent intent = model.getIntent();
        //message = model.getMessage();
        intent.putExtra("Title", title);
        intent.putExtra("Message", message);
        intent.putExtra("image", imgurl);
        intent.putExtra("type", type);
        intent.putExtra("is_Background", isb);
        intent.putExtra("URL", url);
        intent.putExtra("TimeStamp", ts);
        intent.putExtra("activity", "MenuActivity");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        TaskStackBuilder TSB = TaskStackBuilder.create(context);
        TSB.addParentStack(SplashActivity.class);
        TSB.addNextIntent(intent);

        PendingIntent pendingIntent = TSB.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .build();

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

    }

}
