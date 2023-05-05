package controllers;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Orders;
import other.WinChanger;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerOrdersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addOrderButton;

    @FXML
    private TableView<Orders> table_orders;

    @FXML
    private TableColumn<Orders, Integer> idColumn;

    @FXML
    private TableColumn<Orders, Date> dateColumn;

    @FXML
    private TableColumn<Orders, String> emailColumn;

    @FXML
    private TableColumn<Orders, String> stateColumn;

    @FXML
    private Button editOrderButton;

    @FXML
    private Button editOrderStateButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private Button mainMenuButton;

    @FXML
    private ChoiceBox<String> stateChoiceBox = new ChoiceBox<>();

    ObservableList<Orders> orderList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("shipped_date"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("client_email"));
        table_orders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_orders.setItems(getInf());

        stateChoiceBox.getItems().addAll(
                "нет резерва",
                "запрос на резерв",
                "отгружен");

//        stateChoiceBox.setOnAction(event ->{
//            String state = stateChoiceBox.getValue();
//            if (state != "все") {
//                Connect.client.sendMessage("chooseOrderState");
//                showOrdersByState();
//            }
//            else {
//                Connect.client.sendMessage("ordersInf");
//                table_orders.setItems(getInf());
//            }
//        });

        mainMenuButton.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), mainMenuButton, "managerMenu.fxml", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        editOrderStateButton.setDisable(true);
        editOrderStateButton.setOnAction(event ->{
            Orders order = table_orders.getSelectionModel().getSelectedItem();
            if (order == null)
                errorLabel.setText("Выберите заказ для изменения его статуса!");
            else if (!order.getState().equals("запрос на резерв"))
                errorLabel.setText("Выберите заказ со статусом 'запрос на резерв'!");
            else{
                String newState = "резерв";
                updateState(newState, order.getId());
            }
        });

        editOrderButton.setOnAction(event->{
            Orders order = table_orders.getSelectionModel().getSelectedItem();
            if (order == null)
                errorLabel.setText("Выберите заказ для его изменения!");
            else {
                Connect.orderId = order.getId();
                Connect.client.sendMessage("orderContentInf");
                Connect.client.sendObject(Connect.orderId);
                try {
                    WinChanger.changeWindow(getClass(), editOrderButton, "editOrder.fxml", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        addOrderButton.setOnAction(event ->{
            Connect.client.sendMessage("clientsNameInf");
            try {
                WinChanger.changeWindow(getClass(), addOrderButton, "addOrder.fxml", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exit.setOnAction(event ->{
            System.exit(0);
        });
    }

    private ObservableList<Orders> getInf() {
        ObservableList<Orders> orderList = FXCollections.observableArrayList();
        ArrayList<Orders> ordersList = (ArrayList<Orders>) Connect.client.readObject();
        orderList.addAll(ordersList);
        table_orders.setItems(orderList);
        return orderList;
    }

    private void showOrdersByState() {
        String state = stateChoiceBox.getValue();
        Connect.client.sendObject(state);
        try {
            orderList.clear();
            ArrayList<Orders> account = (ArrayList<Orders>) Connect.client.readObject();
            System.out.println("Показаны заказы со статусом '"+ state + "'.");
            orderList.addAll(account);
            for (int i = 0; i<orderList.size(); i++)
                table_orders.setItems(orderList);
        }catch (Exception e){
            System.out.println("Exception");
        }
    }

    private void updateState(String newState, int id) {
        Connect.client.sendMessage("editOrderState");
        Connect.client.sendObject(id);
        Connect.client.sendObject(newState);
        table_orders.getItems().clear();
        Connect.client.sendMessage("showOrders");
        try{
            WinChanger.changeWindow(getClass(), editOrderButton, "headMenu.fxml", false);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
