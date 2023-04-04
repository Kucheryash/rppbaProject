package models.old;

import java.io.Serializable;

public class Authorization implements Serializable {
    private String login;
    private String password;
    private int role;

    public Authorization(){}

    public Authorization(String login, String password, int role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Authorization(String login, int role) {
        this.login = login;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "login: '" + login + '\''+
                ", password: '" + password + '\''+
                ", role: " + role +'}';
    }
}
