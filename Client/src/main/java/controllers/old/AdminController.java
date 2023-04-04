package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import other.WinChanger;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_adddoc;

    @FXML
    private Button btn_deleteacc;

    @FXML
    private Button btn_editacc;

    @FXML
    private Button btn_listworkers;

    @FXML
    private Button btn_logout;

    @FXML
    private Button btn_resolution;

    @FXML
    private Button btn_showdocs;

    @FXML
    private Button btn_charter;

    @FXML
    private Button btn_statistic;

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

        btn_editacc.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_editacc, "editacc.fxml", "Edit account", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_deleteacc.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_deleteacc, "deleteacc.fxml", "Delete account", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_showdocs.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_showdocs, "choosedb.fxml", "Choose table", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_adddoc.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_adddoc, "adddoc.fxml", "Add document", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_charter.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_charter, "addcharterdoc.fxml", "Add charter", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_resolution.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_resolution, "resolution.fxml", "Resolution", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_statistic.setOnAction(event ->{
            Connect.client.sendMessage("statistic");
            try{
                WinChanger.changeWindow(getClass(), btn_statistic, "statistic.fxml", "Statistic", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_logout.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), btn_logout, "login.fxml", "Authorization", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
