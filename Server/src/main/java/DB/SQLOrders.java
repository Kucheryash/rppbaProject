package DB;

import models.OrderContent;
import models.Orders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLOrders extends DBConnect {
    public ArrayList<Orders> getInf() {
        String str = "SELECT id, id_client, state, shipped_date FROM rppba.orders";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Orders> infList = new ArrayList<>();
        for (String[] items : result) {
            Orders orders = new Orders();
            orders.setId(Integer.parseInt(items[0]));
            String email = getClientEmail(Integer.parseInt(items[1]));
            orders.setState(items[2]);
            orders.setShipped_date(items[3]);
            orders.setClient_email(email);
            infList.add(orders);
        }
        return infList;
    }

    public ArrayList<Orders> getInfForHead() {
        String str = "SELECT id, state, manager FROM rppba.orders";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Orders> infList = new ArrayList<>();
        for (String[] items : result) {
            Orders orders = new Orders();
            orders.setId(Integer.parseInt(items[0]));
            orders.setState(items[1]);
            String login = getManagerLogin(Integer.parseInt(items[2]));
            orders.setManager_login(login);
            infList.add(orders);
        }
        return infList;
    }

    public ArrayList<OrderContent> getContent(int id) {
        String str = "SELECT id, id_item, number FROM rppba.order_content WHERE id_order="+id;
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<OrderContent> infList = new ArrayList<>();
        for (String[] items : result) {
            OrderContent content = new OrderContent();
            content.setId(Integer.parseInt(items[0]));
            String item_name = getItemName(Integer.parseInt(items[1]));
            content.setItem_name(item_name);
            content.setNumber(Integer.parseInt(items[2]));
            infList.add(content);
        }
        return infList;
    }

    private String getManagerLogin(int id) {
        String str = "SELECT login FROM rppba.users WHERE id ="+id;
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        String login = "";
        for (String[] items : result) {
            login = items[0];
        }
        return login;
    }

    public String getClientEmail(int id) {
        String str = "SELECT email FROM rppba.clients WHERE id ="+id;
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        String email = "";
        for (String[] items : result) {
            email = items[0];
        }
        return email;
    }

    public String getItemName(int id) {
        String str = "SELECT name FROM rppba.items WHERE id ="+id;
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        String name = "";
        for (String[] items : result) {
            name = items[0];
        }
        return name;
    }

    public void addOrder(Orders order) {
        String add = "INSERT INTO rppba.orders (id_client, state, manager, delivery_date, transp_time) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(add);
            prSt.setInt(1, order.getId_client());
            prSt.setString(2, order.getState());
            prSt.setInt(3, order.getManager());
            prSt.setString(4, order.getDelivery_date());
            prSt.setInt(5, order.getTransp_time());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editOrder(Orders order) {
        String edit = "UPDATE rppba.orders SET delivery_date = ?, transp_time = ? WHERE id = '" + order.getId() + "'";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setString(1, order.getDelivery_date());
            prSt.setInt(2, order.getTransp_time());
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Orders> findOrdersByState(String state){
        String str = "SELECT id, state, manager FROM rppba.orders WHERE state =\""+state+"\"";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Orders> infList = new ArrayList<>();
        for (String[] items : result) {
            Orders orders = new Orders();
            orders.setId(Integer.parseInt(items[0]));
            orders.setState(items[1]);
            String login = getManagerLogin(Integer.parseInt(items[2]));
            orders.setManager_login(login);
            infList.add(orders);
        }
        return infList;
    }

    public static void editState(int id, String newState) {
        String edit = "UPDATE rppba.orders SET state = ? WHERE id = '" + id + "'";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setString(1, newState);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrderContent(String bar_code, Integer number, Integer id_order) {
        int id_item = findByBarCode(bar_code);
        String add = "INSERT INTO rppba.order_content (id_order, id_item, number) VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(add);
            prSt.setInt(1, id_order);
            prSt.setInt(2, id_item);
            prSt.setInt(3, number);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int findByBarCode(String bar_code) {
        String str = "SELECT id FROM rppba.items WHERE bar_code ='"+bar_code+"'";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        int id = 0;
        for (String[] items : result) {
            id = Integer.parseInt(items[0]);
        }
        return id;
    }

    public void newOrderIdInContent(int new_id) {
        String edit = "UPDATE rppba.order_content SET id_order = ? WHERE id_order = '" + 1 + "'";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setInt(1, new_id);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int findClientByOrderId(int id_order) {
        String str = "SELECT id_client FROM rppba.orders WHERE id ="+id_order;
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        int id = 0;
        for (String[] items : result) {
            id = Integer.parseInt(items[0]);
        }
        return id;
    }

    public static boolean deleteContent(int id) {
        String delete = "DELETE FROM rppba.order_content WHERE id =" + id;
        try {
            PreparedStatement preparedStatement = getDBConnect().prepareStatement(delete);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
