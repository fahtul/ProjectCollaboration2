package id.dapurku.dapurku.Model;

public class Kategori {
    private String kategori_id;
    private String kategori;
    private String gambar_kategori;
    private String status;


    public Kategori() {
    }

    public Kategori(String kategoti_id, String kategori, String gambar_kategori, String status) {
        this.kategori_id = kategoti_id;
        this.kategori = kategori;
        this.gambar_kategori = gambar_kategori;
        this.status = status;
    }


    public String getKategoti_id() {
        return kategori_id;
    }

    public void setKategoti_id(String kategoti_id) {
        this.kategori_id = kategoti_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
