package com.zeelearn.ekidzee.mlzs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.zeelearn.ekidzee.mlzs.BaseActivity;
import com.zeelearn.ekidzee.mlzs.BuildConfig;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.model.GenericResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitService;
import com.zeelearn.ekidzee.mlzs.retrofit.RetrofitServiceListener;
import com.zeelearn.ekidzee.mlzs.retrofit.request.LogRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.login.MenuModel;
import com.zeelearn.ekidzee.mlzs.retrofit.request.login.UserInfoModel;
import com.zeelearn.ekidzee.mlzs.retrofit.response.LoginResponse;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;
import com.zeelearn.ekidzee.mlzs.utils.Utils;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;



public class LoginActivity extends BaseActivity implements View.OnClickListener, RetrofitServiceListener, GoogleApiClient.ConnectionCallbacks {

    Button btnLogin;

    EditText edtUserName,edtPassword;
    RetrofitServiceListener mServiceListener;
    CheckBox chk_terms;
    TextView txt_alert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.cirLoginButton);
        btnLogin.setOnClickListener(this);
        edtPassword = findViewById(R.id.edt_password);
        edtUserName = findViewById(R.id.edt_username);
        mServiceListener = this;
        insertDummeyAccount();
        chk_terms = findViewById(R.id.chk_terms);
        TextView textView_terms = findViewById(R.id.txt_terms);
        textView_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
            }
        });
    }

    private void insertDummeyAccount(){
        if(BuildConfig.DEBUG) {
            edtUserName.setText("T83");
            edtPassword.setText("sawan@kumar1");/*edtUserName.setText("E-2-001");
            //edtPassword.setText("yAClshj+fPE/08lIjmjDSA==");*/
        }
    }

    @Override
    public void onClick(View v) {
        if(!chk_terms.isChecked()){
            showErrorAlert("Alert","Please accept the Terms and Conditions");
        }else if(v.getId() == R.id.cirLoginButton){
            if(isValidForm()) {
                RetrofitService.getInstance(LoginActivity.this).checkLogin(edtUserName.getText().toString(), edtPassword.getText().toString(), mServiceListener);
            }
        }
    }

    private boolean isValidForm(){
        String uName = edtUserName.getText().toString();
        String uPass = edtPassword.getText().toString();

        if(TextUtils.isEmpty(uName)){
            showErrorAlert("Error","Please Enter User name and try again");
            return false;
        }else if(TextUtils.isEmpty(uPass)){
            showErrorAlert("Error","Please Enter your password and try again");
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
                    //navigateDashboard();
                    if(mUserResponse.getData()!=null && mUserResponse.getData().getUserInfoModels()!=null && mUserResponse.getData().getUserInfoModels().size()>0){
                        UserInfoModel userModel = mUserResponse.getData().getUserInfoModels().get(0);
                        //clearMenus();
                        if(userModel!=null && userModel.getIsValidLogin()) {
                            /*if(mUserResponse.getData().getMenuList()!=null){
                                for (int index=0;index<mUserResponse.getData().getMenuList().size();index++){
                                    new MenuModel(mUserResponse.getData().getMenuList().get(index).getMenu_Id(),mUserResponse.getData().getMenuList().get(index).getMenuText()).save();
                                }
                            }*/

                            ZeePref.setUserId(LoginActivity.this, (int) userModel.getUser_id());
                            ZeePref.setUserName(LoginActivity.this, userModel.getUser_name());
                            ZeePref.setUserType(LoginActivity.this, userModel.getUser_Type());
                            ZeePref.setUserTypeName(LoginActivity.this, userModel.getUser_TypeName());
                            ZeePref.setUID(LoginActivity.this, Utils.toInt(userModel.getUID()));
                            ZeePref.setUserCode(LoginActivity.this, userModel.getUser_code());
                            ZeePref.setUserCode(LoginActivity.this, userModel.getUser_code());
                            ZeePref.setDisplayName(LoginActivity.this, userModel.getDisplay_name());
                            ZeePref.setContactPerson(LoginActivity.this, userModel.getDisplay_name());
                            ZeePref.setEmailId(LoginActivity.this, userModel.getEmail_id());
                            ZeePref.setMobileNo(LoginActivity.this, userModel.getMobile_no());
                            ZeePref.setCanEdit(LoginActivity.this, userModel.getCanEdit());
                            ZeePref.setExternalUser(LoginActivity.this, userModel.getIsExternalUser());
                            ZeePref.setRefFranid(LoginActivity.this, (int) userModel.getRef_Fran_Id());

                            LogRequest logRequest = new LogRequest("APPLOGIN",userModel.getUser_Type(),""+userModel.getUser_id(),LocalConstance.ACTION_NA,String.valueOf(userModel.getUID()),"",LocalConstance.ACTION_ALL_OK);
                            RetrofitService.getInstance(getApplicationContext()).insertLogs(logRequest,mServiceListener);
                            //navigateDashboard();
                        }else{
                            showErrorAlert(getString(R.string.title_error),getString(R.string.er_invalidlogin));
                        }
                    }else{
                        showErrorAlert(getString(R.string.title_error),getString(R.string.er_invalidlogin));
                    }


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
        }else if(mObject instanceof GenericResponse){
            navigateDashboard();
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
