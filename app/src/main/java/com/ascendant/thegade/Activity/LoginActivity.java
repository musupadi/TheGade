package com.ascendant.thegade.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ascendant.thegade.API.ApiRequest;
import com.ascendant.thegade.API.RetroServer;
import com.ascendant.thegade.Model.ResponseModel;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText Username,Password;
    ImageView eye;
    Boolean SeenPass=true;
    Button Login;
    DB_Helper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Declaration();
        OnClick();
    }
    private void Declaration(){
        dbHelper = new DB_Helper(this);
        Username = findViewById(R.id.etUsername);
        Password = findViewById(R.id.etPassword);
        eye = findViewById(R.id.ivEye);
        Login = findViewById(R.id.btnLogin);
    }
    private void OnClick(){
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SeenPass){
                    SeenPass=false;
                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    int pos = Password.getText().length();
                    Password.setSelection(pos);
                }else{
                    SeenPass=true;
                    Password.setInputType(129);
                    int pos = Password.getText().length();
                    Password.setSelection(pos);
                }
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logic();
            }
        });
    }
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Sedang Mencoba Login");
        pd.show();
        pd.setCancelable(false);
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        final Call<ResponseModel> login =api.login(Username.getText().toString(),Password.getText().toString());
        login.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.hide();
                try {
                    if (response.body().getStatus().equals("success")){
                        dbHelper.SaveUser(Username.getText().toString(),response.body().getData().get(0).getNama(),response.body().getData().get(0).getStatus(),response.body().getData().get(0).getNik());
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }else{
                        Toast.makeText(LoginActivity.this, "Username "+Username.getText().toString()+" Password : "+Password.getText().toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.d("Ascendant Error Log : ",e.toString());
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan pada : "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}