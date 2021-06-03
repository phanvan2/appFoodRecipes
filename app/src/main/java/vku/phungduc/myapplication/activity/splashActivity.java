package vku.phungduc.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.MainActivity;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.danhmuc.result_danhmuc;

import static vku.phungduc.myapplication.constant.danhmucs;

public class splashActivity extends AppCompatActivity {

    Intent intent_main ;
    ProgressBar   progressBar ;
    SeekBar  seekBar;
    SeekBar seekBar1  ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar) ; 
        intent_main = new Intent( splashActivity.this , MainActivity.class) ;
        Context context = getApplicationContext() ;
        ActionBar  actionBar = getSupportActionBar() ;
        actionBar.hide();
        loadData(context);

    }
    public void loadData(Context context){
        ApiService.apiService.getDanhmucApi().enqueue(new Callback<result_danhmuc>() {
            @Override
            public void onResponse(Call<result_danhmuc> call, Response<result_danhmuc> response) {
                result_danhmuc result = response.body() ;
                int sum = result.getData().size() -1  ; // tính tổng sia

                double tientrinh = 0;
                for(int i = 0 ; i < result.getData().size() ;  i++  ) {
                     tientrinh = ((double) i / sum) * 100  ;

                    progressBar.setProgress( (int)tientrinh);
                    danhmucs.add(result.getData().get(i)) ;

                }

                startActivity(intent_main);
                finish();



            }

            @Override
            public void onFailure(Call<result_danhmuc> call, Throwable t) {

            }
        });
    }

}
