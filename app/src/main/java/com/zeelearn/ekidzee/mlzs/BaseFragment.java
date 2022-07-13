package com.zeelearn.ekidzee.mlzs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.zeelearn.ekidzee.mlzs.iface.OnDialogClickListener;
import com.zeelearn.ekidzee.mlzs.utils.Utility;
import com.zeelearn.ekidzee.mlzs.utils.Utils;

public class BaseFragment extends Fragment {
    ProgressDialog mProgressBar = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inilise Dialog
     * */
    private void initDialog(){
        if(isAdded()) {
            mProgressBar = new ProgressDialog(getContext());
            mProgressBar.setCancelable(true);//you can cancel it by pressing back button
        }
    }
    /**
     * Show Error Message
     * */
    public void showErrorAlert(String title,String message){
        if(isAdded()) {
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
        if(isAdded()) {
            builder = new AlertDialog.Builder(getContext());
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
        if(isAdded()) {
            if (mProgressBar == null) {

                initDialog();
            }
            mProgressBar.setMessage(message);
            mProgressBar.show();
        }
    }
    /**
     * hide Progress Dialog
     * */
    public void hideDialog(){
        if(isAdded()) {
            if (mProgressBar != null && mProgressBar.isShowing()) {
                mProgressBar.dismiss();
            }
        }
    }

    protected void showErrormessageAndFinish(String message) {
        Utils.showErrorMessageSheet(getActivity().getSupportFragmentManager(), getString(R.string.title_error), message, false, new OnDialogClickListener() {
            @Override
            public void onOkClicked(Object object) {
                getActivity().finish();
            }

            @Override
            public void onCancelClicked() {

            }
        });
    }


    public void navigateDashboard() {

    }

    public void updateLoginData(Object loginResponse){

    }

    public int toInt(String  value){
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            return 0;
        }
    }

    protected void showMessageNoInternet() {
        Utility.showErrorMessageSheet(getActivity().getSupportFragmentManager(), getString(R.string.title_alert), getString(R.string.msg_no_internet));
    }

    protected void showAlertMessage(String message) {
        Utility.showErrorMessageSheet(getActivity().getSupportFragmentManager(), getString(R.string.title_alert), message);
    }

    protected void showErrorMessage(String message) {
        Utility.showErrorMessageSheet(getActivity().getSupportFragmentManager(), getString(R.string.title_error), message);
    }

    protected void showMessage(String message) {
        Utility.showMessage(getContext(), message);
    }


}
