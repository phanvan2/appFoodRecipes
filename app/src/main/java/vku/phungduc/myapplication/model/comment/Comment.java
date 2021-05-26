package vku.phungduc.myapplication.model.comment;

import java.util.List;

public class Comment {
    private int id ;
    private int idUser ;
    private String tenUser ;
    private String img_user ;
    private String idMon_an ;
    private String noiDung ;
    private String ngay ;
    private List<Re_comment> re_comments ;

    public Comment(int id, int idUser, String tenUser, String img_user, String idMon_an, String noiDung, String ngay, List<Re_comment> re_comments) {
        this.id = id;
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.img_user = img_user;
        this.idMon_an = idMon_an;
        this.noiDung = noiDung;
        this.ngay = ngay;
        this.re_comments = re_comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getImg_user() {
        return img_user;
    }

    public void setImg_user(String img_user) {
        this.img_user = img_user;
    }

    public String getIdMon_an() {
        return idMon_an;
    }

    public void setIdMon_an(String idMon_an) {
        this.idMon_an = idMon_an;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public List<Re_comment> getRe_comments() {
        return re_comments;
    }

    public void setRe_comments(List<Re_comment> re_comments) {
        this.re_comments = re_comments;
    }
}
