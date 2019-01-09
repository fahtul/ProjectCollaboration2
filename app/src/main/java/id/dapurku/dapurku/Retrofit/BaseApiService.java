package id.dapurku.dapurku.Retrofit;

import android.renderscript.Sampler;

import id.dapurku.dapurku.Model.CheckUserResponse;
import id.dapurku.dapurku.Model.User;
import id.dapurku.dapurku.Model.ValueUser;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("nomor_telpon")String nomor_telpon,@Field("email")String email);

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                            @Field("nomor_telpon") String nomor_telpon,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerRequest(@Field("unique_id") String unique_id,
                               @Field("nama") String nama,
                               @Field("jk") String jk,
                               @Field("alamat") String alamat,
                               @Field("email") String email,
                               @Field("nomor_telpon") String nomor_telpon,
                               @Field("password") String password);
    @FormUrlEncoded
    @POST("insertToko.php")
    Call<ValueUser> inserttoko(@Field("user_id") String user_id,
                               @Field("kurir_id") String kurir_id,
                               @Field("nama_toko")  String nama_toko,
                               @Field("alamat") String alamat,
                               @Field("kecamatan") String kecamatan,
                               @Field("longitude_toko") String longitude_toko,
                               @Field("lattitude_toko") String lattitude_toko,
                               @Field("icon_toko") String icon_toko,
                               @Field("status") String status);

    @FormUrlEncoded
    @POST("insertMakanan.php")
    Call<ValueUser> insertmakanan(@Field("toko_id") String toko_id,
                               @Field("kategori_id") String kategori_id,
                               @Field("nama_makanan")  String nama_makanan,
                               @Field("harga") String harga,
                               @Field("berat") String berat,
                               @Field("deskripsi") String deskripsi,
                               @Field("stok") String stok,
                               @Field("gambarmakanan") String gambarmakanan,
                               @Field("status") String status);

    @FormUrlEncoded
    @POST("selectUserWhere.php")
    Call<ValueUser> searchuserwhere(@Field("search") String search);

    @FormUrlEncoded
    @POST("selectTokoWhere.php")
    Call<ValueUser> selecttokowhere(@Field("search") String search);

    @FormUrlEncoded
    @POST("selectMyMakananWhere.php")
    Call<ValueUser> selectmymakananwhere(@Field("search") String search);

    @FormUrlEncoded
    @POST("selectNamaTokoWhere.php")
    Call<ValueUser> selectnamatokowhere(@Field("search") String search);

    @FormUrlEncoded
    @POST("selectKabupaten.php")
    Call<ValueUser> selectkabupaten(@Field("search") String search);

    @FormUrlEncoded
    @POST("selectKecamatan.php")
    Call<ValueUser> selectkecamatan(@Field("search") String search);


    @FormUrlEncoded
    @POST("updateuserstatus.php")
    Call<ValueUser> upadteuserstatus (@Field("status") String status,
                                        @Field("email") String email);


    @Multipart
    @POST("uploadImageToko.php")
    Call<ValueUser> uploadImageToko (@Part MultipartBody.Part image);

    @Multipart
    @POST("uploadImageMakanan.php")
    Call<ValueUser> uploadImageMakanan (@Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("updateToko.php")
    Call<ValueUser> upadteToko (@Field("user_id") String user_id,
                                @Field("kurir_id") String kurir_id,
                                @Field("nama_toko") String nama_toko,
                                @Field("alamat") String alamat,
                                @Field("kecamatan") String kecamatan,
                                @Field("icon_toko") String icon_toko
                                );

    @FormUrlEncoded
    @POST("updateTokoTanpaFoto.php")
    Call<ValueUser> upadteTokoTanpaFoto (@Field("user_id") String user_id,
                                @Field("kurir_id") String kurir_id,
                                @Field("nama_toko") String nama_toko,
                                @Field("alamat") String alamat,
                                @Field("kecamatan") String kecamatan
    );

    @GET("selectAllKategori.php")
    Call<ValueUser> selectAllKategori();

    @GET("selectAllKecamatan.php")
    Call<ValueUser> selectAllKecamatan();
}
