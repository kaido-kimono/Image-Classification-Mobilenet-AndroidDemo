package models;

import java.io.Serializable;

public class Plantation implements Serializable {

    private String uid;
    private float superficie;
    private double localisation;

    public Plantation(String uid, float superficie, double localisation) {
        this.uid = uid;
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
}
