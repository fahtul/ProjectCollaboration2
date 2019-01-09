package id.dapurku.dapurku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.tv.TvInputService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.service.textservice.SpellCheckerService;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.Properties;
import java.util.Random;

import javax.sql.DataSource;

import id.dapurku.dapurku.Model.CheckUserResponse;
import id.dapurku.dapurku.Model.User;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.SentMail.SentMail;
import id.dapurku.dapurku.Utils.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class RegisterActivity extends AppCompatActivity {
    EditText etNama;
    EditText etEmail;
    EditText etPassword;
    Spinner jk;
    EditText etAlamat;
    EditText etNomoRtelpon;
    EditText konfirmasiPass;
    Button btnRegister;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;


    Random r = new Random();
    String randomNumber = String.format("%04d", r.nextInt(1001));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        initComponents();
    }



    private void initComponents() {
        etNama =  findViewById(R.id.etNama);
        etEmail =  findViewById(R.id.etEmail);
        jk  = findViewById(R.id.spnJK);
        etNomoRtelpon = findViewById(R.id.etphone);
        etAlamat= findViewById(R.id.etAlamat);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        konfirmasiPass = findViewById(R.id.etConfPassword);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNama.getText().toString())){
                    etNama.setError("Nama Wajib di isi");
                    etNama.requestFocus();
                }

              else  if (TextUtils.isEmpty(etAlamat.getText().toString())){
                    etAlamat.setError("Alamat Wajib di isi");
                    etAlamat.requestFocus();
                }
              else  if (TextUtils.isEmpty(etEmail.getText().toString())){
                    etEmail.setError("Nama Wajib di isi");
                    etEmail.requestFocus();
                }
               else if (TextUtils.isEmpty(etNomoRtelpon.getText().toString())){
                    etNomoRtelpon.setError("Nomor Telpon Wajib di isi");
                    etNomoRtelpon.requestFocus();
                }
               else if (TextUtils.isEmpty(etPassword.getText().toString())){
                    etPassword.setError("Password Wajib di isi");
                    etPassword.requestFocus();
                }
               else if (TextUtils.isEmpty(konfirmasiPass.getText().toString())){
                    konfirmasiPass.setError("Konfirmasi Password Wajib di isi");
                    konfirmasiPass.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
                    etEmail.setError("Harap Isi Email Sesuai Format");
                    etEmail.requestFocus();
                } else if (!etPassword.getText().toString().equals(konfirmasiPass.getText().toString())){
                    konfirmasiPass.setError("Konfirmasi Password tidak sama");
                    konfirmasiPass.requestFocus();
                }

                else{
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

                    requestRegister();
                }
            }
        });
    }


    private void requestRegister(){
        mApiService.registerRequest(randomNumber,etNama.getText().toString(),
                jk.getSelectedItem().toString(),
                etAlamat.getText().toString(),
                etEmail.getText().toString(),
                etNomoRtelpon.getText().toString(),
                etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    SentMail sentMail = new SentMail(RegisterActivity.this,etEmail.getText().toString(),"Dapurku ","Konfirmasi Kode " + randomNumber);
                                    sentMail.execute();
                                    Toast.makeText(mContext, "BERHASIL REGISTRASI VERIFIKASI AKUN ANDA TERLEBIH DAHULU", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this,VerifikasiActivity.class);
                                    intent.putExtra("kodeverifikasi",randomNumber);
                                    intent.putExtra("emailuser",etEmail.getText().toString());
                                    startActivity(intent);
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    }

