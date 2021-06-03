package vku.phungduc.myapplication;


import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import vku.phungduc.myapplication.model.danhmuc.Danhmuc;
import vku.phungduc.myapplication.model.user.User;

public class constant {

    public static User currentUser = null  ;
    public static boolean checkUser = false  ;
    public static List<Danhmuc> danhmucs = new ArrayList<Danhmuc>();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        constant.currentUser = currentUser;
    }

    public static List<Danhmuc> getDanhmucs() {
        return danhmucs;
    }

    public static void setDanhmucs(List<Danhmuc> danhmucs) {
        constant.danhmucs = danhmucs;
    }

    public static String find_nameDanhmuc(int id){
        for ( Danhmuc danhmuc: danhmucs) {
            if( danhmuc.getId() == id){
                return danhmuc.getTenDanhmuc() ;
            }
        }
      return "" ;
    }
    public static int find_IdDanhmuc(String nameDM){
        for ( Danhmuc danhmuc: danhmucs) {
            if( nameDM.equals(danhmuc.getTenDanhmuc())){
                return danhmuc.getId() ;
            }
        }
        return -1 ;
    }
}
