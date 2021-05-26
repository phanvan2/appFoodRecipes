package vku.phungduc.myapplication.model.comment;

public class Re_comment {
    private int id ;
    private int idBinh_luan  ;
    private int idUser ;
    private String tenUser ;
    private String img_user ;
    private String noiDung ;
    private String ngay ;

    public Re_comment(int id, int idBinh_luan, int idUser, String tenUser, String img_user, String noiDung, String ngay) {
        this.id = id;
        this.idBinh_luan = idBinh_luan;
        this.idUser = idUser;
        this.tenUser = tenUser;
        this.img_user = img_user;
        this.noiDung = noiDung;
        this.ngay = ngay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBinh_luan() {
        return idBinh_luan;
    }

    public void setIdBinh_luan(int idBinh_luan) {
        this.idBinh_luan = idBinh_luan;
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
}
