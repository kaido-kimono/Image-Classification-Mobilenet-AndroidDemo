package models;

import java.io.Serializable;

public class Plantation implements Serializable {

    private String uid;
    private String nom;
    private float superficie;
    private double localisation;

    public Plantation(String uid, String nom, float superficie, double localisation) {
        this.uid = uid;
        this.nom = nom;
        this.superficie = superficie;
        this.localisation = localisation;
    }

    public Plantation() {
    }

    public String getuid() {
        return uid;
    }

    public float getSuperficie() {
        return  this.superficie;
    }

    public double getLocalisation() {
        return localisation;
    }

    public void setuid(String uid) {
        uid = uid;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
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
