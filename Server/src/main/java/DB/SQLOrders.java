package DB;

import models.Orders;

import java.util.ArrayList;

public class SQLOrders extends DBConnect {
    public ArrayList<Orders> getInf() {
        String str = "SELECT id, state, shipped_date, id_client FROM orders";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Orders> infList = new ArrayList<>();
        for (String[] items : result) {
            Orders orders = new Orders();
            orders.setId(Integer.parseInt(items[0]));
            orders.setState(items[1]);
            orders.setShipped_date(items[2]);
            orders.setId_client(Integer.parseInt(items[3]));
            infList.add(orders);
        }
        return infList;
    }


}
