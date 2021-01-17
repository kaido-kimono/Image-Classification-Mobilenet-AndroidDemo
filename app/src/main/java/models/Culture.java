package models;

import java.io.Serializable;
import java.util.Date;

public class Culture implements Serializable {

    private String uid;
    private Date dateSemaille;
    private Date dateRecolte;

    public Culture(String uid, Date dateSemaille, Date dateRecolte) {
        this.uid = uid;
        this.dateSemaille = dateSemaille;
        this.dateRecolte = dateRecolte;
    }

    public Culture() {
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
