package com.fauzanpramulia.dosen_ta_ujian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.model.MahasiswaModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianVeriDanValiModel;
import com.fauzanpramulia.dosen_ta_ujian.shared.RetrofitClientInstance;
import com.fauzanpramulia.dosen_ta_ujian.shared.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUjianActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static String EXTRA_UJIAN = "extra_ujian";
    @BindView(R.id.status_veri_vali) Spinner spinStatus;
    @BindView(R.id.nama_mk) TextView namaMK;
    @BindView(R.id.nama_ujian) TextView namaUjian;
    @BindView(R.id.tanggal_ujian) TextView tanggalUjian;
    @BindView(R.id.waktu_ujian) TextView waktuUjian;
    @BindView(R.id.ruang_ujian) TextView ruangUjian;
    @BindView(R.id.sifat_ujian) TextView sifatUjian;
    @BindView(R.id.kode) TextView kodeUjian;
    @BindView(R.id.semester) TextView semester;
    @BindView(R.id.sks) TextView sks;
    @BindView(R.id.created_by) TextView createdBy;
    @BindView(R.id.status_verifikasi) TextView statusVerifikasi;
    @BindView(R.id.verified_by) TextView verifiedBy;
    @BindView(R.id.verified_at) TextView verifiedAt;
    @BindView(R.id.alasan_tolak_verifikasi) TextView alasanTolakVerifikasi;
    @BindView(R.id.status_validasi) TextView statusValidasi;
    @BindView(R.id.validated_by) TextView validatedBy;
    @BindView(R.id.validated_at) TextView validatedAt;
    @BindView(R.id.alasan_tolak_validasi) TextView alasanTolakValidasi;
    @BindView(R.id.kirim) Button btnSimpan;
    @BindView(R.id.keterangan) EditText keterangan;
    @BindView(R.id.stat) LinearLayout lnrStat;

    public static String E_UJIAN ="extra_u";
    Session session;
    public int status_mhs;
    public String stat1,stat2;

    String[] bankStatusVerifikasi = {"-- Pilih_status --","Belum Diverifikasi","Diverifikasi","Ditolak"};
    String[] bankStatusValidasi = {"-- Pilih_status --","Belum Divalidasi","Divalidasi","Ditolak"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ujian);
        ButterKnife.bind(this);
        session = new Session(this);
        spinStatus.setOnItemSelectedListener(this);

        final UjianVeriDanValiModel ujian = getIntent().getParcelableExtra(EXTRA_UJIAN);

        if (session.getStatusRol()==2){
            if (ujian.getStatus_validasi()==2){
                btnSimpan.setVisibility(View.GONE);
                lnrStat.setVisibility(View.GONE);
                keterangan.setVisibility(View.GONE);
            }else{
                btnSimpan.setVisibility(View.VISIBLE);
                lnrStat.setVisibility(View.VISIBLE);
                keterangan.setVisibility(View.VISIBLE);
            }
            setTitle("Verifikasi Ujian");
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankStatusVerifikasi);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinStatus.setAdapter(aa);
            spinStatus.setSelection(ujian.getStatus_verifikasi());
        }else{
            setTitle("Validasi Ujian");
            if (ujian.getStatus_verifikasi()==1 ||ujian.getStatus_verifikasi()==0){
                btnSimpan.setVisibility(View.GONE);
                lnrStat.setVisibility(View.GONE);
                keterangan.setVisibility(View.GONE);
            }else{
                btnSimpan.setVisibility(View.VISIBLE);
                lnrStat.setVisibility(View.VISIBLE);
                keterangan.setVisibility(View.VISIBLE);
            }
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankStatusValidasi);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinStatus.setAdapter(aa);
            spinStatus.setSelection(ujian.getStatus_validasi());
        }

        if (ujian.getStatus_verifikasi()==0||ujian.getStatus_verifikasi()==1){stat1="Belum Diverifikasi";}
        else if(ujian.getStatus_verifikasi()==2) {stat1="Diverifikasi";}
        else if(ujian.getStatus_verifikasi()==3){stat1="Ditolak";}

        if (ujian.getStatus_validasi()==0||ujian.getStatus_validasi()==1){stat2="Belum Divalidasi";}
        else if(ujian.getStatus_validasi()==2) {stat2="Divalidasi";}
        else if(ujian.getStatus_validasi()==3){stat2="Ditolak";}

        namaMK.setText(ujian.getNama_mk());
        namaUjian.setText(ujian.getNama_ujian());
        tanggalUjian.setText(ujian.getTanggal_mulai()+"/"+ujian.getTanggal_selesai());
        waktuUjian.setText(ujian.getWaktu_ujian()+"/"+ujian.getWaktu_selesai());
        ruangUjian.setText(ujian.getRuang_ujian());
        sifatUjian.setText(ujian.getSifat_ujian());
        kodeUjian.setText(ujian.getKode());
        semester.setText(ujian.getSemester());
        sks.setText(ujian.getSks());
        createdBy.setText(ujian.getCreated_by());
        statusVerifikasi.setText(stat1);
        verifiedBy.setText(ujian.getVerified_by());
        verifiedAt.setText(ujian.getVerified_at());
        if (ujian.getAlasan_tolak_verifikasi()==null){
            alasanTolakVerifikasi.setText("-");
        }else{
            alasanTolakVerifikasi.setText(ujian.getAlasan_tolak_verifikasi());
        }
        statusValidasi.setText(stat2);
        validatedBy.setText(ujian.getValidated_by());
        validatedAt.setText(ujian.getValidated_at());
        if (ujian.getAlasan_tolak_validasi()==null){
            alasanTolakValidasi.setText("-");
        }else{
            alasanTolakValidasi.setText(ujian.getAlasan_tolak_validasi());
        }


        if (session.getStatusRol()==2){
            status_mhs= ujian.getStatus_verifikasi();
        }else if(session.getStatusRol()==3){
            status_mhs = ujian.getStatus_validasi();
        }


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanData(status_mhs,ujian.getId(),keterangan.getText().toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        status_mhs = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void simpanData(int status, int ujian_id, String keterangan){

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);

        Call<ResponseBody> call = client.updateDataVeriVali(session.getToken(),status,ujian_id,keterangan);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jObjError = null;
                try {
                    jObjError = new JSONObject(response.body().string());
                    Toast.makeText(DetailUjianActivity.this, "" + jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(DetailUjianActivity.this, UjianUasUtsActivity.class);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    Toast.makeText(DetailUjianActivity.this, "Error response JSON", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(DetailUjianActivity.this, "Error I/O", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailUjianActivity.this, "Gagal terhubung ke Server!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
