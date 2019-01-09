package id.dapurku.dapurku;

import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

public class DesMakanan extends AppCompatActivity {

    Toolbar toolbar;
    ImageView gambarMakanan;
    TextView namaMakanan;
    TextView hargaMakanan;
    TextView deskripsiMakanan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des_makanan);

        initComponents();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        loadMakanan();
    }

    private void initComponents() {
        toolbar = findViewById(R.id.toolbar);
        gambarMakanan = findViewById(R.id.img_food);
        namaMakanan = findViewById(R.id.food_name);
        hargaMakanan = findViewById(R.id.food_price);
        deskripsiMakanan = findViewById(R.id.food_description);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void  loadMakanan(){
        String nama = getIntent().getStringExtra("NamaMakanan");
        String uri = getIntent().getStringExtra("GambarMakanan");
        String harga = getIntent().getStringExtra("HargaMakanan");
        String deskripsi = getIntent().getStringExtra("DeskripsiMakanan");
        toolbar.setTitle(nama);
        namaMakanan.setText(nama);
        Picasso.get().load(uri).into(gambarMakanan);
        hargaMakanan.setText(harga);
        deskripsiMakanan.setText(deskripsi);
    }


}
