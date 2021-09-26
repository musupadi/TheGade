package com.ascendant.thegade.Activity.ui.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.Adapter.AdapterAbsen;
import com.ascendant.thegade.Adapter.AdapterPekerjaan;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.DataModel;
import com.ascendant.thegade.Model.Response.ResponseArrayObject;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKehadiranActivity extends AppCompatActivity{
    RecyclerView rv;
    Ascendant AscNet;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    private List<DataModel> mItems = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kehadiran);
        Declaration();
        OnClick();
        Logic();
    }
    private void OnClick(){
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void Declaration(){
        AscNet = new Ascendant();
        rv = findViewById(R.id.recycler);;
        Back = findViewById(R.id.ivBack);
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
    }

    private void Logic(){
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(mManager);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseArrayObject> data =api.DaftarAbsen(AscNet.AUTH(),Id);
        data.enqueue(new Callback<ResponseArrayObject>() {
            @Override
            public void onResponse(Call<ResponseArrayObject> call, Response<ResponseArrayObject> response) {
                try {
                    if (response.body().getCode().equals(200)){
                        mItems=response.body().getData();
                        mAdapter = new AdapterAbsen(DaftarKehadiranActivity.this,mItems);
                        rv.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(DaftarKehadiranActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(DaftarKehadiranActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseArrayObject> call, Throwable t) {
                Toast.makeText(DaftarKehadiranActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}