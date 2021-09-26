package com.ascendant.thegade.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.DataModel;
import com.ascendant.thegade.R;

import java.util.List;

public class AdapterPekerjaan extends RecyclerView.Adapter<AdapterPekerjaan.HolderData>{
    private List<DataModel> mList;
    private Context ctx;
    Ascendant AscNet;
    public AdapterPekerjaan(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pekerjaan,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        AscNet = new Ascendant();
        holderData.NamaPekerjaan.setText(dm.getNama_pekerjaan());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaPekerjaan;
        public HolderData(View v) {
            super(v);
            NamaPekerjaan = v.findViewById(R.id.tvNamaPekerjaan);
        }
    }
}
