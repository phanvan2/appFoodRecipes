package vku.phungduc.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.ListData;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.model.user.User;
import vku.phungduc.myapplication.model.user.result_user;
import vku.phungduc.myapplication.recyclerViewAdapter.AdpaterCongthuc;
import vku.phungduc.myapplication.recyclerViewAdapter.ListDataAdapter;

import static vku.phungduc.myapplication.constant.currentUser;

public class InforUserActivity extends AppCompatActivity {
    private User user ;
    private List<Congthuc> congthucList ;
    private AdpaterCongthuc adapterCongthuc  ;
    int status = 0 ;
    TextView txv_inforName  ,txv_inforUserName , txv_inforAbout  ;
    ImageView img_inforUser  ;
    ProgressDialog loading  = null  ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txv_inforName = findViewById(R.id.txv_inforName);
        txv_inforUserName = findViewById(R.id.txv_inforUsername) ;
        txv_inforAbout = findViewById(R.id.txv_inforAbout) ;
        img_inforUser = findViewById(R.id.img_inforUser) ;
        Intent intent = getIntent() ;
        Bundle bundle  = intent.getExtras() ;

        if(bundle != null) {

            String id = bundle.getString("idUser");
//            if(currentUser != null) {
//                if (id.equals(currentUser.getId())) {
//                    user = currentUser;
//                    status = 1   ;
//                    loadUser();
//                    create_data() ;
//                } else {
                 //   loading = ProgressDialog.show(getApplicationContext(),null,"Loading...",false,false);

                    ApiService.apiService.getUserId(bundle.getInt("idUser")).enqueue(new Callback<result_user>() {
                        @Override
                        public void onResponse(Call<result_user> call, Response<result_user> response) {
                            result_user resultUser = response.body();
                            user = resultUser.getData() ;
                            loadUser();
                            create_data() ;
                         //   loading.dismiss();
                        }

                        @Override
                        public void onFailure(Call<result_user> call, Throwable t) {
                            Snackbar.make( getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            Log.d("BBB" , t.getMessage()) ;
                        }
                    });
                //1}

//            }else {
//                ApiService.apiService.getUserId(bundle.getInt("idUser")).enqueue(new Callback<result_user>() {
//                    @Override
//                    public void onResponse(Call<result_user> call, Response<result_user> response) {
//                        result_user resultUser = response.body();
//                        user = resultUser.getData() ;
//                        loadUser();
//                        create_data() ;
//                    }
//
//                    @Override
//                    public void onFailure(Call<result_user> call, Throwable t) {
//                        Snackbar.make( getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                        Log.d("BBB" , t.getMessage()) ;
//                    }
//                });
//            }
        }
        congthucList = new ArrayList<>() ;
        RecyclerView recyclerView = findViewById(R.id.rcv_infroUser) ;
        adapterCongthuc = new AdpaterCongthuc(congthucList , InforUserActivity.this) ;
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterCongthuc);



    }
    private void create_data(){
       // loading = ProgressDialog.show(getApplicationContext(),null,"Loading...",false,false);

        ApiService.apiService.getCongthucApi_user(user.getId()).enqueue(new Callback<result_congthuc>() {
            @Override
            public void onResponse(Call<result_congthuc> call, Response<result_congthuc> response) {
                result_congthuc resultCongthuc = response.body() ;
                TextView txv_countfood = findViewById(R.id.txv_infor_countFood) ;
                txv_countfood.setText("Công thức đã đăng: "+ resultCongthuc.getData().size());
                for( Congthuc congthuc : resultCongthuc.getData()){
                    congthucList.add(congthuc) ;
                }
        //        loading.dismiss();

            }

            @Override
            public void onFailure(Call<result_congthuc> call, Throwable t) {
                loading.dismiss();

            }
        });


    }
    public void  loadUser(){
        if (user.getAbout() == null || user.getAbout() == "") {
            txv_inforAbout.setVisibility(View.GONE);
        } else
            txv_inforAbout.setText(user.getAbout());
        txv_inforName.setText(user.getTen());
        txv_inforUserName.setText(user.getHo_va_ten());
        Picasso.with(getApplicationContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_user/" + user.getImg_user())
                .into(img_inforUser);
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
