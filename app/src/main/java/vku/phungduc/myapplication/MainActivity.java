package vku.phungduc.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
    private static final int MY_PERMINSSION_REQUEST_CODE_ALL_PHONE  = 555 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        askPermissionAndCall(MainActivity.this);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_notifications , R.id.navigation_search,  R.id.navigation_search,  R.id.navigation_acount)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



    }

    private void askPermissionAndCall(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int sendSmsPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) ; // check quyền có đã bật hay chưa

            if( sendSmsPermission != PackageManager.PERMISSION_GRANTED) { // nếu quyền  ko bật thì mở một request đòi bật quyền truy cập lên
                activity.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMINSSION_REQUEST_CODE_ALL_PHONE
                );
                return ;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == 555){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


            }
            else {

                Toast.makeText(MainActivity.this ,  "đéo choi nưa " , Toast.LENGTH_LONG).show();

            }
        }
    }

}