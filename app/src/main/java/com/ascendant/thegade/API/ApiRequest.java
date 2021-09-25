package com.ascendant.thegade.API;

import com.ascendant.thegade.Model.Response.ResponseObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseObject> login(@Header("Authorization") String authHeader,
                               @Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("absen/masuk")
    Call<ResponseObject> CheckIn(@Header("Authorization") String authHeader,
                                 @Field("id_karyawan") String id_karyawan,
                                 @Field("waktu_absen") String waktu_absen,
                                 @Field("latitude") String latitude,
                                 @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("cuti")
    Call<ResponseObject> Cuti(@Header("Authorization") String authHeader,
                              @Field("id_karyawan") String id_karyawan,
                              @Field("tgl_cuti_mulai") String tgl_cuti_mulai,
                              @Field("tgl_cuti_selesai") String tgl_cuti_selesai,
                              @Field("alasan_cuti") String alasan_cuti);

    @FormUrlEncoded
    @POST("absen/keluar")
    Call<ResponseObject> CheckOut(@Header("Authorization") String authHeader,
                                 @Field("id_karyawan") String id_karyawan,
                                 @Field("waktu_absen") String waktu_absen,
                                 @Field("latitude") String latitude,
                                 @Field("longitude") String longitude);

    @POST("location")
    Call<ResponseObject> location(@Header("Authorization") String authHeader);
}
