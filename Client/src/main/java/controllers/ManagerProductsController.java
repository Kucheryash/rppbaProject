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
import models.Products;
import other.WinChanger;

public class ManagerProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addMadeYesterdayButton;

    @FXML
    private TableView<Products> table_products;

    @FXML
    private TableColumn<Products, Integer> currentNumberColumn;

    @FXML
    private TableColumn<Products, Integer> nameColumn;

    @FXML
    private TableColumn<Products, Integer> idColumn;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private Button findButton;

    @FXML
    private TextField madeYesterdayTextField;

    @FXML
    private Button mainMenuButton;

    @FXML
    private TextField nameTextField;

    ObservableList<Products> productList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("current_num"));
        table_products.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_products.setItems(getInf());

        addMadeYesterdayButton.setDisable(true);
        addMadeYesterdayButton.setOnAction(event ->{
            Products product = table_products.getSelectionModel().getSelectedItem();
            if (product == null)
                errorLabel.setText("Выберите запись для её редактирования!");
            else{
                errorLabel.setText("");
                if (!madeYesterdayTextField.getText().equals("")) {
                    editProduct(product.getId());
                }else errorLabel.setText("Заполните необходимое поле!");
            }

        });

        findButton.setOnAction(event ->{
            errorLabel.setText("");
            if(!nameTextField.getText().equals("")) {
                Connect.client.sendMessage("findProduct");
                findProduct();
            }else errorLabel.setText("Введите наименование продукта для поиска!");
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

    private ObservableList<Products> getInf() {
        ObservableList<Products> prodList = FXCollections.observableArrayList();
        ArrayList<Products> productsList = (ArrayList<Products>) Connect.client.readObject();
        prodList.addAll(productsList);
        table_products.setItems(prodList);
        return prodList;
    }

    private void editProduct(int id) {
        int madeYesterday = Integer.parseInt(madeYesterdayTextField.getText());
        Connect.client.sendMessage("editProduct");
        Connect.client.sendObject(id);
        Connect.client.sendObject(madeYesterday);

        System.out.println("Продукт успешно изменён.");
        Connect.client.sendMessage("productsInf");
        try{
            WinChanger.changeWindow(getClass(), addMadeYesterdayButton, "productsForManager.fxml", false);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    private void findProduct() {
        String name = nameTextField.getText(), m="";
        Connect.client.sendObject(name);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK")) {
            try {
                productList.clear();
                ArrayList<Products> products = (ArrayList<Products>) Connect.client.readObject();
                productList.addAll(products);
                for (int i = 0; i < productList.size(); i++)
                    table_products.setItems(productList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else errorLabel.setText("Данный продукт не найден!");
        nameTextField.clear();
    }

}
