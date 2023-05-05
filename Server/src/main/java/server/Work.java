package server;


import DB.*;
import models.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Work implements Runnable{
    protected Socket client = null;
    ObjectInputStream input;
    ObjectOutputStream output;
    public Work(Socket client){this.client = client;}

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
            while (true) {
                System.out.println("\nПолучение команды от клиента...");
                String choice = input.readObject().toString();
                System.out.println("Получена команда: '"+choice+"'.");
                switch (choice){
        //----------------------------МЕНЮ МЕНЕДЖЕРА-------------------------------
                //--------------------ПРОСМОТР ДАННЫХ-----------------------
                    case "ordersInf" ->{
                        System.out.println("Запрос к БД на получение информации о заказах.");
                        SQLOrders orders = new SQLOrders();
                        ArrayList<Orders> ordersList = orders.getInf();
                        output.writeObject(ordersList);
                    }
                    case "billsInf" ->{
                        System.out.println("Запрос к БД на получение информации о чеках.");
                        SQLBills bills = new SQLBills();
                        ArrayList<Bills> billsList = bills.getInf();
                        output.writeObject(billsList);
                    }
                    case "clientsInf" ->{
                        System.out.println("Запрос к БД на получение информации о клиентах.");
                        SQLClients clients = new SQLClients();
                        ArrayList<Clients> clientsList = clients.getInf();
                        output.writeObject(clientsList);
                    }
                    case "productsInf" ->{
                        System.out.println("Запрос к БД на получение информации о продуктах.");
                        SQLProducts products = new SQLProducts();
                        ArrayList<Products> productsList = products.getInf();
                        output.writeObject(productsList);
                    }
                    case "clientsNameInf" ->{
                        System.out.println("Запрос к БД на получение информации о клиентах.");
                        SQLClients clients = new SQLClients();
                        ArrayList<Clients> clientsList = clients.getNameInf();
                        output.writeObject(clientsList);
                    }
                    case "orderContentInf" ->{
                        System.out.println("Запрос к БД на получение информации о содержимого заказа.");
                        SQLOrders orders = new SQLOrders();
                        int id_order = (int) input.readObject();
                        ArrayList<OrderContent> contentList = orders.getContent(id_order);
                        output.writeObject(contentList);
                        int id = (int) input.readObject();
                        if (id!=0) {
                            int id_client = orders.findClientByOrderId(id);
                            output.writeObject(id_client);
                        }
                    }
                //--------------------ВЫБОРКА ДАННЫХ-----------------------
                    case "findClient" ->{
                        System.out.println("Запрос к БД на поиск клиента.");
                        SQLClients clients = new SQLClients();
                        String name = (String) input.readObject();
                        boolean exist = clients.isExist(name);
                        if (exist == true){
                            output.writeObject("OK");
                            System.out.println("Найден пользователь с фамилией '"+name+"'.");
                            ArrayList<Clients> accList = clients.findClient(name);
                            output.writeObject(accList);
                        } else output.writeObject("NotExist");
                    }
                    case "addClient" ->{
                        System.out.println("Запрос к БД на добавление клиента.");
                        SQLClients clients = new SQLClients();
                        String name = (String) input.readObject();
                        String email = (String) input.readObject();
                        String phone = (String) input.readObject();
                        boolean exist = clients.isExistClient(name);
                        if (exist == false) {
                            output.writeObject("OK");
                            clients.addClient(name, email, phone);
                            System.out.println(client.toString());
                        } else output.writeObject("Exist");
                    }
                    case "addOrderContent" ->{
                        System.out.println("Запрос к БД на добавление содержимого заказа.");
                        SQLOrders orderContent = new SQLOrders();
                        int id_order = (int) input.readObject();
                        String bar_code = (String) input.readObject();
                        Integer number = (Integer) input.readObject();
                        orderContent.addOrderContent(bar_code, number, id_order);
                        System.out.println("Содержимое заказа успешно добавлено.");
                    }
                    case "addOrder" ->{
                        System.out.println("Запрос к БД на добавление заказа.");
                        SQLOrders orders = new SQLOrders();
                        Orders order = (Orders) input.readObject();
                        orders.addOrder(order);
                        System.out.println("Заказ успешно добавлен.");
                        ArrayList<Orders> ordersList = orders.getInf();
                        order = ordersList.get(ordersList.size()-1);
                        orders.newOrderIdInContent(order.getId());
                    }
                //--------------------ИЗМЕНЕНИЕ ДАННЫХ-----------------------
                    case "editBillState" ->{
                        SQLBills bills = new SQLBills();
                        int id = (Integer) input.readObject();
                        String newState = (String) input.readObject();
                        System.out.println("Запрос к БД на изменение статуса чека.");
                        bills.editState(id, newState);
                        System.out.println("Статус чека изменён на '" + newState + "'.");
                    }
                    case "editClient" ->{
                        System.out.println("Запрос к БД на редактирование информации о клиенте.");
                        SQLClients clients = new SQLClients();
                        String oldName = (String) input.readObject();
                        String name = (String) input.readObject();
                        String email = (String) input.readObject();
                        String phone = (String) input.readObject();
                        clients.editClient(oldName, name, email, phone);
                        System.out.println("Данные о клиенте изменены.");
                    }
                    case "editOrder" ->{
                        System.out.println("Запрос к БД на изменение заказа.");
                        SQLOrders orders = new SQLOrders();
                        Orders order = (Orders) input.readObject();
                        orders.editOrder(order);
                        System.out.println("Заказ успешно обновлён.");
                    }
                    case "deleteContent" ->{
                        System.out.println("Запрос на удаление содержимого заказа.");
                        SQLOrders orderContent = new SQLOrders();
                        int id = (int) input.readObject();
                        orderContent.deleteContent(id);
                        System.out.println("Удаление прошло успешно.");
                    }
                    case "editProduct" ->{
                        System.out.println("Запрос к БД на изменение продукции.");
                        SQLProducts products = new SQLProducts();
                        int id = (int) input.readObject();
                        int made_yesterday = (int) input.readObject();
                        products.editProduct(id, made_yesterday);
                        System.out.println("Заказ успешно обновлён.");
                    }
//                    case "findProduct" ->{
//                        System.out.println("Запрос к БД на поиск продукции.");
//                        SQLProducts products = new SQLProducts();
//                        String name = (String) input.readObject();
//                        boolean exist = products.isExist(name);
//                        if (exist == true){
//                            output.writeObject("OK");
//                            ArrayList<Products> prodList = products.findProdByItemName(name);
//                            output.writeObject(prodList);
//                        } else output.writeObject("NotExist");
//                    }

        //---------------------------МЕНЮ РУКОВОДИТЕЛЯ------------------------------
                //--------------------ПРОСМОТР ДАННЫХ-----------------------
                    case "showOrders" ->{
                        System.out.println("Запрос к БД на получение информации о заказах для руководителя.");
                        SQLOrders orders = new SQLOrders();
                        ArrayList<Orders> ordersList = orders.getInfForHead();
                        output.writeObject(ordersList);
                    }
                    case "showContent" ->{
                        System.out.println("Запрос к БД на получение информации о содержимого заказа.");
                        int id_order = (Integer) input.readObject();
                        SQLOrders orders = new SQLOrders();
                        ArrayList<OrderContent> contentList = orders.getContent(id_order);
                        output.writeObject(contentList);
                    }
                //--------------------ВЫБОРКА ДАННЫХ-----------------------
                    case "chooseOrderState" ->{
                        System.out.println("Запрос к БД на выбор заказов по статусу.");
                        SQLOrders orders = new SQLOrders();
                        String state = (String) input.readObject();
                        System.out.println("Показаны заказы со статусом '"+state+"'.");
                        ArrayList<Orders> ordersList = orders.findOrdersByState(state);
                        output.writeObject(ordersList);
                    }
                //--------------------ИЗМЕНЕНИЕ ДАННЫХ-----------------------
                    case "editOrderState" ->{
                        SQLOrders order = new SQLOrders();
                        int id = (Integer) input.readObject();
                        String newState = (String) input.readObject();
                        System.out.println("Запрос к БД на изменение статуса заказа.");
                        order.editState(id, newState);
                        System.out.println("Статус заказа изменён на '" + newState + "'.");
                    }
            //---------------------------ПРОЧЕЕ------------------------------
                    case "authorization" -> {
                        System.out.println("Выполняется авторизация пользователя...");
                        SQLUsers auth = new SQLUsers();
                        Users user = (Users) input.readObject();
                        boolean exist = auth.isExist(user);
                        if (exist == true){
                            output.writeObject("Exist");
                            int r = auth.getUser(user);
                            output.writeObject(r);
                            int id = auth.getId(user);
                            output.writeObject(id);
                            System.out.println("Авторизация прошла успешно!");
                            user.setRole(r);
                            System.out.println(user.toString());
                        } else{
                            System.out.println("Неверно введены данные.");
                            output.writeObject("Does not exist!");
                        }
                    }
                    case "registration" ->{
                        System.out.println("Выполняется регистрация пользователя...");
                        SQLUsers reg = new SQLUsers();
                        String login = (String) input.readObject();
                        boolean exist = reg.isLoginExist(login);
                        if (exist == false) {
                            output.writeObject("OK");
                            Users user = (Users) input.readObject();
                            user.setRole(1);
                            reg.registration(user);
                            int id = reg.getId(user);
                            output.writeObject(id);
                            System.out.println(user.toString());
                        }else output.writeObject("Exist");
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Клиент отключён.");
        }
    }
}
