package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.OrderContent;

public class OrderContentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit;

    @FXML
    private TableColumn<OrderContent, String> itemNameColumn;

    @FXML
    private TableColumn<OrderContent, Integer> numberColumn;

    @FXML
    private TableView<OrderContent> table_orderContent;
    
    ObservableList<OrderContent> content = FXCollections.observableArrayList();
    
    @FXML
    void initialize() {
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        table_orderContent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_orderContent.setItems(getInf());

        exit.setOnAction(event -> {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });
    }

    private ObservableList<OrderContent> getInf() {
        ObservableList<OrderContent> content = FXCollections.observableArrayList();
        ArrayList<OrderContent> ordersList = (ArrayList<OrderContent>) Connect.client.readObject();
        content.addAll(ordersList);
        table_orderContent.setItems(content);
        return content;
    }
}

