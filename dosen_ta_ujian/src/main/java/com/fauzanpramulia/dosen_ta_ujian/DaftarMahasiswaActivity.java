package com.fauzanpramulia.dosen_ta_ujian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class DaftarMahasiswaActivity extends AppCompatActivity implements MahasiswaAdapter.OnItemClicked{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    Session session ;
    MahasiswaAdapter mahasiswaAdapter;
    public static String EXTRA_UJIAN ="extra_ujian";
    @BindView(R.id.ujian_profil) LinearLayout linearProfil;
    @BindView(R.id.nama_ujian) TextView txtUjianProfil;


    @BindView(R.id.deskripsi_ujian) LinearLayout linearDeskripsi;

    UjianModel ujian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mahasiswa);
        ButterKnife.bind(this);
        session = new Session(this);
        ujian = getIntent().getExtras().getParcelable(EXTRA_UJIAN);
        final int[] banding = {0};
        setTitle("Daftar Mahasiswa Kelas");
        linearProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (banding[0] ==0){
                    shrinkDeskripsi();
//                    linearDeskripsi.setVisibility(View.GONE);
                    banding[0] =1;
                }else {
//                    linearDeskripsi.setVisibility(View.VISIBLE);
                    banding[0] =0;
                    growDeskripsi();
                }
            }
        });
        mahasiswaAdapter = new MahasiswaAdapter(this);
        mahasiswaAdapter.setHandler(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        grow();
        getMahasiswa(session.getUjianID());
        recyclerView.setAdapter(mahasiswaAdapter);

        txtUjianProfil.setText(
                "Mata Kuliah : "+ujian.getNama_mk()+"\n"
                +"Kelas : "+ujian.getNama_kelas()+"\n"
                +"Jenis Ujian : "+ujian.getNama_ujian()+"\n"
                +"Ruang : "+ujian.getRuang_ujian()+"\n"
                +"Sifat Ujian : "+ujian.getSifat_ujian()+"\n"
                +"Tanggal : "+ujian.getTanggal_mulai()+"/"+ujian.getTanggal_selesai()+"\n"
                +"Waktu : "+ujian.getWaktu_ujian()+"/"+ujian.getWaktu_selesai()+"\n"
                +"Kode Ujian : "+ujian.getKode()
        );
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

    @Override
    public void clik(MahasiswaModel m) {
//        Toast.makeText(this, ""+m.getNama(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(DaftarMahasiswaActivity.this, ChangeStatusActivity.class);
        i.putExtra(ChangeStatusActivity.EXTRA_MAHASISWA, m);
        i.putExtra(ChangeStatusActivity.E_UJIAN, ujian);
        startActivity(i);
        finish();
    }

    public void grow(){
        linearProfil.setVisibility(LinearLayout.VISIBLE);
        Animation animation   =    AnimationUtils.loadAnimation(this, R.anim.grow);
        animation.setDuration(500);
        linearProfil.setAnimation(animation);
        linearProfil.animate();
        animation.start();
    }

    // abaikan kodingan yang lain... fokus ke kodingan ini saja ya..
    //setelah itu hanya tinggal mengatur munculnya animasi seperti dibawah ini
    public void shrinkDeskripsi(){
        Animation animation   =    AnimationUtils.loadAnimation(this, R.anim.shrink);
        animation.setDuration(500);
        linearDeskripsi.setAnimation(animation);
        linearDeskripsi.animate();
        animation.start();
        linearDeskripsi.setVisibility(LinearLayout.GONE);
    }

    public void growDeskripsi(){
        linearDeskripsi.setVisibility(LinearLayout.VISIBLE);
        Animation animation   =    AnimationUtils.loadAnimation(this, R.anim.grow);
        animation.setDuration(500);
        linearDeskripsi.setAnimation(animation);
        linearDeskripsi.animate();
        animation.start();
    }
}
