package vku.phungduc.myapplication.recyclerViewAdapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.model.congthuc.Congthuc;

import static vku.phungduc.myapplication.constant.danhmucs;
import static vku.phungduc.myapplication.constant.find_nameDanhmuc;

public class AdapterQLCongthuc extends RecyclerView.Adapter<AdapterQLCongthuc.ViewHolder>{
    ArrayList<Congthuc> quanlicongthucArrayList;
    Context context;

    public AdapterQLCongthuc(ArrayList<Congthuc> quanlicongthucArrayList, Context context) {
        this.quanlicongthucArrayList = quanlicongthucArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_quanlicongthuc,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Congthuc congthuc = quanlicongthucArrayList.get(position) ;

        holder.txvNameFood.setText(congthuc.getTen_monAn());
        holder.txvdanhmuc.setText( find_nameDanhmuc(congthuc.getIdDanhmuc()) );
        holder.txvNgayDang.setText(congthuc.getNgayDang());
        holder.txvNameUser.setText(congthuc.getTenUser());
        Picasso.with(context).load("https://phungweb.000webhostapp.com/do_an_2/image/img_monAn/"+ congthuc.getImg())
                .into(holder.imageFood);


    }

    @Override
    public int getItemCount() {
        return quanlicongthucArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView txvNameFood ,txvdanhmuc,txvNgayDang , txvNameUser;
        ImageView imageFood;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNameFood = itemView.findViewById(R.id.txv_nameFood_ql);
            txvdanhmuc = itemView.findViewById(R.id.txv_Category_ql);
            txvNgayDang = itemView.findViewById(R.id.txv_createAt_ql);
            txvNameUser = itemView.findViewById(R.id.txv_user_ql) ;
            imageFood = itemView.findViewById(R.id.img_food_ql);
            cardView = itemView.findViewById(R.id.cardview);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardView.showContextMenu() ;
                }

            });
            cardView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle( quanlicongthucArrayList.get(this.getAdapterPosition()).getTen_monAn());
            menu.add(this.getAdapterPosition(),121,0,"Xóa công thức");
            menu.add(this.getAdapterPosition(),122,1,"Cập nhật");
            menu.add(this.getAdapterPosition(), 123, 2, "Chi tiết"); 

        }


    }
    public void removeItem(int position){
        quanlicongthucArrayList.remove(position);
        notifyDataSetChanged();
    }

}







