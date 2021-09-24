package com.ascendant.thegade.Activity.ui.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.BuildConfig;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.Response.ResponseObject;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity implements OnMapReadyCallback {
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    TextView nama,nik;
    ImageView Back;
    //GOOGLE MAPS LOGIC
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    TextView latitude,longitude;
    Button CheckIn;
    GoogleMap Map;
    Double Latitude,Longitude;
    Ascendant AscNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        MainLogic();
    }
    private void MainLogic(){
        Declaration();
        AscNet = new Ascendant();
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
        OnClick();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }
    private void fetchLastLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
//                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()+" And "+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(CheckOutActivity.this);
                    longitude.setText(String.valueOf(currentLocation.getLongitude()));
                    latitude.setText(String.valueOf(currentLocation.getLatitude()));
                }
            }
        });
    }
    private void Declaration(){
        Back = findViewById(R.id.ivBack);
        nama = findViewById(R.id.tvNama);
        nik = findViewById(R.id.tvNik);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        CheckIn = findViewById(R.id.btnCheckIn);
        statusCheck();
    }
    private void OnClick(){
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        CheckIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    Map.clear();
                    Logic(Latitude,Longitude);
                    fetchLastLocation();
                    LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                    Map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18F));
                    double LatGadeMin  = Latitude-0.0031250;
                    double LatGadeMax  = Longitude+0.0031250;

                    double LongGadeMin = Latitude-0.0031250;
                    double LongGadeMax = Longitude+0.0031250;


                    if (Checker(currentLocation.getLatitude(),LatGadeMin,LatGadeMax) && Checker(currentLocation.getLongitude(),LongGadeMin,LongGadeMax)){
//                        Toast.makeText(CheckOutActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        LocalDate dates = LocalDate.now();
                        LocalTime time = LocalTime.now();
                        Absen(dates+" "+AscNet.TimeChanger(time.toString()));
                    }else{
                        Toast.makeText(CheckOutActivity.this, "Diluar Jangkauan", Toast.LENGTH_SHORT).show();
                        Log.d("Ascendant : ",String.valueOf(Checker(currentLocation.getLatitude(),LatGadeMin,LatGadeMax))+" "+String.valueOf(Checker(currentLocation.getLongitude(),LongGadeMin,LongGadeMax)));
                    }
                }catch (Exception e){
                    MainLogic();
                    Log.d("Ascendant : ",e.toString());
                }
            }
        });
    }
    private void Absen(String Waktu){
        final ProgressDialog pd = new ProgressDialog(CheckOutActivity.this);
        pd.setMessage("Proses Absensi");
        pd.show();
        pd.setCancelable(false);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseObject> Absen =api.CheckOut(AscNet.AUTH(),Id,Waktu,latitude.getText().toString(),longitude.getText().toString());
        Absen.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                pd.hide();
                try {
                    Toast.makeText(CheckOutActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(CheckOutActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                pd.hide();
                Toast.makeText(CheckOutActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Boolean Checker(Double Current,Double LatGadeMin,Double LatGadeMax){
        boolean Check;
        if (Current>=LatGadeMin && Current<=LatGadeMax){
            Check = true;
        }else{
            Check = false;
        }
        return Check;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Map = googleMap;
        final ProgressDialog pd = new ProgressDialog(CheckOutActivity.this);
        pd.setMessage("Sedang Mendapatkan Data Peta");
        pd.show();
        pd.setCancelable(false);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseObject> Location =api.location(AscNet.AUTH());
        Location.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                pd.hide();
                try {
                    if (response.body().getCode().equals(200)){
                        Latitude=Double.parseDouble(response.body().getData().latitude);
                        Longitude=Double.parseDouble(response.body().getData().getLongitude());
                        Logic(Latitude,Longitude);
                    }else{
                        Toast.makeText(CheckOutActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
//                    Toast.makeText(CheckOutActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                pd.hide();
                Toast.makeText(CheckOutActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Logic(Double Latitude,Double Longitude){
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Anda berada Disini");
        Map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        Map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18F));
        Map.addMarker(markerOptions);

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.circle);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b,300,300,false);
        Map.addMarker(new MarkerOptions()
                .position(new LatLng(Latitude,Longitude))
                .anchor(0.5f,0.5f)
                .title("GADE")
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}