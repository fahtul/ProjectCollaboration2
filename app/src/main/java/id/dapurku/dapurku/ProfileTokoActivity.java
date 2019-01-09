package id.dapurku.dapurku;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.dapurku.dapurku.Model.Kecamatan;
import id.dapurku.dapurku.Model.Toko;
import id.dapurku.dapurku.Model.User;
import id.dapurku.dapurku.Model.ValueUser;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.Utils.UtilsApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.dapurku.dapurku.Utils.Constant.DEFAULT_STRING;
import static id.dapurku.dapurku.Utils.Constant.PREF_FILE;

public class ProfileTokoActivity extends AppCompatActivity implements LocationListener {
    private SharedPreferences mSharedPreferences;
    private EditText edtNama;
    private EditText edtAlamat;
    private EditText edtNomorTelpon;
    private EditText edtEmail;
    private Spinner spinner;
    private ImageView imgToko;
    private AutoCompleteTextView edtKecamatan;
    private Button btnSaveUpdate;
    BaseApiService mApiService;
    String part_image;
    private static int RESULT_LOAD_IMAGE = 1;
    public static String Longitude, Latitude;
    LocationManager locationManager;
    private static final String URL = "http://dapurku.id/function/";
    List<Toko> tokoList = new ArrayList<>();
    private List<Kecamatan> kecamatanList = new ArrayList<>();
    User user;
    List<String> valueKecamatan = new ArrayList<String>();
    final int REQUEST_GALLERY = 9544;
    ProgressDialog pd;
    String path="";
    String kurir_id="";
    private String user_id;
    private static final int STORAGE_PERMISSION_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_toko);
        requestStoragePermission();

        initComponent();
