package com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.AturanUjianActivity;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.R;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.UjianModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UjianAdapter extends RecyclerView.Adapter<UjianAdapter.UjianHolder>{
    ArrayList<UjianModel> dataUjian;
    Context context;
    OnItemClicked Handler;

    public void setDataUjian(ArrayList<UjianModel> data) {
        this.dataUjian = data;
        notifyDataSetChanged();
    }

    public UjianAdapter(Context context) {
        this.context = context;
    }
    public void setHandler(OnItemClicked clickHandler) {
        this.Handler = clickHandler;
    }

    @NonNull
    @Override
    public UjianHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ujian_item, parent, false);
        return new UjianHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UjianHolder holder, final int position) {

        UjianModel ujian = dataUjian.get(position);

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm a");
        Date date=null;
//        try {
//            date= f.parse(ujian.getCreated_at());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        String tanggalString = formatter.format(date);
//        String waktuString = formatter2.format(date);
        String t = ujian.getTanggal_mulai()+", "+ujian.getWaktu_ujian();

        holder.textNilai.setText(String.valueOf(ujian.getNilai()));
        holder.textMataKuliah.setText(ujian.getNama_mk());
        holder.textKelas.setText(ujian.getNama_kelas());
        holder.textNamaUjian.setText(ujian.getNama_ujian());
        holder.textTanggal.setText(ujian.getTanggal_mulai());
        holder.textRuangUjian.setText(ujian.getRuang_ujian());

    }

    @Override
    public int getItemCount() {
        if (dataUjian != null) {
            return dataUjian.size();
        }
        return 0;
    }

    public class UjianHolder extends RecyclerView.ViewHolder {
        TextView textNilai;
        TextView textMataKuliah;
        TextView textKelas;
        TextView textNamaUjian;
        TextView textTanggal;
        TextView textRuangUjian;

        public UjianHolder(View itemView) {
            super(itemView);
            textNilai = itemView.findViewById(R.id.nilai);
            textMataKuliah = itemView.findViewById(R.id.textMataKuliah);
            textKelas = itemView.findViewById(R.id.textKelas);
            textNamaUjian = itemView.findViewById(R.id.textNamaUjian);
            textTanggal = itemView.findViewById(R.id.textTanggal);
            textRuangUjian = itemView.findViewById(R.id.textRuangUjian);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler.clik(dataUjian.get(getAdapterPosition()));

                }
            });
        }
    }
    public interface OnItemClicked{
        void clik(UjianModel m);
    }

}
