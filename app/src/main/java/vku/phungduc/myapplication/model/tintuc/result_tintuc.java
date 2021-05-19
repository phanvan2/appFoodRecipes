package vku.phungduc.myapplication.model.tintuc;

import java.util.List;

import vku.phungduc.myapplication.model.congthuc.Congthuc;

public class result_tintuc {
    private int status ;
    private String messeage ;
    private List<TinTuc> data ;

    public result_tintuc(int status, String messeage, List<TinTuc> data) {
        this.status = status;
        this.messeage = messeage;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMesseage() {
        return messeage;
    }

    public void setMesseage(String messeage) {
        this.messeage = messeage;
    }

    public List<TinTuc> getData() {
        return data;
    }

    public void setData(List<TinTuc> data) {
        this.data = data;
    }
}
