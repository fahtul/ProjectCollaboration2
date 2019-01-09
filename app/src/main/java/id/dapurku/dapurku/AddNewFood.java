package id.dapurku.dapurku;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.dapurku.dapurku.Model.Kategori;
import id.dapurku.dapurku.Model.Toko;
import id.dapurku.dapurku.Model.ValueUser;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.Utils.UtilsApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewFood extends AppCompatActivity {

    private Spinner spnKategori;
    BaseApiService mApiService;
    List<Kategori> kategoriList ;
    List<String> valueKategori = new ArrayList<String>();
    private FloatingActionButton fabAddImage;
    private MaterialEditText txtNamaMakanan;
    private MaterialEditText txtHarga;
    private MaterialEditText txtDeskripsi;
    private MaterialEditText txtStok;
    private MaterialEditText txtBerat;
    private Button btnAddMakanan;
    private ImageView imgMakanan;
    String path;
    String part_image;
    String toko_id;
    ProgressDialog pd ;
    List<Toko> tokoList = new ArrayList<>();
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);

        spnKategori = findViewById(R.id.spinnerKategori);
        mApiService = UtilsApi.getApiService();
        kategoriList = new ArrayList<Kategori>();
        fabAddImage = findViewById(R.id.fabImage);
        txtNamaMakanan = findViewById(R.id.edtNamaMakanan);
        txtHarga = findViewById(R.id.edtHarga);
        txtDeskripsi = findViewById(R.id.edtDeskripsi);
        txtStok  = findViewById(R.id.edtStok);
        imgMakanan  = findViewById(R.id.fotoMakanan);
        txtBerat = findViewById(R.id.edtBerat);
        btnAddMakanan = findViewById(R.id.btnAddMakanan);
        pd = new ProgressDialog(this);
        requestStoragePermission();
        part_image="";
        toko_id="";
    }

    @Override
    protected void onStart() {
        super.onStart();

        mApiService.selectAllKategori().enqueue(new Callback<ValueUser>() {
            @Override
            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                kategoriList = response.body().getResultKategori();
                Log.d("TAGNYA" , " size " + kategoriList.isEmpty());
                if (response.body().getValue().equals("1")){
                    Log.d("TAGNYA" , " size " + kategoriList.size());
                    valueKategori = new ArrayList<String>();

                    for (int i = 0; i < kategoriList.size(); i++) {
                        valueKategori.add(kategoriList.get(i).getKategori());
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(AddNewFood.this,
                            android.R.layout.simple_spinner_item, valueKategori);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnKategori.setAdapter(spinnerAdapter);
                }



            }



            @Override
            public void onFailure(Call<ValueUser> call, Throwable t) {

            }
        });

        fabAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        btnAddMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (part_image.equals("")){
                    Toast.makeText(AddNewFood.this, "Mohon Pilih Gambar", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(txtNamaMakanan.getText().toString())){
                    Toast.makeText(AddNewFood.this, "Isi Nama Makanan", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(txtDeskripsi.getText().toString())){
                    Toast.makeText(AddNewFood.this, "Isi Deskripsi Makanan", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(txtHarga.getText().toString())){
                    Toast.makeText(AddNewFood.this, "Isi Harga Makanan", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(txtStok.getText().toString())){
                    Toast.makeText(AddNewFood.this, "Isi Stok Makanan", Toast.LENGTH_SHORT).show();
                }else{
                    pd.show();
                File imagefile = new File(part_image);
                final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                final MultipartBody.Part partImage = MultipartBody.Part.createFormData("imageupload", imagefile.getName(), reqBody);
                mApiService.uploadImageMakanan(partImage).enqueue(new Callback<ValueUser>() {
                    @Override
                    public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                        if (response.body().getValue().equals("1")){
                            Toast.makeText(AddNewFood.this, "Berhasil Upload Foto Makanan", Toast.LENGTH_SHORT).show();
                            path = "http://dapurku.id/function"+response.body().getMessage();
                            mApiService.insertmakanan(UtilsApi.currentToko.getToko_id(),String.valueOf(spnKategori.getSelectedItemId()+1),txtNamaMakanan.getText().toString(),
                                    txtHarga.getText().toString(),txtBerat.getText().toString(),txtDeskripsi.getText().toString(),
                                    txtStok.getText().toString(),path,"on").enqueue(new Callback<ValueUser>() {
                                @Override
                                public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {
                                    pd.dismiss();
                                    if (response.body().getValue().equals("1")){
                                        Toast.makeText(AddNewFood.this, "Berhasil Mendaftarkan Toko", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ValueUser> call, Throwable t) {
                                    pd.dismiss();
                                    Toast.makeText(AddNewFood.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<ValueUser> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(AddNewFood.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }
        });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                part_image = cursor.getString(columnIndex);
                cursor.close();
                if (part_image != null) {
                    File image = new File(part_image);
                    imgMakanan.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
//                    imgToko.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
            }
        }
    }

}
