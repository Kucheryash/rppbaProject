package DB;

import models.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLUsers extends DBConnect {
    public int getUser (Users user){
        String get = "SELECT * from rppba.users where login = ?";
        ResultSet res = null;
        int role = -1;
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(get);
            prSt.setString(1, user.getLogin());
            res = prSt.executeQuery();
            while (res.next()){
                role = res.getInt(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    public int getId (Users user){
        String get = "SELECT * from rppba.users where login = ?";
        ResultSet res = null;
        int id = -1;
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(get);
            prSt.setString(1, user.getLogin());
            res = prSt.executeQuery();
            while (res.next()){
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void registration(Users user) {
        String add = "INSERT INTO rppba.users (login, password, role) VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(add);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setInt(3, user.getRole());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExist(Users user){
        ResultSet res = null;
        boolean check = false;
        String str = "SELECT * from rppba.users where login=? and password=?";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(str);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            res = prSt.executeQuery();
            while (res.next()){
                if(user.getLogin().equals(res.getString("login")))
                    if (user.getPassword().equals(res.getString("password")))
                        check = true;
                    else check = false;
                else check = false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }


    public boolean isLoginExist(String login) {
        ResultSet res = null;
        boolean check = false;
        String user = "SELECT * from rppba.users where login=?";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(user);
            prSt.setString(1, login);
            res = prSt.executeQuery();
            while (res.next()) {
                if (login.equals(res.getString("login")))
                    check = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }
}
