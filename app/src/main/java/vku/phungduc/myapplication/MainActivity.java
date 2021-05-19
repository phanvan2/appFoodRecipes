package vku.phungduc.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.danhmuc.result_danhmuc;

import static vku.phungduc.myapplication.constant.danhmucs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications , R.id.navigation_search,  R.id.navigation_search,  R.id.navigation_acount)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        loadData();
    }
    public void loadData(){
        ApiService.apiService.getDanhmucApi().enqueue(new Callback<result_danhmuc>() {
            @Override
            public void onResponse(Call<result_danhmuc> call, Response<result_danhmuc> response) {
                result_danhmuc result = response.body() ;
                for( int i = 0 ; i < result.getData().size() ; i ++ ) {
                    danhmucs.add(result.getData().get(i)) ;
                }
            }

            @Override
            public void onFailure(Call<result_danhmuc> call, Throwable t) {
                Toast.makeText(MainActivity.this, "LỖi " , Toast.LENGTH_LONG).show();

            }
        });
    }

}