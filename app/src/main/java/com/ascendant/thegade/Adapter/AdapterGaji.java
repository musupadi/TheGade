package com.ascendant.thegade.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ascendant.thegade.Method.Ascendant;
import com.ascendant.thegade.Model.DataModel;
import com.ascendant.thegade.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterGaji extends RecyclerView.Adapter<AdapterGaji.HolderData> implements Filterable {
    private List<DataModel> mList;
    private Context ctx;
    Ascendant AscNet;
    private List<DataModel> mListFull;
    Dialog dialog;
    Button Print;
    TextView TotalPenghasilan,TotalPenghasilan2,Total,TotalPotongan,TotalPotongan2,Nama;
    public AdapterGaji(Context ctx, List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);
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
        holderData.NamaPekerjaan.setText(dm.getNama_gaji());
        //Dialog
        dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.dialog_slip_gaji);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corner);
        TotalPenghasilan = dialog.findViewById(R.id.tvTotalPenghasilan);
        TotalPenghasilan2 = dialog.findViewById(R.id.tvTotalPenghasilan2);
        TotalPotongan = dialog.findViewById(R.id.tvTotalPotongan);
        TotalPotongan2 = dialog.findViewById(R.id.tvTotalPotongan2);
        Print = dialog.findViewById(R.id.btnKirim);
        Total = dialog.findViewById(R.id.tvTotal);
        Nama = dialog.findViewById(R.id.tvNama);
        Nama.setText("Slip Gaji : "+dm.getNama_gaji());
        TotalPenghasilan.setText(AscNet.MagicRP(Double.parseDouble(dm.getGaji_seharusnya())));
        TotalPenghasilan2.setText(AscNet.MagicRP(Double.parseDouble(dm.getGaji_seharusnya())));
        int GajiSeharusnya = Integer.parseInt(dm.getGaji_seharusnya());
        int GajiDiterima = Integer.parseInt(dm.getGaji_diterima());
        int Potongan = GajiSeharusnya-GajiDiterima;
        TotalPotongan.setText(String.valueOf(AscNet.MagicRP(Double.parseDouble(String.valueOf(Potongan)))));
        TotalPotongan2.setText(String.valueOf(AscNet.MagicRP(Double.parseDouble(String.valueOf(Potongan)))));
        Total.setText(AscNet.MagicRP(Double.parseDouble(dm.getGaji_diterima())));
        holderData.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        Print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Download File ?")
                        .setCancelable(false)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AscNet.DownloadPDF(AscNet.BaseURL()+"print_data/detail_gaji/"+dm.getId_gaji(),dm.getNama_gaji(),ctx);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        //Set your icon here
                        .setTitle("Perhatian !!!")
                        .setIcon(R.drawable.ic_baseline_print_24);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView NamaPekerjaan;
        CardView card;
        public HolderData(View v) {
            super(v);
            NamaPekerjaan = v.findViewById(R.id.tvNamaPekerjaan);
            card = v.findViewById(R.id.card);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mListFull);
            }else{
                String fillterPattern = constraint.toString().toLowerCase().trim();

                for (DataModel dm : mListFull){
                    if (dm.getNama_karyawan().toLowerCase().contains(fillterPattern)){
                        filteredList.add(dm);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
