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
                //--------------------ВЫБОРКА ДАННЫХ-----------------------
                    /*case "findAcc" ->{
                        System.out.println("Запрос к БД на поиск работника.");
                        SQLAccounts user = new SQLAccounts();
                        String surname = (String) input.readObject();
                        boolean exist = user.isExistSurn(surname);
                        if (exist == true){
                            output.writeObject("OK");
                            System.out.println("Найден пользователь с фамилией '"+surname+"'.");
                            ArrayList<Accounts> accList = user.findAcc(surname);
                            output.writeObject(accList);
                        } else output.writeObject("NotExist");
                    }case "chooseDepartment" ->{
                        System.out.println("Запрос к БД на выбор отдела.");
                        SQLAccounts account = new SQLAccounts();
                        String department = (String) input.readObject();
                        System.out.println("Показаны работники отдела "+department);
                        ArrayList<Accounts> accList = account.findDepartment(department);
                        output.writeObject(accList);
                    }case "chooseDepartHead" ->{
                        System.out.println("Запрос к БД на выбор отдела.");
                        SQLAccounts account = new SQLAccounts();
                        String department = (String) input.readObject();
                        System.out.println("Показан начальник отдела "+department);
                        ArrayList<Heads> accList = account.findDepartHead(department);
                        output.writeObject(accList);
                    }
                //--------------------ИЗМЕНЕНИЕ ДАННЫХ-----------------------
                    case "editAcc" ->{
                        System.out.println("Запрос к БД на редактирование информации о пользователе.");
                        SQLAccounts user = new SQLAccounts();
                        String login = (String) input.readObject();
                        boolean exist = user.isExist(login);
                        if (exist == true){
                            output.writeObject("OK");
                            Accounts acc = (Accounts) input.readObject();
                            Authorization auth = (Authorization) input.readObject();
                            user.editAcc(acc, auth);
                            System.out.println("Данные пользователя '" + login + "' изменены.");
                        } else output.writeObject("NotExist");
                    }
                    case "editLogin" ->{
                        SQLAccounts user = new SQLAccounts();
                        String oldLogin = (String) input.readObject();
                        String newLogin = (String) input.readObject();
                        System.out.println("Запрос к БД на изменение логина пользователя '" + oldLogin + "'.");
                        boolean exist = user.isExist(newLogin);
                        if (exist == false){
                            output.writeObject("OK");
                            user.editLogin(oldLogin, newLogin);
                            System.out.println("Логин изменён на '" + newLogin + "'.");
                        } else output.writeObject("Exist");
                    }
                    case "changePass" ->{
                        System.out.println("Запрос к БД на изменение пароля пользователя.");
                        SQLAccounts user = new SQLAccounts();
                        String login = (String) input.readObject();
                        String oldPass = (String) input.readObject();
                        String newPass = (String) input.readObject();
                        user.editPass(login, oldPass, newPass);
                        output.writeObject("OK");
                        System.out.println("Пароль изменён.");

                    }
                    case "deleteAcc" ->{
                        System.out.println("Запрос на удаление пользователя.");
                        SQLAccounts user = new SQLAccounts();
                        String login = (String) input.readObject();
                        System.out.println("Удаление пользователя: "+login+".");
                        boolean exist = user.isExist(login);
                        if (exist == true){
                            user.deleteAcc(login);
                            output.writeObject("OK");
                        } else {
                            output.writeObject("NotExist");
                        }
                    }

        //---------------------------МЕНЮ РУКОВОДИТЕЛЯ------------------------------
                //--------------------ПРОСМОТР ДАННЫХ-----------------------
                    case "docs1Inf" ->{
                        System.out.println("Запрос к БД на получение информации о дорговорах.");
                        SQLDocuments docs = new SQLDocuments();
                        ArrayList<Documents> infList = docs.getInf1();
                        output.writeObject(infList);
                    }
                    case "docs2Inf" ->{
                        System.out.println("Запрос к БД на получение информации о дорговорах.");
                        SQLDocuments docs = new SQLDocuments();
                        ArrayList<CharterDocs> infList = docs.getInf2();
                        output.writeObject(infList);
                    }
                //--------------------ВЫБОРКА ДАННЫХ-----------------------
                    case "addDoc1" ->{
                        System.out.println("Запрос к БД на добавление договора.");
                        SQLDocuments document = new SQLDocuments();
                        Documents doc = (Documents) input.readObject();
                        boolean exist = document.isExistDoc(doc.getName());
                        if (exist == false) {
                            output.writeObject("OK");
                            document.addDoc(doc);
                            System.out.println(doc.toString());
                        } else output.writeObject("Exist");
                    }
                    case "addDoc2" ->{
                        System.out.println("Запрос к БД на добавление договора.");
                        SQLDocuments document = new SQLDocuments();
                        CharterDocs doc = (CharterDocs) input.readObject();
                        boolean exist = document.isExistCh(doc.getName());
                        if (exist == false) {
                            output.writeObject("OK");
                            document.addCh(doc);
                            System.out.println(doc.toString());
                        } else output.writeObject("Exist");
                    }
                    case "findDoc1" ->{
                        System.out.println("Запрос к БД на поиск договора.");
                        SQLDocuments docs = new SQLDocuments();
                        String doc = (String) input.readObject();
                        boolean exist = docs.isExistDoc(doc);
                        if (exist == true){
                            output.writeObject("OK");
                            ArrayList<Documents> docList = docs.findDoc1(doc);
                            output.writeObject(docList);
                        } else output.writeObject("NotExist");
                    }
                    case "findDoc2" ->{
                        System.out.println("Запрос к БД на поиск договора.");
                        SQLDocuments docs = new SQLDocuments();
                        String doc = (String) input.readObject();
                        boolean exist = docs.isExistCh(doc);
                        if (exist == true){
                            output.writeObject("OK");
                            ArrayList<CharterDocs> docList = docs.findDoc2(doc);
                            output.writeObject(docList);
                        } else output.writeObject("NotExist");
                    }
                    case "showDoneDocs" ->{
                        System.out.println("Запрос к БД на сортировку договоров.");
                        SQLDocuments docs = new SQLDocuments();
                        Documents doc = (Documents) input.readObject();
                        ArrayList<Documents> docList = docs.showDoneDocs(doc);
                        output.writeObject(docList);
                        System.out.println("Показаны выполненные договоры.");
                    }
                //--------------------ИЗМЕНЕНИЕ ДАННЫХ-----------------------
                    case "resolution" ->{
                        System.out.println("Запрос на назначение исполнителя договора.");
                        SQLDocuments doc = new SQLDocuments();
                        SQLAccounts user = new SQLAccounts();
                        String login = (String) input.readObject();
                        String name = (String) input.readObject();
                        boolean existLogin = user.isExist(login), existDoc = doc.isExistDoc(name);
                        if (existLogin == false || existDoc == false)
                            output.writeObject("NotExist");
                        else {
                            output.writeObject("OK");
                            doc.resolution(login, name);
                            System.out.println("Исполнителю '" + login + "' назначен договор '"+name+"'.");
                        }
                    }
                    case "deleteDoc1" ->{
                        System.out.println("Запрос на удаление договора.");
                        SQLDocuments doc = new SQLDocuments();
                        String name = (String) input.readObject();
                        boolean exist = doc.isExistDoc(name);
                        if (exist==true){
                            output.writeObject("OK");
                            System.out.println("Удаление договора '"+name+"'");
                            String table = "doc1";
                            doc.deleteDoc(name, table);
                        } else output.writeObject("NotExist");
                    }
                    case "deleteDoc2" ->{
                        System.out.println("Запрос на удаление устава.");
                        SQLDocuments doc = new SQLDocuments();
                        String name = (String) input.readObject();
                        boolean exist = doc.isExistCh(name);
                        if (exist==true){
                            output.writeObject("OK");
                            System.out.println("Удаление документа '"+name+"'");
                            String table = "doc2";
                            doc.deleteDoc(name, table);
                        } else output.writeObject("NotExist");
                    }
                    case "setNewStatus"->{
                        System.out.println("Запрос на изменение статуса документа.");
                        SQLDocuments doc = new SQLDocuments();
                        String table = (String) input.readObject();
                        String name = (String) input.readObject();
                        String oldstatus = (String) input.readObject();
                        String status = "";
                        if (oldstatus.equals("true"))
                            status = "false";
                        else status = "true";
                        doc.setNewStatus(status, name, table);
                        System.out.println("Статус документа '"+name+"' обновлён на: "+status+".");
                    }*/

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
