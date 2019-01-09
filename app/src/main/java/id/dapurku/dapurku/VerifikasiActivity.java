package id.dapurku.dapurku;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.dapurku.dapurku.Model.User;
import id.dapurku.dapurku.Model.ValueUser;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.Utils.UtilsApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifikasiActivity extends AppCompatActivity {
    BaseApiService mApiService;

    private EditText txtKode;
    private Button btnVerify;
    private static final String URL = "http://dapurku.id/function/";
    String email,kode;

    List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);
        mApiService = UtilsApi.getApiService();
        txtKode = findViewById(R.id.edtVerification);
        btnVerify = findViewById(R.id.btnVerify);

        Intent intent= getIntent();
        kode = intent.getStringExtra("kodeverifikasi");
        email = intent.getStringExtra("emailuser");

//        mApiService.searchuserwhere(email).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()){
//                    Log.i("DEBUG","berhasil");
//
//                    try{
//                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                        if (jsonRESULTS.getString("value").equals("1")){
//                            if (txtKode.getText().toString().equals(kode)){
//
//                            }
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.i("debug", "onResponse: GA BERHASIL");
//
//                }
//                }
//
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final BaseApiService api = retrofit.create(BaseApiService.class);
                Call<ValueUser> call = api.searchuserwhere(email);

                call.enqueue(new Callback<ValueUser>() {
                    @Override
                    public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {

                        final String value = response.body().getValue();
                        if (value.equals("1")){
                            userList = response.body().getResult();
                            if (userList.get(0).getUnique_id().equals(txtKode.getText().toString())){
                                Log.d("COBA","BERHASIL NI");
                                BaseApiService api2 = retrofit.create(BaseApiService.class);
                                api2.upadteuserstatus("active",email).enqueue(new Callback<ValueUser>() {

                                    @Override
                                    public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                        if (response.body().getValue().equals("1")){
                                            Toast.makeText(VerifikasiActivity.this, "Akun Telah Berhasil di verifikasi", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(VerifikasiActivity.this,LoginActivity.class));
                                        }else if (response.body().getValue().equals("0")){
                                            Toast.makeText(VerifikasiActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ValueUser> call, Throwable t) {

                                    }
                                });
                                Log.d("COBA","BERHASIL" + userList.get(0).getUnique_id());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ValueUser> call, Throwable t) {

                    }
                });
                }

//                mApiService.searchuserwhere(email).enqueue(new Callback<Sampler.Value>() {
//                    @Override
//                    public void onResponse(Call<Sampler.Value> call, Response<Sampler.Value> response) {
//                        String value = response.body()
//                        if (response.isSuccessful()){
//                            try{
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                if (jsonRESULTS.getString("value").equals("1")){
//                                    if (jsonRESULTS.getJSONObject("result").getJSONArray("unique_id").equals(txtKode.getText().toString())){
////                                        mApiService.updateuserstatus(email,"active");
//                                        Log.d("COBA","BERHASIL" + jsonRESULTS.getJSONObject("result").getJSONArray("unique_id"));
//                                    }
//                                }
//                            }catch (JSONException e){
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Sampler.Value> call, Throwable t) {
//
//                    }
//
//                });

//                if (kode.equals(txtKode.getText().toString())){
//                    mApiService.updateuserstatus(email,"active")
//                            .enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    Toast.makeText(VerifikasiActivity.this, "Berhasil Registrasi, silahkan Login", Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                }
//                            });
//                }else{
//                    Toast.makeText(VerifikasiActivity.this, "Kode yang anda masukkan salah", Toast.LENGTH_SHORT).show();
//                }
            });
        }
    }

