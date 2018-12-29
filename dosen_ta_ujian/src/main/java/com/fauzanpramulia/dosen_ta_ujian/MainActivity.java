package com.fauzanpramulia.dosen_ta_ujian;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.adapter.UjianAdapter;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;
import com.fauzanpramulia.dosen_ta_ujian.shared.RetrofitClientInstance;
import com.fauzanpramulia.dosen_ta_ujian.shared.Session;

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

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    Session session ;
    UjianAdapter ujianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        session = new Session(this);

        ujianAdapter = new UjianAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUjian();
        recyclerView.setAdapter(ujianAdapter);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getUjian(){
        progressBar.setVisibility(View.VISIBLE);

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);

        Call<List<UjianModel>> call = client.getUjian(session.getToken());
        call.enqueue(new Callback<List<UjianModel>>() {
            @Override
            public void onResponse(Call<List<UjianModel>> call, Response<List<UjianModel>> response) {
                List<UjianModel> absenList = response.body();

                ujianAdapter.setDataUjian((ArrayList<UjianModel>) absenList);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<UjianModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Gagal Load Data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                this);
        myAlertDialog.setTitle("Peringatan");
        myAlertDialog.setMessage("Apakah anda ingin keluar ?");

        myAlertDialog.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        logout();
                    }
                });

        myAlertDialog.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        myAlertDialog.show();
    }

    private void logout() {
        progressBar.setVisibility(View.VISIBLE);

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);

        Call<ResponseBody> call = client.logout(session.getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null){
                    JSONObject jObjError =null;
                    try {
                        jObjError = new JSONObject(response.body().string());
                        Toast.makeText(MainActivity.this, ""+jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terhubung ke server!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

        });

    }
}
