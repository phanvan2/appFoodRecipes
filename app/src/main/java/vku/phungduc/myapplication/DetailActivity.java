package vku.phungduc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.congthuc.Congthuc;

import static vku.phungduc.myapplication.constant.find_nameDanhmuc;

public class DetailActivity extends AppCompatActivity {
    private Congthuc congthuc ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txv_nameUser , txv_nameFood  , txv_mota , txv_category, txv_cachLam , txv_nguyenlieu ;
        ImageView  imgUser, imgFood ;

        txv_nameUser    = findViewById(R.id.txv_nameUser);
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
           txv_category     .setText(find_nameDanhmuc(congthuc.getIdDanhmuc())) ;
           txv_mota         .setText(congthuc.getMoTa());
           txv_nameFood     .setText(congthuc.getTen_monAn());
           txv_nameUser     .setText(congthuc.getTenUser());
           txv_cachLam      .setText( Html.fromHtml(congthuc.getStep(), new URLImageParser(txv_cachLam , getApplicationContext()), null) );

         //  txv_nguyenlieu   .setText(congthuc.getNguyenLieu());
           txv_nguyenlieu   .setText(Html.fromHtml(congthuc.getNguyenLieu() ));

            Picasso.with(getApplicationContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_user/"+ congthuc.getImg_user())
                    .into(imgUser);
           Picasso.with(getApplicationContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_monAn/"+ congthuc.getImg())
                    .into(imgFood);

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



