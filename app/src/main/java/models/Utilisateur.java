package models;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    private String uid;
    private String uNom;
    private String uPrenom;
    private String uEmail;

    public Utilisateur(String uid, String uNom, String uPrenom, String uEmail) {
        this.uid = uid;
        this.uNom = uNom;
        this.uPrenom = uPrenom;
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

    public String getuPrenom() {
        return uPrenom;
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

    public void setuPrenom(String uPrenom) {
        this.uPrenom = uPrenom;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}
