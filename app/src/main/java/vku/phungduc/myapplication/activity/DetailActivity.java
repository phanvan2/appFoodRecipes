package vku.phungduc.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.URLImageParser;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.comment.Comment;
import vku.phungduc.myapplication.model.comment.result_comment;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.post_binhluan;
import vku.phungduc.myapplication.recyclerViewAdapter.AdapterComent;

import static vku.phungduc.myapplication.constant.currentUser;
import static vku.phungduc.myapplication.constant.find_nameDanhmuc;

public class DetailActivity extends AppCompatActivity {
    private Congthuc congthuc ;
    private RecyclerView recyclerView ;
    private List<Comment> commentList ;
    private AdapterComent adapterComent ;
    private int idMonan ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txv_nameUser , txv_nameFood  , txv_mota , txv_category, txv_cachLam , txv_nguyenlieu ;
        ImageView  imgUser, imgFood ;

        txv_nameUser    = findViewById(R.id.txv_inforName);
        txv_nameFood    = findViewById(R.id.txv_nameFood) ;
        txv_category    = findViewById(R.id.txv_category_recipes) ;
        txv_mota        = findViewById(R.id.txv_mota) ;
        imgFood         = findViewById(R.id.img_avatar_food)  ;
        imgUser         = findViewById(R.id.img_user_food) ;
        txv_cachLam     = findViewById(R.id.cachlam) ;
        txv_nguyenlieu  = findViewById(R.id.nguyenlieu) ;
        Intent intent = getIntent() ;
        Bundle bundle  = intent.getExtras() ;


        if(bundle != null) {
            String s = bundle.getString("congthuc") ;

            Congthuc congthuc = new Gson().fromJson(s , Congthuc.class) ;
            idMonan  = congthuc.getId() ;
            txv_category     .setText(find_nameDanhmuc(congthuc.getIdDanhmuc())) ;
            txv_mota         .setText(congthuc.getMoTa());
            txv_nameFood     .setText(congthuc.getTen_monAn());
            txv_nameUser     .setText(congthuc.getTenUser());
            txv_cachLam      .setText( Html.fromHtml(congthuc.getStep(), new URLImageParser(txv_cachLam , getApplicationContext()), null) );

            txv_nguyenlieu   .setText(Html.fromHtml(congthuc.getNguyenLieu() ));

            Picasso.with(getApplicationContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_user/"+ congthuc.getImg_user())
                    .into(imgUser);
            Picasso.with(getApplicationContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_monAn/"+ congthuc.getImg())
                    .into(imgFood);

        }

        recyclerView = (RecyclerView)findViewById(R.id.recycler_comment) ;
        addControl(getApplicationContext(), idMonan);

        Button btn_postComment = findViewById(R.id.btn_postComment)  ;
        btn_postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_comment = findViewById(R.id.binhluan) ;
                String s  = edit_comment.getText().toString() ;
                if( s.equals("") || s == null ){
                    Snackbar.make(v, "Vui lòng nhập bình luân của bạn", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    if( currentUser == null )
                        Snackbar.make(v, "Bạn cần đăng nhập để bình luận", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    else {
                        String str_comment = new Gson().toJson(new post_binhluan(currentUser.getId() , idMonan , s)) ;
                        MultipartBody.Part comment =  MultipartBody.Part.createFormData("comment", str_comment ) ;

                        ApiService.apiService.post_Comment(comment).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String s = response.body() ;
                                if( s.equals("0")){
                                    Snackbar.make(v, "Bình luận thất bại", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else {
                                    Snackbar.make(v, "Bình luận thành công", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    commentList.clear();
                                    createData(idMonan);
                                    adapterComent.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Snackbar.make(v, "Bình luận thất bại", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }
                        });
                    }
                }
            }
        });


    }

    private void addControl(Context context , int id ){




        commentList = new ArrayList<>();
        adapterComent = new AdapterComent(commentList , context ) ;
        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager(context);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterComent);
        createData(id) ;
    }
    private void createData(int id ){
        ProgressBar progressBar = findViewById(R.id.progress_comment) ;
        progressBar.setVisibility(View.VISIBLE) ;
        ApiService.apiService.getCommentApi(id + "").enqueue(new Callback<result_comment>() {
            @Override
            public void onResponse(Call<result_comment> call, Response<result_comment> response) {

                result_comment resultComment  = response.body() ;
                for ( Comment comment : resultComment.getData() ) {
                    commentList.add(comment) ;
                }
                progressBar.setVisibility(View.GONE);
                adapterComent.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<result_comment> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    private void handle_postComment(){

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





