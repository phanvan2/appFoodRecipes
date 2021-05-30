package vku.phungduc.myapplication.model.user;

public class UserUpdate {
    private String id ;
    private String ten ;
    private String email ;
    private String about ;

    public UserUpdate(String id, String ten, String email, String about) {
        this.id = id;
        this.ten = ten;
        this.email = email;
        this.about = about;
    }
}
