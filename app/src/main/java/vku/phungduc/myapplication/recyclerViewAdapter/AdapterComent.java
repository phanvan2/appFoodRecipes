package vku.phungduc.myapplication.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.model.comment.Comment;

public class AdapterComent extends RecyclerView.Adapter<AdapterComent.CallViewHolder> {
    private List<Comment> comments  ;
    private Activity activity ;
    private Context context ;

    public AdapterComent(List<Comment> comments , Context context) {
        this.comments = comments;
        this.context  = context;

    }

    @NonNull
    @Override
    public AdapterComent.CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment , parent, false) ;

        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComent.CallViewHolder holder, int position) {
        Comment comment = comments.get(position) ;
        holder.txv_name.setText(comment.getTenUser());
        holder.txv_createAt.setText(comment.getNgay());
        holder.txv_content.setText(comment.getNoiDung());

        Picasso.with(context).load("https://phungweb.000webhostapp.com/do_an_2/image/img_user/"+ comment.getImg_user())
                .into(holder.img_user);


    }

    @Override
    public int getItemCount() {
        if(comments == null){
            return  0 ;
        }
        return comments.size();
    }
    public class CallViewHolder extends RecyclerView.ViewHolder{
        private TextView txv_name ;
        private TextView txv_createAt ;
        private TextView txv_content ;
        private ImageView img_user ;
        @SuppressLint("WrongViewCast")
        public CallViewHolder(View itemView){
            super(itemView);
            txv_content = itemView.findViewById(R.id.txv_content_comment)  ;
            txv_createAt= itemView.findViewById(R.id.txv_creaetAt_comment) ;
            txv_name    = itemView.findViewById(R.id.txv_nameUser_comment) ;
            img_user    = itemView.findViewById(R.id.img_userComment) ;


        }
    }
}
