package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import other.WinChanger;

public class WorkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_account;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_showdocs;

    @FXML
    void initialize() {
        btn_showdocs.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_showdocs, "choosedb.fxml", "Choose table", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_account.setOnAction(event ->{
            Connect.client.sendMessage("account");
            try{
                WinChanger.changeWindow(getClass(), btn_showdocs, "account.fxml", "User information", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_logout.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_logout, "login.fxml", "Login", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

}
