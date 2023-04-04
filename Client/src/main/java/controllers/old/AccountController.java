package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.old.Accounts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import other.WinChanger;

public class AccountController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_changepass;

    @FXML
    private Button btn_editlog;

    @FXML
    private TableColumn<Accounts, String> column_name_docs;
    @FXML
    private TableColumn<Accounts, String> column_login;

    @FXML
    private TableColumn<Accounts, Integer> column_num_docs;

    @FXML
    private TextField field_editlog;

    @FXML
    private TableView<Accounts> table_accounts;

    String login = Connect.login;
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        System.out.println(login);
        Connect.client.sendObject(login);

        column_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        column_num_docs.setCellValueFactory(new PropertyValueFactory<>("num_docs"));
        column_name_docs.setCellValueFactory(new PropertyValueFactory<>("name_docs"));
        table_accounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_accounts.setItems(getInf());

        btn_editlog.setOnAction(event->{
            if (field_editlog.getText() != "")
                editLogin();
            else ShowErrors.showAlertWithNoLogin();

        });

        btn_changepass.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), btn_changepass, "changepass.fxml", "ChangePassword", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ObservableList<Accounts> getInf(){
        ObservableList<Accounts> accList = FXCollections.observableArrayList();
        ArrayList<Accounts> accountsList = (ArrayList<Accounts>) Connect.client.readObject();
        accList.addAll(accountsList);
        table_accounts.setItems(accList);
        return accList;
    }

    public void back(ActionEvent event) throws IOException{
        if (Connect.role == 1)
            WinChanger.changeWindow(getClass(), btn_back, "head.fxml", "Heads menu", false);
        else
            WinChanger.changeWindow(getClass(), btn_back, "worker.fxml", "Worker menu", false);
    }

    private void editLogin() {
        Connect.client.sendMessage("editLogin");
        Connect.client.sendObject(login);
        String newLogin = field_editlog.getText(), m = "";
        Connect.client.sendObject(newLogin);
        try {
            m = Connect.client.readMessage();
        } catch (IOException ex) {
            System.out.println("Error in reading");
        }
        if (m.equals("OK")) {
            Connect.login = newLogin;
            field_editlog.clear();
            ShowErrors.correctOperation();
            table_accounts.getItems().clear();
            Connect.client.sendMessage("account");
            try{
                WinChanger.changeWindow(getClass(), btn_editlog, "account.fxml", "User information", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        }else ShowErrors.showAlertWithExistLogin();
        System.out.println(m);
    }

}
