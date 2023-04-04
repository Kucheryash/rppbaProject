package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import other.WinChanger;

public class ResolutionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button btn_back;

    @FXML
    private Button btn_resolution;

    @FXML
    private Button btn_show;

    @FXML
    private TextField field_login;

    @FXML
    private TextField field_namedoc;

    @FXML
    void initialize() {
        btn_resolution.setOnAction(event->{
            if (!checkInput())
                resolution();
            else ShowErrors.showAlertWithNullInput();
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
            Connect.client.sendMessage("docs1Inf");
            Connect.visible = 1;
            try {
                WinChanger.changeWindow(getClass(), btn_back, "documents.fxml", "Documents list", true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void resolution() {
        String m="";
        String name = field_namedoc.getText();
        String login = field_login.getText();
        Connect.client.sendMessage("resolution");
        Connect.client.sendObject(login);
        Connect.client.sendObject(name);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK"))
            ShowErrors.correctOperation();
        else ShowErrors.showAlertWithData();
        field_login.clear();
        field_namedoc.clear();
        System.out.println(m);
    }

    private boolean checkInput() {
        try {
            return field_namedoc.getText().equals("") || field_login.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }

}
