package com.zeelearn.ekidzee.mlzs.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.zeelearn.ekidzee.mlzs.fragments.bs.BSErrorDialogFragment;
import com.zeelearn.ekidzee.mlzs.iface.OnDialogClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public  static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return  sdf.format(date);
    }

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

    public static int toInt(String value){
        try{
            return Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


}
