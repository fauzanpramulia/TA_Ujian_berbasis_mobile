package com.fauzanpramulia.dosen_ta_ujian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.adapter.MahasiswaAdapter;
import com.fauzanpramulia.dosen_ta_ujian.model.MahasiswaModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;
import com.fauzanpramulia.dosen_ta_ujian.shared.RetrofitClientInstance;
import com.fauzanpramulia.dosen_ta_ujian.shared.Session;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarMahasiswaActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    Session session ;
    MahasiswaAdapter mahasiswaAdapter;
    public static String EXTRA_ID ="extra_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mahasiswa);
        ButterKnife.bind(this);
        session = new Session(this);

        mahasiswaAdapter = new MahasiswaAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        int ujianKelasID = getIntent().getExtras().getInt(EXTRA_ID);
        getMahasiswa(ujianKelasID);
        recyclerView.setAdapter(mahasiswaAdapter);
    }

    private void getMahasiswa(int ujianKelasID){
        progressBar.setVisibility(View.VISIBLE);

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);

        Call<List<MahasiswaModel>> call = client.getDaftarMhs(session.getToken(),ujianKelasID);
        call.enqueue(new Callback<List<MahasiswaModel>>() {
            @Override
            public void onResponse(Call<List<MahasiswaModel>> call, Response<List<MahasiswaModel>> response) {
                List<MahasiswaModel> absenList = response.body();

                mahasiswaAdapter.setDataMahasiswa((ArrayList<MahasiswaModel>) absenList);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<MahasiswaModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(DaftarMahasiswaActivity.this, "Gagal Load Data", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
