package com.ascendant.thegade.Activity.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ascendant.thegade.Activity.ui.Karyawan.MenuCutiActivity;
import com.ascendant.thegade.R;

public class KaryawanFragment extends Fragment {
    LinearLayout Cuti,Claim,Dispensasion;
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