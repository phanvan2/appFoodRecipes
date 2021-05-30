package vku.phungduc.myapplication.model;

public class PostCongthuc {
    private int idUser ;
    private int idDanhmuc ;
    private String ten_monAn ;
    private String moTa ;
    private String nguyenLieu ;
    private String step ;

    public PostCongthuc(int idUser, int idDanhmuc, String ten_monAn, String moTa, String nguyenLieu, String step) {
        this.idUser = idUser;
        this.idDanhmuc = idDanhmuc;
        this.ten_monAn = ten_monAn;
        this.moTa = moTa;
        this.nguyenLieu = nguyenLieu;
        this.step = step;
    }
}
