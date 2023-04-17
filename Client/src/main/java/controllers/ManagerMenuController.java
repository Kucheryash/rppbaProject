package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import other.WinChanger;

public class ManagerMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button checksButton;

    @FXML
    private Button clientsButton;

    @FXML
    private Button exit;

    @FXML
    private Button exitAccButton;

    @FXML
    private Button ordersButton;

    @FXML
    private Button productsButton;

    @FXML
    void initialize() {
        ordersButton.setOnAction(event ->{
            Connect.client.sendMessage("ordersInf");
            Connect.visible = 0;
            try{
                WinChanger.changeWindow(getClass(), ordersButton, "ordersForManager.fxml", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        checksButton.setOnAction(event ->{
            Connect.client.sendMessage("billsInf");
            try{
                WinChanger.changeWindow(getClass(), checksButton, "billsForManager.fxml", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        clientsButton.setOnAction(event ->{
            Connect.client.sendMessage("clientsInf");
            try{
                WinChanger.changeWindow(getClass(), clientsButton, "clientsForManager.fxml", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        productsButton.setOnAction(event ->{
            Connect.client.sendMessage("productsInf");
            try{
                WinChanger.changeWindow(getClass(), productsButton, "productsForManager.fxml", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        exitAccButton.setOnAction(event ->{
            try{
                WinChanger.changeWindow(getClass(), exitAccButton, "authorization.fxml",  false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        exit.setOnAction(event ->{
            System.exit(0);
        });
    }

}
