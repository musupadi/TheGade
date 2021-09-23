package com.ascendant.thegade.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

public class SplashActivity extends AppCompatActivity {
    DB_Helper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler handler = new Handler();
        dbHelper = new DB_Helper(SplashActivity.this);
        Cursor cursor = dbHelper.checkUser();
        if (cursor.getCount()>0){
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 0);
        }else{
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000); //3000 L = 3 detik
        }
    }
}