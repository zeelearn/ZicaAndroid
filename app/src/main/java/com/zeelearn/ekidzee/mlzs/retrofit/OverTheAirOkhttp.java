package com.zeelearn.ekidzee.mlzs.retrofit;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zeelearn.ekidzee.mlzs.iface.OnResponseListener;
import com.zeelearn.ekidzee.mlzs.retrofit.response.CampusModel;
import com.zeelearn.ekidzee.mlzs.retrofit.response.CampusResponse;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OverTheAirOkhttp {
    static MediaType MEDIA_TYPE = MediaType.parse("application/json");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String LOG ="D/OkHttp";




    public static void sendPostRequest(String url,JSONObject requestData, OnResponseListener mListener) {
        //Logger.d(LOG, "DsendPostRequest : ");
        System.out.println("D/OkHttp sendPostRequest ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            OkHttpClient client = new OkHttpClient();

            /*Request request = new Request.Builder()
                    .url(url)
                    .build();*/
            //Logger.d(LOG, "sendPostRequest : ");
            System.out.println("D/OkHttp endPostRequest ");
            RequestBody body = RequestBody.create(MEDIA_TYPE, requestData.toString());
            //RequestBody body = RequestBody.create(requestData.toString().getBytes(), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //Logger.d(LOG, e.getLocalizedMessage());
                    System.out.println("D/OkHttp onFailure "+e.getLocalizedMessage());
                    call.cancel();
                    mListener.onSuccess(e.getLocalizedMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //Logger.d(LOG, "onResponse : ");
                    System.out.println("D/OkHttp onResponse ");
                    final String myResponse = response.body().string();
                    //Logger.d(LOG, "onResponse : " + myResponse);
                    System.out.println("D/OkHttp onResponse 123"+myResponse);

                    try {
                        Type founderListType = new TypeToken<ArrayList<CampusModel>>() {
                        }.getType();

                        //JSONArray object = new JSONArray(myResponse);
                        //System.out.println("D/OkHttp JSON ARRAY  " + object.toString());
                        Gson gson = new Gson();
                        List<CampusModel> campusList = gson.fromJson(myResponse, founderListType);

                        System.out.println("D/OkHttp campusList size  " + campusList.size());
                        CampusResponse response1 = new CampusResponse();
                        response1.setModelList(campusList);
                        /*for (int index=0;index<campusList.size();index++){
                            System.out.println("D/OkHttp Class found  " + campusList.get(index).getClassName());
                            System.out.println("D/OkHttp supportName found  " + campusList.get(index).getSupportName());
                            System.out.println("D/OkHttp subject found  " + campusList.get(index).getSubjectName());
                        }*/

                        //System.out.println("D/OkHttp supportName found  " + object.toString());
                        mListener.onSuccess(response1);
                    }catch (Exception e){
                        System.out.println("D/OkHttp supportName found  " + e.getLocalizedMessage());
                    }


                }
            });
        } else {
            doPost(url, mListener, "POST");
        }
    }

    public static void sendGetRequest(String url, OnResponseListener mListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            OkHttpClient client = new OkHttpClient();
            Log.d("Requesting", "URL  : " + url);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("Requesting", e.getLocalizedMessage());
                    call.cancel();
                    mListener.onSuccess(e.getLocalizedMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    /*Log.d("Requesting","onResponse : ");*/
                    final String myResponse = response.body().string();
                    Log.d("Requesting", "onResponse : " + myResponse);
                    mListener.onSuccess(myResponse);
                }
            });
        } else {
            doPost(url, mListener, "GET");
        }

    }

    private static BufferedReader getBufferReader(InputStream is) {
        BufferedReader br = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        } else {
            br = new BufferedReader(new InputStreamReader(is));
        }
        return br;
    }

    public static void doPost(String urlString, OnResponseListener mListener, String type) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                String responseMessage;
                try {
                    String result;
                    URL object = new URL(urlString);

                    HttpURLConnection con = (HttpURLConnection) object.openConnection();
                    con.setRequestProperty("connection", "close");
                    try {
                        BufferedReader bufferedReader = getBufferReader(con.getInputStream());
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        result = stringBuilder.toString();
                        Log.d("Requesting", urlString);
                        Log.d("Requesting", result);
                        return result;
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage(), e);
                        return null;
                    } finally {
                        con.disconnect();
                    }

                } catch (Exception e) {
                    responseMessage = e.getMessage();
                    e.printStackTrace();
                }
                return responseMessage;
            }

            @Override
            protected void onPostExecute(String responseMessage) {
                super.onPostExecute(responseMessage);
                mListener.onSuccess(responseMessage);
            }
        }.execute();

    }
}
