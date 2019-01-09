package id.dapurku.dapurku.Model;

public class Kecamatan {

    private String id_kec;
    private String id_kab;
    private String nama;

    public Kecamatan() {
    }

    public Kecamatan(String id_kec, String id_kab, String nama) {
        this.id_kec = id_kec;
        this.id_kab = id_kab;
        this.nama = nama;
    }

    public String getId_kec() {
        return id_kec;
    }

    public void setId_kec(String id_kec) {
        this.id_kec = id_kec;
    }

    public String getId_kab() {
        return id_kab;
    }

    public void setId_kab(String id_kab) {
        this.id_kab = id_kab;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
