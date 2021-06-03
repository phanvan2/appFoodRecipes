package vku.phungduc.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.MainActivity;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.User_regis;

public class RegisterActivity extends AppCompatActivity {
    EditText edit_name  ;
    EditText edit_email ;
    EditText edit_pass;
    EditText edit_rePass ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edit_name = findViewById(R.id.editText_uesname_regis) ;
        edit_email = findViewById(R.id.editText_email) ;
        edit_pass = findViewById(R.id.editText_passWord_regis) ;
        edit_rePass = findViewById(R.id.editText_rePassword_regis );

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// h minhf ddawng kys xong nos bay qua acount_fragment

                Intent  intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);


                String user_regis = new Gson().toJson(new User_regis(
                        edit_name.getText().toString(),
                        edit_email.getText().toString(),
                        edit_pass.getText().toString()
                )) ;
                Snackbar.make(v, user_regis, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                MultipartBody.Part user1 =  MultipartBody.Part.createFormData("user_register", user_regis) ;
                ApiService.apiService.postUser_register(user1).enqueue(
                        new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String result  = response.body() ;
                                if (result.equals("1")) {
                                    Snackbar.make(v, "Đăng ký thành công", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class) ;
                                }else {
                                    Snackbar.make(v, "Đăng ký thất bại", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }




                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Snackbar.make(v, "thát bại " + t.getMessage() + "that bai ", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                );
            }
        });


    }
}

