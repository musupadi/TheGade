package com.ascendant.thegade.Activity.ui.Karyawan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.Activity.HomeActivity;
import com.ascendant.thegade.Activity.ui.Home.CheckInActivity;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.Response.ResponseObject;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCutiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String Tanggals = "Anak";
    String tanggalDari = "1994-01-15";
    String tanggalSampai = "1994-01-15";
    ImageView Back;
    EditText Alasan;
    LinearLayout LTanggalDari, LTanggalSampai;
    TextView TanggalDari, TanggalSampai;
    Ascendant AscNet;
    TextView nama,nik;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    Button Kirim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cuti);
        AscNet = new Ascendant();
        Declaration();
        OnClick();
    }
    private void OnClick(){
        LTanggalDari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tanggals = "Dari";
                showDatePicker();
            }
        });
        LTanggalSampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tanggals = "Sampai";
                showDatePicker();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logic();
            }
        });
    }
    private void Declaration(){
        Kirim = findViewById(R.id.btnKirim);
        Back = findViewById(R.id.ivBack);
        LTanggalDari = findViewById(R.id.linearTanggalMulai);
        LTanggalSampai = findViewById(R.id.linearTanggalSelesai);
        TanggalDari = findViewById(R.id.tvTanggalMulai);
        TanggalSampai = findViewById(R.id.tvTanggalSelesai);
        Alasan = findViewById(R.id.etAlasan);
        TanggalDari.setText(AscNet.thisDay());
        TanggalSampai.setText(AscNet.thisDay());
        tanggalDari = AscNet.Today();
        tanggalSampai = AscNet.Today();
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
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(MenuCutiActivity.this);
        pd.setMessage("Proses Absensi");
        pd.show();
        pd.setCancelable(false);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseObject> Absen =api.Cuti(AscNet.AUTH(),Id,tanggalDari,tanggalSampai,Alasan.getText().toString());
        Absen.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                pd.hide();
                try {
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(MenuCutiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MenuCutiActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MenuCutiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MenuCutiActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    Log.d("Ascendant : ",e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                pd.hide();
                Toast.makeText(MenuCutiActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showDatePicker(){
        DatePickerDialog dialog = new DatePickerDialog(this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String M = "01";
        String D = "01";
        int m = month+1;
        if (m<10){
            M="0"+String.valueOf(month+1);
        }else{
            M=String.valueOf(month+1);
        }
        if (dayOfMonth<10){
            D="0"+String.valueOf(dayOfMonth);
        }else{
            D=String.valueOf(dayOfMonth);
        }
        String date = year+"-"+M+"-"+D;
        if (Tanggals.equals("Dari")){
            TanggalDari.setText(AscNet.DateChanges(String.valueOf(year),M,D));
            tanggalDari = date;
        }else if (Tanggals.equals("Sampai")){
            TanggalSampai.setText(AscNet.DateChanges(String.valueOf(year),M,D));
            tanggalSampai = date;
        }

    }
}