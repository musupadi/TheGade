package com.ascendant.thegade.Activity.ui;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

public class PengaturanFragment extends Fragment {
    Ascendant AscNet;
    TextView nama,nik;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;


    public PengaturanFragment() {
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
        return inflater.inflate(R.layout.fragment_pengaturan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    }
}