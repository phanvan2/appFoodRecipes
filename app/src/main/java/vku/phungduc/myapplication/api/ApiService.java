package vku.phungduc.myapplication.api;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONStringer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import vku.phungduc.myapplication.model.comment.result_comment;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.model.danhmuc.result_danhmuc;
import vku.phungduc.myapplication.model.tintuc.result_tintuc;
import vku.phungduc.myapplication.model.user.result_user;

public interface ApiService {

    // api : http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    ApiService  apiService = new Retrofit.Builder()
            .baseUrl("https://phungweb.000webhostapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
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

    @GET("foodRecipes/getComment.php")
    Call<result_comment> getCommentApi(@Query("idMon_an") String idMon_an) ;

    @Multipart
    @POST("foodRecipes/postUser.php")
    Call<String> postUser_register(@Part MultipartBody.Part user_regis);


}

