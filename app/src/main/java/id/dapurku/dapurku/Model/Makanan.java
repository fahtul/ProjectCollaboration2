package id.dapurku.dapurku.Model;

public class Makanan {

    private String makanan_id;
    private String toko_id;
    private String kategori_id;
    private String nama_makanan;
    private String harga;
    private String berat;
    private String deskripsi;
    private String stok;
    private String gambarmakanan;
    private String status;
    private String kurir_id;
    private String nama_toko;
    private String alamat;
    private String longitude_toko;
    private String lattitude_toko;
    private String icon_toko;
    private String kategori;
    private String gambar_kategori;
    private String nama_kurir;


    public Makanan() {
    }

    public Makanan(String makanan_id, String toko_id, String kategori_id, String nama_makanan, String harga, String berat, String deskripsi, String stok, String gambarmakanan, String status, String kurir_id, String nama_toko, String alamat, String longitude_toko, String lattitude_toko, String icon_toko, String kategori, String gambar_kategori, String nama_kurir) {
        this.makanan_id = makanan_id;
        this.toko_id = toko_id;
        this.kategori_id = kategori_id;
        this.nama_makanan = nama_makanan;
        this.harga = harga;
        this.berat = berat;
        this.deskripsi = deskripsi;
        this.stok = stok;
        this.gambarmakanan = gambarmakanan;
        this.status = status;
        this.kurir_id = kurir_id;
        this.nama_toko = nama_toko;
        this.alamat = alamat;
        this.longitude_toko = longitude_toko;
        this.lattitude_toko = lattitude_toko;
        this.icon_toko = icon_toko;
        this.kategori = kategori;
        this.gambar_kategori = gambar_kategori;
        this.nama_kurir = nama_kurir;
    }

    public String getMakanan_id() {
        return makanan_id;
    }

    public void setMakanan_id(String makanan_id) {
        this.makanan_id = makanan_id;
    }

    public String getToko_id() {
        return toko_id;
    }

    public void setToko_id(String toko_id) {
        this.toko_id = toko_id;
    }

    public String getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(String kategori_id) {
        this.kategori_id = kategori_id;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getGambarmakanan() {
        return gambarmakanan;
    }

    public void setGambarmakanan(String gambarmakanan) {
        this.gambarmakanan = gambarmakanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKurir_id() {
        return kurir_id;
    }

    public void setKurir_id(String kurir_id) {
        this.kurir_id = kurir_id;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLongitude_toko() {
        return longitude_toko;
    }

    public void setLongitude_toko(String longitude_toko) {
        this.longitude_toko = longitude_toko;
    }

    public String getLattitude_toko() {
        return lattitude_toko;
    }

    public void setLattitude_toko(String lattitude_toko) {
        this.lattitude_toko = lattitude_toko;
    }

    public String getIcon_toko() {
        return icon_toko;
    }

    public void setIcon_toko(String icon_toko) {
        this.icon_toko = icon_toko;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getGambar_kategori() {
        return gambar_kategori;
    }

    public void setGambar_kategori(String gambar_kategori) {
        this.gambar_kategori = gambar_kategori;
    }

    public String getNama_kurir() {
        return nama_kurir;
    }

    public void setNama_kurir(String nama_kurir) {
        this.nama_kurir = nama_kurir;
    }
}
