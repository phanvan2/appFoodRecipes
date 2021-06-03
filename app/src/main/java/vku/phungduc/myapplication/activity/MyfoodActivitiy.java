package vku.phungduc.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.recyclerViewAdapter.AdpaterCongthuc;

public class MyfoodActivitiy extends AppCompatActivity {
    private AdpaterCongthuc  adpaterCongthuc ;
    private  RecyclerView recyclerView ;
    private List<Congthuc> congthucList ;
    String id  = "0" ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfood);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent() ;
        Bundle bundle  = intent.getExtras() ;
        if( bundle != null){
            id = bundle.getString("idUser") ;
        }
        congthucList = new ArrayList<>() ;
        recyclerView = (RecyclerView) findViewById(R.id.rcv_myFood)  ;
        adpaterCongthuc  = new AdpaterCongthuc(congthucList , MyfoodActivitiy.this);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adpaterCongthuc);
    }
    public void reateData(){
        ApiService.apiService.getCongthucApi_user(id).enqueue(new Callback<result_congthuc>() {
            @Override
            public void onResponse(Call<result_congthuc> call, Response<result_congthuc> response) {
                result_congthuc resultCongthuc = response.body() ;
                for ( Congthuc item: resultCongthuc.getData() ) {
                    congthucList.add(item );
                }
                adpaterCongthuc.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<result_congthuc> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
