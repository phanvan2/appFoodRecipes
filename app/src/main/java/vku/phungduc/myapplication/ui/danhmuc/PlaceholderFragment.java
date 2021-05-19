package vku.phungduc.myapplication.ui.danhmuc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import static vku.phungduc.myapplication.constant.danhmucs;

public class PlaceholderFragment extends Fragment {
    int index  ;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView ;
    private List<Congthuc> congthucs ;
    private AdpaterCongthuc  CallAdapter ;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                index = getArguments().getInt(ARG_SECTION_NUMBER);
            }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_danhmuc_chitiet, container, false);
//        final TextView textView = root.findViewById(R.id.textView4);
//        textView.setText("chafo" + danhmucs.get(index).getImg());
        addControl(root);
        createData(danhmucs.get(index).getId());
        return root;
    }
    private void addControl(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_congthuc_danhmuc) ;
        congthucs = new ArrayList<>() ;
        CallAdapter = new AdpaterCongthuc(congthucs, this.getActivity()) ;
        RecyclerView.LayoutManager mLayoutManager =  new GridLayoutManager( view.getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(CallAdapter);
    }
    private void createData(int id ){
        ApiService.apiService.getCongthucApi_danhmuc(id).enqueue(new Callback<result_congthuc>() {
            @Override
            public void onResponse(Call<result_congthuc> call, Response<result_congthuc> response) {

                result_congthuc resultCongthuc  = response.body() ;
                for ( Congthuc ct : resultCongthuc.getData() ) {
                    congthucs.add(ct) ;
                }

                CallAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<result_congthuc> call, Throwable t) {
                Toast.makeText(getContext(), "lloix rồi" , Toast.LENGTH_LONG).show();
            }
        });
    }
}