package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import other.WinChanger;
import models.old.Documents;

public class AddDocController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_adddoc;

    @FXML
    private Button btn_back;

    @FXML
    private TextField field_company;

    @FXML
    private DatePicker field_end_date;

    @FXML
    private TextField field_file_path;

    @FXML
    private TextField field_namedoc;

    @FXML
    private DatePicker field_reg_date;

    @FXML
    void initialize() {
        field_reg_date.setEditable(false);
        field_end_date.setEditable(false);

        btn_adddoc.setOnAction(event->{
            if (!checkInput()) {
                addDoc();
            }else ShowErrors.showAlertWithNullInput();
        });

        btn_back.setOnAction(event->{
            try {
                if (Connect.role == 0)
                    WinChanger.changeWindow(getClass(), btn_back, "admin.fxml", "Administrator", false);
                else
                    WinChanger.changeWindow(getClass(), btn_back, "head.fxml", "Head menu", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addDoc() {
        String worker = null, status = "false", m="";
        String name = field_namedoc.getText();
        String company = field_company.getText();
        String reg_date = field_reg_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String end_date = field_end_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String path = field_file_path.getText();
        Documents document = new Documents(name, worker, company, reg_date, end_date, path, status);
        Connect.client.sendMessage("addDoc1");
        Connect.client.sendObject(document);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK"))
            ShowErrors.correctOperation();
        else ShowErrors.showAlertWithExistDoc();
    }

    private boolean checkInput() {
        try {
            return field_namedoc.getText().equals("") || field_company.getText().equals("") || field_reg_date.getValue().equals("") ||
                    field_end_date.getValue().equals("") || field_file_path.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
