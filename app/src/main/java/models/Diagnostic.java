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

    public Diagnostic(String uid, Date createdDate, String uidMaladie, String uidConseil) {
        this.uid = uid;
        this.createdDate = createdDate;
        this.uidMaladie = uidMaladie;
        this.uidConseil = uidConseil;
    }

    public Diagnostic() {
    }

    public String getuid() {
        return uid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getuidMaladie() {
        return uidMaladie;
    }

    public String getuidConseil() {
        return uidConseil;
    }

    public void setuid(String uid) {
        uid = uid;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setuidMaladie(String uidMaladie) {
        uidMaladie = uidMaladie;
    }

    public void setuidConseil(String uidConseil) {
        uidConseil = uidConseil;
    }
}
