package com.ascendant.thegade.Activity.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.Activity.HomeActivity;
import com.ascendant.thegade.Activity.LoginActivity;
import com.ascendant.thegade.Activity.ui.Home.CheckInActivity;
import com.ascendant.thegade.Activity.ui.Pengaturan.ProfilKaryawanActivity;
import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.Response.ResponseArrayObject;
import com.ascendant.thegade.Model.Response.ResponseObject;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaturanFragment extends Fragment {
    Ascendant AscNet;
    TextView nama,nik;
    DB_Helper dbHelper;
    String Id,Username,Nama,Status,Nik;
    LinearLayout Profil,GantiPassword,FAQ,Logout;
    Dialog dialog;
    Button No,Yes;

    Dialog dialogPassword;
    EditText PassLama,PassBaru,PassBaru2;
    ImageView Eye1,Eye2,Eye3;
    Boolean SeenPass=true;
    Boolean SeenPass2=true;
    Boolean SeenPass3=true;
    Button Submit;
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

        //Dialog2
        dialogPassword = new Dialog(getActivity());
        dialogPassword.setContentView(R.layout.dialog_change_password);
        PassLama = dialogPassword.findViewById(R.id.etPasswordLama);
        PassBaru = dialogPassword.findViewById(R.id.etPasswordNew1);
        PassBaru2 = dialogPassword.findViewById(R.id.etPasswordNew2);
        Eye1 = dialogPassword.findViewById(R.id.ivEye1);
        Eye2 = dialogPassword.findViewById(R.id.ivEye2);
        Eye3 = dialogPassword.findViewById(R.id.ivEye3);
        Submit = dialogPassword.findViewById(R.id.btnKirim);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner);
        OnClick();
    }
    private void Checker(){
        if (PassLama.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Password Lama Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }if (PassBaru.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Password Baru Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }if (PassBaru2.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Password Konfirmasi Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if (!PassBaru.getText().toString().equals(PassBaru2.getText().toString())){
            Toast.makeText(getActivity(), "Password Baru dan Password Konfirmasi Harus Sama", Toast.LENGTH_SHORT).show();
        }else{
            ChangePassword();
        }
    }
    private void ChangePassword(){
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Proses Mengubah Password");
        pd.show();
        pd.setCancelable(false);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseObject> data =api.ChangePassword(AscNet.AUTH()
                ,Id
                ,PassLama.getText().toString()
                ,PassBaru.getText().toString()
                ,PassBaru2.getText().toString());
        data.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                pd.hide();
                try {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getCode().equals(200)){
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    Log.d("Ascendant Error Log : ",e.toString());
                    Toast.makeText(getActivity(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                pd.hide();
                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void OnClick(){
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checker();
            }
        });
        Eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SeenPass){
                    Eye1.setImageResource(R.drawable.eye_off);
                    SeenPass=false;
                    PassLama.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    int pos = PassLama.getText().length();
                    PassLama.setSelection(pos);
                }else{
                    Eye1.setImageResource(R.drawable.eye);
                    SeenPass=true;
                    PassLama.setInputType(129);
                    int pos = PassLama.getText().length();
                    PassLama.setSelection(pos);
                }
            }
        });
        Eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SeenPass2){
                    Eye2.setImageResource(R.drawable.eye_off);
                    SeenPass2=false;
                    PassBaru.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    int pos = PassBaru.getText().length();
                    PassBaru.setSelection(pos);
                }else{
                    Eye2.setImageResource(R.drawable.eye);
                    SeenPass2=true;
                    PassBaru.setInputType(129);
                    int pos = PassBaru.getText().length();
                    PassBaru.setSelection(pos);
                }
            }
        });
        Eye3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SeenPass3){
                    Eye3.setImageResource(R.drawable.eye_off);
                    SeenPass3=false;
                    PassBaru2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    int pos = PassBaru2.getText().length();
                    PassBaru2.setSelection(pos);
                }else{
                    Eye3.setImageResource(R.drawable.eye);
                    SeenPass3=true;
                    PassBaru2.setInputType(129);
                    int pos = PassBaru2.getText().length();
                    PassBaru2.setSelection(pos);
                }
            }
        });
        Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfilKaryawanActivity.class);
                startActivity(intent);
            }
        });
        GantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPassword.show();
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