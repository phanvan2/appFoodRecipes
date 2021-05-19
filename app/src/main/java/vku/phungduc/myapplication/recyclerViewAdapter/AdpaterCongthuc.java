package vku.phungduc.myapplication.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.tintuc.TinTuc;

public class AdpaterCongthuc extends RecyclerView.Adapter<AdpaterCongthuc.CallViewHolder> {
    private List<Congthuc> congthucs ;
    private Activity activity ;

    public void setData(List<Congthuc> list){
        this.congthucs = list;
        notifyDataSetChanged();
    }
    public AdpaterCongthuc(){

    }
    public AdpaterCongthuc(List<Congthuc> congthucs, Activity activity){
        this.congthucs = congthucs ;
        this.activity = activity ;

    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_congthuc, parent, false ) ;

        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpaterCongthuc.CallViewHolder holder, int position) {
        Congthuc congthuc = congthucs.get(position) ;

        holder.nameCongthuc.setText(congthuc.getTen_monAn());;
        Picasso.with(this.activity).load("https://phungweb.000webhostapp.com/do_an_2/image/img_monAn/"+ congthuc.getImg())
                .into(holder.imageCongthuc);
        holder.nameUser.setText(congthuc.getTenUser());
    }

    @Override
    public int getItemCount() {
        if(congthucs == null){
            return  0 ;
        }
        return congthucs.size();
    }

    public class CallViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageCongthuc ;
        private TextView nameCongthuc ;
        private TextView nameUser ;

        @SuppressLint("WrongViewCast")
        public CallViewHolder(View itemView){
            super(itemView);
            imageCongthuc = (ImageView)itemView.findViewById(R.id.img_MonAnmoinhat) ;
            nameCongthuc = (TextView)itemView.findViewById(R.id.txtView_nameMonAnmoinhat) ;
            nameUser = (TextView)itemView.findViewById(R.id.txtView_PersonName)  ;

        }
    }
}