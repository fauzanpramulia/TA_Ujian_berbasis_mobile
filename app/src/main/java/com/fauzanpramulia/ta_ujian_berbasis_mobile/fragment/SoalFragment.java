package com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.DaftarSoalActivity;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.R;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.OptionAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.AppDatabase;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.JawabanUjian;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.JawabanModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SoalFragment extends Fragment {
    @BindView(R.id.textNo)
    TextView textNo;
    @BindView(R.id.soalUjian)
    TextView soalUjian;
    @BindView(R.id.next)
    Button btnNext;
    @BindView(R.id.prev)
    Button btnPrev;
    @BindView(R.id.finish)
    Button btnFinish;
    @BindView(R.id.kirimJS)
    Button btnSimpanJS;
    @BindView(R.id.jawaban_js)
    EditText edtJawabanJS;
    public static AppDatabase db;
    Session session;
    OptionAdapter optionAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.home) Button btnHome;
    ArrayList<OptionModel> listOption;
    Bundle bundle;
    public static String DATABASENAME = "ujian.db";

    public SoalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_soal, container, false);
        ButterKnife.bind(this, rootView);
        session = new Session(getActivity());
        db = Room.databaseBuilder(getActivity(), AppDatabase.class, DATABASENAME)
                .allowMainThreadQueries()
                .build();

        bundle = this.getArguments();
        textNo.setText("No : " + String.valueOf(bundle.getInt("no_urut")));
        soalUjian.setText(bundle.getString("soal"));
        listOption = (ArrayList<OptionModel>) getArguments().getSerializable("options");

        if (listOption != null ){
            Collections.shuffle(listOption);
        }
        if (bundle.getInt("tipe_soal") == 2) {
            edtJawabanJS.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            btnSimpanJS.setVisibility(View.VISIBLE);
            int i = checkDataJS(session.getSoalUjianID(), session.getUjianMahasiswaID());
            if (i == 1) {
                JawabanModel jawabanJS = ambilDataJS();
                edtJawabanJS.setText(jawabanJS.getJawaban());
            }
        } else {
            edtJawabanJS.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            btnSimpanJS.setVisibility(View.GONE);
        }
        int pos = getArguments().getInt("pos");
        if (pos == 1) {
            btnNext.setVisibility(View.INVISIBLE);
            btnFinish.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        } else if (pos == 0) {
            btnPrev.setVisibility(View.INVISIBLE);
            btnFinish.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.INVISIBLE);
        }
        optionAdapter = new OptionAdapter(getActivity());
        optionAdapter.setHandler((OptionAdapter.OnItemClicked) getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        optionAdapter.setDataOption(listOption);
        recyclerView.setAdapter(optionAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tampilOption();
//                final ProgressB progress = new ProgressDialog(getActivity());
//                progress.setTitle("Loading");
//                progress.setMessage("Wait while loading...");
//                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progressBar.setVisibility(View.VISIBLE);
//                progressBar.setMax(100);
//                progressBar.setProgress(0);
//                progress.show();

                final Thread thread = new Thread(){
                    @Override
                    public void run(){
                        try {
                            for (int i = 0;i <100;i++){
//                                progressBar.setProgress(i);
                                sleep(20);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            ((DaftarSoalActivity) getActivity()).next();
                        }
                    }
                };
                thread.start();
//                progress.dismiss();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
//disini masalah saya adalah memanggil thread yang terllau banyak sehingga kita harus membatasi dengan
                //loading
                //loading yang saya buat disini dengan thread juga.
                //sehingga error tadi tidak ditemukan lagi..
                //permasalahan thread berbeda beda tetapi dikasus saya ini adalah banyaknye thread yang menumpuk sehingga
                //untuk membatasinya menggunakan thread juga
                final Thread thread = new Thread(){
                    @Override
                    public void run(){
                        try {
                            for (int i = 0;i <100;i++){
//                                progressBar.setProgress(i);
                                sleep(20);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            ((DaftarSoalActivity) getActivity()).prev();
                        }
                    }
                };
                thread.start();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tampilOption();
                progressBar.setVisibility(View.VISIBLE);
                final Thread thread = new Thread(){
                    @Override
                    public void run(){
                        try {
                            for (int i = 0;i <100;i++){
                                sleep(20);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            ((DaftarSoalActivity) getActivity()).fine();
                        }
                    }
                };
                thread.start();

            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final Thread thread = new Thread(){
                    @Override
                    public void run(){
                        try {
                            for (int i = 0;i <100;i++){
                                sleep(20);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally {
                            ((DaftarSoalActivity) getActivity()).fine();
                        }
                    }
                };
                thread.start();
            }
        });

        btnSimpanJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveJawaban();
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
        return rootView;
    }

    public void saveJawaban() {
        JawabanUjian jawabanUjian = new JawabanUjian();
        jawabanUjian.no_urut = session.getNoUrut();
        jawabanUjian.soal_ujian_id = session.getSoalUjianID();
        jawabanUjian.ujian_mahasiswa_id = session.getUjianMahasiswaID();
        jawabanUjian.jawaban = edtJawabanJS.getText().toString();
        jawabanUjian.tipe_soal = session.getTipeSoal();
        int i = checkDataJS(jawabanUjian.soal_ujian_id, jawabanUjian.ujian_mahasiswa_id);
        if (i == 0) {
            Toast.makeText(getActivity(), "Data masuk", Toast.LENGTH_SHORT).show();
            db.jawabanUjianDao().insertJawabanUjian(jawabanUjian);
        } else {
            db.jawabanUjianDao().updateJawaban(jawabanUjian.soal_ujian_id, jawabanUjian.ujian_mahasiswa_id, jawabanUjian.jawaban);
            Toast.makeText(getActivity(), "Jawaban diperbarui!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveMovieToDb(final List<JawabanModel> listJawaban) {
        for (JawabanModel jawabanModel : listJawaban) {

            JawabanUjian jawabanUjian = new JawabanUjian();
            jawabanUjian.no_urut = jawabanModel.getNo_urut();
            jawabanUjian.soal_ujian_id = jawabanModel.soal_id;
            jawabanUjian.ujian_mahasiswa_id = jawabanModel.ujian_mahasiswa_id;
            jawabanUjian.jawaban = jawabanModel.jawaban;
            int i = checkData(jawabanUjian.soal_ujian_id, jawabanUjian.ujian_mahasiswa_id, Integer.parseInt(jawabanUjian.jawaban));
            if (i == 0) {
                Toast.makeText(getActivity(), "Data masuk", Toast.LENGTH_SHORT).show();
                db.jawabanUjianDao().insertJawabanUjian(jawabanUjian);
            } else {
                Toast.makeText(getActivity(), "Data Sudah Ada", Toast.LENGTH_SHORT).show();
            }

        }
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

    public JawabanModel ambilDataJS() {

        JawabanUjian n = db.jawabanUjianDao().getJawabanByID(session.getSoalUjianID(), session.getUjianMahasiswaID());
        JawabanModel m = new JawabanModel(
                n.no_urut,
                n.soal_ujian_id,
                n.ujian_mahasiswa_id,
                n.jawaban,
                n.tipe_soal
        );
//            Toast.makeText(this, ""+m.jawaban, Toast.LENGTH_SHORT).show();
        return m;
    }


}