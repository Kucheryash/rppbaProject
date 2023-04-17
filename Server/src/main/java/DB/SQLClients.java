package DB;

import models.Clients;
import models.Orders;

import java.util.ArrayList;

public class SQLClients extends DBConnect {
    public ArrayList<Clients> getInf() {
        String str = "SELECT name, email, phone FROM clients";
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


}
