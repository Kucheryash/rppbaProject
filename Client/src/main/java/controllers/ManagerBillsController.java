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
import models.Bills;
import models.Orders;
import other.WinChanger;

public class ManagerBillsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changeStateButton;

    @FXML
    private TableView<Bills> table_bills;

    @FXML
    private TableColumn<Bills, Double> costColumn;

    @FXML
    private TableColumn<Bills, Integer> numberColumn;

    @FXML
    private TableColumn<Bills, String> payment_methColumn;

    @FXML
    private TableColumn<Bills, String> stateColumn;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private Button mainMenuButton;

    @FXML
    private ChoiceBox<String> stateChoiceBox;

    ObservableList<Bills> billsList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        payment_methColumn.setCellValueFactory(new PropertyValueFactory<>("payment_meth"));
        table_bills.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_bills.setItems(getInf());

        stateChoiceBox.getItems().addAll(
                "оплачено",
                "не оплачено");

        mainMenuButton.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), mainMenuButton, "managerMenu.fxml", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        changeStateButton.setOnAction(event ->{
            Bills bill = table_bills.getSelectionModel().getSelectedItem();
            if (bill == null)
                errorLabel.setText("Выберите чек для изменения его статуса!");
            else{
                String newState = stateChoiceBox.getValue();
                updateStateBill(newState, bill.getId());
            }

        });

        exit.setOnAction(event ->{
            System.exit(0);
        });
    }

    private ObservableList<Bills> getInf() {
        ObservableList<Bills> billsList = FXCollections.observableArrayList();
        ArrayList<Bills> list = (ArrayList<Bills>) Connect.client.readObject();
        billsList.addAll(list);
        table_bills.setItems(billsList);
        return billsList;
    }

    private void updateStateBill(String newState, int id) {
        Connect.client.sendMessage("editBillState");
        Connect.client.sendObject(id);
        Connect.client.sendObject(newState);
        table_bills.getItems().clear();
        Connect.client.sendMessage("billsInf");
        try{
            WinChanger.changeWindow(getClass(), changeStateButton, "billsForManager.fxml", false);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}