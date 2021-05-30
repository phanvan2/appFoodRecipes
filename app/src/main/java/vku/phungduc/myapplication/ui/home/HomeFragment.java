package vku.phungduc.myapplication.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import kotlinx.coroutines.Delay;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vku.phungduc.myapplication.MainActivity;
import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.api.ApiService;
import vku.phungduc.myapplication.model.ListData;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.congthuc.result_congthuc;
import vku.phungduc.myapplication.model.tintuc.TinTuc;
import vku.phungduc.myapplication.model.tintuc.result_tintuc;
import vku.phungduc.myapplication.recyclerViewAdapter.AdapterTintuc;
import vku.phungduc.myapplication.recyclerViewAdapter.AdpaterCongthuc;
import vku.phungduc.myapplication.recyclerViewAdapter.ListDataAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView ;
    private List<Congthuc> congthucs ;
    private  List<TinTuc> tinTucList ;
    private List<ListData> listData ;
    private AdapterTintuc CallAdapter ;
    private ListDataAdapter  listDataAdapter ;
    ProgressDialog loading  = null  ;

    int status = 0 ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        addControl(root);

        return root;
    }
    private void addControl(View view){


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerCongthuc) ;

        listData = new ArrayList<>();
        listDataAdapter = new ListDataAdapter(view.getContext() , getActivity() , createData(view ) ) ;
        RecyclerView.LayoutManager mLayoutManager  = new LinearLayoutManager( view.getContext());
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(listDataAdapter);

    }
    private List<ListData> createData(View view){

        congthucs = new ArrayList<>() ;
        tinTucList = new ArrayList<>() ;

        loading = ProgressDialog.show(view.getContext(),null,"Loading...",false,false);

        ApiService.apiService.getTintuc().enqueue(new Callback<result_tintuc>() {
            @Override
            public void onResponse(Call<result_tintuc> call, Response<result_tintuc> response) {

                result_tintuc resultTintuc  = response.body() ;
                for ( TinTuc ct : resultTintuc.getData() ) {
                    tinTucList.add(ct) ;
                }

                listData.add( new ListData(ListDataAdapter.TYPE_TINTUC, null, tinTucList)) ;
                listDataAdapter.notifyDataSetChanged();
                status = 1  ;


            }

            @Override
            public void onFailure(Call<result_tintuc> call, Throwable t) {
                Toast.makeText(getContext(), "lloix rồi" , Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });



        new Thread(){
            @Override
            public void run() {
                super.run();
                Boolean check = true ;
                while (check) {
                    if ( status == 1) {
                        ApiService.apiService.getCongthucApi().enqueue(new Callback<result_congthuc>() { // rest api cong thuc mon an
                            
                            @Override
                            public void onResponse(Call<result_congthuc> call, Response<result_congthuc> response) {

                                result_congthuc resultCongthuc  = response.body() ;
                                for ( Congthuc ct : resultCongthuc.getData() ) {
                                    congthucs.add(ct) ;
                                }

                                listData.add( new ListData(ListDataAdapter.TYPE_MOINHAT, congthucs, null)) ;
                                listDataAdapter.notifyDataSetChanged();

                                loading.dismiss();
                            }

                            @Override
                            public void onFailure(Call<result_congthuc> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(getContext(), "lỗi " , Toast.LENGTH_LONG).show();
                            }
                        });

                        check = false ;
                    }

                }

            }
        }.start();


        return listData ;

    }
}