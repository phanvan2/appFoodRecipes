package vku.phungduc.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.MainActivity;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.user.result_user;

import static vku.phungduc.myapplication.constant.checkUser;
import static vku.phungduc.myapplication.constant.currentUser;

public class LoginActivity extends AppCompatActivity {
    private EditText  user ;
    private EditText pass ;
    ProgressDialog loading  = null  ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.hide();


        user = findViewById(R.id.editText_username) ;
        pass = findViewById(R.id.editText_pass) ;

        Button btn_login = findViewById(R.id.btn_login) ;
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              loading = ProgressDialog.show(v.getContext(),null,"Loading...",false,false);

                ApiService.apiService.getUserNameApi(user.getText().toString(), pass.getText().toString())
                        .enqueue(new Callback<result_user>() {
                    @Override
                    public void onResponse(Call<result_user> call, Response<result_user> response) {
                        result_user resultUser = response.body() ;
                        currentUser = resultUser.getData() ;
                        loading.dismiss();
                        checkUser = true ;
                        Snackbar.make(v, "Đăng nhập thành công ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Intent intent_acount = new Intent(getApplicationContext(), MainActivity.class)  ;
                        startActivity(intent_acount);

                        finish();

                    }

                    @Override
                    public void onFailure(Call<result_user> call, Throwable t) {
                        loading.dismiss();
                        Snackbar.make(v, "Đăng nhập thất bại ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
    }
}
