package com.ascendant.thegade.Activity.ui;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascendant.thegade.Activity.LoginActivity;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

public class PengaturanFragment extends Fragment {
    Ascendant AscNet;
    TextView nama,nik;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    LinearLayout Profil,GantiPassword,FAQ,Logout;
    Dialog dialog;
    Button No,Yes;
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
        Profil = view.findViewById(R.id.linearProfilKaryawan);
        GantiPassword = view.findViewById(R.id.linearGantiPassword);
        FAQ = view.findViewById(R.id.linearFaq);
        Logout = view.findViewById(R.id.linearLogout);
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

        //Dialog
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_logout);
        No = dialog.findViewById(R.id.btnTidak);
        Yes = dialog.findViewById(R.id.
                btnYa);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner);
        OnClick();
    }
    private void OnClick(){
        Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        GantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.Logout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            }
        });
    }
}