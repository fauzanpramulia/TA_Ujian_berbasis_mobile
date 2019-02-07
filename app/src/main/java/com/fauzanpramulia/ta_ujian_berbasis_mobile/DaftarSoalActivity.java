package com.fauzanpramulia.ta_ujian_berbasis_mobile;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.OptionAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.adapter.SoalAdapter;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.AppDatabase;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.JawabanUjian;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.db.SoalUjian;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment.DaftarFragment;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.fragment.SoalFragment;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.JawabanModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.HomeWatcher;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.OnHomePressedListener;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.RetrofitClientInstance;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.shared.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarSoalActivity extends AppCompatActivity implements SoalAdapter.OnItemClicked, OptionAdapter.OnItemClicked {
    public static int posisi = 0;
    public static String EXTRA_KODE = "extra_kode";
    public static String EXTRA_UJIAN_ID = "extra_ujian_id";
    public static String EXTRA_NAMA_MK = "nama_mk";
    DaftarFragment daftarFragment;
    SoalFragment soalFragment;
    OptionAdapter optionAdapter;
    Session session;
    ArrayList<SoalModel> daftarSoal = null;
    Bundle bundle, c;
    AppDatabase db;
    public static final String DATABASENAME = "ujian.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_soal);
        ButterKnife.bind(this);
        bundle = new Bundle();
        optionAdapter = new OptionAdapter(this);
        db = Room.databaseBuilder(this, AppDatabase.class, DATABASENAME)
                .allowMainThreadQueries()
                .build();

        String kode = getIntent().getExtras().getString(EXTRA_KODE);
        int ujianID = getIntent().getExtras().getInt(EXTRA_UJIAN_ID);
        String namaMK = getIntent().getExtras().getString(EXTRA_NAMA_MK);
        setTitle(namaMK);

        session = new Session(this);
        getDaftarSoal(kode, ujianID);

        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                Toast.makeText(DaftarSoalActivity.this, "Pelanggaran, Anda Terblokir!\n Hubungi Dosen Bersangkutan", Toast.LENGTH_SHORT).show();
                pelanggaran();
                session.setToken("");
                Intent intent = new Intent(DaftarSoalActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
    }

    @Override
    public void onBackPressed() {
        bundle.putSerializable("daftarSoal", (Serializable) daftarSoal);
        daftarFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.soal_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.keluar:
//                Intent i = new Intent(DaftarSoalActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
                //break;
            case R.id.selesai:
                    startPengumpulan();
                break;

//            case R.id.clearDB:
//                hapuDB();
//                hapusSoalDB();
//                Toast.makeText(DaftarSoalActivity.this, "data db dihapus!", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.aturan:
                startDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDaftarSoal(String kode, int ujianID) {

        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);

        Call<List<SoalModel>> call = client.daftarSoal(session.getToken(), kode, ujianID);
        call.enqueue(new Callback<List<SoalModel>>() {
            @Override
            public void onResponse(Call<List<SoalModel>> call, Response<List<SoalModel>> response) {

                if (getCheckSoalDB(response.body()) == 0) {
                    daftarSoal = (ArrayList<SoalModel>) response.body();
                    Collections.shuffle(daftarSoal);
                    Toast.makeText(DaftarSoalActivity.this, "Soal Diacak", Toast.LENGTH_SHORT).show();
                    saveSoalDB(daftarSoal);
                    daftarSoal = (ArrayList<SoalModel>) getSoalDB();
                } else {
                    daftarSoal = (ArrayList<SoalModel>) getSoalDB();
                    Toast.makeText(DaftarSoalActivity.this, "soal dari db", Toast.LENGTH_SHORT).show();
                }
                bundle.putSerializable("daftarSoal", (Serializable) daftarSoal);
                //((MyContext) getApplicationContext()).setSavedData(bundle);
                daftarFragment();
            }

            @Override
            public void onFailure(Call<List<SoalModel>> call, Throwable t) {
                Toast.makeText(DaftarSoalActivity.this, "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                keluarGagalTerhubung();
            }
        });
    }

    //berhubungan ke DB SQLITE soal====================================
    public void saveSoalDB(List<SoalModel> data) {
        int index = 1;
        for (SoalModel m : data) {
            SoalUjian soalUjian = new SoalUjian();
            soalUjian.no_urut = index++;
            soalUjian.id = m.getId();
            soalUjian.soal_id = m.getSoal_id();
            soalUjian.soal = m.getSoal();
            soalUjian.tipeSoal = m.getTipe_soal();
            soalUjian.options = m.getOptions();
//            Toast.makeText(this, "tampil: "+m.getTipe_soal(), Toast.LENGTH_SHORT).show();
            db.soalUjianDao().insertSoal(soalUjian);
        }
    }

    public List<SoalModel> getSoalDB() {
        List<SoalUjian> soalDiDB = db.soalUjianDao().getSoal();
        ArrayList<SoalModel> soals = new ArrayList<>();
        for (SoalUjian n : soalDiDB) {
            SoalModel m = new SoalModel(
                    n.id,
                    n.soal_id,
                    n.soal,
                    n.tipeSoal,
                    n.no_urut,
                    n.options
            );
            soals.add(m);
        }
        return soals;
    }

    public int getCheckSoalDB(List<SoalModel> data) {
        int t = 0;
        for (SoalModel m : data) {

            t = db.soalUjianDao().checkSoal(m.getId(), m.getSoal_id(), m.getSoal());
        }
        return t;
    }

    public void hapusSoalDB() {
        db.soalUjianDao().clear();
    }

    //===============================================================================
    public void daftarFragment() {
        daftarFragment = new DaftarFragment();
        daftarFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_placeholder, daftarFragment);
        transaction.commit();
    }

    public void soalFragment() {
        int pos;
        if (posisi == daftarSoal.size() - 1) {
            pos = 1;
        } else if (posisi == 0) {
            pos = 0;
        } else {
            pos = 2;
        }
        bundle.putInt("pos", pos);

        //cara memanggil fragment sangat muda hanya dengan deklarsikan fragment tersebut dengan kelas fragmentnya..
        //disini saya deklarasi global SoalFragment dengan variabel soalFragment.
        soalFragment = new SoalFragment();
        //ketika mau memasukkan item ke dalam fragment juga bisa. maksudnya mengirim data
        //dengan menggunakan bundle
        soalFragment.setArguments(bundle);
        //setelah itu cukup memanggil fragment manager dan transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //jangan lupa tempat meletakkan fragmentnya.
        transaction.replace(R.id.fragment_placeholder, soalFragment);
        //setelah itu commit untuk memulai menampilkan fragmentnnya...
        transaction.commit();
    }

    @Override
    public void clik(SoalModel s, int position) {
        posisi = position;

        saveBundle(s);
        soalFragment();
    }

    @Override
    public void clik(OptionModel m, int position) {
        Toast.makeText(this, "Item yang diklik " + m.pilihan, Toast.LENGTH_SHORT).show();
    }

    public void next() {
        posisi = posisi + 1;
        if (posisi < daftarSoal.size()) {
            SoalModel soal = daftarSoal.get(posisi);
            saveBundle(soal);
            soalFragment();
        } else {
            posisi = posisi - 1;

        }
    }

    public void prev() {
        posisi = posisi - 1;
        if (posisi >= 0) {
            SoalModel soal = daftarSoal.get(posisi);
            saveBundle(soal);
            soalFragment();
        } else {
            posisi = posisi + 1;
        }
    }

    public void fine() {
        bundle.putSerializable("daftarSoal", (Serializable) daftarSoal);
        daftarFragment();
    }

    public void saveBundle(SoalModel s) {
        session.setSoalUjianID(s.getId());
        session.setNoUrut(s.getNo_urut());
        session.setTipeSoal(s.getTipe_soal());
//        Toast.makeText(this, "tampil: "+session.getTipeSoal()+" ini aslinya:"+s.getTipe_soal(), Toast.LENGTH_SHORT).show();

        bundle = new Bundle();
        bundle.putInt("id", s.getId());
        bundle.putInt("no_urut", posisi + 1);
        bundle.putString("soal", s.getSoal());
        bundle.putInt("tipe_soal", s.getTipe_soal());
        bundle.putSerializable("options", (Serializable) s.getOptions());
    }

//    public SoalModel findSoal(int id, List<SoalModel> daftarSoal) {
//
//        for (SoalModel soal : daftarSoal) {
//            if (soal.getId().equals(id)) {
//                return soal;
//            }
//        }
//        return null;
//    }

    //DB untuk jawaban ==============================================
    public List<JawabanModel> tampilData() {
        List<JawabanUjian> nowPlayings = db.jawabanUjianDao().getJawaban();
        ArrayList<JawabanModel> jawab = new ArrayList<>();
        for (JawabanUjian n : nowPlayings) {
            JawabanModel m = new JawabanModel(
                    n.no_urut,
                    n.soal_ujian_id,
                    n.ujian_mahasiswa_id,
                    n.jawaban,
                    n.tipe_soal
            );
//            Toast.makeText(this, ""+m.jawaban, Toast.LENGTH_SHORT).show();
            jawab.add(m);
        }
        return jawab;
    }

    public int getJawaban() {
        int t = 0;
        t = db.jawabanUjianDao().checkJumlah();
        return t;
    }
    public void hapuDB() {
        db.jawabanUjianDao().clear();
    }

    //jawaban=============================================================
    public void keluarGagalTerhubung() {
        Intent i = new Intent(DaftarSoalActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void pelanggaran(){
        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);
        Call<ResponseBody> call = client.updateStatusMHS(session.getToken(), session.getUjianMahasiswaID());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jObjError = null;
                try {
                    jObjError = new JSONObject(response.body().string());
                    Toast.makeText(DaftarSoalActivity.this, "" + jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    hapuDB();
                    hapusSoalDB();
                } catch (JSONException e) {
                    Toast.makeText(DaftarSoalActivity.this, "hmm 1", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(DaftarSoalActivity.this, "hmmm 2", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DaftarSoalActivity.this, "tidak berhasil", Toast.LENGTH_SHORT).show();
            }
        });

    }
//DISABLE BUTTON=====================================================================================
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
//    @Override
//    protected void onUserLeaveHint() {
//        Toast.makeText(this, "Home Button Press!", Toast.LENGTH_SHORT).show();
//        finish();
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        super.onUserLeaveHint();
//    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                this);
        myAlertDialog.setTitle("Aturan");
        myAlertDialog.setMessage(getResources().getString(R.string.aturan));

        myAlertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        myAlertDialog.show();
    }
    private void startPengumpulan() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                this);
        myAlertDialog.setTitle("Perhatian");
        myAlertDialog.setMessage(getResources().getString(R.string.periksa_selesai));

        myAlertDialog.setPositiveButton("Selesai",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        int j = getJawaban();
                        if(j == 0){
                            Toast.makeText(DaftarSoalActivity.this, "Isi jawaban ujian terlebih dahulu!", Toast.LENGTH_SHORT).show();
                        }else{
                            selesaiKumpul();
                        }
                    }
                });
        myAlertDialog.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        myAlertDialog.show();
    }

    public void selesaiKumpul(){
        UjianClient client = RetrofitClientInstance.getRetrofitInstance().create(UjianClient.class);
        Call<ResponseBody> call = client.simpanJawaban("Bearer " + session.getToken(), tampilData());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jObjError = null;
                try {
                    jObjError = new JSONObject(response.body().string());
                    Toast.makeText(DaftarSoalActivity.this, "" + jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    hapuDB();
                    hapusSoalDB();
                } catch (JSONException e) {
                    Toast.makeText(DaftarSoalActivity.this, "hmm 1", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(DaftarSoalActivity.this, "hmmm 2", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DaftarSoalActivity.this, "tidak berhasil", Toast.LENGTH_SHORT).show();
            }
        });
        Intent in = new Intent(DaftarSoalActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }
}
