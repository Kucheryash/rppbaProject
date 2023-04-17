package DB;

import models.Bills;
import models.Orders;

import java.util.ArrayList;

public class SQLBills extends DBConnect {
    public ArrayList<Bills> getInf() {
        String str = "SELECT id, state, shipped_date, id_client FROM bills";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Bills> infList = new ArrayList<>();
        for (String[] items : result) {
            Bills bills = new Bills();
            bills.setId(Integer.parseInt(items[0]));
            bills.setState(items[1]);
            //bills.setShipped_date(items[2]);
            bills.setId_client(Integer.parseInt(items[3]));
            infList.add(bills);
        }
        return infList;
    }


}
