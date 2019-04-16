package com.fauzanpramulia.dosen_ta_ujian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.model.MahasiswaModel;
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

public class ChangeStatusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static String EXTRA_MAHASISWA = "extra_mahasiswa";
    @BindView(R.id.status_mahasiswaST) Spinner spinStatus;
    @BindView(R.id.nama_mahasiswaST) TextView namaMhs;
    @BindView(R.id.nim_mahasiswaST) TextView nimMhs;
    @BindView(R.id.nilai_mahasiswaST) TextView nilaiMhs;
    @BindView(R.id.kirim) Button btnSimpan;

    public static String E_UJIAN ="extra_u";
    Session session;
    public int status_mhs;

    String[] bankStatus = {"Belum Ujian","Sedang Ujian","Selesai Ujian","Diblokir"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);
        ButterKnife.bind(this);
        session = new Session(this);
        setTitle("Ubah Status Mahasiswa");

        spinStatus.setOnItemSelectedListener(this);
        final UjianModel ujian= getIntent().getExtras().getParcelable(E_UJIAN);

        final MahasiswaModel mhs = getIntent().getParcelableExtra(EXTRA_MAHASISWA);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankStatus);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStatus.setAdapter(aa);
        spinStatus.setSelection(mhs.getStatus());

        namaMhs.setText(mhs.getNama());
        nimMhs.setText(mhs.getNim());
        nilaiMhs.setText(String.valueOf(mhs.getNilai()));

        status_mhs = mhs.getStatus();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChangeStatusActivity.this, "Data diubah!", Toast.LENGTH_SHORT).show();
                simpanData(status_mhs, mhs.getId(), ujian);
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

    private void simpanData(int status, int ujian_mahasiswa_id, final UjianModel ujian){

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);

        Call<ResponseBody> call = client.updateStatusMHS(session.getToken(),status,ujian_mahasiswa_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jObjError = null;
                try {
                    jObjError = new JSONObject(response.body().string());
                    Toast.makeText(ChangeStatusActivity.this, "" + jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ChangeStatusActivity.this, DaftarMahasiswaActivity.class);
                    i.putExtra(DaftarMahasiswaActivity.EXTRA_UJIAN, ujian);
                    startActivity(i);
                    finish();
                } catch (JSONException e) {
                    Toast.makeText(ChangeStatusActivity.this, "Error response JSON", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(ChangeStatusActivity.this, "Error I/O", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChangeStatusActivity.this, "Gagal terhubung ke Server!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
