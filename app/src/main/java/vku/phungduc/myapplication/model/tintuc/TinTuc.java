package vku.phungduc.myapplication.model.tintuc;

public class TinTuc {
    private int id  ;
    private String ten_tin_tuc ;
    private String tac_gia ;
    private String nguon ;
    private String noi_dung ;
    private String nguoi_dang ;
    private String ngay_dang ;

    public TinTuc(int id, String ten_tin_tuc, String tac_gia, String nguon, String noi_dung, String nguoi_dang, String ngay_dang) {
        this.id = id;
        this.ten_tin_tuc = ten_tin_tuc;
        this.tac_gia = tac_gia;
        this.nguon = nguon;
        this.noi_dung = noi_dung;
        this.nguoi_dang = nguoi_dang;
        this.ngay_dang = ngay_dang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_tin_tuc() {
        return ten_tin_tuc;
    }

    public void setTen_tin_tuc(String ten_tin_tuc) {
        this.ten_tin_tuc = ten_tin_tuc;
    }

    public String getTac_gia() {
        return tac_gia;
    }

    public void setTac_gia(String tac_gia) {
        this.tac_gia = tac_gia;
    }

    public String getNguon() {
        return nguon;
    }

    public void setNguon(String nguon) {
        this.nguon = nguon;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }

    public String getNguoi_dang() {
        return nguoi_dang;
    }

    public void setNguoi_dang(String nguoi_dang) {
        this.nguoi_dang = nguoi_dang;
    }

    public String getNgay_dang() {
        return ngay_dang;
    }

    public void setNgay_dang(String ngay_dang) {
        this.ngay_dang = ngay_dang;
    }
}
