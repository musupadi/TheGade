package com.ascendant.thegade.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ascendant.thegade.Activity.ui.HomeFragment;
import com.ascendant.thegade.Activity.ui.KaryawanFragment;
import com.ascendant.thegade.Activity.ui.PengaturanFragment;
import com.ascendant.thegade.R;
import com.ascendant.thegade.SharedPreferance.DB_Helper;

import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends AppCompatActivity {
    ImageView imageHome,imageKaryawan,imagePengaturan;
    TextView textHome,textKaryawan,textPengaturan;
    LinearLayout linearHome,linearKaryawan,linearPengaturan;
    Fragment fragment;
    DB_Helper dbHelper;
    String Count;
    String Lang;
    Context context;

    Dialog dialog;
    Button No,Yes;
    boolean doubleBackToExitPressedOnce = false;
    private String[] galleryPermissions =
            {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(EasyPermissions.hasPermissions(HomeActivity.this, galleryPermissions)) {

        }else{
            EasyPermissions.requestPermissions(HomeActivity.this, "Access for storage",
                    101, galleryPermissions);
        }
        Declaration();
        Home();
        OnClick();
    }
    private void OnClick(){
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                finish();
                System.exit(0);
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        linearHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home();
            }
        });
        linearKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Karyawan();
            }
        });
        linearPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pengaturan();
            }
        });
    }
    private void Declaration(){
        imageHome = findViewById(R.id.ivHome);
        textHome = findViewById(R.id.tvHome);
        imageKaryawan = findViewById(R.id.ivFeedback);
        textKaryawan = findViewById(R.id.tvFeedback);
        imagePengaturan = findViewById(R.id.ivAbout);
        textPengaturan = findViewById(R.id.tvAbout);
        linearHome = findViewById(R.id.linearHome);
        linearKaryawan = findViewById(R.id.linearFeedback);
        linearPengaturan = findViewById(R.id.linearAbout);

        dbHelper = new DB_Helper(this);
        //Dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit);
        No = dialog.findViewById(R.id.btnTidak);
        Yes = dialog.findViewById(R.id.
                btnYa);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner);
    }

    private void Default(){
        linearHome.setBackgroundResource(R.drawable.rounded);
        linearKaryawan.setBackgroundResource(R.drawable.rounded);
        linearPengaturan.setBackgroundResource(R.drawable.rounded);
        imageHome.setImageResource(R.drawable.home);
        imageKaryawan.setImageResource(R.drawable.karyawan);
        imagePengaturan.setImageResource(R.drawable.setting);
        textHome.setTextColor(Color.rgb(214,189,137));
        textKaryawan.setTextColor(Color.rgb(214,189,137));
        textPengaturan.setTextColor(Color.rgb(214,189,137));
    }
    private void Home(){
        Default();
        linearHome.setBackgroundResource(R.drawable.rounded_active);
        imageHome.setImageResource(R.drawable.home_active);
        textHome.setTextColor(Color.WHITE);
        fragment = new HomeFragment();
        ChangeFragment(fragment);
    }
    private void Karyawan(){
        Default();
        linearKaryawan.setBackgroundResource(R.drawable.rounded_active);
        imageKaryawan.setImageResource(R.drawable.karyawan_active);
        textKaryawan.setTextColor(Color.WHITE);
        fragment = new KaryawanFragment();
        ChangeFragment(fragment);
    }
    private void Pengaturan(){
        Default();
        linearPengaturan.setBackgroundResource(R.drawable.rounded_active);
        imagePengaturan.setImageResource(R.drawable.setting_active);
        textPengaturan.setTextColor(Color.WHITE);
        fragment = new PengaturanFragment();
        ChangeFragment(fragment);
    }

    private void ChangeFragment(Fragment fragment){
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.Container,fragment);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            dialog.show();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}