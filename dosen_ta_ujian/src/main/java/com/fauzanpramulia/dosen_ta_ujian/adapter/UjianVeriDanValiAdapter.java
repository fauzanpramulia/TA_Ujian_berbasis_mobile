package com.fauzanpramulia.dosen_ta_ujian.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.DaftarMahasiswaActivity;
import com.fauzanpramulia.dosen_ta_ujian.DetailUjianActivity;
import com.fauzanpramulia.dosen_ta_ujian.R;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianVeriDanValiModel;
import com.fauzanpramulia.dosen_ta_ujian.shared.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UjianVeriDanValiAdapter  extends RecyclerView.Adapter<UjianVeriDanValiAdapter.UjianHolder>{

    ArrayList<UjianVeriDanValiModel> dataUjian;
    Context context;
    Session session;
    public void setDataUjian(ArrayList<UjianVeriDanValiModel> data) {
        this.dataUjian = data;
        notifyDataSetChanged();
    }

    public UjianVeriDanValiAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UjianVeriDanValiAdapter.UjianHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ujian_uas_uts_item, parent, false);
        return new UjianVeriDanValiAdapter.UjianHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UjianVeriDanValiAdapter.UjianHolder holder, final int position) {

        final UjianVeriDanValiModel ujian = dataUjian.get(position);
        session = new Session(context);

        String t = ujian.getTanggal_mulai()+", "+ujian.getWaktu_ujian();
        String stat1="",stat2="";
        String warna1="#fff",warna2="#fff";

        if (ujian.getStatus_verifikasi()==0||ujian.getStatus_verifikasi()==1){stat1="Belum Diverifikasi";warna1="#d1a02c";}
        else if(ujian.getStatus_verifikasi()==2) {stat1="Diverifikasi";warna1="#38ab27";}
        else if(ujian.getStatus_verifikasi()==3){stat1="Ditolak";warna1="#b71d50";}

        if (ujian.getStatus_validasi()==0||ujian.getStatus_validasi()==1){stat2="Belum Divalidasi";warna2="#d1a02c";}
        else if(ujian.getStatus_validasi()==2) {stat2="Divalidasi";warna2="#38ab27";}
        else if(ujian.getStatus_validasi()==3){stat2="Ditolak";warna2="#b71d50";}


        holder.textMataKuliah.setText(ujian.getNama_mk());
        holder.textNamaUjian.setText(ujian.getNama_ujian());
        holder.textTanggal.setText(ujian.getTanggal_mulai());
        holder.textRuangUjian.setText(ujian.getRuang_ujian());
        holder.textVerifikasi.setText(stat1);
        holder.textVerifikasi.setBackgroundColor(Color.parseColor(warna1));

        holder.textValidasi.setText(stat2);
        holder.textValidasi.setBackgroundColor(Color.parseColor(warna2));

        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailUjianActivity.class);
                session.setUjianID(ujian.getId());
                i.putExtra(DetailUjianActivity.EXTRA_UJIAN, ujian);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (dataUjian != null) {
            return dataUjian.size();
        }
        return 0;
    }

    public class UjianHolder extends RecyclerView.ViewHolder{
        TextView textNilai;
        TextView textMataKuliah;
        TextView textKelas;
        TextView textNamaUjian;
        TextView textTanggal;
        TextView textRuangUjian;
        TextView textVerifikasi;
        TextView textValidasi;

        RelativeLayout view_container;
        UjianAdapter.ItemLongClickListener itemLongClickListener;

        public UjianHolder(View itemView) {
            super(itemView);
//            textNilai = itemView.findViewById(R.id.nilai);
            textMataKuliah = itemView.findViewById(R.id.textMataKuliah);
            textVerifikasi = itemView.findViewById(R.id.statusVerifikasi);
            textValidasi = itemView.findViewById(R.id.statusValidasi);
            textNamaUjian = itemView.findViewById(R.id.textNamaUjian);
            textTanggal = itemView.findViewById(R.id.textTanggal);
            textRuangUjian = itemView.findViewById(R.id.textRuangUjian);
            view_container = (RelativeLayout) itemView.findViewById(R.id.view_container);

        }

    }

}
