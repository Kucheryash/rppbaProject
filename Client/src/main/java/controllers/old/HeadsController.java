package controllers.old;

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
import other.WinChanger;
import models.old.Heads;

public class HeadsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private ChoiceBox<String> chooseDepartment = new ChoiceBox<>();

    @FXML
    private TableColumn<Heads, String> column_department;

    @FXML
    private TableColumn<Heads, String> column_login;

    @FXML
    private TableColumn<Heads, String> column_name;

    @FXML
    private TableColumn<Heads, Integer> column_num_docs;

    @FXML
    private TableColumn<Heads, Integer> column_num_workers;

    @FXML
    private TableColumn<Heads, String> column_surname;

    @FXML
    private TableView<Heads> table_accounts;

    ObservableList<Heads> accList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        column_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        column_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        column_num_docs.setCellValueFactory(new PropertyValueFactory<>("num_docs"));
        column_num_workers.setCellValueFactory(new PropertyValueFactory<>("num_workers"));
        chooseDepartment.setValue("Все");
        chooseDepartment.getItems().addAll(
                "Все",
                "Бухгалтерия",
                "Маркетинг",
                "Заключения договоров");
        table_accounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_accounts.setItems(getInf());

        chooseDepartment.setOnAction(event ->{
            String department = chooseDepartment.getValue();
            if (department != "Все") {
                Connect.client.sendMessage("chooseDepartHead");
                showDepartment();
            }
            else {
                Connect.client.sendMessage("headsInf");
                table_accounts.setItems(getInf());
            }
        });

        btn_back.setOnAction(event ->{
            Connect.client.sendMessage("accountsInf");
            try {
                WinChanger.changeWindow(getClass(), btn_back, "accounts.fxml", "Accounts information", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ObservableList<Heads> getInf() {
        ObservableList<Heads> accList = FXCollections.observableArrayList();
        ArrayList<Heads> accountsList = (ArrayList<Heads>) Connect.client.readObject();
        accList.addAll(accountsList);
        table_accounts.setItems(accList);
        return accList;
    }


    private void showDepartment() {
        String department = chooseDepartment.getValue();
        Connect.client.sendObject(department);
        try {
            accList.clear();
            ArrayList<Heads> account = (ArrayList<Heads>) Connect.client.readObject();
            System.out.println("Показаны работники отдела '"+ department + "'.");
            accList.addAll(account);
            for (int i = 0; i<accList.size(); i++)
                table_accounts.setItems(accList);
        }catch (Exception e){
            System.out.println("Exception");
        }
    }

}
