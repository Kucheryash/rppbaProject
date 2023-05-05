package DB;

import models.Bills;
import models.Orders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLBills extends DBConnect {
    public ArrayList<Bills> getInf() {
        String str = "SELECT id, cost, payment_meth, state FROM rppba.bills";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Bills> infList = new ArrayList<>();
        for (String[] items : result) {
            Bills bills = new Bills();
            bills.setId(Integer.parseInt(items[0]));
            bills.setCost(Double.parseDouble(items[1]));
            bills.setPayment_meth(items[2]);
            bills.setState(items[3]);
            infList.add(bills);
        }
        return infList;
    }

    public static void editState(int id, String newState) {
        String edit = "UPDATE rppba.bills SET state = ? WHERE id = '" + id + "'";
        try {
            PreparedStatement prSt = getDBConnect().prepareStatement(edit);
            prSt.setString(1, newState);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
