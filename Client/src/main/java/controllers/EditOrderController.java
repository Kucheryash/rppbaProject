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
import models.OrderContent;
import models.Orders;
import other.WinChanger;

public class EditOrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addItemButton;

    @FXML
    private Button backToOrdersButton;

    @FXML
    private Button deleteItemButton;

    @FXML
    private DatePicker deliveryDateField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private Label idLabel;

    @FXML
    private TableView<OrderContent> table_orders;

    @FXML
    private TableColumn<OrderContent, String> itemColumn;

    @FXML
    private TableColumn<OrderContent, Integer> numberColumn;

    @FXML
    private TableColumn<OrderContent, Integer> idOrderContColumn;

    @FXML
    private TextField numberTextField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField transpTimeTextField;

    @FXML
    private ChoiceBox<String> barCodeChoiceBox = new ChoiceBox<>();

    @FXML
    void initialize() {
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        table_orders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_orders.setItems(getInf());

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

        Connect.client.sendObject(Connect.orderId);
        int id_client = (int) Connect.client.readObject();
        idLabel.setText(String.valueOf(id_client));

        addItemButton.setOnAction(event->{
            errorLabel.setText("");
            if (!barCodeChoiceBox.getValue().equals("бар-код товара")&&!numberTextField.getText().equals("")) {
                String bar_code = barCodeChoiceBox.getValue();
                Integer number = Integer.valueOf(numberTextField.getText());
                Connect.client.sendMessage("addOrderContent");
                Connect.client.sendObject(Connect.orderId);
                Connect.client.sendObject(bar_code);
                Connect.client.sendObject(number);

                Connect.client.sendMessage("orderContentInf");
                Connect.client.sendObject(Connect.orderId);
                idOrderContColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                itemColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
                table_orders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                table_orders.setItems(getInf());
                Connect.client.sendObject(0);
            }else errorLabel.setText("Заполните поля для добавления товара!");
        });

        deleteItemButton.setOnAction(event->{
            OrderContent order = table_orders.getSelectionModel().getSelectedItem();
            if (order == null)
                errorLabel.setText("Выберите запись для её удаления!");
            else{
                Connect.client.sendMessage("deleteContent");
                deleteContent(order.getId());
            }
        });

        saveButton.setOnAction(event->{
            errorLabel.setText("");
            if (idLabel.getText().equals("")||transpTimeTextField.getText().equals("")||deliveryDateField.getValue()==null)
                errorLabel.setText("Заполните необходимые поля!");
            else {
                editOrder();
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

    private ObservableList<OrderContent> getInf() {
        ObservableList<OrderContent> content = FXCollections.observableArrayList();
        ArrayList<OrderContent> ordersList = (ArrayList<OrderContent>) Connect.client.readObject();
        content.addAll(ordersList);
        table_orders.setItems(content);
        return content;
    }

    private void editOrder() {
        int id = Connect.orderId;
        String delivery_date = deliveryDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int transp_time = Integer.parseInt(transpTimeTextField.getText());
        Orders order = new Orders(id, delivery_date, transp_time);
        Connect.client.sendMessage("editOrder");
        Connect.client.sendObject(order);

        System.out.println("Заказ успешно изменён.");
        Connect.client.sendMessage("ordersInf");
        try{
            WinChanger.changeWindow(getClass(), saveButton, "ordersForManager.fxml", false);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void deleteContent(int id) {
        Connect.client.sendObject(id);
        table_orders.getItems().clear();
        Connect.client.sendMessage("orderContentInf");
        Connect.client.sendObject(Connect.orderId);
        idOrderContColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        table_orders.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_orders.setItems(getInf());
        Connect.client.sendObject(0);
    }
}
