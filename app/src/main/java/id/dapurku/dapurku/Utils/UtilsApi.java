package id.dapurku.dapurku.Utils;

import id.dapurku.dapurku.Model.Toko;
import id.dapurku.dapurku.Model.User;
import id.dapurku.dapurku.Retrofit.BaseApiService;
import id.dapurku.dapurku.Retrofit.RetrofitClient;

public class UtilsApi {

    private static final String BASE_URL = "http://dapurku.id/function/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL).create(BaseApiService.class);
    }

    public static User currentUser ;
    public static Toko currentToko;

}
