package vku.phungduc.myapplication.model.congthuc;

import java.util.List;

public class result_congthuc {
    private int status ;
    private String messeage ;
    private List<Congthuc> data ;

    public result_congthuc(int status, String messeage, List<Congthuc> data) {
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

    public List<Congthuc> getData() {
        return data;
    }

    public void setData(List<Congthuc> data) {
        this.data = data;
    }
}
