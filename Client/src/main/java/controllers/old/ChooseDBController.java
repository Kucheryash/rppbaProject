package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import other.WinChanger;

public class ChooseDBController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_clients;

    @FXML
    private Button btn_learn;

    @FXML
    void initialize() {
        btn_clients.setOnAction(event->{
            Connect.client.sendMessage("docs1Inf");
            Connect.visible = 0;
            try{
                WinChanger.changeWindow(getClass(), btn_clients, "documents.fxml", "Documents with companies", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_learn.setOnAction(event->{
            Connect.client.sendMessage("docs2Inf");
            try{
                WinChanger.changeWindow(getClass(), btn_learn, "charterdocs.fxml", "Documents for review", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        btn_back.setOnAction(event ->{
            try {
                if (Connect.role == 0)
                    WinChanger.changeWindow(getClass(), btn_back, "admin.fxml", "Administrator", false);
                else if(Connect.role == 1)
                    WinChanger.changeWindow(getClass(), btn_back, "head.fxml", "Head menu", false);
                else
                    WinChanger.changeWindow(getClass(), btn_back, "worker.fxml", "Worker menu", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}