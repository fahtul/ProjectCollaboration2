package id.dapurku.dapurku.Model;

public class Toko {
    private String toko_id;
    private String user_id;
    private String kurir_id;
    private String nama_toko;
    private String alamat;
    private String longitude;
    private String lattitude;
    private String kecamatan;
    private String icon_toko;
    private String status;
    private String email;
    private String nomor_telpon;
    private String token;
    private String nama_kurir;

    public Toko() {
    }


    public Toko(String toko_id, String user_id, String kurir_id, String nama_toko, String alamat, String longitude, String lattitude, String kecamatan, String icon_toko, String status, String email, String nomor_telpon, String token, String nama_kurir) {
        this.toko_id = toko_id;
        this.user_id = user_id;
        this.kurir_id = kurir_id;
        this.nama_toko = nama_toko;
        this.alamat = alamat;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.kecamatan = kecamatan;
        this.icon_toko = icon_toko;
        this.status = status;
        this.email = email;
        this.nomor_telpon = nomor_telpon;
        this.token = token;
        this.nama_kurir = nama_kurir;
        }

    public String getToko_id() {
        return toko_id;
    }

    public void setToko_id(String toko_id) {
        this.toko_id = toko_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getIcon_toko() {
        return icon_toko;
    }

    public void setIcon_toko(String icon_toko) {
        this.icon_toko = icon_toko;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomor_telpon() {
        return nomor_telpon;
    }

    public void setNomor_telpon(String nomor_telpon) {
        this.nomor_telpon = nomor_telpon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNama_kurir() {
        return nama_kurir;
    }

    public void setNama_kurir(String nama_kurir) {
        this.nama_kurir = nama_kurir;
    }
}