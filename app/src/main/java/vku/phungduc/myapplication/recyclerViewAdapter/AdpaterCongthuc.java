package vku.phungduc.myapplication.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.activity.DetailActivity;
import vku.phungduc.myapplication.activity.InforUserActivity;
import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.tintuc.TinTuc;

import static vku.phungduc.myapplication.constant.currentUser;

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

        holder.nameUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , InforUserActivity.class)  ;
                intent.putExtra("idUser" , congthuc.getIdUser()) ;
                activity.startActivity(intent);
            }
        });
        holder.linear_congthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_detail_Item = new Intent(v.getContext(), DetailActivity.class) ;

                intent_detail_Item.putExtra("congthuc", new Gson().toJson(congthuc)) ;
                v.getContext().startActivity(intent_detail_Item);

            }
        });
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
        private LinearLayout linear_congthuc ;
        @SuppressLint("WrongViewCast")
        public CallViewHolder(View itemView){
            super(itemView);
            linear_congthuc = (LinearLayout)itemView.findViewById(R.id.body_congthuc)  ;
            imageCongthuc = (ImageView)itemView.findViewById(R.id.img_MonAnmoinhat) ;
            nameCongthuc = (TextView)itemView.findViewById(R.id.txtView_nameMonAnmoinhat) ;
            nameUser = (TextView)itemView.findViewById(R.id.txtView_PersonName)  ;

        }
    }
}
