package vku.phungduc.myapplication.api;




import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.model.danhmuc.result_danhmuc;
import vku.phungduc.myapplication.model.tintuc.result_tintuc;
import vku.phungduc.myapplication.model.user.result_user;

public interface ApiService {

    // api : http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1

    ApiService  apiService = new Retrofit.Builder()
            .baseUrl("https://phungweb.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class) ;
    @GET("foodRecipes/getDanhMuc.php")
    Call<result_danhmuc> getDanhmucApi();

    @GET("foodRecipes/getUsername.php")
    Call<result_user> getUserNameApi(@Query("email") String email , @Query("password") String password ) ;

    @GET("foodRecipes/getCongthuc.php")
    Call<result_congthuc> getCongthucApi() ;

    @GET("foodRecipes/getCongthuc.php")
    Call<result_congthuc> getCongthucApi_danhmuc(@Query("idDanhmuc") int id ) ;

    @GET("foodRecipes/getTintucs.php")
    Call<result_tintuc> getTintuc() ;

    @GET("foodRecipes/getCongthuc.php")
    Call<result_congthuc> getCongthucApi_search(@Query("search") String search) ;

}

