package com.fauzanpramulia.dosen_ta_ujian;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.adapter.UjianAdapter;
import com.fauzanpramulia.dosen_ta_ujian.model.BiodataModel;
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
    BiodataModel profil;
    @BindView(R.id.profil_user) LinearLayout linearProfil;
    @BindView(R.id.nama_profil) TextView txtNama;

    //deklarasi terlebih dahulu fade in sebagai animation
    Animation fadeOut,fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        session = new Session(this);


        fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);


//kemudian deklarasikan ini sebagai fadi in
        fadeIn= AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);

        setTitle("Daftar Ujian");
        ujianAdapter = new UjianAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getProfil();
        getUjian();
        recyclerView.setAdapter(ujianAdapter);
        linearProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                universalDialog("Nim/Username : " + profil.getNip()
                                + "\nEmail : " + profil.getEmail()
                        , "Profil : " + profil.getNama());
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startDialog();
        }
        return super.onKeyDown(keyCode, event);
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
        }
        return super.onOptionsItemSelected(item);
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
                gagalTerhubungServer();
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
                gagalTerhubungServer();
            }

        });

    }

    public void gagalTerhubungServer(){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void getProfil(){

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);

        Call<BiodataModel> call = client.getProfil(session.getToken());
        call.enqueue(new Callback<BiodataModel>() {
            @Override
            public void onResponse(Call<BiodataModel> call, Response<BiodataModel> response) {
                profil = response.body();
                grow();
                txtNama.setText(profil.getNama()+"\n"+profil.getNip());
            }

            @Override
            public void onFailure(Call<BiodataModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Load Data", Toast.LENGTH_SHORT).show();
            }

        });

    }
    public void fadeout(){
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
        linearProfil.startAnimation(fadeOut);
    }




//cara penggunaaannya seperti dibawah ini
    public void fadein(){
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
        linearProfil.setVisibility(View.VISIBLE);
        linearProfil.startAnimation(fadeIn);
    }











    public void grow(){
        linearProfil.setVisibility(LinearLayout.VISIBLE);
        Animation animation   =    AnimationUtils.loadAnimation(this, R.anim.grow);
        animation.setDuration(500);
        linearProfil.setAnimation(animation);
        linearProfil.animate();
        animation.start();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Log.d("Focus debug", "Focus changed !");

        if(!hasFocus) {
            Log.d("Focus debug", "Lost focus !");

            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
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
