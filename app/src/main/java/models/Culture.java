package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Culture implements Serializable {

    private String uid;
    private Date dateSemaille;
    private Date dateRecolte;
    private String typeCulture;
    private String uidTypeCulture;
    private String uidPlantation;

    public String getTypeCulture() {
        return typeCulture;
    }

    public void setTypeCulture(String typeCulture) {
        this.typeCulture = typeCulture;
    }

    public String getUidTypeCulture() {
        return uidTypeCulture;
    }

    public void setUidTypeCulture(String uidTypeCulture) {
        this.uidTypeCulture = uidTypeCulture;
    }

    public String getUidPlantation() {
        return uidPlantation;
    }

    public void setUidPlantation(String uidPlantation) {
        this.uidPlantation = uidPlantation;
    }

    public Culture(String uid, Date dateSemaille, Date dateRecolte, String uidTypeCulture) {
        this.uid = uid;
        this.dateSemaille = dateSemaille;
        this.dateRecolte = dateRecolte;
        this.uidTypeCulture = uidTypeCulture;
    }

    public Culture() {
        this.uid = UUID.randomUUID().toString();
    }

    public String getUid() {
        return uid;
    }

    public Date getDateSemaille() {
        return dateSemaille;
    }

    public Date getDateRecolte() {
        return dateRecolte;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDateSemaille(Date dateSemaille) {
        this.dateSemaille = dateSemaille;
    }

    public void setDateRecolte(Date dateRecolte) {
        this.dateRecolte = dateRecolte;
    }
}
