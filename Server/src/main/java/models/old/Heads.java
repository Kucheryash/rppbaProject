package models.old;

import java.io.Serializable;

public class Heads implements Serializable {
    private String surname;
    private String name;
    private String login;
    private String department;
    private int num_docs;
    private int num_workers;

    public Heads(String surname, String name, String login, String department, int num_docs, int num_workers) {
        this.surname = surname;
        this.name = name;
        this.login = login;
        this.department = department;
        this.num_docs = num_docs;
        this.num_workers = num_workers;
    }

    public Heads(){this.department = ""; this.num_docs = 0; this.num_workers = 0;}

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getNum_docs() {
        return num_docs;
    }

    public void setNum_docs(int num_docs) {
        this.num_docs = num_docs;
    }

    public int getNum_workers() {
        return num_workers;
    }

    public void setNum_workers(int num_workers) {
        this.num_workers = num_workers;
    }


    @Override
    public String toString() {
        return "Head of department{" +
                ", surname: '" + surname + '\'' +
                ", name: '" + name + '\'' +
                ", login: '" + login + '\'' +
                ", department: '" + department + '\'' +
                ", num_docs: " + num_docs +
                ", num_workers: " + num_workers  + '}';
    }
}
