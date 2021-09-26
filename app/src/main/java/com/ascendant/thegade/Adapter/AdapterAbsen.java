package com.ascendant.thegade.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.DataModel;
import com.ascendant.thegade.R;

import java.util.List;

public class AdapterAbsen extends RecyclerView.Adapter<AdapterAbsen.HolderData>{
    private List<DataModel> mList;
    private Context ctx;
    Ascendant AscNet;
    public AdapterAbsen(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_absen,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        AscNet = new Ascendant();
        if (dm.getStatus_absen().equals("keluar")){
            holderData.LinearCheck.setBackgroundColor(Color.rgb(147,199,113));
            holderData.Check.setText("Check-Out");
            holderData.ImageCheck.setImageResource(R.drawable.check_out);
        }else if (dm.getStatus_absen().equals("masuk")){
            holderData.LinearCheck.setBackgroundColor(Color.rgb(0,85,142));
            holderData.Check.setText("Check-In");
            holderData.ImageCheck.setImageResource(R.drawable.check_in);
        }else if (dm.getStatus_absen().equals("izin dispensasi") || dm.getStatus_absen().equals("izin cuti")){
            holderData.LinearCheck.setBackgroundColor(Color.rgb(147,199,113));
            holderData.Check.setText("Izin");
            holderData.ImageCheck.setImageResource(R.drawable.home_active);
        }else{
            holderData.LinearCheck.setBackgroundColor(Color.rgb(241,00,0));
            holderData.Check.setText("Alfa");
            holderData.ImageCheck.setImageResource(R.drawable.warning);
        }
        holderData.Tanggal.setText(AscNet.MagicDateChange(dm.getWaktu_absen()));
        holderData.Jam.setText(dm.getWaktu_absen().substring(11));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView Tanggal,Jam,Check;
        ImageView ImageCheck;
        LinearLayout LinearCheck;
        public HolderData(View v) {
            super(v);
            Tanggal = v.findViewById(R.id.tvTanggal);
            Jam = v.findViewById(R.id.tvJam);
            Check = v.findViewById(R.id.tvCheck);
            ImageCheck = v.findViewById(R.id.ivCheck);
            LinearCheck = v.findViewById(R.id.linearAbsen);
        }
    }
}
