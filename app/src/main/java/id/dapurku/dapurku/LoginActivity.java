package id.dapurku.dapurku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.dapurku.dapurku.Model.User;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.Utils.UtilsApi;
import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailorPhone;
    private EditText etPassword;
    private Button btnLogin;
    ProgressDialog loading;
    private TextView txtRegist,txtLupapass;
    Context mContext;
    CheckBox ckbRemember;
    BaseApiService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        Paper.init(this);

        String user =  Paper.book().read("User");
        String password = Paper.book().read("Password");

        initComponents();
        if(user!=null && password!=null){
            if (!user.isEmpty() && !password.isEmpty())
                login(user,password);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckbRemember.isChecked()){
                    Paper.book().write("User",etEmailorPhone.getText().toString());
                    Paper.book().write("Password",etPassword.getText().toString());
                }
               login(etEmailorPhone.getText().toString(),etPassword.getText().toString());


            }
        });

        txtRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        txtLupapass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPassword.class));
            }
        });
    }

    private void login(String user, String password) {

            loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
            mApiService.loginRequest(user, user, password)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                    if (jsonRESULTS.getString("error").equals("false") && jsonRESULTS.getJSONObject("user").getString("status").equals("inactive")) {
                                        Toast.makeText(mContext, "Aktifkan Akun Anda Terlebih dahulu", Toast.LENGTH_SHORT).show();
                                    } else if (jsonRESULTS.getString("error").equals("false") && jsonRESULTS.getJSONObject("user").getString("status").equals("active")) {

                                        Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                        String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                                        Intent intent = new Intent(mContext, HomeActivity.class);
                                        intent.putExtra("result_nama", nama);
                                        User user = new User();
                                        user.setId(jsonRESULTS.getJSONObject("user").getString("id"));
                                        user.setNama(jsonRESULTS.getJSONObject("user").getString("nama"));
                                        user.setAlamat(jsonRESULTS.getJSONObject("user").getString("alamat"));
                                        user.setEmail(jsonRESULTS.getJSONObject("user").getString("email"));
                                        user.setJk(jsonRESULTS.getJSONObject("user").getString("jk"));
                                        user.setNomor_telpon(jsonRESULTS.getJSONObject("user").getString("nomor_telpon"));
                                        user.setStatus(jsonRESULTS.getJSONObject("user").getString("status"));
                                        user.setToken(jsonRESULTS.getJSONObject("user").getString("token"));
                                        UtilsApi.currentUser = user;
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Jika login gagal
                                        String error_message = jsonRESULTS.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                loading.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                            loading.dismiss();
                        }
                    });
        }



    private void requestLogin(){
        if (TextUtils.isEmpty(etEmailorPhone.getText().toString())){
            etEmailorPhone.requestFocus();
            etEmailorPhone.setError("Masukkan Email atau password");
        } else if (TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.requestFocus();
            etPassword.setError("Masukkan password");
        }else{
            loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);

            mApiService.loginRequest(etEmailorPhone.getText().toString(),etEmailorPhone.getText().toString(), etPassword.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                loading.dismiss();
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());

                                    if (jsonRESULTS.getString("error").equals("false") && jsonRESULTS.getJSONObject("user").getString("status").equals("inactive")){
                                        Toast.makeText(mContext, "Aktifkan Akun Anda Terlebih dahulu", Toast.LENGTH_SHORT).show();
                                    }

                                    else if(jsonRESULTS.getString("error").equals("false") && jsonRESULTS.getJSONObject("user").getString("status").equals("active")) {

                                        Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                        String nama = jsonRESULTS.getJSONObject("user").getString("nama");
                                        Intent intent = new Intent(mContext, HomeActivity.class);
                                        intent.putExtra("result_nama", nama);
                                        User user = new User();
                                        user.setNama(jsonRESULTS.getJSONObject("user").getString("nama"));
                                        user.setAlamat(jsonRESULTS.getJSONObject("user").getString("alamat"));
                                        user.setEmail(jsonRESULTS.getJSONObject("user").getString("email"));
                                        user.setJk(jsonRESULTS.getJSONObject("user").getString("jk"));
                                        user.setNomor_telpon(jsonRESULTS.getJSONObject("user").getString("nomor_telpon"));
                                        user.setStatus(jsonRESULTS.getJSONObject("user").getString("status"));
                                        user.setToken(jsonRESULTS.getJSONObject("user").getString("token"));
                                        UtilsApi.currentUser = user;
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        // Jika login gagal
                                        String error_message = jsonRESULTS.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                    }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                loading.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                            loading.dismiss();
                        }
                    });
        }
    }

    private void initComponents() {
        etEmailorPhone =  findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin =  findViewById(R.id.btnLogin);
        txtRegist = findViewById(R.id.buatakun);
        txtLupapass  = findViewById(R.id.lupapass);
        ckbRemember = findViewById(R.id.ckbRemember);
    }



}
