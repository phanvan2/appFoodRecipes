package vku.phungduc.myapplication.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.URLImageParser;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.PostCongthuc;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.danhmuc.Danhmuc;

import static vku.phungduc.myapplication.constant.currentUser;
import static vku.phungduc.myapplication.constant.danhmucs;
import static vku.phungduc.myapplication.constant.find_IdDanhmuc;
import static vku.phungduc.myapplication.constant.find_nameDanhmuc;

public class updateFoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String realpath = "" ;
    ImageView img_postFood ;
    Congthuc congthuc ;
    List<String> list ;
    int idDanhmuc = -1 ;
    private EditText edit_nameFood  , edit_moTa , edit_nguyenLieu , edit_step, edit_danhmuc;
    private Spinner spinner_danhmuc ;
    private Button btn_postFood  ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dang_cong_thuc);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        anhxa();

        list = new ArrayList<>();
        createListDanhmuc() ;
        ArrayList<String > list_danhmuc = new ArrayList<>() ;
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_danhmuc.setAdapter(adapter);
        spinner_danhmuc.setOnItemSelectedListener(this);

        Intent intent = getIntent() ;
        Bundle bundle  = intent.getExtras() ;
        if(bundle != null) {
            String s = bundle.getString("congthuc");

            congthuc = new Gson().fromJson(s, Congthuc.class);

            int post = adapter.getPosition(find_nameDanhmuc(congthuc.getIdDanhmuc())) ; // lấy vị trí của danh mục trong spniper
            spinner_danhmuc.setSelection(post); // set  danh mục lại
            edit_nameFood.setText(congthuc.getTen_monAn());
            edit_moTa.setText(congthuc.getMoTa());
            edit_nguyenLieu.setText(Html.fromHtml(congthuc.getNguyenLieu()));
            Picasso.with(getApplicationContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_monAn/"+ congthuc.getImg())
                    .into(img_postFood);
            edit_step.setText( Html.fromHtml(congthuc.getStep(), new URLImageParser(edit_step , getApplicationContext()), null) );


        }
        img_postFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK) ;
                intent.setType("image/*") ;
                startActivityForResult(intent, 123);
            }
        });
        btn_postFood.setText("update Food");
        btn_postFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });

    }
    public void anhxa(){
        spinner_danhmuc = findViewById(R.id.spinerdanhmuc);
        edit_nameFood   = findViewById(R.id.edit_nameFood) ;
        edit_moTa       = findViewById(R.id.edit_moTa) ;
        edit_nguyenLieu = findViewById(R.id.edit_nguyenLieu) ;
        edit_step       = findViewById(R.id.edit_step) ;
        img_postFood    = findViewById(R.id.img_Postfood) ;
        btn_postFood    = findViewById(R.id.btn_postFood) ;
    }
    public void createListDanhmuc(){
        for ( Danhmuc item: danhmucs ) {
            list.add(item.getTenDanhmuc());
        }
    }

    public void update(View v){
        String congthuc1 = new Gson().toJson(new PostCongthuc(
                Integer.parseInt(currentUser.getId()),
                idDanhmuc,
                edit_nameFood.getText().toString() ,
                edit_moTa.getText().toString(),
                edit_nguyenLieu.getText().toString(),
                edit_step.getText().toString()

        )) ;
        Snackbar.make(v, congthuc1, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        MultipartBody.Part data =  MultipartBody.Part.createFormData("update_congthuc", congthuc1) ;
        MultipartBody.Part id = MultipartBody.Part.createFormData("id",congthuc.getId()+"") ;

        ApiService.apiService.postUpdateCongthuc(getImageBody(), data , id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s = response.body() ;
                Snackbar.make(v, s, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(v, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public MultipartBody.Part getImageBody(){
        File file = new File(realpath) ;
        String file_path = file.getAbsolutePath() ;
        String[] mangtenfile = file_path.split("\\.") ;

        file_path = mangtenfile[0] + System.currentTimeMillis() +"." + mangtenfile[1] ;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file) ;
        MultipartBody.Part body = MultipartBody.Part.createFormData("updated_img", file_path, requestBody) ;
        return body ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( requestCode == 123  && resultCode == RESULT_OK  && data != null){
            Uri uri = data.getData() ;
            realpath = getRealPathFromUri(uri) ;
            try{
                InputStream inputStream = this.getContentResolver().openInputStream(uri) ;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream) ;
                img_postFood.setImageBitmap(bitmap);
            } catch (FileNotFoundException e ){
                e.printStackTrace();
            }
        }else{

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromUri ( Uri contentUri){
        String path = null  ;
        String[] proj = {MediaStore.MediaColumns.DATA}  ;
        Cursor cursor = this.getContentResolver().query(contentUri, proj , null , null, null) ;
        if( cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index) ;
        }
        cursor.close();
        return path ;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s = parent.getItemAtPosition(position).toString() ;
        idDanhmuc = find_IdDanhmuc(s) ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
