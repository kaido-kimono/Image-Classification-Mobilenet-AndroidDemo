package models;

import java.io.Serializable;

public class Plantation implements Serializable {

    private String uid;
    private String nom;
    private double superficie;
    private double longitude;
    private double latitude;

    public Plantation(String uid, String nom, double superficie, double longitude, double latitude) {
        this.uid = uid;
        this.nom = nom;
        this.superficie = superficie;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Plantation() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
