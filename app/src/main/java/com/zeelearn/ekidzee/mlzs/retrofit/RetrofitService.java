package com.zeelearn.ekidzee.mlzs.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zee.zeedoc.model.FileModel;
import com.zeelearn.ekidzee.mlzs.model.DigitalSupportModel;
import com.zeelearn.ekidzee.mlzs.model.GenericResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.request.CampusRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.DigitalSupportRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.ECampusLogRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.ECampusRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.LogRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.LoginRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.request.TipsRequest;
import com.zeelearn.ekidzee.mlzs.retrofit.response.CampusModel;
import com.zeelearn.ekidzee.mlzs.retrofit.response.CampusResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.response.DigitalSupportResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.response.ECampusResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.response.LoginResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.response.NewsResponse;
import com.zeelearn.news.retrofit.response.tips.ContentResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitService {

    String sessionId;
    private static RetrofitService ourInstance ;
    ApiService mApi;
    private Context mcontext;

    public static RetrofitService getInstance(Context mContext) {
        if(ourInstance==null){
            ourInstance = new RetrofitService(mContext);
        }
        return ourInstance;
    }

    private RetrofitService(Context context) {
        //Creating an object of our api interface
        mcontext  = context;
        mApi = RetroClient.getApiService(context);
    }

    public void checkLogin(String userName, String password,
                           final RetrofitServiceListener mListener){
        final LoginResponse info = new LoginResponse();
        if(TextUtils.isEmpty(userName)){
            mListener.onFailure(info,null);
            return;
        }
        mListener.onRequestStarted(null);
        Call<LoginResponse> call = mApi.checkLogin(new LoginRequest(userName,password));
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    LoginResponse userInfo  = response.body();
                    mListener.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mListener.onFailure(info,t);
            }
        });
    }


    public void getECampus(int userid,
                           final RetrofitServiceListener mListener){
        final LoginResponse info = new LoginResponse();

        mListener.onRequestStarted(null);
        Call<ECampusResponse> call = mApi.getECampus(new ECampusRequest(userid));
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<ECampusResponse>() {
            @Override
            public void onResponse(Call<ECampusResponse> call, Response<ECampusResponse> response) {

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    ECampusResponse userInfo  = response.body();
                    mListener.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<ECampusResponse> call, Throwable t) {
                mListener.onFailure(info,t);
            }
        });
        /*call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {

                if (response.isSuccessful()) {
                    *//**
                     * Got Successfully
                     *//*
                    FileModel userInfo  = response.body();
                    mListener.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                mListener.onFailure(info,t);
            }
        });*/
    }

    public void getECampusTest(int userid,
                           final RetrofitServiceListener mListener){
        final LoginResponse info = new LoginResponse();

        mListener.onRequestStarted(null);
        Call<FileModel> call = mApi.getECampus();
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {

                if (response.isSuccessful()) {
                    FileModel userInfo  = response.body();
                    mListener.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                mListener.onFailure(info,t);
            }
        });
    }


    public void getECampusList(int userId,int appId,
                           final RetrofitServiceListener mListener){
        final List<CampusModel> info = new ArrayList<>();

        mListener.onRequestStarted(null);
        Call<ECampusResponse> call = mApi.getECampusList(new CampusRequest(userId,appId));
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<ECampusResponse>() {
            @Override
            public void onResponse(Call<ECampusResponse> call, Response<ECampusResponse> response) {

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    ECampusResponse campusList  = response.body();
                    CampusResponse response1 = new CampusResponse();
                    //response1.setModelList(campusList);
                    mListener.onResponse(response1);
                }
            }

            @Override
            public void onFailure(Call<ECampusResponse> call, Throwable t) {
                mListener.onFailure(info,t);
            }
        });
    }


    public void getTips(TipsRequest request,
                        final com.zeelearn.news.retrofit.RetrofitServiceListener mListener){
        final List<ContentResponse> info = new ArrayList<>();

        mListener.onRequestStarted(null);
        Call<NewsResponse> call = mApi.getTips(request);
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    NewsResponse userInfo  = response.body();
                    mListener.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                mListener.onFailure(info,t);
            }
        });
    }

    public void insertLogs(LogRequest request,
                           final RetrofitServiceListener mListener){
        final List<ContentResponse> info = new ArrayList<>();

        if(mListener!=null)
            mListener.onRequestStarted(null);
        Call<GenericResponse> call = mApi.insertLogs(request);
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    GenericResponse userInfo  = response.body();
                    if(mListener!=null)
                    mListener.onResponse(userInfo);
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                if(mListener!=null)
                mListener.onFailure(info,t);
            }
        });
    }

    public void getDigitalSupportV3(DigitalSupportRequest request, final RetrofitServiceListener mListener){
        final List<DigitalSupportModel> info = new ArrayList<>();
        mListener.onRequestStarted(null);
        mApi =  RetroClient.getECampusApiService(mcontext);
        Call<List<DigitalSupportModel>> call = mApi.getDigitalSupport3(request);
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<List<DigitalSupportModel>>() {
            @Override
            public void onResponse(Call<List<DigitalSupportModel>> call, Response<List<DigitalSupportModel>> response) {

                Log.d("D/OkHttp","response received...."+response.code());

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    Log.d("D/OkHttp","response received....");
                    List<DigitalSupportModel> supportModelsList  = response.body();
                    DigitalSupportResponse mResponse = new DigitalSupportResponse(true,supportModelsList);
                    mListener.onResponse(mResponse);
                }
            }

            @Override
            public void onFailure(Call<List<DigitalSupportModel>> call, Throwable t) {
                Log.d("D/OkHttp","response error");
                DigitalSupportResponse mResponse = new DigitalSupportResponse(false,null);
                mListener.onFailure(mResponse,t);
            }
        });
    }


    public void sendAuditLogEvent(ECampusLogRequest request,
                                  final RetrofitServiceListener mListener){
        Log.d("D/OkHttp","getUserList ");
        //mApi = RetroClient.getApiServiceSupoortAPI(mcontext);
        Call<String> call = mApi.auditLogEvent(request);
        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.d("D/OkHttp","response received...."+response.code());

                if (response.isSuccessful()) {
                    /**
                     * Got Successfully
                     */
                    Log.d("D/OkHttp","response received....");
                    // String userLists  = response.body();
                    //mListener.onResponse(userLists);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("D/OkHttp","response error");
                Log.d("D/OkHttp",t.getLocalizedMessage());
                //mListener.onFailure("FAIL",t);
            }
        });
    }

}
