package com.ascendant.thegade.Activity.ui.Karyawan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MenuCutiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String Tanggals = "Anak";
    String tanggalDari = "1994-01-15";
    String tanggalSampai = "1994-01-15";

    LinearLayout LTanggalDari, LTanggalSampai;
    TextView TanggalDari, TanggalSampai;

    Ascendant AscNet;

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
    }
    private void Declaration(){
        LTanggalDari = findViewById(R.id.linearTanggalMulai);
        LTanggalSampai = findViewById(R.id.linearTanggalSelesai);
        TanggalDari = findViewById(R.id.tvTanggalMulai);
        TanggalSampai = findViewById(R.id.tvTanggalSelesai);
        TanggalDari.setText(AscNet.thisDay());
        TanggalSampai.setText(AscNet.thisDay());
        tanggalDari = AscNet.Today();
        tanggalSampai = AscNet.Today();
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