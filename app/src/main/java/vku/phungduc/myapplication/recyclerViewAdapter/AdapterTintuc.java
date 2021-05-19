package vku.phungduc.myapplication.recyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.model.tintuc.TinTuc;

public class AdapterTintuc extends RecyclerView.Adapter<AdapterTintuc.TintucViewHolder>{
    private List<TinTuc> listTintuc;
    public void setData(List<TinTuc> list){
        this.listTintuc = list;
        notifyDataSetChanged();
    }

    public AdapterTintuc(List<TinTuc> listTintuc) {
        this.listTintuc = listTintuc;
    }
    public AdapterTintuc() {
        this.listTintuc = listTintuc;
    }

    @NonNull
    @Override
    public TintucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tintuc,parent,false);

        return new TintucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TintucViewHolder holder, int position) {
        TinTuc tintuc = listTintuc.get(position);

        holder.imgtintuc.setImageResource(R.drawable.tintuc1);
        holder.mota.setText(tintuc.getTen_tin_tuc());
//        holder.btnxemthem.setOnClickListener(tintuc.getButton());
    }

    @Override
    public int getItemCount() {
        if(listTintuc != null){
            return listTintuc.size();
        }
        return 0;
    }

    public class TintucViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgtintuc;
        private TextView mota;

        public TintucViewHolder(@NonNull View itemView) {
            super(itemView);
            imgtintuc = itemView.findViewById(R.id.tintucimg);
            mota = itemView.findViewById(R.id.motatintuc);

        }
    }
}
