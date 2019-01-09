package id.dapurku.dapurku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.dapurku.dapurku.Adapter.RvAdapterMyMakanan;
import id.dapurku.dapurku.Model.Makanan;
import id.dapurku.dapurku.Model.ValueUser;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.Utils.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarMakananSaya extends AppCompatActivity {

    private Button btnTambahMakanan;
    private List<Makanan> makananList = new ArrayList<>();
    private RvAdapterMyMakanan rvAdapterMyMakanan;
    ProgressDialog pd;
    private RecyclerView recyclerView;
    BaseApiService mApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_makanan_saya);

        btnTambahMakanan = findViewById(R.id.btnTambahMakanan);
        pd = new ProgressDialog(this);
        mApiService = UtilsApi.getApiService();
        pd.show();
        recyclerView = findViewById(R.id.rvDaftarMakananSaya);

//        rvAdapterMyMakanan = new RvAdapterMyMakanan(this,makananList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        btnTambahMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaftarMakananSaya.this,AddNewFood.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadDataMakanan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataMakanan();
    }

    private void loadDataMakanan(){
        mApiService.selectmymakananwhere(UtilsApi.currentToko.getToko_id()).enqueue(new Callback<ValueUser>() {
            @Override
            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                pd.dismiss();
                if (response.body().getValue().equals("1") ){
                    makananList = response.body().getResultMakanan();
                    rvAdapterMyMakanan = new RvAdapterMyMakanan(DaftarMakananSaya.this,makananList);
                    recyclerView.setAdapter(rvAdapterMyMakanan);
                }
            }

            @Override
            public void onFailure(Call<ValueUser> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(DaftarMakananSaya.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
