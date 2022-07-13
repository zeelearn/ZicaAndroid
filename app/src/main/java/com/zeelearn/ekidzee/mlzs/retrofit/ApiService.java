package com.zeelearn.ekidzee.mlzs.retrofit;

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
import com.zeelearn.ekidzee.mlzs.retrofit.response.ECampusResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.response.LoginResponse;
import com.zeelearn.ekidzee.mlzs.retrofit.response.NewsResponse;
import com.zeelearn.news.retrofit.response.tips.ContentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/ZicaDataServices/api/User/IsValidUser")
    Call<LoginResponse> checkLogin(@Body LoginRequest body);

    @POST("/ZillDoc/getDocList")
    Call<FileModel> getECampus();

    @POST("/ZicaDataServices/api/ds/GetDigitalSupport")
    Call<ECampusResponse> getECampus(@Body ECampusRequest body);

    @POST("/ZicaDataServices/api/ds/GetDigitalSupport")
    Call<ECampusResponse> getECampusList(@Body CampusRequest body);

    @POST("/zicadataservices/api/user/GetTipsData")
    Call<NewsResponse> getTips(@Body TipsRequest body);

    @POST("/zicadataservices/api/log/AuditLog")
    Call<GenericResponse> insertLogs(@Body LogRequest body);


    @POST("/Android/Get_DigitalSupportApi_V3")
    Call<List<DigitalSupportModel>> getDigitalSupport3(@Body DigitalSupportRequest request);

    @POST("/android/LogEcampus")
    Call<String> auditLogEvent(@Body ECampusLogRequest request);

}
