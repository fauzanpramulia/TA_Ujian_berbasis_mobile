package com.fauzanpramulia.dosen_ta_ujian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fauzanpramulia.dosen_ta_ujian.model.LoginModel;
import com.fauzanpramulia.dosen_ta_ujian.shared.RetrofitClientInstance;
import com.fauzanpramulia.dosen_ta_ujian.shared.Session;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.text_username)
    MaterialEditText editUserName;
    @BindView(R.id.text_pass) MaterialEditText editPass;
    @BindView(R.id.btn_signin)
    Button signIn;
    @BindView(R.id.signDosen)
    TextView titleSign;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);

        //cara menggunakan kelas tadi
        session = new Session(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(editUserName.getText().toString(), editPass.getText().toString());
            }
        });
        titleSign.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(LoginActivity.this, "Login Khusus Dosen", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void logIn(String username, String password) {
        progressBar.setVisibility(View.VISIBLE);

        DosenClient client = RetrofitClientInstance.getRetrofitInstance().create(DosenClient.class);
        Call<LoginModel> call = client.login(username, password);
        //dan mengambil callbak berupa model LoginModel
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null){
                    LoginModel login = response.body();
                    //ini memamnggil itemnya tadi
                    session.setToken(login.getAccess_token());
//                    Toast.makeText(LoginActivity.this, ""+session.getToken(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, ""+jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal terhubung ke server!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }

        });

    }

}
