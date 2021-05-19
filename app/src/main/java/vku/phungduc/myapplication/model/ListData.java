package vku.phungduc.myapplication.model;

import java.util.List;

import vku.phungduc.myapplication.model.congthuc.Congthuc;
import vku.phungduc.myapplication.model.tintuc.TinTuc;

public class ListData {
    private int type  ;
    private List<Congthuc> congthucList  ;
    private List<TinTuc> tinTucList ;

    public ListData(int type, List<Congthuc> congthucList, List<TinTuc> tinTucList) {
        this.type = type;
        this.congthucList = congthucList;
        this.tinTucList = tinTucList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Congthuc> getCongthucList() {
        return congthucList;
    }

    public void setCongthucList(List<Congthuc> congthucList) {
        this.congthucList = congthucList;
    }

    public List<TinTuc> getTinTucList() {
        return tinTucList;
    }

    public void setTinTucList(List<TinTuc> tinTucList) {
        this.tinTucList = tinTucList;
    }
}
