package models;

import java.util.Date;

public class TypeCulture {
    private String uid;
    private String type;
    private Date createdAt;

    @Override
    public String toString() {
        return this.type;
    }

    public TypeCulture() {
    }

    public TypeCulture(String uid, String type, Date createdAt) {
        this.uid = uid;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
