package DB;

import models.Orders;
import models.Products;
import models.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLProducts extends DBConnect {
    public ArrayList<Products> getInf() {
        String str = "SELECT id, id_item, current_num FROM rppba.products";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Products> infList = new ArrayList<>();
        for (String[] items : result) {
            Products products = new Products();
            products.setId(Integer.parseInt(items[0]));
            String name = getNameItem(Integer.parseInt(items[1]));
            products.setName(name);
            products.setCurrent_num(Integer.parseInt(items[2]));
            infList.add(products);
        }
        return infList;
    }

    public String getNameItem(int id) {
        String str = "SELECT name FROM rppba.items WHERE id ="+id;
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        String email = "";
        for (String[] items : result) {
            email = items[0];
        }
        return email;

    }

    public void editProduct(int id, int made_yesterday) {
        String edit = "UPDATE rppba.products SET made_all_time = ? WHERE id = " + id;
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setInt(1, made_yesterday);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExist(String name){
        ResultSet res = null;
        boolean check = false;
        String item = "SELECT * from rppba.items where name=?";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(item);
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

//    public ArrayList<Products> findProdByItemName(String name) {
//        String str = "SELECT id, id_item, current_num FROM rppba.products WHERE  =\""+state+"\"";
//        ArrayList<String[]> result = DBConnect.getArrayResult(str);
//        ArrayList<Products> infList = new ArrayList<>();
//        for (String[] items : result) {
//            Products orders = new Products();
//            orders.setId(Integer.parseInt(items[0]));
//            orders.setState(items[1]);
//            String login = getManagerLogin(Integer.parseInt(items[2]));
//            orders.setManager_login(login);
//            infList.add(orders);
//        }
//        return infList;
//    }
}
