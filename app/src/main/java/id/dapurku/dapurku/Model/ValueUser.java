package id.dapurku.dapurku.Model;

import java.util.List;

public class ValueUser {
    String value;
    String message;
    List<User> result;
    List<Toko> resultToko;
    List<Kategori> resultKategori;
    List<Makanan> resultMakanan;
    List<Kabupaten> resultKabupaten;
    List<Kecamatan> resultKecamatan;
    List<NamaToko> resultNamaToko;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getResult() {
        return result;
    }

    public List<Toko> getResultToko() {
        return resultToko;
    }

    public List<Kategori> getResultKategori() {
        return resultKategori;
    }

    public List<Makanan> getResultMakanan() {
        return resultMakanan;
    }

    public List<Kabupaten> getResultKabupaten() {
        return resultKabupaten;
    }

    public List<Kecamatan> getResultKecamatan() {
        return resultKecamatan;
    }

    public List<NamaToko> getResultNamaToko() {
        return resultNamaToko;
    }
}
