package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ChangePassController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_savepass;

    @FXML
    private TextField field_login;

    @FXML
    private PasswordField field_newpass;

    @FXML
    private PasswordField field_newpass1;

    @FXML
    private TextField field_password;

    @FXML
    private Text text_error;

    String login = Connect.login;

    @FXML
    void initialize() {
        text_error.setVisible(false);
        field_login.setEditable(false);
        field_login.setText(login);

        btn_savepass.setOnAction(event->{
            if (!checkInput())
                editPass();
            else ShowErrors.showAlertWithNullInput();
        });

        btn_back.setOnAction(event->{
            btn_back.getScene().getWindow().hide();
        });
    }

    private void editPass() {
        text_error.setVisible(false);
        String m = "";
        String oldPass = field_password.getText();
        String newPass = field_newpass.getText();
        String newPass1 = field_newpass1.getText();

        String localPass =  Connect.password;
        if (!oldPass.equals(localPass) || oldPass.equals(newPass))
            ShowErrors.showAlertWithData();
        else if (newPass.equals(newPass1)){
            Connect.client.sendMessage("changePass");
            Connect.client.sendObject(login);
            Connect.client.sendObject(oldPass);
            Connect.client.sendObject(newPass);
            try {
                m = Connect.client.readMessage();
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
            if (m.equals("OK"))
                ShowErrors.correctOperation();
            else ShowErrors.showAlertWithExistLogin();
            System.out.println(m);
            field_password.clear();
            field_newpass.clear();
            field_newpass1.clear();
            btn_savepass.getScene().getWindow().hide();
        } else {
            text_error.setVisible(true);
            field_newpass1.clear();
        }
    }

    private boolean checkInput() {
        try {
            return field_login.getText().equals("") || field_password.getText().equals("") || field_newpass.getText().equals("") ||
                    field_newpass1.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