//        loadString();
        kurir_id="";
        if (spinner.getSelectedItemId() == 0) {
            kurir_id = "Kurir Dapurku";
        } else if (spinner.getSelectedItemId() == 1) {
            kurir_id = "COD";
        } else {
            kurir_id = "Ambil Sendiri";
        }

        part_image = "";


    }



    public void saveString(String valueNama,String valueAlamat,String valueNomor,String valueEmail) {
        mSharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("Nama", valueNama);
        editor.putString("Alamat", valueAlamat);
        editor.putString("NomorTelpon", valueNomor);
        editor.putString("Email", valueEmail);
        editor.apply();
    }

    private void loadString() {
        mSharedPreferences = getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String savedValueNama = mSharedPreferences.getString("Nama", DEFAULT_STRING);
        String savedValueAlamat = mSharedPreferences.getString("Alamat", DEFAULT_STRING);
        String savedValueNomor = mSharedPreferences.getString("NomorTelpon", DEFAULT_STRING);
        String savedValueEmail = mSharedPreferences.getString("Email", DEFAULT_STRING);
        //String savedValueAlamat = mSharedPreferences.getString("Alamat", DEFAULT_STRING);
        if (!savedValueNama.equals(DEFAULT_STRING) && !savedValueAlamat.equals(DEFAULT_STRING) &&
        !savedValueNomor.equals(DEFAULT_STRING) && !savedValueEmail.equals(DEFAULT_STRING)  ){
            edtNama.setText(savedValueNama);
            edtAlamat.setText(savedValueAlamat);
            edtNomorTelpon.setText(savedValueNomor);
            edtEmail.setText(savedValueEmail);
            edtNama.setSelection(edtNama.getText().length());
            edtAlamat.setSelection(edtAlamat.getText().length());
            edtNomorTelpon.setSelection(edtNomorTelpon.getText().length());
            edtEmail.setSelection(edtEmail.getText().length());
    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

        String nama,alamat,email,nomortelpon;
        nama = edtNama.getText().toString();
        alamat = edtAlamat.getText().toString();
        email  = edtEmail.getText().toString();
        nomortelpon = edtNomorTelpon.getText().toString();
        saveString(nama,alamat,nomortelpon,email);
    }

    private void getLocation() {

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void initComponent() {
        edtNama = findViewById(R.id.edtNama);
        edtAlamat = findViewById(R.id.edtAlamat);
        edtNomorTelpon = findViewById(R.id.edtNomorTelpon);
        edtEmail = findViewById(R.id.edtEmail);
        edtKecamatan = findViewById(R.id.edtKecamatan);
        spinner = findViewById(R.id.spnKurir);
        imgToko = findViewById(R.id.fotoToko);
        btnSaveUpdate = findViewById(R.id.btnsaveupdate);
        mApiService = UtilsApi.getApiService();
        user = new User();
        pd = new ProgressDialog(this);
        pd.setMessage("loading ... ");

        pd.show();


    }

    @Override
    public void onLocationChanged(Location location) {
        Longitude = String.valueOf(location.getLongitude());
        Latitude = String.valueOf(location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            if (cursor !=null){
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                part_image = cursor.getString(columnIndex);
                cursor.close();
                if (part_image!=null){
                    File image = new File(part_image);
                    imgToko.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
//                    imgToko.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
            }






        }
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        user_id = UtilsApi.currentUser.getId();



        mApiService.selecttokowhere(UtilsApi.currentUser.getId()).enqueue(new Callback<ValueUser>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {

                tokoList = response.body().getResultToko();
                Log.d("TAGNYA" ,"" +  tokoList.size());
                if (response.body().getValue().equals("1") && tokoList.size() == 1) {
                    edtNama.setText(tokoList.get(0).getNama_toko());
                    edtAlamat.setText(tokoList.get(0).getAlamat());
                    edtEmail.setText(tokoList.get(0).getEmail());
                    edtKecamatan.setText(tokoList.get(0).getKecamatan());
                    edtNomorTelpon.setText(tokoList.get(0).getNomor_telpon());
                    String pathImagePicasso = tokoList.get(0).getIcon_toko();
                    if (pathImagePicasso.equals("")){
                        imgToko.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
                    }else{
                        Picasso.get().load(pathImagePicasso).into(imgToko);
                    }

                    pd.dismiss();
                    Log.d("TAGNYA", " " + tokoList.size());
                    Log.d("TAGNYA","User id " + user_id);
                    Log.d("TAGNYA", tokoList.get(0).getNama_kurir());
                    Toast.makeText(ProfileTokoActivity.this, tokoList.get(0).getNama_kurir(), Toast.LENGTH_SHORT).show();
                    switch (tokoList.get(0).getNama_kurir()) {
                        case "COD":
                            spinner.setSelection(1);
                            break;
                        case "Kurir Dapurku":
                            spinner.setSelection(0);
                            break;
                        default:
                            spinner.setSelection(2);
                            break;
                    }



                } else {
                    Log.d("TAGNYA", " " + tokoList.size());
                    Log.d("TAGNYA","User id " + user_id);
                    pd.dismiss();
                    Toast.makeText(ProfileTokoActivity.this, "Mohon Lengkapi Profile Toko Anda", Toast.LENGTH_SHORT).show();

                }

                imgToko.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                });

                btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pd.show();
                        if (part_image.equals("")){
                            mApiService.upadteTokoTanpaFoto(user_id,String.valueOf(spinner.getSelectedItemId()),edtNama.getText().toString(),edtAlamat.getText().toString(),edtKecamatan.getText().toString())
                                    .enqueue(new Callback<ValueUser>() {
                                @Override
                                public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                    if (response.body().getValue().equals("1")) {
                                        pd.dismiss();
                                        Toast.makeText(ProfileTokoActivity.this, "Berhasil Memperbarui Profile Toko", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ValueUser> call, Throwable t) {
                                    Toast.makeText(ProfileTokoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                        File imagefile = new File(part_image);
                        final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                        final MultipartBody.Part partImage = MultipartBody.Part.createFormData("imageupload", imagefile.getName(), reqBody);


                        mApiService.selecttokowhere(UtilsApi.currentUser.getId()).enqueue(new Callback<ValueUser>() {

                            @Override
                            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                tokoList = response.body().getResultToko();
                                if (tokoList.size() > 0 && response.body().getValue().equals("1")) {
                                    //update data
                                    mApiService.uploadImageToko(partImage).enqueue(new Callback<ValueUser>() {
                                        @Override
                                        public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                            Log.d("RETRO", "ON RESPONSE  : " + response.body().toString());
                                            if (response.body().getValue().equals("1")){
                                                path = "http://dapurku.id/function"+response.body().getMessage();
                                                Toast.makeText(ProfileTokoActivity.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();

                                                mApiService.upadteToko(user_id,String.valueOf(spinner.getSelectedItemId()),edtNama.getText().toString(),edtAlamat.getText().toString(),
                                                        edtKecamatan.getText().toString(),
                                                        path).enqueue(new Callback<ValueUser>() {
                                                    @Override
                                                    public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                                        if (response.body().getValue().equals("1")) {
                                                            pd.dismiss();
                                                            Toast.makeText(ProfileTokoActivity.this, "Berhasil Memperbarui Profile Toko", Toast.LENGTH_SHORT).show();
                                                        }
                                                        }

                                                    @Override
                                                    public void onFailure(Call<ValueUser> call, Throwable t) {
                                                        Toast.makeText(ProfileTokoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ValueUser> call, Throwable t) {

                                        }
                                    });

                                } else {
                                    mApiService.uploadImageToko(partImage).enqueue(new Callback<ValueUser>() {
                                        @Override
                                        public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                            Log.d("RETRO", "ON RESPONSE  : " + response.body().toString());

                                            if(response.body().getValue().equals("1"))
                                            {
                                                path = "http://dapurku.id/function"+response.body().getMessage();
                                                Toast.makeText(ProfileTokoActivity.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                                                mApiService.inserttoko(user_id, kurir_id, edtNama.getText().toString(),
                                                        edtAlamat.getText().toString(),edtKecamatan.getText().toString(), Longitude, Latitude, path, "on").enqueue(
                                                        new Callback<ValueUser>() {
                                                            @Override
                                                            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                                                if (response.body().getValue().equals("1")){
                                                                    Toast.makeText(ProfileTokoActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                    pd.dismiss();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ValueUser> call, Throwable t) {
                                                                Toast.makeText(ProfileTokoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                );
                                            }else
                                            {
                                                Toast.makeText(ProfileTokoActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ValueUser> call, Throwable t) {
                                            Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                                            pd.dismiss();
                                        }
                                    });





                                }
                            }

                            @Override
                            public void onFailure(Call<ValueUser> call, Throwable t) {

                            }
                        });





                    }
                    }
                });


            }

            @Override
            public void onFailure(Call<ValueUser> call, Throwable t) {
                Toast.makeText(ProfileTokoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.selectAllKecamatan().enqueue(new Callback<ValueUser>() {
            @Override
            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                kecamatanList = response.body().getResultKecamatan();
                if (response.body().getValue().equals("1")){
                    valueKecamatan = new ArrayList<String>();
                    for (int i = 0; i < kecamatanList.size(); i++) {
                        valueKecamatan.add(kecamatanList.get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileTokoActivity.this,
                            android.R.layout.simple_list_item_1, valueKecamatan);
                    edtKecamatan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ValueUser> call, Throwable t) {

            }
        });


    }
}