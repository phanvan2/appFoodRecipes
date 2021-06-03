package vku.phungduc.myapplication.ui.postFood;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.DialogTitle;
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
import java.util.ArrayList;
import java.util.List;

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
import vku.phungduc.myapplication.model.danhmuc.Danhmuc;
import vku.phungduc.myapplication.ui.home.HomeFragment;

import static android.R.layout.simple_spinner_item;
import static android.app.Activity.RESULT_OK;
import static vku.phungduc.myapplication.constant.currentUser;
import static vku.phungduc.myapplication.constant.danhmucs;
import static vku.phungduc.myapplication.constant.find_IdDanhmuc;
import static vku.phungduc.myapplication.constant.find_nameDanhmuc;

public class PostFoodFragment extends Fragment  implements AdapterView.OnItemSelectedListener  {

    String realpath = "" ;
    ImageView img_postFood ;
    List<String> list ;
    int idDanhmuc = -1 ;
    private EditText edit_nameFood  , edit_moTa , edit_nguyenLieu , edit_step, edit_danhmuc;
    private Spinner spinner_danhmuc ;
    private Button btn_postFood  ;
    private Spinner spinnerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dang_cong_thuc, container, false);


        if (currentUser == null) { ;
            TextView textView = root.findViewById(R.id.txv_check1);
            textView.setText("Bạn cần đăng nhập để thực hiện chức năng này");
            LinearLayout linearLayout = root.findViewById(R.id.linear_1) ;
            linearLayout.setVisibility(View.GONE);
        }

        anhxa(root);
       list = new ArrayList<>();
        createListDanhmuc() ;
        ArrayList<String > list_danhmuc = new ArrayList<>() ;
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_danhmuc.setAdapter(adapter);
        spinner_danhmuc.setOnItemSelectedListener(this);
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
                if( realpath != ""){
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
                }else {
                    Snackbar.make(v, "Bạn cần điều dầy đủ thông tin", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }
        });
        return root;
    }



    public void anhxa(View view){
        spinner_danhmuc = view.findViewById(R.id.spinerdanhmuc);
        edit_nameFood = view.findViewById(R.id.edit_nameFood) ;
        edit_moTa     = view.findViewById(R.id.edit_moTa) ;
        edit_nguyenLieu = view.findViewById(R.id.edit_nguyenLieu) ;
        edit_step     = view.findViewById(R.id.edit_step) ;
    }
    public void createListDanhmuc(){
        for ( Danhmuc item: danhmucs ) {
            list.add(item.getTenDanhmuc());
        }
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s = parent.getItemAtPosition(position).toString() ;
        idDanhmuc = find_IdDanhmuc(s) ;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}