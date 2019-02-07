package com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.AturanUjianActivity;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.DaftarSoalActivity;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.MainActivity;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.R;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.AppDatabase;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment.SoalFragment;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SoalAdapter extends RecyclerView.Adapter<SoalAdapter.SoalHolder> {
    ArrayList<SoalModel> dataSoal;
    Context context;
    AppDatabase db;
    OnItemClicked Handler;
    Session session;
    public static final String DATABASENAME = "ujian.db";
    public void setDataSoal(ArrayList<SoalModel> data) {
        this.dataSoal = data;
        notifyDataSetChanged();
    }

    public SoalAdapter(Context context) {
        this.context = context;
    }

    public void setHandler(OnItemClicked clickHandler) {
        this.Handler = clickHandler;
    }

    @NonNull
    @Override
    public SoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.soal_item, parent, false);
        db = Room.databaseBuilder(context, AppDatabase.class, DATABASENAME)
                .allowMainThreadQueries()
                .build();
        return new SoalHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SoalHolder holder, final int position) {

        session = new Session(context);
        final SoalModel ujian = dataSoal.get(position);
        holder.textNo.setText(String.valueOf(position+1));

        int i = checkData(ujian.getId(),session.getUjianMahasiswaID());

        if (i!=0){
            holder.view_container.setBackgroundColor(Color.parseColor("#C4DFE6"));
            holder.textNo.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.view_container.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.textNo.setTextColor(Color.parseColor("#808080"));
        }
    }

    @Override
    public int getItemCount() {
        if (dataSoal != null) {
            return dataSoal.size();
        }
        return 0;
    }

    public class SoalHolder extends RecyclerView.ViewHolder {
        TextView textNo;
        RelativeLayout view_container;
        public SoalHolder(View itemView) {
            super(itemView);
            view_container = (RelativeLayout) itemView.findViewById(R.id.view_container);
            textNo = itemView.findViewById(R.id.no);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler.clik(dataSoal.get(getAdapterPosition()),getAdapterPosition());
                }
            });

        }
    }
    public interface OnItemClicked{
        void clik(SoalModel m,int position);
    }

    public int checkData(int id, int ujian_mahasiswa_id) {
        int nilai = 0;

        nilai = db.jawabanUjianDao().checkJawabanJS(id, ujian_mahasiswa_id);
        return nilai;
    }
}