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

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createAccButton;

    @FXML
    private Button backButton;

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
        createAccButton.setOnAction(event ->{
            if (!checkInput()) {
                Connect.client.sendMessage("registration");
                register();
            }
            else errorLabel.setText("Заполните все поля!");
        });

        backButton.setOnAction(event->{
            try {
                WinChanger.changeWindow(getClass(), backButton, "authorization.fxml",  false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void register() {
        String m="";
        String login = loginField.getText();
        String password = passwordField.getText();
        Connect.client.sendObject(login);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK")){
            int role = 1;
            Connect.login = login;
            Connect.role = role;
            Connect.password = password;
            Users user = new Users(login, password, role);
            Connect.client.sendObject(user);
            int id = (int) Connect.client.readObject();
            Connect.manager = id;
            try {
                System.out.println("Окно менеджера.");
                WinChanger.changeWindow(getClass(), createAccButton, "managerMenu.fxml", false);

            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else errorLabel.setText("Данный логин уже занят!");
        loginField.clear();
        passwordField.clear();
    }

    private boolean checkInput() {
        try {
            return loginField.getText().equals("") || passwordField.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }

}
