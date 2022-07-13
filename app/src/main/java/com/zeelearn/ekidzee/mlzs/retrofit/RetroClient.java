package com.zeelearn.ekidzee.mlzs.retrofit;

import android.content.Context;


import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;

import java.io.IOException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

   // private static final String ROOT_URL = "http://115.112.90.194:81/";
    //private static final String ROOT_URL = "http://23.23.42.14:8080/";
    //private static final String ROOT_URL = "http://202.46.202.97/";
    private static final String EKIDZEE_URL = "https://www.ekidzee.com/";

    private static Retrofit getRetrofitInstance(final Context context) {


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        TimeZone tz = TimeZone.getDefault();
                        //System.out.println("TimeZone   "+tz.getDisplayName(false, TimeZone.SHORT)+" Timezon id :: " +tz.getID());
                        Request request = chain.request().newBuilder()
                                .addHeader("user_id", ""+ ZeePref.getUserId(context))
                                .addHeader("fcm_token", ZeePref.getToken(context))
                                .addHeader("tz_id",tz.getID()).addHeader("tz_name", tz.getDisplayName(false, TimeZone.SHORT)).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseuel))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit getECampusRetrofitInstance(final Context context) {


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        TimeZone tz = TimeZone.getDefault();
                        //System.out.println("TimeZone   "+tz.getDisplayName(false, TimeZone.SHORT)+" Timezon id :: " +tz.getID());
                        Request request = chain.request().newBuilder()
                                .addHeader("user_id", ""+ ZeePref.getUserId(context))
                                .addHeader("fcm_token", ZeePref.getToken(context))
                                .addHeader("tz_id",tz.getID()).addHeader("tz_name", tz.getDisplayName(false, TimeZone.SHORT)).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(EKIDZEE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    private static Retrofit getRetrofitInstanceMap(final Context context) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        TimeZone tz = TimeZone.getDefault();
                        //System.out.println("TimeZone   "+tz.getDisplayName(false, TimeZone.SHORT)+" Timezon id :: " +tz.getID());
                        Request request = chain.request().newBuilder()
                                .addHeader("user_id", ""+ZeePref.getUserId(context))
                                .addHeader("tz_id",tz.getID()).addHeader("tz_name", tz.getDisplayName(false, TimeZone.SHORT)).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiService getApiService(Context context) {
        return getRetrofitInstance(context).create(ApiService.class);
    }

    public static ApiService getECampusApiService(Context context) {
        return getECampusRetrofitInstance(context).create(ApiService.class);
    }


}
