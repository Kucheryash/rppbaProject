package DB;

import models.Clients;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLClients extends DBConnect {
    public ArrayList<Clients> getInf() {
        String str = "SELECT name, email, phone FROM rppba.clients";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Clients> infList = new ArrayList<>();
        for (String[] items : result) {
            Clients clients = new Clients();
            clients.setName(items[0]);
            clients.setEmail(items[1]);
            clients.setPhone(items[2]);
            infList.add(clients);
        }
        return infList;
    }

    public ArrayList<Clients> getNameInf() {
        String str = "SELECT id, name FROM rppba.clients";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Clients> infList = new ArrayList<>();
        for (String[] items : result) {
            Clients clients = new Clients();
            clients.setId(Integer.parseInt(items[0]));
            clients.setName(items[1]);
            infList.add(clients);
        }
        return infList;
    }

    public boolean isExist(String name) {
        ResultSet res = null;
        boolean check = false;
        String client = "SELECT * from rppba.clients where name=?";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(client);
            prSt.setString(1, name);
            res = prSt.executeQuery();
            while (res.next()) {
                if (name.equals(res.getString("name")))
                    check = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    public static ArrayList<Clients> findClient(String name) {
        String str = "SELECT name, email, phone FROM rppba.clients" +
                " WHERE name = '" + name + "'";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Clients> clientsList = new ArrayList<>();
        for (String[] items : result) {
            Clients client = new Clients();
            client.setName(items[0]);
            client.setEmail(items[1]);
            client.setPhone(items[2]);
            clientsList.add(client);
        }
        return clientsList;
    }

    public static String editClient(String oldName, String name, String email, String phone) {
        String edit = "UPDATE rppba.clients SET name = ?, email = ?, phone = ? WHERE name = '" + oldName + "'";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setString(1, name);
            prSt.setString(2, email);
            prSt.setString(3, phone);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public boolean isExistClient(String name){
        ResultSet res = null;
        boolean check = false;
        String str = "SELECT * from rppba.clients where name=?";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(str);
            prSt.setString(1, name);
            res = prSt.executeQuery();
            while (res.next()){
                if(name.equals(res.getString("name")))
                    check = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    public void addClient(String name, String email, String phone) {
        String add = "INSERT INTO rppba.clients (name, email, phone) VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(add);
            prSt.setString(1, name);
            prSt.setString(2, email);
            prSt.setString(3, phone);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
