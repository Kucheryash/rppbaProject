package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Orders;
import other.WinChanger;

public class HeadMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Orders> table_orders;

    @FXML
    private TableColumn<Orders, Integer> idColumn;

    @FXML
    private TableColumn<Orders, String> managerColumn;

    @FXML
    private TableColumn<Orders, String> statusColumn;

    @FXML
    private Button abrogateResButton;

    @FXML
    private Button acceptResButton;

    @FXML
    private Button rejectResButton;

    @FXML
    private Button showContentButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private Button exitAccButton;

    @FXML
    private ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();

    ObservableList<Orders> orderList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("manager_login"));
        table_orders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_orders.setItems(getInf());

        statusChoiceBox.setValue("все");
        statusChoiceBox.getItems().addAll(
                "все",
                "нет резерва",
                "запрос на резерв",
                "резерв отклонён",
                "резерв",
                "отгружен");

        statusChoiceBox.setOnAction(event ->{
            String state = statusChoiceBox.getValue();
            if (state != "все") {
                Connect.client.sendMessage("chooseOrderState");
                showOrdersByState();
            }
            else {
                Connect.client.sendMessage("showOrders");
                table_orders.setItems(getInf());
            }
        });

        acceptResButton.setOnAction(event ->{
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

        rejectResButton.setOnAction(event ->{
            Orders order = table_orders.getSelectionModel().getSelectedItem();
            if (order == null)
                errorLabel.setText("Выберите заказ для изменения его статуса!");
            else if (!order.getState().equals("запрос на резерв"))
                errorLabel.setText("Выберите заказ со статусом 'запрос на резерв'!");
            else{
                String newState = "резерв отклонён";
                updateState(newState, order.getId());
            }
        });

        abrogateResButton.setOnAction(event ->{
            Orders order = table_orders.getSelectionModel().getSelectedItem();
            if (order == null)
                errorLabel.setText("Выберите заказ для изменения его статуса!");
            else if (!order.getState().equals("резерв"))
                errorLabel.setText("Выберите заказ со статусом 'резерв'!");
            else{
                String newState = "нет резерва";
                updateState(newState, order.getId());
            }
        });

        showContentButton.setOnAction(event ->{
            Orders order = table_orders.getSelectionModel().getSelectedItem();
            if (order == null)
                errorLabel.setText("Выберите заказ для просмотра содержимого!");
            else{
                Connect.client.sendMessage("showContent");
                Connect.client.sendObject(order.getId());
                try{
                    WinChanger.changeWindow(getClass(), showContentButton, "orderContent.fxml",  true);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        exitAccButton.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), exitAccButton, "authorization.fxml",  false);
            } catch (IOException e){
                e.printStackTrace();
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
        String state = statusChoiceBox.getValue();
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
            WinChanger.changeWindow(getClass(), acceptResButton, "headMenu.fxml", false);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
