package models;

import java.io.Serializable;

public class Plantation implements Serializable {

    private String uid;
    private String nom;
    private double superficie;
    private double localisation;

    public Plantation(String uid, String nom, double superficie, double localisation) {
        this.uid = uid;
        this.nom = nom;
        this.superficie = superficie;
        this.localisation = localisation;
    }

    public Plantation() {
    }

    public double getSuperficie() {
        return  this.superficie;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public double getLocalisation() {
        return localisation;
    }

    public void setLocalisation(double localisation) {
        this.localisation = localisation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
