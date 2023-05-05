package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Bills;
import models.Clients;
import models.Orders;
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        table_clients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_clients.setItems(getInf());

        findButton.setOnAction(event ->{
            if(!nameTextField.getText().equals("")) {
                Connect.client.sendMessage("findClient");
                findClient();
            }else errorLabel.setText("Введите название компании для поиска!");
        });

        table_clients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Clients client = table_clients.getSelectionModel().getSelectedItem();
                if (client != null){
                    nameTextField.setText(client.getName());
                    emailTextField.setText(client.getEmail());
                    phoneTextField.setText(client.getPhone());
                }
                editButton.setOnAction(event ->{
                    if (client == null)
                        errorLabel.setText("Выберите клиента для редактирования!");
                    else{
                        editClient(client.getName(),nameTextField.getText(), emailTextField.getText(), phoneTextField.getText());
                    }
                });
            }
        });

        addButton.setOnAction(event ->{
            if (!checkInput()) {
                addClient();
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

    private void editClient(String oldName, String name, String email, String phone) {
        Connect.client.sendMessage("editClient");
        Connect.client.sendObject(oldName);
        Connect.client.sendObject(name);
        Connect.client.sendObject(email);
        Connect.client.sendObject(phone);

        System.out.println("Данные клиента успешно изменены.");
        nameTextField.clear();
        emailTextField.clear();
        phoneTextField.clear();
        table_clients.getItems().clear();
        Connect.client.sendMessage("clientsInf");
        try{
            WinChanger.changeWindow(getClass(), editButton, "clientsForManager.fxml", false);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void findClient() {
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
                ArrayList<Clients> clientsList = (ArrayList<Clients>) Connect.client.readObject();
                System.out.println("Найден клиент " + nameTextField.getText());
                clients.addAll(clientsList);
                for (int i = 0; i < clients.size(); i++)
                    table_clients.setItems(clients);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else errorLabel.setText("Клиент не найден!");
        nameTextField.clear();
    }

    private void addClient() {
        String m="";
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        Connect.client.sendMessage("addClient");
        Connect.client.sendObject(name);
        Connect.client.sendObject(email);
        Connect.client.sendObject(phone);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("Exist")) {
            nameTextField.clear();
            errorLabel.setText("Данный клиент уже существует! Измените название компании.");
        }else {
            System.out.println("Новый клиент успешно добавлен.");
            nameTextField.clear();
            emailTextField.clear();
            phoneTextField.clear();
            table_clients.getItems().clear();
            Connect.client.sendMessage("clientsInf");
            try {
                WinChanger.changeWindow(getClass(), editButton, "clientsForManager.fxml", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
