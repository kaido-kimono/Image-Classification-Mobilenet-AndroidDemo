package models;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    private String uid;
    private String uNom;
    private String uEmail;
    private String photoUrl;

    public Utilisateur(String uid, String uNom, String uEmail) {
        this.uid = uid;
        this.uNom = uNom;
        this.uEmail = uEmail;
    }

    public Utilisateur() {
    }

    public String getuid() {
        return uid;
    }

    public String getuNom() {
        return uNom;
    }


    public String getuEmail() {
        return uEmail;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public void setuNom(String uNom) {
        this.uNom = uNom;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
