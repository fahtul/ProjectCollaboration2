package id.dapurku.dapurku.Model;

public class Kabupaten {

    private String id_kab;
    private String id_prov;
    private String nama;
    private String id_jenis;


    public Kabupaten() {
    }


    public Kabupaten(String id_kab, String id_prov, String nama, String id_jenis) {
        this.id_kab = id_kab;
        this.id_prov = id_prov;
        this.nama = nama;
        this.id_jenis = id_jenis;
    }


    public String getId_kab() {
        return id_kab;
    }

    public void setId_kab(String id_kab) {
        this.id_kab = id_kab;
    }

    public String getId_prov() {
        return id_prov;
    }

    public void setId_prov(String id_prov) {
        this.id_prov = id_prov;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(String id_jenis) {
        this.id_jenis = id_jenis;
    }
}
