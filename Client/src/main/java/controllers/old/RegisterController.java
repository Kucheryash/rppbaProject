package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import other.WinChanger;
import models.old.Accounts;
import models.old.Authorization;
import models.old.Heads;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_register;

    @FXML
    private ChoiceBox<String> chooseDepartment = new ChoiceBox<>();

    @FXML
    private TextField field_login;

    @FXML
    private TextField field_name;

    @FXML
    private PasswordField field_password;

    @FXML
    private TextField field_position;

    @FXML
    private TextField field_surname;

    @FXML
    private Text txt_department;

    @FXML
    private Text label;

    @FXML
    void initialize() {
        chooseDepartment.getItems().addAll(
                "Бухгалтерия",
                "Маркетинг",
                "Заключения договоров");

        btn_register.setOnAction(event ->{
            if (!checkInput()) {
                Connect.client.sendMessage("registration");
                register();
            }
            else
                ShowErrors.showAlertWithNullInput();
        });

        btn_back.setOnAction(event->{
                try {
                    WinChanger.changeWindow(getClass(), btn_back, "login.fxml", "Authorization", false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        });
    }

    public void register() {
        String m="";
        String surname = field_surname.getText();
        String name = field_name.getText();
        String department = chooseDepartment.getValue();
        String position = field_position.getText();
        String login = field_login.getText();
        String password = field_password.getText();
        Connect.client.sendObject(login);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK")){
            Heads head = null;
            int num_docs = 0, role, num_workers = 0;
            if (position.equals("Начальник")) {
                role = 1;
                head = new Heads(surname, name, login, department, num_docs, num_workers);
            } else role = 2;
            Connect.login = login;
            Connect.role = role;
            Connect.password = password;
            Connect.department = department;
            Accounts user = new Accounts(surname, name, login, department, position, num_docs);
            Authorization userKey = new Authorization(login, password, role);
            Connect.client.sendObject(user);
            Connect.client.sendObject(userKey);
            Connect.client.sendObject(head);
            try {
                if (Connect.role == 1){
                    System.out.println("Окно начальника отдела.");
                    WinChanger.changeWindow(getClass(), btn_register, "head.fxml", "Head menu", false);
                }else if(Connect.role == 2){
                    System.out.println("Окно работника.");
                    WinChanger.changeWindow(getClass(), btn_register, "worker.fxml", "Worker menu", false);
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else ShowErrors.showAlertWithExistLogin();
        field_login.clear();
        field_password.clear();
    }

    private boolean checkInput() {
        try {
            return field_surname.getText().equals("") || field_name.getText().equals("") || chooseDepartment.getValue().equals("") ||
                    field_position.getText().equals("") || field_login.getText().equals("") || field_password.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }

}
