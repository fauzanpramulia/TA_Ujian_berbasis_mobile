package com.fauzanpramulia.dosen_ta_ujian.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fauzanpramulia.dosen_ta_ujian.R;
import com.fauzanpramulia.dosen_ta_ujian.model.MahasiswaModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaHolder>{
    ArrayList<MahasiswaModel> dataUjian;
    Context context;
    OnItemClicked Handler;
    public void setDataMahasiswa(ArrayList<MahasiswaModel> data) {
        this.dataUjian = data;
        notifyDataSetChanged();
    }

    public MahasiswaAdapter(Context context) {
        this.context = context;
    }
    public void setHandler(OnItemClicked clickHandler) {
        this.Handler = clickHandler;
    }
    @NonNull
    @Override
    public MahasiswaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mahasiswa_item, parent, false);
        return new MahasiswaAdapter.MahasiswaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.MahasiswaHolder holder, final int position) {

        MahasiswaModel ujian = dataUjian.get(position);

        String stat = "";
        holder.textNama.setText(ujian.getNama());
        holder.textNim.setText(ujian.getNim());
        if(ujian.getStatus()==0){
            stat = "Belum Ujian";
        }else if(ujian.getStatus()==1){
            stat = "Sedang Ujian";
        }else if(ujian.getStatus()==2){
            stat = "Selesai Ujian";
        }else if (ujian.getStatus()==3){
            stat = "Diblokir";
        }

        holder.textStatus.setText("Status   : "+String.valueOf(ujian.getStatus())+" / ( "+stat+" )");
        holder.textNilai.setText("Nilai     : "+String.valueOf(ujian.getNilai()));

    }

    @Override
    public int getItemCount() {
        if (dataUjian != null) {
            return dataUjian.size();
        }
        return 0;
    }

    public class MahasiswaHolder extends RecyclerView.ViewHolder {
        TextView textNama;
        TextView textNim;
        TextView textStatus;
        TextView textNilai;

        public MahasiswaHolder(View itemView) {
            super(itemView);
//            textNilai = itemView.findViewById(R.id.nilai);
            textNama = itemView.findViewById(R.id.textNama);
            textNim = itemView.findViewById(R.id.textNim);
            textStatus = itemView.findViewById(R.id.textStatus);
            textNilai = itemView.findViewById(R.id.textNilai);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler.clik(dataUjian.get(getAdapterPosition()));

                }
            });
        }
    }
    public interface OnItemClicked{
        void clik(MahasiswaModel m);
    }
}
