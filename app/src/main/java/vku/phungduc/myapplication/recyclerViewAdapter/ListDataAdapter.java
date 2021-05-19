package vku.phungduc.myapplication.recyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.model.ListData;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ListDataViewHolder>{
    public static final int TYPE_DANHMUC = 3;
    public static final int TYPE_TINTUC = 2;
    public static final int TYPE_MOINHAT = 1;
    public static final int TYPE_YEUTHICH = 4;


    private List<ListData> listData;
    private Context mcontext;

    public ListDataAdapter(Context context, List<ListData> data) {
        this.mcontext = context;
        this.listData = data;
    }

    public void setData(Context context, List<ListData> listData){
        this.mcontext = context;
        this.listData = listData;
        notifyDataSetChanged();
    }
  @Override
    public int getItemViewType(int position) {
        return listData.get(position).getType();
    }

    @NonNull
    @Override
    public ListDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_data, parent,false);
        return new ListDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDataViewHolder holder, int position) {
        ListData list = listData.get(position);
        if(list == null){
            return;
        }


        if(TYPE_TINTUC == holder.getItemViewType()){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext, RecyclerView.HORIZONTAL,false);
                holder.title_recycler.setText("TIN TỨC ẨM THỰC");
                holder.rcvItem.setLayoutManager(linearLayoutManager);
                holder.rcvItem.setFocusable(false);
                AdapterTintuc tintucAdapter = new AdapterTintuc();
                tintucAdapter.setData(list.getTinTucList());
                holder.rcvItem.setAdapter(tintucAdapter);
        } else  if(TYPE_MOINHAT == holder.getItemViewType()){
                GridLayoutManager layoutManager = new GridLayoutManager(mcontext,2, GridLayoutManager.VERTICAL,false);
                holder.title_recycler.setText("MỚI NHẤT");
                holder.rcvItem.setLayoutManager(layoutManager);
                holder.rcvItem.setFocusable(false);
                AdpaterCongthuc moinhatAdapter = new AdpaterCongthuc( list.getCongthucList(), (Activity) this.mcontext);
                moinhatAdapter.setData(list.getCongthucList());
                holder.rcvItem.setAdapter(moinhatAdapter);
        }

    }

    @Override
    public int getItemCount() {
        if( listData != null){
            return listData.size();
        }
        return 0;
    }

    public class ListDataViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rcvItem;
        private TextView title_recycler ;
        public ListDataViewHolder(@NonNull View itemView) {
            super(itemView);
            title_recycler = itemView.findViewById(R.id.title_recycler) ;
            rcvItem = itemView.findViewById(R.id.rcv_item);
        }
    }
}
