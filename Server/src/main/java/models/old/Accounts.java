package models.old;

import java.io.Serializable;

public class Accounts implements Serializable {
    private String surname;
    private String name;
    private String login;
    private String department;
    private String position;
    private int num_docs;
    private String name_docs;
    private String password;

    public Accounts(){this.department = ""; this.num_docs = 0; this.name_docs = "";}

    public Accounts(String surname, String name, String login, String department, String position, int num_docs) {
        this.surname = surname;
        this.name = name;
        this.login = login;
        this.department = department;
        this.position = position;
        this.num_docs = num_docs;
    }

    public Accounts(String surname, String name, String login, String department, String position) {
        this.surname = surname;
        this.name = name;
        this.login = login;
        this.department = department;
        this.position = position;
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNum_docs() {
        return num_docs;
    }

    public void setNum_docs(int num_docs) {
        this.num_docs = num_docs;
    }

    public String getName_docs() {
        return name_docs;
    }

    public void setName_docs(String name_docs) {
        this.name_docs = name_docs;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
       return "Worker{" +
               "surname: '" + surname + '\'' +
               ", name: '" + name + '\'' +
               ", login: '" + login + '\'' +
               ", department: '" + department + '\'' +
               ", position: '" + position + '\'' +
               ", num_docs: " + num_docs + '}';
    }
}
