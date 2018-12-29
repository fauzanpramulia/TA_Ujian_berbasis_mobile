package com.fauzanpramulia.ta_ujian_berbasis_mobile;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.SoalAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.UjianAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment.DaftarFragment;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment.SoalFragment;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.UjianModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.RetrofitClientInstance;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarSoalActivity extends AppCompatActivity {

    public static String EXTRA_KODE = "extra_kode";
    public static String EXTRA_UJIAN_ID = "extra_ujian_id";
    public static String EXTRA_NAMA_MK = "nama_mk";
    DaftarFragment daftarFragment;
    SoalAdapter soalAdapter;
    Session session ;
    List<SoalModel> daftarSoal;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_soal);
        ButterKnife.bind(this);

        String kode = getIntent().getExtras().getString(EXTRA_KODE);
        int ujianID = getIntent().getExtras().getInt(EXTRA_UJIAN_ID);
        String namaMK = getIntent().getExtras().getString(EXTRA_NAMA_MK);
        setTitle(namaMK);

        session = new Session(this);
        getDaftarSoal(kode,ujianID);
        bundle = new Bundle();
    }
    @Override
    public void onBackPressed()
    { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.soal_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.keluar:
                Intent i = new Intent(DaftarSoalActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDaftarSoal(String kode, int ujianID) {

        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);

        Call<List<SoalModel>> call = client.daftarSoal(session.getToken(),kode,ujianID);
        call.enqueue(new Callback<List<SoalModel>>() {
            @Override
            public void onResponse(Call<List<SoalModel>> call, Response<List<SoalModel>> response) {
                daftarSoal = response.body();
                bundle.putSerializable("daftarSoal", (Serializable) daftarSoal);
                daftarFragment();
            }

            @Override
            public void onFailure(Call<List<SoalModel>> call, Throwable t) {
                Toast.makeText(DaftarSoalActivity.this, "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void daftarFragment(){
        daftarFragment = new DaftarFragment();
        daftarFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_placeholder, daftarFragment);
        transaction.commit();
    }


}
