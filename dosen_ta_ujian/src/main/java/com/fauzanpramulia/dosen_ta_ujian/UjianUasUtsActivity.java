package com.fauzanpramulia.dosen_ta_ujian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.adapter.UjianAdapter;
import com.fauzanpramulia.dosen_ta_ujian.adapter.UjianVeriDanValiAdapter;
import com.fauzanpramulia.dosen_ta_ujian.model.BiodataModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianVeriDanValiModel;
import com.fauzanpramulia.dosen_ta_ujian.shared.RetrofitClientInstance;
import com.fauzanpramulia.dosen_ta_ujian.shared.Session;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UjianUasUtsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Session session;
    UjianVeriDanValiAdapter ujianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian_uas_uts);

        ButterKnife.bind(this);
        session = new Session(this);

        if(session.getStatusRol()==2){
            setTitle("Verifikasi Ujian");
        }else{
            setTitle("Validasi Ujian");
        }

        ujianAdapter = new UjianVeriDanValiAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getUjian();

        recyclerView.setAdapter(ujianAdapter);
    }

    private void getUjian() {
        progressBar.setVisibility(View.VISIBLE);

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);

        Call<List<UjianVeriDanValiModel>> call = client.getUjianUasUts(session.getToken());
        call.enqueue(new Callback<List<UjianVeriDanValiModel>>() {
            @Override
            public void onResponse(Call<List<UjianVeriDanValiModel>> call, Response<List<UjianVeriDanValiModel>> response) {
                List<UjianVeriDanValiModel> ujianList = response.body();
                ujianAdapter.setDataUjian((ArrayList<UjianVeriDanValiModel>) ujianList);
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<UjianVeriDanValiModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(UjianUasUtsActivity.this, "Gagal Load Data", Toast.LENGTH_SHORT).show();
                gagalTerhubungServer();
            }
        });
    }

    public void gagalTerhubungServer() {
        Intent i = new Intent(UjianUasUtsActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(UjianUasUtsActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
