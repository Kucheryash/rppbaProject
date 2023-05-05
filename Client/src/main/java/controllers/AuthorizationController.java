package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Users;
import other.WinChanger;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button registerButton;

    @FXML
    private Button enterButton;

    @FXML
    private TextField loginField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button exit;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
        enterButton.setOnAction(actionEvent -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();

            if (!checkInput()){
                Connect.client.sendMessage("authorization");
                loginUser(login, password);
            }else errorLabel.setText("Заполните все поля!");
        });

        registerButton.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), registerButton, "registration.fxml", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        exit.setOnAction(event -> {
            System.exit(0);
        });
    }

    public void loginUser(String login, String password){
        Users user = new Users();
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
            errorLabel.setText("Данного пользователя не существует!");
        else {
            Connect.login = login;
            int r = (int) Connect.client.readObject();
            int id = (int) Connect.client.readObject();
            Connect.manager = id;
            Connect.role = r;
            Connect.password = password;

            try {
                if (Connect.role == 0){
                    System.out.println("Окно руководителя.");
                    Connect.client.sendMessage("showOrders");
                    WinChanger.changeWindow(getClass(), enterButton, "headMenu.fxml", false);
                } else{
                    System.out.println("Окно менеджера.");
                    WinChanger.changeWindow(getClass(), enterButton, "managerMenu.fxml",  false);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean checkInput(){
        try {
            return loginField.getText().equals("") || passwordField.getText().equals("");
        } catch (Exception e){
            System.out.println("Не заполнены требуемые поля!");
            return true;
        }
    }
}

