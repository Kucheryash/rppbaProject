package controllers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Orders;
import other.WinChanger;

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
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private Button mainMenuButton;

    @FXML
    private ChoiceBox<String> stateChoiceBox = new ChoiceBox<>();

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("номер"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("статус"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("дата отгрузки"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email клиента"));
        table_orders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_orders.setItems(getInf());

        stateChoiceBox.getItems().addAll(
                "какие",
                "то",
                "статусы");

        mainMenuButton.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), mainMenuButton, "managerMenu.fxml", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        editOrderButton.setOnAction(event ->{
            Connect.client.sendMessage("editOrderState");

        });

        addOrderButton.setOnAction(event ->{
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

}
