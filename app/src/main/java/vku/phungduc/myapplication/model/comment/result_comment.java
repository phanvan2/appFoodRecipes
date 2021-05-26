package vku.phungduc.myapplication.model.comment;

import java.util.List;

import vku.phungduc.myapplication.model.congthuc.Congthuc;

public class result_comment {
    private int status ;
    private String messeage ;
    private List<Comment> data ;

    public result_comment(int status, String messeage, List<Comment> data) {
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

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }
}
