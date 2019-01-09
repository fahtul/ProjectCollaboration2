package id.dapurku.dapurku.Model;

import java.util.List;

public class User {

    private String id;
    private String unique_id;
    private String nama;
    private String jk;
    private String alamat;
    private String email;
    private String nomor_telpon;
    private String status;
    private String token;

    public User() {
    }

    public User(String id, String unique_id, String nama, String jk, String alamat, String email, String nomor_telpon, String status, String token) {
        this.id = id;
        this.unique_id = unique_id;
        this.nama = nama;
        this.jk = jk;
        this.alamat = alamat;
        this.email = email;
        this.nomor_telpon = nomor_telpon;
        this.status = status;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
