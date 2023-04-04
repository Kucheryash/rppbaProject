package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import other.WinChanger;
import models.old.CharterDocs;

public class AddCharterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_adddoc;

    @FXML
    private Button btn_back;

    @FXML
    private ChoiceBox<String> chooseDepartment = new ChoiceBox<>();

    @FXML
    private DatePicker field_end_date;

    @FXML
    private TextField field_file_path;

    @FXML
    private TextField field_namedoc;

    @FXML
    private Text txt_department;

    @FXML
    void initialize() {
        field_end_date.setEditable(false);

        chooseDepartment.getItems().addAll(
                "Бухгалтерия",
                "Маркетинг",
                "Заключения договоров");

        btn_adddoc.setOnAction(event->{
            if (!checkInput()) {
                addDoc();
            }else ShowErrors.showAlertWithNullInput();
        });

        btn_back.setOnAction(event->{
            try {
                WinChanger.changeWindow(getClass(), btn_back, "admin.fxml", "Administrator", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addDoc() {
        String name = field_namedoc.getText();
        String department = chooseDepartment.getValue();
        String end_date = field_end_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String path = field_file_path.getText();
        String status = "false", worker = null, m="";
        CharterDocs document = new CharterDocs(name, department, end_date, path, status, worker);
        Connect.client.sendMessage("addDoc2");
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
            return field_namedoc.getText().equals("") || chooseDepartment.getValue().equals("") ||
                    field_end_date.getValue().equals("") || field_file_path.getText().equals("");
        }
        catch (Exception e) {
            System.out.println("Error");
            return true;
        }
    }
}
