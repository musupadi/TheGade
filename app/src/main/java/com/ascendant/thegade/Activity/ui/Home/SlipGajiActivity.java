package com.ascendant.thegade.Activity.ui.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.Adapter.AdapterGaji;
import com.ascendant.thegade.Adapter.AdapterKaryawan;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.DataModel;
import com.ascendant.thegade.Model.Response.ResponseArrayObject;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SlipGajiActivity extends AppCompatActivity {
    RecyclerView rv;
    Ascendant AscNet;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    private List<DataModel> mItems = new ArrayList<>();
    AdapterGaji adapter;
    private RecyclerView.LayoutManager mManager;
    ImageView Back,Print;
    EditText Cari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip_gaji);
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
        Print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintData();
            }
        });
    }
    private void Declaration(){
        AscNet = new Ascendant();
        rv = findViewById(R.id.recycler);
        Cari = findViewById(R.id.etCari);
        Back = findViewById(R.id.ivBack);
        Print = findViewById(R.id.ivPrint);
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
    private void PrintData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SlipGajiActivity.this);
        builder.setMessage("Download File ?")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AscNet.DownloadPDF(AscNet.BaseURL()+"print_data/data_gaji/"+Id,"Data Gaji "+Nama,SlipGajiActivity.this);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                //Set your icon here
                .setTitle("Perhatian !!!")
                .setIcon(R.drawable.ic_baseline_print_24);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void Logic(){
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(mManager);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseArrayObject> data =api.SlipGaji(AscNet.AUTH(),Id);
        data.enqueue(new Callback<ResponseArrayObject>() {
            @Override
            public void onResponse(Call<ResponseArrayObject> call, Response<ResponseArrayObject> response) {
                try {
                    if (response.body().getCode().equals(200)){
                        mItems=response.body().getData();
                        adapter = new AdapterGaji(SlipGajiActivity.this,mItems);
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Cari.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                adapter.getFilter().filter(Cari.getText());
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }else{
                        Toast.makeText(SlipGajiActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(SlipGajiActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseArrayObject> call, Throwable t) {
                Toast.makeText(SlipGajiActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}