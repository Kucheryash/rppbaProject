package models.old;

import javafx.scene.control.CheckBox;
import java.io.Serializable;

public class Documents implements Serializable {
    private String name;
    private String worker;
    private String company;
    private String reg_date;
    private String end_date;
    private String doc;
    private String status;
    private CheckBox checkbox;

    public Documents(){}

    public Documents(String name, String worker, String company, String reg_date, String end_date, String doc, String status) {
        this.name = name;
        this.worker = worker;
        this.company = company;
        this.reg_date = reg_date;
        this.end_date = end_date;
        this.doc = doc;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public CheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(CheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Document{" +
                ", name: '" + name + '\'' +
                ", worker: '" + worker + '\'' +
                ", company: '" + company + '\'' +
                ", reg_date: " + reg_date  +
                ", end_date: " + end_date  +
                ", path: " + doc  +
                ", status: '" + status + '\'' + '}';
    }
}
