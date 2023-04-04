package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import other.WinChanger;

public class HeadController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_account;

    @FXML
    private Button btn_adddoc;

    @FXML
    private Button btn_deleteacc;

    @FXML
    private Button btn_resolution;

    @FXML
    private Button btn_listworkers;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_showdocs;

    @FXML
    void initialize() {
        btn_listworkers.setOnAction(event ->{
            Connect.client.sendMessage("accountsInf");
            Connect.visible = 0;
            try{
                WinChanger.changeWindow(getClass(), btn_listworkers, "accounts.fxml", "List of accounts", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_deleteacc.setOnAction(event ->{
            Connect.client.sendMessage("deleteAcc");
            try{
                WinChanger.changeWindow(getClass(), btn_deleteacc, "deleteacc.fxml", "Delete account", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_showdocs.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_showdocs, "choosedb.fxml", "Choose db", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_adddoc.setOnAction(event ->{
            Connect.client.sendMessage("addDoc");
            try{
                WinChanger.changeWindow(getClass(), btn_adddoc, "adddoc.fxml", "Add document", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_resolution.setOnAction(event ->{
            Connect.client.sendMessage("editDoc");
            try{
                WinChanger.changeWindow(getClass(), btn_resolution, "resolution.fxml", "Edit document", false);
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
