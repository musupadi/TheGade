package com.ascendant.thegade.Activity.ui;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ascendant.thegade.Activity.ui.Home.CheckInActivity;
import com.ascendant.thegade.Activity.ui.Home.CheckOutActivity;
import com.ascendant.thegade.Activity.ui.Home.DaftarKehadiranActivity;
import com.ascendant.thegade.Activity.ui.Home.DaftarPekerjaanActivity;
import com.ascendant.thegade.Activity.ui.Home.DataKaryawanActivity;
import com.ascendant.thegade.Activity.ui.Home.SlipGajiActivity;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;


public class HomeFragment extends Fragment {

    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    TextView nama,nik;
    CardView CheckIn,CheckOut;
    CardView DaftarPekerjaan,Absen,SlipGaji,DataKaryawan;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nama = view.findViewById(R.id.tvNama);
        nik = view.findViewById(R.id.tvNik);
        CheckIn = view.findViewById(R.id.cardCheckIn);
        CheckOut = view.findViewById(R.id.cardCheckOut);
        DaftarPekerjaan = view.findViewById(R.id.cardDaftarPekerjaan);
        SlipGaji = view.findViewById(R.id.cardSlipGaji);
        DataKaryawan = view.findViewById(R.id.cardDataKaryawan);
        Absen = view.findViewById(R.id.cardAbsen);


        dbHelper = new DB_Helper(getActivity());
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

        CheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);
            }
        });
        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                startActivity(intent);
            }
        });
        DaftarPekerjaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DaftarPekerjaanActivity.class);
                startActivity(intent);
            }
        });
        Absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DaftarKehadiranActivity.class);
                startActivity(intent);
            }
        });
        SlipGaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SlipGajiActivity.class);
                startActivity(intent);
            }
        });
        DataKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataKaryawanActivity.class);
                startActivity(intent);
            }
        });
    }
}