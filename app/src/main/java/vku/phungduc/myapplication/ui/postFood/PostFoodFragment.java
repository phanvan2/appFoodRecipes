package vku.phungduc.myapplication.ui.postFood;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.MainActivity;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.PostCongthuc;
import vku.phungduc.myapplication.ui.home.HomeFragment;

import static android.app.Activity.RESULT_OK;
import static vku.phungduc.myapplication.constant.currentUser;

public class PostFoodFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    String realpath = "" ;
    ImageView img_postFood ;
    private EditText edit_nameFood  , edit_moTa , edit_nguyenLieu , edit_step, edit_danhmuc;
    private Button btn_postFood  ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dang_cong_thuc, container, false);
        if (currentUser == null) {

            BottomNavigationView navView =  root.findViewById(R.id.nav_view);

//            FragmentManager fragmentManager = getParentFragmentManager() ;
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.nav_host_fragment, new HomeFragment());
//            transaction.commit();
        }

        anhxa(root);
        img_postFood = root.findViewById(R.id.img_Postfood) ;
        img_postFood.setImageResource(R.drawable.error);
        img_postFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK) ;
                intent.setType("image/*") ;
                startActivityForResult(intent, 123);
            }
        });
        Button btn_post = root.findViewById(R.id.btn_postFood) ;
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String congthuc1 = new Gson().toJson(new PostCongthuc(
                        9,
                        3,
                        edit_nameFood.getText().toString() ,
                        edit_moTa.getText().toString(),
                        edit_nguyenLieu.getText().toString(),
                        edit_step.getText().toString()

                )) ;
                Snackbar.make(v, congthuc1, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                MultipartBody.Part data =  MultipartBody.Part.createFormData("json_food", congthuc1) ;


                ApiService.apiService.postCongthuc(getImageBody(), data).enqueue(new Callback<String>() {
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
        });
        return root;
    }
    public void anhxa(View view){
        edit_nameFood = view.findViewById(R.id.edit_nameFood) ;
        edit_moTa     = view.findViewById(R.id.edit_moTa) ;
        edit_nguyenLieu = view.findViewById(R.id.edit_nguyenLieu) ;
        edit_step     = view.findViewById(R.id.edit_step) ;
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
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri) ;
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
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj , null , null, null) ;
        if( cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index) ;
        }
        cursor.close();
        return path ;
    }

}