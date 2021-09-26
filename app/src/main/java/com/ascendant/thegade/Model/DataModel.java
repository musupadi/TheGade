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

    //Dispensasi
    @SerializedName("id_dispensasi")
    @Expose
    public String id_dispensasi;

    @SerializedName("id_user")
    @Expose
    public String id_user;

    @SerializedName("jenis_dispensasi")
    @Expose
    public String jenis_dispensasi;

    @SerializedName("keterangan_dispensasi")
    @Expose
    public String keterangan_dispensasi;

    @SerializedName("tgl_pengajuan")
    @Expose
    public String tgl_pengajuan;

    @SerializedName("tgl_mulai")
    @Expose
    public String tgl_mulai;

    @SerializedName("tgl_selesai")
    @Expose
    public String tgl_selesai;

    @SerializedName("file_dispensasi")
    @Expose
    public String lfile_dispensasi;

    @SerializedName("tgl_diterima")
    @Expose
    public String tgl_diterima;

    @SerializedName("status_dispensasi")
    @Expose
    public String status_dispensasi;

    //Dafar Pekerjaan
    @SerializedName("id_pekerjaan")
    @Expose
    public String id_pekerjaan;

    @SerializedName("nama_pekerjaan")
    @Expose
    public String nama_pekerjaan;

    //Profile Worker
    @SerializedName("no_telp_karyawan")
    @Expose
    public String no_telp_karyawan;

    @SerializedName("alamat_karyawan")
    @Expose
    public String alamat_karyawan;

    @SerializedName("unit_kerja")
    @Expose
    public String unit_kerja;

    @SerializedName("alamat_unit")
    @Expose
    public String alamat_unit;

    @SerializedName("jabatan")
    @Expose
    public String jabatan;

    @SerializedName("atasan_langsung")
    @Expose
    public String atasan_langsung;

    @SerializedName("tgl_bergabung")
    @Expose
    public String tgl_bergabung;

    @SerializedName("salary")
    @Expose
    public String salary;


    //Daftar Absen
    @SerializedName("waktu_absen")
    @Expose
    public String waktu_absen;

    @SerializedName("lat_absen")
    @Expose
    public String lat_absen;

    @SerializedName("long_absen")
    @Expose
    public String long_absen;


    @SerializedName("status_absen")
    @Expose
    public String status_absen;





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

    public String getId_dispensasi() {
        return id_dispensasi;
    }

    public void setId_dispensasi(String id_dispensasi) {
        this.id_dispensasi = id_dispensasi;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getJenis_dispensasi() {
        return jenis_dispensasi;
    }

    public void setJenis_dispensasi(String jenis_dispensasi) {
        this.jenis_dispensasi = jenis_dispensasi;
    }

    public String getKeterangan_dispensasi() {
        return keterangan_dispensasi;
    }

    public void setKeterangan_dispensasi(String keterangan_dispensasi) {
        this.keterangan_dispensasi = keterangan_dispensasi;
    }

    public String getTgl_pengajuan() {
        return tgl_pengajuan;
    }

    public void setTgl_pengajuan(String tgl_pengajuan) {
        this.tgl_pengajuan = tgl_pengajuan;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_selesai() {
        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }

    public String getLfile_dispensasi() {
        return lfile_dispensasi;
    }

    public void setLfile_dispensasi(String lfile_dispensasi) {
        this.lfile_dispensasi = lfile_dispensasi;
    }

    public String getTgl_diterima() {
        return tgl_diterima;
    }

    public void setTgl_diterima(String tgl_diterima) {
        this.tgl_diterima = tgl_diterima;
    }

    public String getStatus_dispensasi() {
        return status_dispensasi;
    }

    public void setStatus_dispensasi(String status_dispensasi) {
        this.status_dispensasi = status_dispensasi;
    }

    public String getId_pekerjaan() {
        return id_pekerjaan;
    }

    public void setId_pekerjaan(String id_pekerjaan) {
        this.id_pekerjaan = id_pekerjaan;
    }

    public String getNama_pekerjaan() {
        return nama_pekerjaan;
    }

    public void setNama_pekerjaan(String nama_pekerjaan) {
        this.nama_pekerjaan = nama_pekerjaan;
    }

    public String getNo_telp_karyawan() {
        return no_telp_karyawan;
    }

    public void setNo_telp_karyawan(String no_telp_karyawan) {
        this.no_telp_karyawan = no_telp_karyawan;
    }

    public String getAlamat_karyawan() {
        return alamat_karyawan;
    }

    public void setAlamat_karyawan(String alamat_karyawan) {
        this.alamat_karyawan = alamat_karyawan;
    }

    public String getUnit_kerja() {
        return unit_kerja;
    }

    public void setUnit_kerja(String unit_kerja) {
        this.unit_kerja = unit_kerja;
    }

    public String getAlamat_unit() {
        return alamat_unit;
    }

    public void setAlamat_unit(String alamat_unit) {
        this.alamat_unit = alamat_unit;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getAtasan_langsung() {
        return atasan_langsung;
    }

    public void setAtasan_langsung(String atasan_langsung) {
        this.atasan_langsung = atasan_langsung;
    }

    public String getTgl_bergabung() {
        return tgl_bergabung;
    }

    public void setTgl_bergabung(String tgl_bergabung) {
        this.tgl_bergabung = tgl_bergabung;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLat_absen() {
        return lat_absen;
    }

    public void setLat_absen(String lat_absen) {
        this.lat_absen = lat_absen;
    }

    public String getLong_absen() {
        return long_absen;
    }

    public void setLong_absen(String long_absen) {
        this.long_absen = long_absen;
    }

    public String getWaktu_absen() {
        return waktu_absen;
    }

    public void setWaktu_absen(String waktu_absen) {
        this.waktu_absen = waktu_absen;
    }

    public String getStatus_absen() {
        return status_absen;
    }

    public void setStatus_absen(String status_absen) {
        this.status_absen = status_absen;
    }
}
