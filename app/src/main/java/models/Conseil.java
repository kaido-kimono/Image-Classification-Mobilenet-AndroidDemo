package models;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Conseil implements Serializable {

    private String Uid;
    private String UidMaladie;
    private String titre;
    private String descriptionMaladie;
    @ServerTimestamp
    private Date createdDate;

    public Conseil(String uid, String uidMaladie, String titre, String descriptionMaladie, Date createdDate) {
        Uid = uid;
        UidMaladie = uidMaladie;
        this.titre = titre;
        this.descriptionMaladie = descriptionMaladie;
        this.createdDate = createdDate;
    }

    public Conseil() {
    }

    public String getUid() {
        return Uid;
    }

    public String getUidMaladie() {
        return UidMaladie;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescriptionMaladie() {
        return descriptionMaladie;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public void setUidMaladie(String uidMaladie) {
        UidMaladie = uidMaladie;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescriptionMaladie(String descriptionMaladie) {
        this.descriptionMaladie = descriptionMaladie;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
