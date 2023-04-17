package DB;

import models.Orders;
import models.Products;

import java.util.ArrayList;

public class SQLProducts extends DBConnect {
    public ArrayList<Products> getInf() {
        String str = "SELECT id, current_num, id_item FROM products";
        ArrayList<String[]> result = DBConnect.getArrayResult(str);
        ArrayList<Products> infList = new ArrayList<>();
        for (String[] items : result) {
            Products products = new Products();
            products.setId(Integer.parseInt(items[0]));
            products.setCurrent_num(Integer.parseInt(items[1]));
            products.setId_item(Integer.parseInt(items[2]));
            infList.add(products);
        }
        return infList;
    }


}
