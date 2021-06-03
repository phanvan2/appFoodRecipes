package vku.phungduc.myapplication.model.congthuc;

public class quanlicongthuc {
    private int image;
    private String name, danhmuc,tenmonan;

    public quanlicongthuc(int image, String name, String danhmuc, String tenmonan) {
        this.image = image;
        this.name = name;
        this.danhmuc = danhmuc;
        this.tenmonan = tenmonan;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(String danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }
}
