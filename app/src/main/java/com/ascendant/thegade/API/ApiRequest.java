package com.ascendant.thegade.API;

import com.ascendant.thegade.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("user/Login")
    Call<ResponseModel> login(@Field("username") String username,
                              @Field("password") String password);
}
