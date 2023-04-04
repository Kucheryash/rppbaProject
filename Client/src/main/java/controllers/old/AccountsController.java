package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import other.WinChanger;
import models.old.Accounts;

public class AccountsController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_findworker;

    @FXML
    private Button btn_listchiefs;

    @FXML
    private Button btn_roles;

    @FXML
    private ChoiceBox<String> chooseDepartment = new ChoiceBox<>();

    @FXML
    private TableColumn<Accounts, String> column_department;

    @FXML
    private TableColumn<Accounts, String> column_login;

    @FXML
    private TableColumn<Accounts, String> column_name;

    @FXML
    private TableColumn<Accounts, Integer> column_num_docs;

    @FXML
    private TableColumn<Accounts, String> column_position;

    @FXML
    private TableColumn<Accounts, String> column_surname;

    @FXML
    private TextField field_findworker;

    @FXML
    private TableView<Accounts> table_accounts;

    ObservableList<Accounts> accList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Connect.visible == 1){
            btn_listchiefs.setVisible(false);
            btn_roles.setVisible(false);
            btn_back.setVisible(false);
        }
        if (Connect.role == 1) {
            btn_listchiefs.setVisible(false);
            btn_roles.setVisible(false);
            chooseDepartment.setVisible(false);
            String department = Connect.department;
            chooseDepartment.setValue(department);
            Connect.client.sendMessage("chooseDepartment");
            showDepartment();
        }
        column_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        column_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        column_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        column_num_docs.setCellValueFactory(new PropertyValueFactory<>("num_docs"));
        chooseDepartment.setValue("Выбрыть отдел");
        chooseDepartment.getItems().addAll(
                "Все",
                "Бухгалтерия",
                "Маркетинг",
                "Заключения договоров");
        table_accounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_accounts.setItems(getInf());



        btn_findworker.setOnAction(event ->{
            if(field_findworker.getText() != "") {
                Connect.client.sendMessage("findAcc");
                findAcc();
            }else ShowErrors.showAlertWithNullInput();
        });

        chooseDepartment.setOnAction(event ->{
            String department = chooseDepartment.getValue();
            if (department != "Все") {
                Connect.client.sendMessage("chooseDepartment");
                showDepartment();
            }
            else {
                Connect.client.sendMessage("accountsInf");
                table_accounts.setItems(getInf());
            }
        });

        btn_listchiefs.setOnAction(event ->{
            Connect.client.sendMessage("headsInf");
            try {
                WinChanger.changeWindow(getClass(), btn_listchiefs, "heads.fxml", "Heads of department", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btn_roles.setOnAction(event ->{
            Connect.client.sendMessage("rolesInf");
            try {
                WinChanger.changeWindow(getClass(), btn_roles, "roles.fxml", "Keys", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void back(ActionEvent event) throws IOException {
        if (Connect.role == 0)
            WinChanger.changeWindow(getClass(), btn_back, "admin.fxml", "Administrator", false);
        else if(Connect.role == 1)
            WinChanger.changeWindow(getClass(), btn_back, "head.fxml", "Head menu", false);
        else
            WinChanger.changeWindow(getClass(), btn_back, "worker.fxml", "Worker menu", false);
    }

    private ObservableList<Accounts> getInf() {
        ObservableList<Accounts> accList = FXCollections.observableArrayList();
        ArrayList<Accounts> accountsList = (ArrayList<Accounts>) Connect.client.readObject();
        accList.addAll(accountsList);
        table_accounts.setItems(accList);
        return accList;
    }

    private void findAcc() {
        String surname = field_findworker.getText(), m="";
        Connect.client.sendObject(surname);

        try {
            m = Connect.client.readMessage();
        }catch (Exception e){
            System.out.println("Exception");
        }
        if (m.equals("OK")){
            accList.clear();
            ArrayList<Accounts> account = (ArrayList<Accounts>) Connect.client.readObject();
            System.out.println("Найден пользователь с фамилией '" + surname + "'.");
            accList.addAll(account);
            for (int i = 0; i < accList.size(); i++)
                table_accounts.setItems(accList);
        }
        else ShowErrors.showAlertWithData();
        field_findworker.clear();
        System.out.println(m);
    }

    private void showDepartment() {
        String department = chooseDepartment.getValue();
        Connect.client.sendObject(department);
        try {
            accList.clear();
            ArrayList<Accounts> account = (ArrayList<Accounts>) Connect.client.readObject();
            System.out.println("Показаны работники отдела '"+ department + "'.");
            accList.addAll(account);
            for (int i = 0; i<accList.size(); i++)
                table_accounts.setItems(accList);
        }catch (Exception e){
            System.out.println("Exception");
        }
    }
}