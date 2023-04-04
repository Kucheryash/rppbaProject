package DB.old;

import DB.DBConnect;
import models.old.Accounts;
import models.old.Authorization;
import models.old.Heads;

import java.sql.*;
import java.util.ArrayList;


public class SQLAccounts extends DBConnect {
    public ArrayList<Accounts> getInf() {
        String str = "SELECT surname, name, login, department, position, num_docs FROM accounts";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Accounts> infList = new ArrayList<>();
        for (String[] items : result) {
            Accounts acc = new Accounts();
            acc.setSurname(items[0]);
            acc.setName(items[1]);
            acc.setLogin(items[2]);
            acc.setDepartment(items[3]);
            acc.setPosition(items[4]);
            try {
                acc.setNum_docs(Integer.parseInt(items[5]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            infList.add(acc);
        }
        return infList;
    }

    public static ArrayList<Heads> getInfHeads() {
        String str = "SELECT surname, name, login, department, num_workers, num_docs FROM heads";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Heads> headsList = new ArrayList<>();
        for (String[] items : result) {
            Heads heads = new Heads();
            heads.setSurname(items[0]);
            heads.setName(items[1]);
            heads.setLogin(items[2]);
            heads.setDepartment(items[3]);
            try {
                heads.setNum_workers(Integer.parseInt(items[4]));
                heads.setNum_docs(Integer.parseInt(items[5]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            headsList.add(heads);
        }
        return headsList;
    }

    public void addAcc(Accounts acc, Authorization key, Heads head) {
        String add = "INSERT INTO kp.accounts (surname, name, login, department, position, num_docs) VALUES (?,?,?,?,?,?)";
        String add2 = "INSERT INTO kp.keys (login, password, role) VALUES(?,?,?)";
        String add3 = "INSERT INTO kp.account (login, num_docs, name_docs) VALUES(?,?,?)";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(add);
            PreparedStatement prSt2 = getDBConnect().prepareStatement(add2);
            PreparedStatement prSt3 = getDBConnect().prepareStatement(add3);
            prSt2.setString(1, key.getLogin());
            prSt2.setString(2, key.getPassword());
            prSt2.setInt(3, key.getRole());
            prSt2.executeUpdate();
            prSt.setString(1, acc.getSurname());
            prSt.setString(2, acc.getName());
            prSt.setString(3, acc.getLogin());
            prSt.setString(4, acc.getDepartment());
            prSt.setString(5, acc.getPosition());
            prSt.setInt(6, acc.getNum_docs());
            prSt.executeUpdate();
            prSt3.setString(1, acc.getLogin());
            prSt3.setInt(2, acc.getNum_docs());
            prSt3.setString(3, acc.getName_docs());
            prSt3.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (acc.getPosition().equals("Начальник")) {
            int num_workers;
            if (acc.getDepartment().equals("Заключения договоров"))
                num_workers = 15;
            else if (acc.getDepartment().equals("Бухгалтерия"))
                num_workers = 19;
            else num_workers = 16;
            String addHead = "INSERT INTO kp.heads (surname, name, login, department, num_docs, num_workers) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement prSt1 = getDBConnect().prepareStatement(addHead);
                prSt1.setString(1, head.getSurname());
                prSt1.setString(2, head.getName());
                prSt1.setString(3, head.getLogin());
                prSt1.setString(4, head.getDepartment());
                prSt1.setInt(5, head.getNum_docs());
                prSt1.setInt(6, num_workers);
                prSt1.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getNameDocs(String login){
        String str = "SELECT name, worker FROM doc1 where worker =?";
        String name_docs = "";
        ResultSet res = null;
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(str);
            prSt.setString(1, login);
            res = prSt.executeQuery();
            while (res.next()){
                name_docs += res.getString(1) + ", ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return name_docs;
    }

    public ArrayList<Accounts> showAcc(String login, String name_docs) {
        String insert = "UPDATE kp.account SET name_docs=? where login = '" + login + "'";
        try {
            PreparedStatement prSt1 = getDBConnect().prepareStatement(insert);
            prSt1.setString(1, name_docs);
            prSt1.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String str = "SELECT login, num_docs, name_docs FROM account " +
                "WHERE login = \"" + login + "\"";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Accounts> infList = new ArrayList<>();
        for (String[] items : result) {
            Accounts acc = new Accounts();
            acc.setLogin(items[0]);
            try {
                acc.setNum_docs(Integer.parseInt(items[1]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            acc.setName_docs(items[2]);
            infList.add(acc);
        }
        return infList;
    }

    public static ArrayList<Accounts> findAcc(String surname) {
        String str = "SELECT surname, name, login, department, position, num_docs FROM accounts" +
                " WHERE surname = '" + surname + "'";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Accounts> accList = new ArrayList<>();
        for (String[] items : result) {
            Accounts acc = new Accounts();
            acc.setSurname(items[0]);
            acc.setName(items[1]);
            acc.setLogin(items[2]);
            acc.setDepartment(items[3]);
            acc.setPosition(items[4]);
            try {
                acc.setNum_docs(Integer.parseInt(items[5]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            accList.add(acc);
        }
        return accList;
    }

    public static ArrayList<Accounts> findDepartment(String department) {
        String str = "SELECT surname, name, login, department, position, num_docs FROM accounts" +
                " WHERE department = '" + department+ "'";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Accounts> accList = new ArrayList<>();
        for (String[] items : result) {
            Accounts acc = new Accounts();
            acc.setSurname(items[0]);
            acc.setName(items[1]);
            acc.setLogin(items[2]);
            acc.setDepartment(items[3]);
            acc.setPosition(items[4]);
            try {
                acc.setNum_docs(Integer.parseInt(items[5]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            accList.add(acc);
        }
        return accList;
    }

    public static ArrayList<Heads> findDepartHead(String department) {
        String str = "SELECT surname, name, login, department, num_workers, num_docs FROM heads" +
                " WHERE department = \"" + department + "\"";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Heads> accList = new ArrayList<>();
        for (String[] items : result) {
            Heads acc = new Heads();
            acc.setSurname(items[0]);
            acc.setName(items[1]);
            acc.setLogin(items[2]);
            acc.setDepartment(items[3]);
            try {
                acc.setNum_docs(Integer.parseInt(items[5]));
            } catch (NumberFormatException e) {
                System.out.println("null");
            }
            accList.add(acc);
        }
        return accList;
    }

    public static String editAcc(Accounts user, Authorization keys) {
        String edit1 = "UPDATE kp.accounts SET surname = ?, name = ?, department = ?, position = ? WHERE login = '" + user.getLogin() + "'";
        String edit2 = "UPDATE kp.keys SET role = ? WHERE login = '" + user.getLogin() + "'";
        String edit3 = "UPDATE kp.heads SET department = ? WHERE login = '" + user.getLogin() + "'";
        try {
            PreparedStatement prSt1 = getDBConnect().prepareStatement(edit1);
            PreparedStatement prSt2 = getDBConnect().prepareStatement(edit2);
            PreparedStatement prSt3 = getDBConnect().prepareStatement(edit3);
            prSt1.setString(1, user.getSurname());
            prSt1.setString(2, user.getName());
            prSt1.setString(3, user.getDepartment());
            prSt1.setString(4, user.getPosition());
            prSt1.execute();
            prSt2.setInt(1, keys.getRole());
            prSt2.execute();
            prSt3.setString(1, user.getDepartment());
            prSt3.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (keys.getRole() != 1){
            String del = "DELETE FROM kp.heads WHERE login = '" + user.getLogin() + "'";
            try {
                PreparedStatement prSt = getDBConnect().prepareStatement(del);
                prSt.execute();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else{
            int num_workers;
            if (user.getDepartment().equals("Заключения договоров"))
                num_workers = 15;
            else if (user.getDepartment().equals("Бухгалтерия"))
                num_workers = 19;
            else num_workers = 16;
            String addHead = "INSERT INTO kp.heads (surname, name, login, department, num_docs, num_workers) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement prSt1 = getDBConnect().prepareStatement(addHead);
                prSt1.setString(1, user.getSurname());
                prSt1.setString(2, user.getName());
                prSt1.setString(3,user.getLogin());
                prSt1.setString(4, user.getDepartment());
                prSt1.setInt(5, user.getNum_docs());
                prSt1.setInt(6, num_workers);
                prSt1.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    public static String editLogin(String oldLogin, String newlogin) {
        String edit = "UPDATE kp.keys SET login = ? WHERE login = '" + oldLogin + "'";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setString(1, newlogin);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return newlogin;
    }

    public static String editPass(String login, String oldPass, String newPass) {
        String edit = "UPDATE kp.keys SET password = ? WHERE password = '" + oldPass + "' and login = '"+login+"'";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setString(1, newPass);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return newPass;
    }

    public static boolean deleteAcc(String login) {
        String deleteH = " DELETE FROM kp.heads WHERE login = '" + login + "'";
        String delete = " DELETE FROM kp.keys WHERE login = '" + login + "'";
        try {
            PreparedStatement prStH = getDBConnect().prepareStatement(deleteH);
            prStH.execute();
            PreparedStatement prSt = getDBConnect().prepareStatement(delete);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isExist(String login) {
        ResultSet res = null;
        boolean check = false;
        String user = "SELECT * from kp.keys where login=?";
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

    public boolean isExistSurn(String surname) {
        ResultSet res = null;
        boolean check = false;
        String user = "SELECT * from kp.accounts where surname=?";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(user);
            prSt.setString(1, surname);
            res = prSt.executeQuery();
            while (res.next()) {
                if (surname.equals(res.getString("surname")))
                    check = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    public ArrayList<Accounts> statistic() {
        String str = "select login, department, num_docs from accounts";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Accounts> accList = new ArrayList<>();
        for (String[] items: result){
            Accounts acc = new Accounts();
            acc.setLogin(items[0]);
            acc.setDepartment(items[1]);
            acc.setNum_docs(Integer.parseInt(items[2]));
            accList.add(acc);
        }
        return accList;
    }

}
