package com.zeelearn.ekidzee.mlzs.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.zeelearn.ekidzee.mlzs.BaseActivity;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitService;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitServiceListener;
import com.zeelearn.ekidzee.mlzs.retrofit.response.LoginResponse;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;
import com.zeelearn.ekidzee.mlzs.utils.Utils;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;


public class RegistrationActivity extends BaseActivity implements View.OnClickListener, RetrofitServiceListener, GoogleApiClient.ConnectionCallbacks {

    Button btnLogin;

    EditText edtUserName;
    RetrofitServiceListener mServiceListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btnLogin = findViewById(R.id.cirLoginButton);
        btnLogin.setOnClickListener(this);
        //edtPassword = findViewById(R.id.edt_password);
        edtUserName = findViewById(R.id.edt_username);
        mServiceListener = this;
        //insertDummeyAccount();
    }

    private void insertDummeyAccount(){
        edtUserName.setText("T83");
        //edtPassword.setText("sawan@kumar1");/*edtUserName.setText("E-2-001");
        //edtPassword.setText("yAClshj+fPE/08lIjmjDSA==");*/
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cirLoginButton){
            ZeePref.setDisplayName(this,"demo");
            //ZeePref.setUserType(this,"F");
            ZeePref.setUserId(this,23);
            ZeePref.setAppId(this, Utils.toInt(getString(R.string.appid)));
            navigateDashboard();
            /*if(isValidForm()) {
                if(edtUserName.getText().toString().equalsIgnoreCase("demo") && edtPassword.getText().toString().equalsIgnoreCase("demo")){
                    ZeePref.setDisplayName(this,"demo");
                    ZeePref.setUserType(this,"F");
                    ZeePref.setUserId(this,23);
                    ZeePref.setAppId(this,getString(R.string.appid));
                    navigateDashboard();
                }else {
                    RetrofitService.getInstance(RegistrationActivity.this).checkLogin(edtUserName.getText().toString(), edtPassword.getText().toString(), mServiceListener);
                }
            }*/
        }
    }

    private boolean isValidForm(){
        String uName = edtUserName.getText().toString();
        //String uPass = edtPassword.getText().toString();

        if(TextUtils.isEmpty(uName)){
            showErrorAlert("Error","Please Enter User name and try again");
            return false;
        }else{
            return true;
        }

    }

    @Override
    public void onRequestStarted(Object mObject) {
        showProgressDialog("Please wait");
    }

    @Override
    public void onResponse(Object mObject) {
        hideDialog();
        if(mObject instanceof LoginResponse) {
            LoginResponse mUserResponse = (LoginResponse) mObject;
            if(mUserResponse!=null) {
                if(mUserResponse.getResponseCode()== LocalConstance.ACTION_OK){
                    navigateDashboard();
                    /*if(mUserResponse.getData()!=null && mUserResponse.getData().getUserInfoModels()!=null && mUserResponse.getData().getUserInfoModels().size()>0){
                        UserInfoModel userModel = mUserResponse.getData().getUserInfoModels().get(0);
                        clearMenus();
                        if(userModel.getIsValidLogin()) {
                            if(mUserResponse.getData().getMenuList()!=null){
                                for (int index=0;index<mUserResponse.getData().getMenuList().size();index++){
                                    new MenuModel(mUserResponse.getData().getMenuList().get(index).getMenu_Id(),mUserResponse.getData().getMenuList().get(index).getMenuText()).save();
                                }
                            }

                            ZeePref.setUserId(LoginActivity.this, (int) userModel.getUser_id());
                            ZeePref.setUserName(LoginActivity.this, userModel.getUser_name());
                            ZeePref.setUserType(LoginActivity.this, userModel.getUser_Type());
                            ZeePref.setUserTypeName(LoginActivity.this, userModel.getUser_TypeName());
                            ZeePref.setUID(LoginActivity.this, userModel.getUID());
                            ZeePref.setUserCode(LoginActivity.this, userModel.getUser_code());
                            ZeePref.setUserCode(LoginActivity.this, userModel.getUser_code());
                            ZeePref.setDisplayName(LoginActivity.this, userModel.getDisplay_name());
                            ZeePref.setContactPerson(LoginActivity.this, userModel.getDisplay_name());
                            ZeePref.setEmailId(LoginActivity.this, userModel.getEmail_id());
                            ZeePref.setMobileNo(LoginActivity.this, userModel.getMobile_no());
                            ZeePref.setCanEdit(LoginActivity.this, userModel.getCanEdit());
                            ZeePref.setExternalUser(LoginActivity.this, userModel.getIsExternalUser());
                            ZeePref.setRefFranid(LoginActivity.this, (int) userModel.getRef_Fran_Id());

                            navigateDashboard();
                        }else{
                            showErrorAlert(getString(R.string.title_error),getString(R.string.er_invalidlogin));
                        }
                    }*/


                }else{
                    if(TextUtils.isEmpty(mUserResponse.getResponseMessage())){
                        showErrorAlert(getString(R.string.title_error),getString(R.string.er_inff_connection));
                    }else{
                        showErrorAlert(getString(R.string.title_error),mUserResponse.getResponseMessage());
                    }
                }


            }else{
                showErrorAlert(getString(R.string.title_error),getString(R.string.er_inff_connection));
            }
            hideDialog();
        }
    }

    @Override
    public void onFailure(Object mObject, Throwable t) {
        hideDialog();
        showErrorAlert(getString(R.string.title_error),getString(R.string.er_inff_connection));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
