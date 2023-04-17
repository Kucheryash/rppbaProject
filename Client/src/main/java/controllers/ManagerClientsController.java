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
import models.Clients;
import other.WinChanger;

public class ManagerClientsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Clients> table_clients;

    @FXML
    private TableColumn<Clients, String> emailColumn;

    @FXML
    private TableColumn<Clients, String> nameColumn;

    @FXML
    private TableColumn<Clients, String> phoneColumn;

    @FXML
    private Button editButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private Button findButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    ObservableList<Clients> clients = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ФИО"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("телефон"));
        table_clients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_clients.setItems(getInf());

        findButton.setOnAction(event ->{
            if(nameTextField.getText() != "") {
                Connect.client.sendMessage("findClient");
                findDoc();
            }else errorLabel.setText("Введите ФИО для поиска!");
        });

        editButton.setOnAction(event ->{
            Connect.client.sendMessage("editClient");

        });

        addButton.setOnAction(event ->{
            if (!checkInput()) {
                Connect.client.sendMessage("addClient");
            }else errorLabel.setText("Заполните необходимые поля!");

        });

        mainMenuButton.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), mainMenuButton, "managerMenu.fxml", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exit.setOnAction(event ->{
            System.exit(0);
        });
    }

    private ObservableList<Clients> getInf() {
        ObservableList<Clients> clientList = FXCollections.observableArrayList();
        ArrayList<Clients> clientsList = (ArrayList<Clients>) Connect.client.readObject();
        clientList.addAll(clientsList);
        table_clients.setItems(clientList);
        return clientList;
    }

    private void findDoc() {
        String name = nameTextField.getText(), m="";
        Connect.client.sendObject(name);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK")) {
            try {
                clients.clear();
                ArrayList<Clients> document = (ArrayList<Clients>) Connect.client.readObject();
                System.out.println("Найден документ " + nameTextField.getText());
                clients.addAll(document);
                for (int i = 0; i < clients.size(); i++)
                    table_clients.setItems(clients);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else errorLabel.setText("Данный клиент не найден!");
        nameTextField.clear();
    }


    private boolean checkInput() {
        try {
            return nameTextField.getText().equals("") || emailTextField.getText().equals("") || phoneTextField.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
