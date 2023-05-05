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
import models.Clients;
import models.OrderContent;
import models.Orders;
import other.WinChanger;

public class AddOrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addItemButton;

    @FXML
    private Button backToOrdersButton;

    @FXML
    private TableView<OrderContent> table_order_content;

    @FXML
    private TableColumn<OrderContent, Integer> idOrderContColumn;

    @FXML
    private TableColumn<OrderContent, String> itemColumn;

    @FXML
    private TableColumn<OrderContent, Integer> numberColumn;

    @FXML
    private TableView<Clients> table_clients;

    @FXML
    private TableColumn<Clients, String> nameColumn;

    @FXML
    private TableColumn<Clients, Integer> idColumn;

    @FXML
    private Label errorLabel;

    @FXML
    private Button deleteItemButton;

    @FXML
    private DatePicker deliveryDateField;

    @FXML
    private Button exit;

    @FXML
    private Button findClientButton;

    @FXML
    private TextField idClientTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField transpTimeTextField;

    @FXML
    private ChoiceBox<String> barCodeChoiceBox = new ChoiceBox<>();
    ObservableList<Clients> clients = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_clients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_clients.setItems(getInfClients());

        barCodeChoiceBox.setValue("бар-код товара");
        barCodeChoiceBox.getItems().addAll(
                "111111",
                "888888",
                "101010",
                "141414",
                "161616",
                "171717",
                "191919",
                "202020");

        addItemButton.setOnAction(event->{
            errorLabel.setText("");
            if (!barCodeChoiceBox.getValue().equals("бар-код товара")&&!numberTextField.getText().equals("")) {
                String bar_code = barCodeChoiceBox.getValue();
                Integer number = Integer.valueOf(numberTextField.getText());
                Connect.client.sendMessage("addOrderContent");
                Connect.client.sendObject(1);
                Connect.client.sendObject(bar_code);
                Connect.client.sendObject(number);

                Connect.client.sendMessage("orderContentInf");
                Connect.client.sendObject(1);
                idOrderContColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                itemColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
                table_order_content.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                table_order_content.setItems(getInfOrders());
                Connect.client.sendObject(0);
            }else errorLabel.setText("Заполните поля для добавления товара!");
        });

        deleteItemButton.setOnAction(event->{
            OrderContent order = table_order_content.getSelectionModel().getSelectedItem();
            if (order == null)
                errorLabel.setText("Выберите запись для её удаления!");
            else{
                Connect.client.sendMessage("deleteContent");
                deleteContent(order.getId());
            }
        });

        table_clients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Clients client = table_clients.getSelectionModel().getSelectedItem();
                if (client != null){
                    idClientTextField.setText(String.valueOf(client.getId()));
                }
            }
        });

        findClientButton.setOnAction(event->{
            errorLabel.setText("");
            if(!nameTextField.getText().equals("")) {
                Connect.client.sendMessage("findClient");
                findClient();
            }else errorLabel.setText("Введите название компании для поиска!");
        });

        saveButton.setOnAction(event->{
            errorLabel.setText("");
            if (idClientTextField.getText().equals("")||transpTimeTextField.getText().equals("")||deliveryDateField.getValue()==null)
                errorLabel.setText("Заполните необходимые поля!");
            else {
                addOrder();
            }
        });

        backToOrdersButton.setOnAction(event->{
            Connect.client.sendMessage("ordersInf");
            try{
                WinChanger.changeWindow(getClass(), backToOrdersButton, "ordersForManager.fxml", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        exit.setOnAction(event ->{
            System.exit(0);
        });
    }

    private ObservableList<OrderContent> getInfOrders() {
        ObservableList<OrderContent> content = FXCollections.observableArrayList();
        ArrayList<OrderContent> ordersList = (ArrayList<OrderContent>) Connect.client.readObject();
        content.addAll(ordersList);
        table_order_content.setItems(content);
        return content;
    }
    private ObservableList<Clients> getInfClients() {
        ObservableList<Clients> clientList = FXCollections.observableArrayList();
        ArrayList<Clients> clientsList = (ArrayList<Clients>) Connect.client.readObject();
        clientList.addAll(clientsList);
        table_clients.setItems(clientList);
        return clientList;
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

    private void deleteContent(int id) {
        Connect.client.sendObject(id);
            table_order_content.getItems().clear();
        Connect.client.sendMessage("orderContentInf");
        Connect.client.sendObject(1);
        idOrderContColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        table_order_content.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_order_content.setItems(getInfOrders());
        Connect.client.sendObject(0);
    }

    private void addOrder() {
        int id_client = Integer.parseInt(idClientTextField.getText());
        String state = "запрос на резерв";
        int manager = Connect.manager;
        String delivery_date = deliveryDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int transp_time = Integer.parseInt(transpTimeTextField.getText());
        Orders order = new Orders(id_client, state, manager, delivery_date, transp_time);
        Connect.client.sendMessage("addOrder");
        Connect.client.sendObject(order);

        System.out.println("Новый заказ успешно добавлен.");
        Connect.client.sendMessage("clientsNameInf");
        try {
            WinChanger.changeWindow(getClass(), saveButton, "addOrder.fxml", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
