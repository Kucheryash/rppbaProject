package models.old;

import java.awt.*;
import java.io.Serializable;

public class CharterDocs implements Serializable {
    private String id_user;
    private String name;
    private String department;
    private String date;
    private String document;
    private String status;
    private Checkbox checkbox;

    public CharterDocs(){}

    public CharterDocs(String name, String department, String date, String document, String status, String id_user) {
        this.name = name;
        this.department = department;
        this.date = date;
        this.document = document;
        this.status = status;
        this.id_user = id_user;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Checkbox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Checkbox checkbox) {
        this.checkbox = checkbox;
    }

    @Override
    public String toString() {
        return "Document{" +
                ", name: '" + name + '\'' +
                ", department: '" + department + '\'' +
                ", date: " + date  +
                ", doc: " + document  +
                ", status: '" + status + '\'' + '}';
    }
}
