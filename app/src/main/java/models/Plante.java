package models;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Plante implements Serializable {

    private String uid;
    private String urlPhoto;
    @ServerTimestamp
    private Date datePrise;

    public Plante(String uid, String urlPhoto, Date datePrise) {
        this.uid = uid;
        this.urlPhoto = urlPhoto;
        this.datePrise = datePrise;
    }

    public Plante() {
    }

    public String getuid() {
        return uid;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }


    public Date getDatePrise() {
        return datePrise;
    }

    public void setuid(String uid) {
        uid = uid;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public void setDatePrise(Date datePrise) {
        this.datePrise = datePrise;
    }
}
