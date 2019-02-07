package com.fauzanpramulia.dosen_ta_ujian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.DaftarMahasiswaActivity;
import com.fauzanpramulia.dosen_ta_ujian.R;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;
import com.fauzanpramulia.dosen_ta_ujian.shared.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UjianAdapter extends RecyclerView.Adapter<UjianAdapter.UjianHolder>{
    ArrayList<UjianModel> dataUjian;
    Context context;
    Session session;
    public void setDataUjian(ArrayList<UjianModel> data) {
        this.dataUjian = data;
        notifyDataSetChanged();
    }

    public UjianAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UjianHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ujian_item, parent, false);
        return new UjianHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UjianHolder holder, final int position) {

        final UjianModel ujian = dataUjian.get(position);
        session = new Session(context);

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm a");
        Date date=null;

        String t = ujian.getTanggal_mulai()+", "+ujian.getWaktu_ujian();

//        holder.textNilai.setText(String.valueOf(ujian.getNilai()));
        holder.textMataKuliah.setText(ujian.getNama_mk());
        holder.textKelas.setText(ujian.getNama_kelas());
        holder.textNamaUjian.setText(ujian.getNama_ujian());
        holder.textTanggal.setText(ujian.getTanggal_mulai());
        holder.textRuangUjian.setText(ujian.getRuang_ujian());

        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DaftarMahasiswaActivity.class);
                session.setUjianID(ujian.getId());
                i.putExtra(DaftarMahasiswaActivity.EXTRA_UJIAN, ujian);
                context.startActivity(i);
            }
        });

        //=====untuk long click saja//////////============================================
        holder.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {
                Toast.makeText(context,""+ujian.getNama_kelas(),Toast.LENGTH_SHORT).show();
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

    public class UjianHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener{
        TextView textNilai;
        TextView textMataKuliah;
        TextView textKelas;
        TextView textNamaUjian;
        TextView textTanggal;
        TextView textRuangUjian;
        RelativeLayout view_container;
        ItemLongClickListener itemLongClickListener;

        public UjianHolder(View itemView) {
            super(itemView);
//            textNilai = itemView.findViewById(R.id.nilai);
            textMataKuliah = itemView.findViewById(R.id.textMataKuliah);
            textKelas = itemView.findViewById(R.id.textKelas);
            textNamaUjian = itemView.findViewById(R.id.textNamaUjian);
            textTanggal = itemView.findViewById(R.id.textTanggal);
            textRuangUjian = itemView.findViewById(R.id.textRuangUjian);
            view_container = (RelativeLayout) itemView.findViewById(R.id.view_container);

            itemView.setOnLongClickListener(this);
        }

        public void setItemLongClickListener(ItemLongClickListener ic)
        {
            this.itemLongClickListener=ic;
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemLongClickListener.onItemLongClick(v,getLayoutPosition());
            return false;
        }
    }

    public interface ItemLongClickListener {

        void onItemLongClick(View v,int pos);

    }
}
