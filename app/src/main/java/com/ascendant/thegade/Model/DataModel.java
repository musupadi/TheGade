package com.ascendant.thegade.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {
    @SerializedName("id_karyawan")
    @Expose
    public String id_karyawan;

    @SerializedName("email_karyawan")
    @Expose
    public String email_karyawan;

    @SerializedName("nama_karyawan")
    @Expose
    public String nama_karyawan;

    @SerializedName("nik_karyawan")
    @Expose
    public String nik_karyawan;

    @SerializedName("status_karyawan")
    @Expose
    public String status_karyawan;

    @SerializedName("alamat")
    @Expose
    public String alamat;

    @SerializedName("latitude")
    @Expose
    public String latitude;

    @SerializedName("longitude")
    @Expose
    public String longitude;


    public String getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan = id_karyawan;
    }

    public String getEmail_karyawan() {
        return email_karyawan;
    }

    public void setEmail_karyawan(String email_karyawan) {
        this.email_karyawan = email_karyawan;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan = nama_karyawan;
    }

    public String getNik_karyawan() {
        return nik_karyawan;
    }

    public void setNik_karyawan(String nik_karyawan) {
        this.nik_karyawan = nik_karyawan;
    }

    public String getStatus_karyawan() {
        return status_karyawan;
    }

    public void setStatus_karyawan(String status_karyawan) {
        this.status_karyawan = status_karyawan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
