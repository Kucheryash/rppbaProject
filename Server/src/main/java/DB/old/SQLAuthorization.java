package DB.old;

import DB.DBConnect;
import models.old.Authorization;

import java.sql.*;
import java.util.ArrayList;

public class SQLAuthorization extends DBConnect {
    public int getUser (Authorization user){
        String get = "SELECT * from kp.keys where login = ?";
        ResultSet res = null;
        int role = -1;
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(get);
            prSt.setString(1, user.getLogin());
            res = prSt.executeQuery();
            while (res.next()){
                role = res.getInt(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    public static ArrayList<Authorization> getInfRoles() {
        String get = "SELECT login, password, role from kp.keys";
        ArrayList<String[]> result = DBConnect.getArrayResult(get);
        ArrayList<Authorization> rolesList = new ArrayList<>();
        for (String[] items: result){
            Authorization roles = new Authorization();
            roles.setLogin(items[0]);
            roles.setPassword(items[1]);
            try {
                roles.setRole(Integer.parseInt(items[2]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            rolesList.add(roles);
        }
        return rolesList;
    }

    public String getDepartment (Authorization user){
        String get = "SELECT * from kp.accounts where login = ?";
        ResultSet res = null;
        String  department = null;
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(get);
            prSt.setString(1, user.getLogin());
            res = prSt.executeQuery();
            while (res.next()){
                department = res.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return department;
    }

    public boolean isExist(Authorization user){
        ResultSet res = null;
        boolean check = false;
        String str = "SELECT * from kp.keys where login=? and password=?";
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
}
