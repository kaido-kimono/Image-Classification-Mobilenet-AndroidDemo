package models;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Diagnostic implements Serializable {

    private String uid;
    @ServerTimestamp
    private Date createdDate;
    private String uidMaladie;
    private String uidConseil;
    private String urlImage;

    public Diagnostic() {
    }

    public Diagnostic(String uid, Date createdDate, String uidMaladie, String uidConseil, String urlImage) {
        this.uid = uid;
        this.createdDate = createdDate;
        this.uidMaladie = uidMaladie;
        this.uidConseil = uidConseil;
        this.urlImage = urlImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUidMaladie() {
        return uidMaladie;
    }

    public void setUidMaladie(String uidMaladie) {
        this.uidMaladie = uidMaladie;
    }

    public String getUidConseil() {
        return uidConseil;
    }

    public void setUidConseil(String uidConseil) {
        this.uidConseil = uidConseil;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
