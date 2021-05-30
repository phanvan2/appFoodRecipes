package vku.phungduc.myapplication.ui.search;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.recyclerViewAdapter.AdpaterCongthuc;

public class SearchFragment extends Fragment {

    private List<Congthuc> congthucs  ;
    private AdpaterCongthuc adpaterCongthuc ;
    private TextView textView  ;
    private  EditText editText ;
    ProgressDialog  loading  = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  root  = inflater.inflate(R.layout.fragment_search_foods, container, false);
        EditText editText_search = root.findViewById(R.id.editTextSearch);
        editText_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        return root  ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






         editText = view.findViewById(R.id.editTextSearch) ;
        textView = view.findViewById(R.id.txtsearch);
        congthucs = new ArrayList<>() ;
        RecyclerView recyclerView = view.findViewById(R.id.recycler_search) ;
        RecyclerView.LayoutManager  layoutManager = new GridLayoutManager(view.getContext(), 2,GridLayoutManager.VERTICAL, false ) ;
        recyclerView.setLayoutManager(layoutManager);
        adpaterCongthuc = new AdpaterCongthuc() ;
        recyclerView.setAdapter(adpaterCongthuc);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if( !hasFocus) {

                    loading = ProgressDialog.show(view.getContext(),null,"loading...",false,false);

                    loadData(editText.getText().toString());

                }

            }
        }) ;

    }
    public void loadData(String text_search){
        ApiService.apiService.getCongthucApi_search(text_search).enqueue(new Callback<result_congthuc>() {
            @Override
            public void onResponse(Call<result_congthuc> call, Response<result_congthuc> response) {

                result_congthuc resultCongthuc = response.body() ;
                adpaterCongthuc.setData(resultCongthuc.getData());
                textView.setText("Kết quả tìm kiếm ("+ editText.getText().toString() + ") : " + adpaterCongthuc.getItemCount());
                loading.dismiss();

            }

            @Override
            public void onFailure(Call<result_congthuc> call, Throwable t) {

            }
        });
    }
}
