package id.dapurku.dapurku;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import id.dapurku.dapurku.Model.Kabupaten;
import id.dapurku.dapurku.Model.Kecamatan;
import id.dapurku.dapurku.Model.NamaToko;
import id.dapurku.dapurku.Model.Toko;
import id.dapurku.dapurku.Model.User;
import id.dapurku.dapurku.Model.ValueUser;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.Utils.UtilsApi;
import io.paperdb.Paper;
import meridianid.farizdotid.actdaerahindonesia.util.JsonParse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements LocationListener ,NavigationView.OnNavigationItemSelectedListener {
    private TextView txtFullName;
    private TextView txtEmail;
    private TextView txtKota;
    private Spinner spnKecamatan,spnNamaToko;
    public static String Longitude,Latitude;
    LocationManager locationManager;
    Context mContext;
    String kodeKota;
    BaseApiService mApiService;
    AutoCompleteTextView acttext_prov,acttext_kab,acttext_kec,acttext_desa;

    private JsonParse jsonParse;
    ProgressDialog pd;
    String result = null;
    List<Toko> tokoList = new ArrayList<>();
    List<Kabupaten> kabupatenList = new ArrayList<>();
    List<Kecamatan> kecamatanList = new ArrayList<>();
    List<String> valueKecamatan= new ArrayList<String>();
    List<NamaToko> namaTokoList = new ArrayList<>();
    List<String> valueNamaToko = new ArrayList<>();
    boolean isSelected ;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtKota = findViewById(R.id.txtKota);
        jsonParse = new JsonParse(this);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(this);
        mContext = this;
        mApiService = UtilsApi.getApiService();
        spnKecamatan = findViewById(R.id.spnKecamatan);
        spnNamaToko = findViewById(R.id.spnResto);
        isSelected = false;
        CheckPermission();
        pd.show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText("Hi, " + UtilsApi.currentUser.getNama());


    }

    @Override
    protected void onStart() {
        super.onStart();
        mApiService.selecttokowhere(UtilsApi.currentUser.getId()).enqueue(new Callback<ValueUser>() {
            @Override
            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                tokoList = response.body().getResultToko();
                if (response.body().getValue().equals("1") && tokoList.size() != 0) {
                    Toast.makeText(HomeActivity.this, "Berhasil get toko", Toast.LENGTH_SHORT).show();
                    Toko toko = new Toko();
                    toko.setToko_id(tokoList.get(0).getToko_id());
                    toko.setAlamat(tokoList.get(0).getAlamat());
                    toko.setEmail(tokoList.get(0).getAlamat());
                    toko.setIcon_toko(tokoList.get(0).getIcon_toko());
                    toko.setKurir_id(tokoList.get(0).getKurir_id());
                    toko.setLattitude(tokoList.get(0).getLattitude());
                    toko.setLongitude(tokoList.get(0).getLongitude());
                    toko.setNama_kurir(tokoList.get(0).getNama_kurir());
                    toko.setNama_toko(tokoList.get(0).getNama_toko());
                    toko.setNomor_telpon(tokoList.get(0).getNomor_telpon());
                    toko.setStatus(tokoList.get(0).getStatus());
                    toko.setToken(tokoList.get(0).getToken());
                    toko.setUser_id(tokoList.get(0).getUser_id());
                    UtilsApi.currentToko = toko;
                }

                if (!spnKecamatan.isSelected()){
                mApiService.selectkabupaten(txtKota.getText().toString()).enqueue(new Callback<ValueUser>() {
                    @Override
                    public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                        kabupatenList = response.body().getResultKabupaten();
                        if (response.body().getValue().equals("1")) {
                            pd.dismiss();
                            if (kabupatenList.size() > 0) {
                                kodeKota = kabupatenList.get(0).getId_kab();
                                Toast.makeText(HomeActivity.this, kodeKota, Toast.LENGTH_SHORT).show();
                                mApiService.selectkecamatan(kodeKota).enqueue(new Callback<ValueUser>() {
                                    @Override
                                    public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                        pd.show();
                                        kecamatanList = response.body().getResultKecamatan();
                                        if (response.body().getValue().equals("1")) {
                                            valueKecamatan = new ArrayList<>();
                                            for (int i = 0; i < kecamatanList.size(); i++) {
                                                valueKecamatan.add(kecamatanList.get(i).getNama());
                                            }

                                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(HomeActivity.this,
                                                    android.R.layout.simple_spinner_item, valueKecamatan);
                                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spnKecamatan.setAdapter(spinnerAdapter);
                                            pd.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ValueUser> call, Throwable t) {

                                    }
                                });
                            } else {
                                Toast.makeText(mContext, "Mohon Periksa Koneksi Anda Atau Refresh", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ValueUser> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


            }

            @Override
            public void onFailure(Call<ValueUser> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


       selectedKecamatan();

    }

    private void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},101);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (txtKota.getText().equals("")){
            getLocation();
        }

        if (isSelected){
            Toast.makeText(mContext, "Sudah Terpilih", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "Belum Terpilih", Toast.LENGTH_SHORT).show();
           // selectedKecamatan();
        }


    }


    private void getLocation() {
        try{
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,5,this);

        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        if (spnKecamatan.isSelected()){
            isSelected = true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
          startActivity(new Intent(getBaseContext(),HomeActivity.class));
        } else if (id == R.id.nav_transaksi) {
            startActivity(new Intent(getBaseContext(),HomeActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getBaseContext(),ProfileTokoActivity.class));
        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent signIn  = new Intent(getApplicationContext(),LoginActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }else if (id==R.id.nav_daftarMakanan){
            startActivity(new Intent(getBaseContext(),DaftarMakananSaya.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        Longitude = String.valueOf(location.getLongitude());
        Latitude = String.valueOf(location.getLatitude());
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            List<Address> addressList = geocoder.getFromLocation(
                    latitude,longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");//adress
                }
                sb.append(address.getLocality()).append("\n");//village

                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                sb.append(address.getAdminArea()).append("\n"); //state

                sb.append(address.getSubAdminArea()).append("\n");//district

                sb.append(address.getSubLocality()).append("\n");
                txtKota.setText(address.getSubAdminArea());
                Toast.makeText(mContext, "Set Kota", Toast.LENGTH_SHORT).show();
                result = sb.toString();
            }

        } catch (IOException e) {
            // Log.e(TAG, "Unable connect to Geocoder", e);
        }
        Log.d("LONGLAT", Longitude + Latitude);

//        new AlertDialog.Builder(this).setMessage(result).create().show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enable new Provider", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Please Enable GPS and internet", Toast.LENGTH_SHORT).show();
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Kota" ,  txtKota.getText().toString());
        outState.putInt("Kecamatan"  , spnKecamatan.getSelectedItemPosition() );
        outState.putInt("Toko"  , spnNamaToko.getSelectedItemPosition());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String kota  = savedInstanceState.getString("Kota");
        txtKota.setText(kota);
        int kecamatan = savedInstanceState.getInt("Kecamatan");
        spnKecamatan.setSelection(kecamatan);
        int toko  = savedInstanceState.getInt("Toko");
        spnNamaToko.setSelection(toko);
    }


    public void selectedKecamatan(){
        spnKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "Lagi Terpilih", Toast.LENGTH_SHORT).show();
                mApiService.selectnamatokowhere(spnKecamatan.getSelectedItem().toString()).enqueue(new Callback<ValueUser>() {
                    @Override
                    public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                        namaTokoList = response.body().getResultNamaToko();
                        if (response.body().getValue().equals("1")){
                            valueNamaToko = new ArrayList<>();
                            for (int i=0; i<namaTokoList.size();i++){
                                valueNamaToko.add(namaTokoList.get(i).getNama_toko());
                            }

                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(HomeActivity.this,
                                    android.R.layout.simple_spinner_item, valueNamaToko);
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnNamaToko.setAdapter(spinnerAdapter);
                            pd.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ValueUser> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
