package com.fauzanpramulia.ta_ujian_berbasis_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.UjianAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.BiodataModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.UjianModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.RetrofitClientInstance;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

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

public class MainActivity extends AppCompatActivity implements UjianAdapter.OnItemClicked {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.ex)
    TextView ex;
    Session session;
    @BindView(R.id.nama_profil)
    TextView txtNama;
    @BindView(R.id.profil_user)
    LinearLayout linearProfil;
    UjianAdapter ujianAdapter;
    BiodataModel profil;
    //deklarasikan fade out sebagai Animation
    Animation fadeIn;
    Animation fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        session = new Session(this);

        //ambil data fade out dengan animationutis
        //jangan lupa buat folder dengan anim kemudian filenya dengan nama fadeout
        fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        fadeIn = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);
        ex.setText(session.getToken().toString());

        ujianAdapter = new UjianAdapter(this);
        ujianAdapter.setHandler(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getProfil();
        getUjian();
        recyclerView.setAdapter(ujianAdapter);

        linearProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                universalDialog("Nim/Username : " + profil.getNim()
                                + "\nEmail : " + profil.getEmail()
                                + "\nAlamat : " + profil.getAlamat()
                                +"\nAgama : "+profil.getAgama()
                        , "Profil : " + profil.getNama());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.petunjuk:
                universalDialog(getResources().getString(R.string.aturan_menu), "Petunjuk");
                break;

            case R.id.about:
                universalDialog(getResources().getString(R.string.about), "Tentang");
                break;
            case R.id.refresh:
                Toast.makeText(this, "Refresh Data", Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.INVISIBLE);
                getProfil();
                getUjian();
                recyclerView.setAdapter(ujianAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getUjian() {
        progressBar.setVisibility(View.VISIBLE);

        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);

        Call<List<UjianModel>> call = client.getUjian(session.getToken());
        call.enqueue(new Callback<List<UjianModel>>() {
            @Override
            public void onResponse(Call<List<UjianModel>> call, Response<List<UjianModel>> response) {
                List<UjianModel> absenList = response.body();

                ujianAdapter.setDataUjian((ArrayList<UjianModel>) absenList);
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<UjianModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Gagal Load Data", Toast.LENGTH_SHORT).show();
                keluarGagalTerhubung();
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

        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);

        Call<ResponseBody> call = client.logout(session.getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.body().string());
                        Toast.makeText(MainActivity.this, "" + jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        finish();
                        startActivity(i);
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
                keluarGagalTerhubung();
                progressBar.setVisibility(View.INVISIBLE);
            }

        });

    }

    private void getProfil() {

        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);

        Call<BiodataModel> call = client.getProfil(session.getToken());
        call.enqueue(new Callback<BiodataModel>() {
            @Override
            public void onResponse(Call<BiodataModel> call, Response<BiodataModel> response) {
                profil = response.body();
//                Toast.makeText(MainActivity.this, ""+profil.getNama()+"\n"+profil.getNim(), Toast.LENGTH_SHORT).show();
//                fadein();
                grow();
                txtNama.setText(profil.getNama() + "\n" + profil.getNim());
            }

            @Override
            public void onFailure(Call<BiodataModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Load Data", Toast.LENGTH_SHORT).show();
                keluarGagalTerhubung();
            }

        });

    }

    @Override
    public void clik(UjianModel m) {
        session.setUjianMahasiswaID(m.getId());
        Intent i = new Intent(MainActivity.this, AturanUjianActivity.class);
        i.putExtra(AturanUjianActivity.EXTRA_UJIAN, m);
        startActivity(i);
        // fadeout();
    }

    public void keluarGagalTerhubung() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }


//disini saya menggunakan method agar bisa digunakan berkali kali....
    public void fadeout() {
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearProfil.setVisibility(View.GONE);
            }
        });
        //tipe dari linearProfil adalah LinaerLayout.. jadi saya ingin membuat linear layout menghilang secara dafe out
        linearProfil.startAnimation(fadeOut);
    }

    public void fadein() {
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //linearProfil.setVisibility(View.GONE);
            }
        });
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.startAnimation(fadeIn);
    }

    public void grow() {
        linearProfil.setVisibility(LinearLayout.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.grow);
        animation.setDuration(500);
        linearProfil.setAnimation(animation);
        linearProfil.animate();
        animation.start();
    }

    private void universalDialog(String message, String title) {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                this);
        myAlertDialog.setTitle(title);
        myAlertDialog.setMessage(message);

        myAlertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        myAlertDialog.show();
    }
}
