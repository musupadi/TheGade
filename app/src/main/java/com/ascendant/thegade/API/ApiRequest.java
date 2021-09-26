package com.ascendant.thegade.API;

import com.ascendant.thegade.Model.Response.ResponseArrayObject;
import com.ascendant.thegade.Model.Response.ResponseObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("login")
    Call<ResponseObject> login(@Header("Authorization") String authHeader,
                               @Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("pekerjaan")
    Call<ResponseArrayObject> DaftarPekerjaan(@Header("Authorization") String authHeader,
                                              @Field("id_karyawan") String id_karyawan);

    @FormUrlEncoded
    @POST("absen/daftar")
    Call<ResponseArrayObject> DaftarAbsen(@Header("Authorization") String authHeader,
                                              @Field("id_karyawan") String id_karyawan);

    @FormUrlEncoded
    @POST("user/profil")
    Call<ResponseObject> ProfileUser(@Header("Authorization") String authHeader,
                                              @Field("id_karyawan") String id_karyawan);

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

    @FormUrlEncoded
    @POST("user/change_pass")
    Call<ResponseObject> ChangePassword(@Header("Authorization") String authHeader,
                                        @Field("id_karyawan") String id_karyawan,
                                        @Field("pass_lama") String pass_lama,
                                        @Field("pass_baru") String pass_baru,
                                        @Field("pass_conf") String pass_conf);

    @Multipart
    @POST("reimburse/pengajuan")
    Call<ResponseObject> ReimbursePengajuan(@Header("Authorization") String authHeader,
                                            @Part("id_karyawan") RequestBody id_karyawan,
                                            @Part("jenis_klaim") RequestBody jenis_klaim,
                                            @Part("tgl_pengajuan") RequestBody tgl_pengajuan,
                                            @Part("tgl_berkas") RequestBody tgl_berkas,
                                            @Part("nominal_reimburse") RequestBody nominal_reimburse,
                                            @Part MultipartBody.Part file_reimburse);

    @Multipart
    @POST("dispensasi/request")
    Call<ResponseObject> DispensasionPengajuan(@Header("Authorization") String authHeader,
                                            @Part("id_karyawan") RequestBody id_karyawan,
                                            @Part("jenis_dispensasi") RequestBody jenis_klaim,
                                            @Part("tgl_mulai") RequestBody tgl_pengajuan,
                                            @Part("tgl_selesai") RequestBody tgl_berkas,
                                            @Part("keterangan_dispensasi") RequestBody nominal_reimburse,
                                            @Part MultipartBody.Part file_reimburse);

    @POST("location")
    Call<ResponseObject> location(@Header("Authorization") String authHeader);

}
