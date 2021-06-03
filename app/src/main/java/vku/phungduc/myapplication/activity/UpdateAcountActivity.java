package vku.phungduc.myapplication.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.PostCongthuc;
import vku.phungduc.myapplication.model.user.UserUpdate;

import static vku.phungduc.myapplication.constant.currentUser;

public class UpdateAcountActivity extends AppCompatActivity {
    private EditText edit_updateName , edit_updateEmail ,edit_updateAbout ;
    private ImageView img_updateAvatar ;
    String realpath = "" ;
    int idDanhmuc  ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_acount);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        anhxa();
        setData();
        Button btn_updateInfor = findViewById(R.id.btn_updateInfor) ;
        btn_updateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(v);
            }
        });
        img_updateAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK) ;
                intent.setType("image/*") ;
                startActivityForResult(intent, 123);
            }
        });

    }
    public void anhxa(){
        edit_updateName  = findViewById(R.id.edit_updateName) ;
        edit_updateEmail = findViewById(R.id.edit_updateEmail)  ;
        edit_updateAbout = findViewById(R.id.edit_updateAbout) ;
        img_updateAvatar = findViewById(R.id.img_updateAvatar) ;
    }
    public void setData(){

        edit_updateAbout.setText(currentUser.getAbout());
        edit_updateEmail.setText(currentUser.getEmail());
        edit_updateName.setText(currentUser.getTen());

        Picasso.with(getApplicationContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_user/"+ currentUser.getImg_user())
                .into(img_updateAvatar);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public void updateUser(View view ){
        String user_update = new Gson().toJson(new UserUpdate(
                currentUser.getId(),
                edit_updateName.getText().toString() ,
                edit_updateEmail.getText().toString(),
                edit_updateName.getText().toString()
        )) ;
        MultipartBody.Part data =  MultipartBody.Part.createFormData("user_update" , user_update) ;
        ApiService.apiService.post_updateUser(getImageBody(), data).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Snackbar.make(view, response.body(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.d("BBB", "onFailure: "+ t.getMessage());
            }
        });
    }
    public MultipartBody.Part getImageBody(){
        File file = new File(realpath) ;
        String file_path = file.getAbsolutePath() ;
        String[] mangtenfile = file_path.split("\\.") ;

        file_path = mangtenfile[0] + System.currentTimeMillis() +"." + mangtenfile[1] ;
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file) ;
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_img", file_path, requestBody) ;
        return body ;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( requestCode == 123  && resultCode == RESULT_OK  && data != null){
            Uri uri = data.getData() ;
            realpath = getRealPathFromUri(uri) ;
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri) ;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream) ;
                img_updateAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e ){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromUri ( Uri contentUri){
        String path = null  ;
        String[] proj = {MediaStore.MediaColumns.DATA}  ;
        Cursor cursor = getContentResolver().query(contentUri, proj , null , null, null) ;
        if( cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index) ;
        }
        cursor.close();
        return path ;
    }

}
