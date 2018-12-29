package com.fauzanpramulia.ta_ujian_berbasis_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.LoginModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.UjianModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.RetrofitClientInstance;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AturanUjianActivity extends AppCompatActivity {
    @BindView(R.id.text_kode) MaterialEditText editKode;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.btn_mulai) Button btnMulai;
    Session session;
    public static String EXTRA_UJIAN = "extra_ujian";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aturan_ujian);
        ButterKnife.bind(this);
        session = new Session(this);
        final UjianModel ujian = getIntent().getParcelableExtra(EXTRA_UJIAN);
        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUjian(editKode.getText().toString(),ujian.getUjian_id(),ujian.getNama_mk());
            }
        });
    }

    private void getUjian(final String kode, final int ujianID, final String namaMK){
        progressBar.setVisibility(View.VISIBLE);

        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);

        Call<ResponseBody> call = client.getSoal(session.getToken(),kode,ujianID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.body() != null){

                    Intent i = new Intent(AturanUjianActivity.this, DaftarSoalActivity.class);
                    i.putExtra(DaftarSoalActivity.EXTRA_KODE, kode);
                    i.putExtra(DaftarSoalActivity.EXTRA_UJIAN_ID, ujianID);
                    i.putExtra(DaftarSoalActivity.EXTRA_NAMA_MK, namaMK);
                    startActivity(i);
                    finish();
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(AturanUjianActivity.this, ""+jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AturanUjianActivity.this, "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
