package id.dapurku.dapurku.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.dapurku.dapurku.DesMakanan;
import id.dapurku.dapurku.Model.Makanan;
import id.dapurku.dapurku.R;

public class RvAdapterMyMakanan extends RecyclerView.Adapter<RvAdapterMyMakanan.ViewHolder>{

    private Context context;
    private List<Makanan> makananList;

    public RvAdapterMyMakanan(Context context, List<Makanan> makananList) {
        this.context = context;
        this.makananList = makananList;
    }


    @NonNull
    @Override
    public RvAdapterMyMakanan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_list_makanan,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapterMyMakanan.ViewHolder holder, final int position) {
        holder.nama.setText(makananList.get(position).getNama_makanan());
        holder.harga.setText("Rp. "  + makananList.get(position).getHarga());
        holder.stok.setText("Stok : " + makananList.get(position).getStok());
        holder.kategori.setText("Kategori : "  + makananList.get(position).getKategori());
        Picasso.get().load(makananList.get(position).getGambarmakanan()).into(holder.imgMakanan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DesMakanan.class);
                intent.putExtra("NamaMakanan", makananList.get(position).getNama_makanan());
                intent.putExtra("GambarMakanan", makananList.get(position).getGambarmakanan());
                intent.putExtra("HargaMakanan" , makananList.get(position).getHarga());
                intent.putExtra("DeskripsiMakanan" , makananList.get(position).getDeskripsi());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return makananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nama,kategori,harga,stok;
        private ImageView imgMakanan;


        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.NamaMakanan);
            kategori = itemView.findViewById(R.id.Kategori);
            harga = itemView.findViewById(R.id.HargaMakanan);
            stok = itemView.findViewById(R.id.Stok);
            imgMakanan =itemView.findViewById(R.id.FotoMakanan);
        }

    }
}
