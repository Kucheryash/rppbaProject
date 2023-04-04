package DB.old;

import DB.DBConnect;
import models.old.CharterDocs;
import models.old.Documents;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SQLDocuments extends DBConnect {

    //-----------------------------РАБОТА С ДОГОВОРАМИ С КЛИЕНТАМИ---------------------------------------
      public static ArrayList<Documents> getInf1() {
        String str = "SELECT name, worker, company, reg_date, end_date, doc, status FROM kp.doc1";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Documents> docsList = new ArrayList<>();
        for (String[] items: result){
            Documents docs = new Documents();
            docs.setName(items[0]);
            docs.setWorker(items[1]);
            docs.setCompany(items[2]);
            docs.setReg_date(items[3]);
            docs.setEnd_date(items[4]);
            docs.setDoc(items[5]);
            docs.setStatus(items[6]);
            docsList.add(docs);
        }
        return docsList;
    }

    public void addDoc(Documents doc) {
        String add = "INSERT INTO kp.doc1 (name, worker, company, reg_date, end_date, doc, status) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(add);
            prSt.setString(1, doc.getName());
            prSt.setString(2, doc.getWorker());
            prSt.setString(3, doc.getCompany());
            prSt.setString(4, doc.getReg_date());
            prSt.setString(5, doc.getEnd_date());
            prSt.setString(6, doc.getDoc());
            prSt.setString(7, doc.getStatus());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Documents> findDoc1(String name) {
        String str = "SELECT name, worker, company, reg_date, end_date, doc, status  FROM doc1 " +
                " WHERE name = \"" + name + "\";";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Documents> docsList = new ArrayList<>();
        for (String[] items: result){
            Documents docs = new Documents();
            docs.setName(items[0]);
            docs.setWorker(items[1]);
            docs.setCompany(items[2]);
            docs.setReg_date(items[3]);
            docs.setEnd_date(items[4]);
            docs.setDoc(items[5]);
            docs.setStatus(items[6]);
            docsList.add(docs);
        }
        return docsList;
    }

    public static ArrayList<Documents> showDoneDocs(Documents obj) {
        String str = "SELECT name, worker, company, reg_date, end_date, doc, status  FROM doc1 " +
                " where status = '" + obj.getStatus() + "'";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Documents> docsList = new ArrayList<>();
        for (String[] items: result){
            Documents docs = new Documents();
            docs.setName(items[0]);
            docs.setWorker(items[1]);
            docs.setCompany(items[2]);
            docs.setReg_date(items[3]);
            docs.setEnd_date(items[4]);
            docs.setDoc(items[5]);
            docs.setStatus(items[6]);
            docsList.add(docs);
        }
        return docsList;
    }

    public static String resolution(String login, String name){
        String edit1 = "UPDATE doc1 SET worker = ? WHERE name = '" + name + "'";
        try {
            PreparedStatement prSt1 = getDBConnect().prepareStatement(edit1);
            prSt1.setString(1, login);
            prSt1.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static String setNewStatus(String status, String name, String table){
        String edit1 = "UPDATE "+table+" SET status = ? WHERE name = '" + name + "'";
        try {
            PreparedStatement prSt1 = getDBConnect().prepareStatement(edit1);
            prSt1.setString(1, status);
            prSt1.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static boolean deleteDoc(String name, String table) {
        String delete = " DELETE FROM "+table+" WHERE name ='" + name+"'";
        try {
            PreparedStatement preparedStatement = getDBConnect().prepareStatement(delete);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isExistDoc(String name){
        ResultSet res = null;
        boolean check = false;
        String str = "SELECT * from kp.doc1 where name=?";
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


    //----------------------------РАБОТА С УСТАВАМИ-----------------------------
 public static ArrayList<CharterDocs> getInf2() {
        String str = "SELECT name, department, date, document, status FROM kp.doc2";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<CharterDocs> docsList = new ArrayList<>();
        for (String[] items: result){
            CharterDocs docs = new CharterDocs();
            docs.setName(items[0]);
            docs.setDepartment(items[1]);
            docs.setDate(items[2]);
            docs.setDocument(items[3]);
            docs.setStatus(items[4]);
            docsList.add(docs);
        }
        return docsList;
    }

    public void addCh(CharterDocs doc) {
        String add = "INSERT INTO kp.doc2 (name, department, date, document, status) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(add);
            prSt.setString(1, doc.getName());
            prSt.setString(2, doc.getDepartment());
            prSt.setString(3, doc.getDate());
            prSt.setString(4, doc.getDocument());
            prSt.setString(5, doc.getStatus());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<CharterDocs> findDoc2(String name) {
        String str = "SELECT name, department, date, document, status  FROM doc2 " +
                " WHERE name = \"" + name + "\";";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<CharterDocs> docsList = new ArrayList<>();
        for (String[] items: result){
            CharterDocs docs = new CharterDocs();
            docs.setName(items[0]);
            docs.setDepartment(items[1]);
            docs.setDate(items[2]);
            docs.setDocument(items[3]);
            docs.setStatus(items[4]);
            if (docs.getStatus().equals("false")){

            }
            docsList.add(docs);
        }
        return docsList;
    }

    public boolean isExistCh(String name){
        ResultSet res = null;
        boolean check = false;
        String str = "SELECT * from kp.doc2 where name=?";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(str);
            prSt.setString(1, name);
            res = prSt.executeQuery();
            while (res.next()){
                if(name.equals(res.getString("name")))
                    check = true;
                else check = false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

}
