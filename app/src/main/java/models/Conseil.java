package models;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Conseil implements Serializable {

    private String uid;
    private String uidMaladie;
    private String titre;
    private String descriptionMaladie;
    @ServerTimestamp
    private Date createdDate;

    public Conseil(String uid, String uidMaladie, String titre, String descriptionMaladie, Date createdDate) {
        this.uid = uid;
        this.uidMaladie = uidMaladie;
        this.titre = titre;
        this.descriptionMaladie = descriptionMaladie;
        this.createdDate = createdDate;
    }

    public Conseil() {
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidMaladie() {
        return uidMaladie;
    }

    public void setUidMaladie(String uidMaladie) {
        this.uidMaladie = uidMaladie;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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
}
