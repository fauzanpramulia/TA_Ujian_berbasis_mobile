package com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.R;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.AppDatabase;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.JawabanUjian;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionHolder> {
    ArrayList<OptionModel> dataOption;
    Context context;
    OnItemClicked Handler;
    AppDatabase db;
    Session session;
    public static final String DATABASENAME = "ujian.db";

    public void setDataOption(ArrayList<OptionModel> data) {
        this.dataOption = data;
        notifyDataSetChanged();
    }

    public OptionAdapter(Context context) {
        this.context = context;
    }

    public void setHandler(OptionAdapter.OnItemClicked clickHandler) {
        this.Handler = clickHandler;
    }

    @NonNull
    @Override
    public OptionAdapter.OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new OptionAdapter.OptionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionAdapter.OptionHolder holder, final int position) {

        final OptionModel option = dataOption.get(position);
        session = new Session(context);
        db = Room.databaseBuilder(context, AppDatabase.class, DATABASENAME)
                .allowMainThreadQueries()
                .build();
        int ada = checkData(session.getSoalUjianID(), session.getUjianMahasiswaID(), option.getId());
        if (ada == 1) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.textOption.setText(option.getPilihan());

        holder.checkBox.setTag(dataOption.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                OptionModel opModel = (OptionModel) cb.getTag();
                opModel.setSelected(cb.isChecked());
                dataOption.get(position).setSelected(cb.isChecked());

                if (cb.isChecked() == false) {
                    int i = checkData(session.getSoalUjianID(), session.getUjianMahasiswaID(), option.id);
                    if (i != 0) {
                        db.jawabanUjianDao().deleteByItem( String.valueOf(option.id));
                        Toast.makeText(context, "hapus data"+option.soal_id+session.getUjianMahasiswaID()+String.valueOf(option.id), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    JawabanUjian jawabanUjian = new JawabanUjian();
                    jawabanUjian.no_urut = session.getNoUrut();
                    jawabanUjian.soal_ujian_id = session.getSoalUjianID();
                    jawabanUjian.ujian_mahasiswa_id = session.getUjianMahasiswaID();
                    jawabanUjian.jawaban = String.valueOf(option.id);
                    jawabanUjian.tipe_soal = session.getTipeSoal();
                    //tandai ini
                    int i = checkData(jawabanUjian.soal_ujian_id, jawabanUjian.ujian_mahasiswa_id,option.id);
                    if (i == 0) {
                        Toast.makeText(context, "Data masuk", Toast.LENGTH_SHORT).show();
                        db.jawabanUjianDao().insertJawabanUjian(jawabanUjian);
                    } else {
                        Toast.makeText(context, "Data sudah ada", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "Pilih satu option saja", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (dataOption != null) {
            return dataOption.size();
        }
        return 0;
    }

    public class OptionHolder extends RecyclerView.ViewHolder {
        CheckBox textOp;
        TextView textOption;
        CheckBox checkBox;

        public OptionHolder(View itemView) {
            super(itemView);
            textOp = itemView.findViewById(R.id.textOp);
            textOption = itemView.findViewById(R.id.textOption);
            checkBox = itemView.findViewById(R.id.textOp);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Handler.clik(dataOption.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClicked {
        void clik(OptionModel m, int position);
    }

    public List<OptionModel> getOption() {
        return dataOption;
    }

    public int checkData(int id, int ujian_mahasiswa_id, int pilihan_id) {
        int nilai = 0;

        nilai = db.jawabanUjianDao().checkUjianSOal(id, ujian_mahasiswa_id, String.valueOf(pilihan_id));
        return nilai;
    }

    public int checkDataJS(int id, int ujian_mahasiswa_id) {
        int nilai = 0;

        nilai = db.jawabanUjianDao().checkJawabanJS(id, ujian_mahasiswa_id);
        return nilai;
    }
}
