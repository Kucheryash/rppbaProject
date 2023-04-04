package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import other.WinChanger;
import models.old.Accounts;
import models.old.Authorization;

public class EditAccController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_show;

    @FXML
    private Button btn_editsave;

    @FXML
    private ChoiceBox<String> chooseDepartment = new ChoiceBox<>();

    @FXML
    private TextField field_login;

    @FXML
    private TextField field_name;

    @FXML
    private TextField field_position;

    @FXML
    private TextField field_surname;

    @FXML
    void initialize() {
        chooseDepartment.setValue("Выбрать отдел");
        chooseDepartment.getItems().addAll(
                "Выбрать отдел",
                "Бухгалтерия",
                "Маркетинг",
                "Заключения договоров");

        btn_editsave.setOnAction(event->{
            if (!checkInput())
                saveChanges();
            else ShowErrors.showAlertWithNullInput();
        });

        btn_show.setOnAction(event ->{
            Connect.client.sendMessage("accountsInf");
            Connect.visible = 1;
            try{
                WinChanger.changeWindow(getClass(), btn_show, "accounts.fxml", "List of accounts", true);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_back.setOnAction(event->{
            try {
                WinChanger.changeWindow(getClass(), btn_back, "admin.fxml", "Administrator", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void saveChanges() {
        String surname = field_surname.getText();
        String name = field_name.getText();
        String department = chooseDepartment.getValue();
        String position = field_position.getText();
        String login = field_login.getText();
        String m="";
        Connect.client.sendMessage("editAcc");
        Connect.client.sendObject(login);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK")) {
            int role;
            if (position.equals("Начальник")) {
                role = 1;
            }
            else role = 2;
            Accounts user = new Accounts(surname, name, login, department, position);
            Authorization userKey = new Authorization(login, role);
            Connect.client.sendObject(user);
            Connect.client.sendObject(userKey);
            System.out.println("Данные пользователя '"+login+"' успешно изменены.");
            ShowErrors.correctOperation();
            field_login.clear();
            field_surname.clear();
            field_name.clear();
            chooseDepartment.setValue("Выбрать отдел");
            field_position.clear();
        } else ShowErrors.showAlertWithNoLogin();
        System.out.println(m);
    }

    private boolean checkInput() {
        try {
            return field_surname.getText().equals("") || field_name.getText().equals("") || chooseDepartment.getValue().equals("Выбрать отдел") ||
                    field_position.getText().equals("") || field_login.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
