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
import models.Products;
import other.WinChanger;

public class ManagerProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addCostButton;

    @FXML
    private Button addMadeYesterdayButton;

    @FXML
    private TextField costTextField;

    @FXML
    private TableView<Products> table_products;

    @FXML
    private TableColumn<Products, Integer> currentNumberColumn;

    @FXML
    private TableColumn<Products, Integer> costColumn;

    @FXML
    private TableColumn<Products, Integer> nameColumn;

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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("наименование"));
        currentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("текущее кол-во"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("цена"));
        table_products.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_products.setItems(getInf());

        addMadeYesterdayButton.setOnAction(event ->{
            if (madeYesterdayTextField.getText() != "") {
                Connect.client.sendMessage("editNumProduct");
            }else errorLabel.setText("Заполните необходимое поле!");

        });

        addCostButton.setOnAction(event ->{
            if (costTextField.getText() != "") {
                Connect.client.sendMessage("editCostProduct");
            }else errorLabel.setText("Заполните необходимое поле!");

        });

        findButton.setOnAction(event ->{
            if(nameTextField.getText() != "") {
                Connect.client.sendMessage("findProduct");
                findDoc();
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
                productList.clear();
                ArrayList<Products> document = (ArrayList<Products>) Connect.client.readObject();
                System.out.println("Найден документ " + nameTextField.getText());
                productList.addAll(document);
                for (int i = 0; i < productList.size(); i++)
                    table_products.setItems(productList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else errorLabel.setText("Данный клиент не найден!");
        nameTextField.clear();
    }

}
