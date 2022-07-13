package com.zeelearn.ekidzee.mlzs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.zeelearn.ekidzee.mlzs.activities.DashboardNew;
import com.zeelearn.ekidzee.mlzs.activities.LoginActivity;
import com.zeelearn.ekidzee.mlzs.activities.RegistrationActivity;
import com.zeelearn.ekidzee.mlzs.fragments.bs.AboutUsFragment;
import com.zeelearn.ekidzee.mlzs.retrofit.request.login.MenuModel;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;


import java.util.List;

public class BaseActivity extends AppCompatActivity {

    ProgressDialog mProgressBar = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void clearMenus(){
        new Delete().from(MenuModel.class).execute();
    }

    public static List<MenuModel> getAllMenus() {
        return new Select()
                .from(MenuModel.class)
                .orderBy("menu_text ASC")
                .execute();
    }


    /**
     * Inilise Dialog
     * */
    private void initDialog(){
        if(!isFinishing()) {
            mProgressBar = new ProgressDialog(this);
            mProgressBar.setCancelable(false);//you can cancel it by pressing back button
        }
    }
    /**
     * Show Error Message
     * */
    public void showErrorAlert(String title,String message){
        if(!isFinishing()) {
            if (builder == null) {
                initAlertDialog();
            }
            builder.setTitle(title).setMessage(message).show();
        }
    }


    AlertDialog.Builder builder;
    /**
     * Alert Dialog not doing anything in onclick listener
     * */
    private void initAlertDialog(){
        if(!isFinishing()) {
            builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            // Create the AlertDialog object and return it
            builder.create();
        }
    }

    /**
     * Show Progress Dialog
     * */
    public void showProgressDialog(String message){
        if(!isFinishing()) {
            if (mProgressBar == null) {
                initDialog();
            }
            mProgressBar.setMessage(message);
            if(!mProgressBar.isShowing())
                mProgressBar.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if(mProgressBar!=null){
            if(mProgressBar.isShowing())
                mProgressBar.dismiss();
            mProgressBar = null;
        }*/
    }

    /**
     * hide Progress Dialog
     * */
    public void hideDialog(){
        if(!isFinishing()) {
            if (mProgressBar != null && mProgressBar.isShowing()) {
                mProgressBar.dismiss();
            }
        }
    }

    public static int getPermissionStatus(Activity mActivity, int status, String permission) {

        if (mActivity != null) {
            if (status == PackageManager.PERMISSION_GRANTED) {
                return 0;
            } else if (status == PackageManager.PERMISSION_DENIED) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                    //Show permission explanation dialog...
                    return -1;
                } else {
                    //Never ask again selected, or device policy prohibits the app from having that permission.
                    //So, disable that feature, or fall back to another situation...

                    return -2;
                }
            }
        } else if (mActivity != null) {
            if (ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_GRANTED) {
                return 0;
            } else {
                //Do the stuff that requires permission...
                return -1;
            }
        }
        return -3;
    }

    public void navigateLogin(){
        if(ZeePref.getUserId(getApplicationContext())>0){
            navigateDashboard();
        }else {
            if(BuildConfig.FLAVOR.equalsIgnoreCase("mlzs")){
                Intent in = new Intent(getApplicationContext(), RegistrationActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }else{
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        }
    }
    public void navigateDashboard(){
        Intent in = new Intent(getApplicationContext(), DashboardNew.class);
        try {
            if(getIntent()!=null && getIntent().getExtras()!=null && getIntent().hasExtra("action")){
                in.putExtra("action",getIntent().getExtras().getString("action"));
                in.putExtra("notification_message", getIntent().getExtras().getString("notification_message"));
                in.putExtra("Title", getIntent().getExtras().getString("Title"));
                in.putExtra("Message", getIntent().getExtras().getString("Message"));
                in.putExtra("image", getIntent().getExtras().getString("image"));
                in.putExtra("URL", getIntent().getExtras().getString("URL"));
            }
        }catch (Exception e){}
        startActivity(in);
        finish();
    }


    public void showProfile(){
        /*ProfileDialogFragment bottomSheetFragment = new ProfileDialogFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());*/
    }

    public void showAboutUs(){
        AboutUsFragment bottomSheetFragment = new AboutUsFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

}
