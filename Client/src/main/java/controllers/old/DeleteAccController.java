package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import other.WinChanger;

public class DeleteAccController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_show;

    @FXML
    private Button btn_deleteacc;

    @FXML
    private TextField field_login;

    @FXML
    void initialize() {
        btn_deleteacc.setOnAction(event ->{
            if(field_login.getText() != "") {
                deleteAcc();
            }else ShowErrors.showAlertWithNullInput();
        });

        btn_back.setOnAction(event ->{
            try {
                if (Connect.role == 0)
                    WinChanger.changeWindow(getClass(), btn_back, "admin.fxml", "Menu of administrator", false);
                else WinChanger.changeWindow(getClass(), btn_back, "head.fxml", "Head of department", false);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btn_show.setOnAction(event ->{
            Connect.client.sendMessage("accountsInf");
            Connect.visible = 1;
            try {
                WinChanger.changeWindow(getClass(), btn_show, "accounts.fxml", "Accounts list", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteAcc() {
        String login = field_login.getText();
        if (login.equals(Connect.login))
            ShowErrors.showAlertWithData();
        else {
            Connect.client.sendMessage("deleteAcc");
            Connect.client.sendObject(login);
            System.out.println("Зарос на удаление пользователя '" + login + "' отправлен.");

            String mes = "";
            try {
                mes = Connect.client.readMessage();
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
            if (mes.equals("NotExist"))
                ShowErrors.showAlertWithNoLogin();
            else ShowErrors.correctOperation();
            System.out.println(mes);
        }
        field_login.clear();
    }
}
