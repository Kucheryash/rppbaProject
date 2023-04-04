package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import other.WinChanger;
import models.old.Authorization;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_login;

    @FXML
    private Button btn_register;

    @FXML
    private TextField field_login;

    @FXML
    private PasswordField field_password;

    @FXML
    void initialize() throws IOException{

        btn_login.setOnAction(event ->{
            String login = field_login.getText().trim();
            String password = field_password.getText().trim();

            if (!checkInput()){
                Connect.client.sendMessage("authorization");
                loginUser(login, password);
            }
            else ShowErrors.showAlertWithNullInput();
        });

        btn_register.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), btn_register, "register.fxml", "Registration", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loginUser(String login, String password){
        Authorization user = new Authorization();
        user.setLogin(login);
        user.setPassword(password);
        Connect.client.sendObject(user);

        String m = "";
        try {
            m = Connect.client.readMessage();
        } catch (IOException ex) {
            System.out.println("Error in reading");
        }
        if (m.equals("Does not exist!"))
            ShowErrors.showAlertWithNoLogin();
        else {
            Connect.login = login;
            int r = (int) Connect.client.readObject();
            String department = (String) Connect.client.readObject();
            Connect.role = r;
            Connect.password = password;
            Connect.department = department;

            try {
                if (Connect.role == 0){
                    System.out.println("Окно администратора.");
                    WinChanger.changeWindow(getClass(), btn_login, "admin.fxml", "Administrator menu", false);
                } else if (Connect.role == 1){
                    System.out.println("Окно начальника отдела.");
                    WinChanger.changeWindow(getClass(), btn_login, "head.fxml", "Head of department menu", false);
                } else{
                    System.out.println("Окно работника.");
                    WinChanger.changeWindow(getClass(), btn_login, "worker.fxml", "Worker menu", false);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkInput(){
        try {
            return field_login.getText().equals("") || field_password.getText().equals("");
        } catch (Exception e){
            System.out.println("Не заполнены требуемые поля!");
            return true;
        }
    }
}
