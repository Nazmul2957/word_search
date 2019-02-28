package com.example.myapplication.retrofit;

import com.example.myapplication.POJOGetLatestData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by rzroky on 01/01/2019.
 */

public interface ApiInterface {


    @POST("get_all_data")
    @FormUrlEncoded
    Call<List<POJOGetLatestData>> get_all_data(@Field("last_update") String last_update);



}
