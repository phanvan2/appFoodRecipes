package vku.phungduc.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.congthuc.quanlicongthuc;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.recyclerViewAdapter.AdapterQLCongthuc;

import static vku.phungduc.myapplication.constant.currentUser;

public class QuanLyCongThucAcitivity extends AppCompatActivity {
    private AdapterQLCongthuc adapter;
    private  ArrayList<Congthuc> arrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_cong_thuc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rcv_quanlicongthuc);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addItemDecoration(dividerItemDecoration);
         arrayList = new ArrayList<>();
         createData();
        adapter = new AdapterQLCongthuc(arrayList,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }
    public void createData(){
        ApiService.apiService.getCongthucApi_user(currentUser.getId()).enqueue(new Callback<result_congthuc>() {
            @Override
            public void onResponse(Call<result_congthuc> call, Response<result_congthuc> response) {
                result_congthuc resultCongthuc = response.body() ;
                for ( Congthuc item: resultCongthuc.getData() ) {
                    arrayList.add(item );
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<result_congthuc> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case 121:

                AlertDialog.Builder alBuilder = new AlertDialog.Builder(this)  ;
                alBuilder.setMessage(Html.fromHtml( "Bạn có chắc chắn muốn xóa công thức <b>"+ arrayList.get(item.getGroupId()).getTen_monAn() +"</b> này ko!!" ) ) ;
                alBuilder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MultipartBody.Part id = MultipartBody.Part.createFormData("id" , String.valueOf(arrayList.get(item.getGroupId()).getId())) ;
                        MultipartBody.Part idUser = MultipartBody.Part.createFormData("idUser", currentUser.getId()) ;

                        ApiService.apiService.postDeleteFood(id , idUser).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String s = response.body() ;
                                if( s.equals("1")){
                                    adapter.removeItem(item.getGroupId());
                                    Snackbar.make( getCurrentFocus(), "Xoá thành công", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make( getCurrentFocus(), "Xoá thất bại", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }


                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Snackbar.make( getCurrentFocus(), "Xoá thất bại", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                    }
                }) ;
                alBuilder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }) ;

                AlertDialog alertDialog = alBuilder.create() ;
                alertDialog.show();
                return true;
            case 122:
                Intent intent_updateFood = new Intent(getApplicationContext(), updateFoodActivity.class) ;
                intent_updateFood.putExtra("congthuc", new Gson().toJson(arrayList.get(item.getGroupId()))) ;
                startActivity(intent_updateFood);

                return true;
            case 123:
                Intent intent_detail_Item = new Intent(getApplicationContext(), DetailActivity.class) ;

                intent_detail_Item.putExtra("congthuc", new Gson().toJson(arrayList.get(item.getGroupId()))) ;
                startActivity(intent_detail_Item);
            default:  return super.onContextItemSelected(item);
        }
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