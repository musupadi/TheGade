package com.ascendant.thegade.Activity.ui.Pengaturan;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.Response.ResponseObject;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilKaryawanActivity extends AppCompatActivity {
    EditText NomorPonsel,Email,Alamat,UnitKerja,AlamatUnit,Jabatan,AtasanLangsung,TanggalBergabung;
    Ascendant AscNet;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    TextView nama,nik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_karyawan);
        Declaration();
        Logic();
    }
    private void Logic(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseObject> Data =api.ProfileUser(AscNet.AUTH(),Id);
        Data.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                try {
                    if (response.body().getCode().equals(200)){
                        NomorPonsel.setText(response.body().getData().getNo_telp_karyawan());
                        Email.setText(response.body().getData().getEmail_karyawan());
                        Alamat.setText(response.body().getData().getAlamat_karyawan());

                        UnitKerja.setText(response.body().getData().getUnit_kerja());
                        AlamatUnit.setText(response.body().getData().getAlamat_unit());
                        Jabatan.setText(response.body().getData().getJabatan());
                        AtasanLangsung.setText(response.body().getData().getAtasan_langsung());
                        TanggalBergabung.setText(AscNet.MagicDateChange(response.body().getData().getTgl_bergabung()));
                    }else{
                        Toast.makeText(ProfilKaryawanActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ProfilKaryawanActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Toast.makeText(ProfilKaryawanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Declaration(){
        AscNet = new Ascendant();
        NomorPonsel = findViewById(R.id.etNoTelpon);
        Email = findViewById(R.id.etEmail);
        Alamat = findViewById(R.id.etAlamat);
        UnitKerja = findViewById(R.id.etUnitKerja);
        AlamatUnit = findViewById(R.id.etAlamatUnit);
        Jabatan = findViewById(R.id.etJabatan);
        AtasanLangsung = findViewById(R.id.etAtasanLangsung);
        TanggalBergabung = findViewById(R.id.etTanggalBergabung);
        nama = findViewById(R.id.tvNama);
        nik = findViewById(R.id.tvNik);
        dbHelper = new DB_Helper(this);
        Cursor cursor = dbHelper.checkUser();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                Id = cursor.getString(0);
                Username = cursor.getString(1);
                Nama = cursor.getString(2);
                Status = cursor.getString(3);
                Nik = cursor.getString(4);
            }
        }
        nama.setText(Nama);
        nik.setText(Nik);
    }
}