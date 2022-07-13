package com.zeelearn.ekidzee.mlzs.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;


import com.zeelearn.ekidzee.mlzs.fragments.bs.AudioPlayerBottomSheet;
import com.zeelearn.ekidzee.mlzs.fragments.bs.BSErrorDialogFragment;
import com.zeelearn.ekidzee.mlzs.iface.OnDialogClickListener;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public final class Utility {

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showErrorMessageSheet(FragmentManager manager, String title, String message) {
        BSErrorDialogFragment bottomSheetFragment = new BSErrorDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LocalConstance.CONST_TITLE, title);
        bundle.putString(LocalConstance.CONST_MESSAGE, message);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(manager, bottomSheetFragment.getTag());
    }

   public static void showAudioFile(FragmentManager manager, String title, String url) {
        AudioPlayerBottomSheet bottomSheetFragment = new AudioPlayerBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putString(LocalConstance.CONST_TITLE, title);
        bundle.putString(LocalConstance.CONST_MESSAGE, url);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(manager, bottomSheetFragment.getTag());
    }

    /*public static void onCountDownTimer(int seconds, onTimerCompleted mListerner) {
        new CountDownTimer(seconds, 1000) {

            public void onTick(long millisUntilFinished) {
                mListerner.onTick(millisUntilFinished);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mListerner.onFinish();
            }
        }.start();
    }*/

    public static void showErrorMessageSheet(FragmentManager manager, String title, String message, boolean isCancelable, OnDialogClickListener mListener) {
        BSErrorDialogFragment bottomSheetFragment = new BSErrorDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LocalConstance.CONST_TITLE, title);
        bundle.putString(LocalConstance.CONST_MESSAGE, message);

        bundle.putSerializable("listener", mListener);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.setCancelable(isCancelable);
        bottomSheetFragment.show(manager, bottomSheetFragment.getTag());
    }

    /*public static void showErrorMessageSheet(FragmentManager manager, String title, String message, OnDialogClickListener mListener) {
        BSErrorDialogMultiFragment bottomSheetFragment = new BSErrorDialogMultiFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LocalConstance.CONST_TITLE, title);
        bundle.putString(LocalConstance.CONST_MESSAGE, message);
        bottomSheetFragment.setCancelable(false);
        bundle.putSerializable("listener", mListener);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(manager, bottomSheetFragment.getTag());
    }*/

    public static void showIllumeSheet(FragmentManager manager) {
        /*BSIllumeDialogFragment bottomShet = new BSIllumeDialogFragment();
        bottomShet.show(manager, bottomShet.getTag());*/
    }

    public static void launchLocationSettings(Context context) {
        try {
            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(viewIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDynamicYearList() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> yearList = new ArrayList<>();
        int lastYear = currentYear - 9;
        for (int index = lastYear; index < (currentYear + 1); index++) {
            yearList.add(String.valueOf(index));
        }
        return yearList;
    }

    public static int toInt(String value){
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            return 0;
        }
    }

    public  static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }

    public static int getAcdamicYear(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yy");
            Date date = new Date();
            return Integer.parseInt(sdf.format(date));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;

    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getImei(Context context){
        return getAndroidId(context);
//        String imei = "";
//        if(PermissionUtil.hasPermissions(context,Manifest.permission.READ_PHONE_STATE)) {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//
//                return getAndroidId(context);
//            }
//            /*if (android.os.Build.VERSION.SDK_INT >= 26) {
//                imei = telephonyManager.getImei();
//            } else {
//                imei = telephonyManager.getDeviceId();
//            }*/
//        }
//        return imei;
    }

    public static String getSource() {
        return "Android";
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
