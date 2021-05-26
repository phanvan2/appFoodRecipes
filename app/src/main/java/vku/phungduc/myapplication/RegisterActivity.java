package vku.phungduc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONStringer;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.ui.acount.AcountFragment;
import vku.phungduc.myapplication.ui.home.HomeFragment;

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
class User_regis {
    private  String ten ;
    private String email ;
    private String password ;

    public User_regis(String ten, String email, String password) {
        this.ten = ten;
        this.email = email;
        this.password = password;
    }
}
