package models;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Maladie implements Serializable {

    private String uid;
    private String nomMaladie;
    private String descriptionMaladie;
    @ServerTimestamp
    private Date createdDate;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNomMaladie() {
        return nomMaladie;
    }

    public void setNomMaladie(String nomMaladie) {
        this.nomMaladie = nomMaladie;
    }

    public String getDescriptionMaladie() {
        return descriptionMaladie;
    }

    public void setDescriptionMaladie(String descriptionMaladie) {
        this.descriptionMaladie = descriptionMaladie;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Maladie(String uid, String nomMaladie, String descriptionMaladie, Date createdDate) {
        this.uid = uid;
        this.nomMaladie = nomMaladie;
        this.descriptionMaladie = descriptionMaladie;
        this.createdDate = createdDate;
    }

    public Maladie() {
    }
}
