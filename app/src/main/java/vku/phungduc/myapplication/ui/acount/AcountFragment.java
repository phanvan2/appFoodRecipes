package vku.phungduc.myapplication.ui.acount;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import vku.phungduc.myapplication.R;
import vku.phungduc.myapplication.activity.InforUserActivity;
import vku.phungduc.myapplication.activity.LoginActivity;
import vku.phungduc.myapplication.activity.MyfoodActivitiy;
import vku.phungduc.myapplication.activity.QuanLyCongThucAcitivity;
import vku.phungduc.myapplication.activity.RegisterActivity;
import vku.phungduc.myapplication.activity.UpdateAcountActivity;
import vku.phungduc.myapplication.model.user.User;

import static vku.phungduc.myapplication.constant.checkUser;
import static vku.phungduc.myapplication.constant.currentUser;

public class AcountFragment extends Fragment {


    private User user = null ;
    TextView txt_username ;
    ImageView img_user ;
    View view ;
    FragmentTransaction transaction ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_acount, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        view.invalidate();
        this.view = view ;
        txt_username = view.findViewById(R.id.txv_username) ;
        img_user = view.findViewById(R.id.img_user);
        load_inForUser(user , view);

        FragmentManager fragmentManager = getParentFragmentManager();
        transaction = fragmentManager.beginTransaction();

        LinearLayout linear_inforUser =  view.findViewById(R.id.linear_userInfor) ;
        linear_inforUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , InforUserActivity.class)  ;
                intent.putExtra("idUser" , Integer.valueOf(currentUser.getId())) ;
                startActivity(intent);
            }
        });
        LinearLayout linear_myfood = view.findViewById(R.id.linear_myfood)  ;
        linear_myfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , QuanLyCongThucAcitivity.class)  ;
              //  intent.putExtra("idUser" , Integer.valueOf(currentUser.getId())) ;
                startActivity(intent);
            }
        });
        LinearLayout linear_login = view.findViewById(R.id.linear_login)  ;
        linear_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_login = new Intent(v.getContext() , LoginActivity.class) ;
                startActivity(intent_login);

            }
        });

        LinearLayout linear_register = view.findViewById(R.id.linear_regis) ;
        linear_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent(v.getContext() , RegisterActivity.class) ;
                startActivity(intent_login);
            }
        });
        Button btn_editUser = view.findViewById(R.id.btn_editAcount) ;
        btn_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentUser != null){
                    Intent intent = new Intent(v.getContext(), UpdateAcountActivity.class) ;
                    startActivity(intent);
                }else {

                }

            }
        });

        Button btn_log_out = view.findViewById(R.id.btn_Logout) ;
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = null ;
                user = null ;
                load_inForUser(user, view) ;
                view.invalidate();


            }
        });

        txt_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentUser != null){// set xem thông tin user 
                    Intent intent = new Intent(v.getContext() , InforUserActivity.class)  ;
                    intent.putExtra("idUser" , Integer.valueOf(currentUser.getId())) ;
                    startActivity(intent);
                }

            }
        });

    }


    public void load_inForUser(User user , View view) {
        if( currentUser != null) {
            user = currentUser ;
            LinearLayout linear_account = view.findViewById(R.id.linear_acount);
            linear_account.setVisibility(View.VISIBLE);

            LinearLayout linear_SignIN = view.findViewById(R.id.linear_SignIn) ;
            linear_SignIN.setVisibility(View.GONE);

            txt_username.setText(user.getTen().toString());

            Picasso.with(view.getContext()).load("https://phungweb.000webhostapp.com/do_an_2/image/img_user/"+ user.getImg_user())
                    .into(img_user);



        }else {
            LinearLayout linear_account = view.findViewById(R.id.linear_acount);
            linear_account.setVisibility(View.GONE);

            LinearLayout linear_SignIN = view.findViewById(R.id.linear_SignIn) ;
            linear_SignIN.setVisibility(View.VISIBLE);
            txt_username.setText("chào bạn");
            img_user.setImageResource(R.drawable.address);
        }
    }


}