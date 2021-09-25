package com.ascendant.thegade.Activity.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascendant.thegade.Activity.ui.Karyawan.MenuCutiActivity;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

public class KaryawanFragment extends Fragment {
    LinearLayout Cuti,Claim,Dispensasion;
    Ascendant AscNet;
    TextView nama,nik;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    public KaryawanFragment() {
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
        return inflater.inflate(R.layout.fragment_karyawan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Cuti = view.findViewById(R.id.linearPengajuanCuti);
        Claim = view.findViewById(R.id.linearPengjuanClaim);
        Dispensasion = view.findViewById(R.id.linearPengajuanDispensasi);
        nama = view.findViewById(R.id.tvNama);
        nik = view.findViewById(R.id.tvNik);
        AscNet = new Ascendant();
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
        Cuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenuCutiActivity.class);
                startActivity(intent);
            }
        });
        Claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Dispensasion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}