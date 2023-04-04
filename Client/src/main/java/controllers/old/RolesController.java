package controllers.old;

import java.io.IOException;
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
import other.WinChanger;
import models.old.Authorization;

public class RolesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private TableColumn<Authorization, String> column_login;

    @FXML
    private TableColumn<Authorization, String> column_password;

    @FXML
    private TableColumn<Authorization, Integer> column_role;

    @FXML
    private TableView<Authorization> table_accounts;

    @FXML
    void initialize() {
        column_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        column_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        column_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        table_accounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_accounts.setItems(getInf());

        btn_back.setOnAction(event ->{
            Connect.client.sendMessage("accountsInf");
            try {
                WinChanger.changeWindow(getClass(), btn_back, "accounts.fxml", "Accounts information", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ObservableList<Authorization> getInf() {
        ObservableList<Authorization> accList = FXCollections.observableArrayList();
        ArrayList<Authorization> accountsList = (ArrayList<Authorization>) Connect.client.readObject();
        accList.addAll(accountsList);
        table_accounts.setItems(accList);
        return accList;
    }

}
