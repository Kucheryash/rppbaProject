package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class EditOrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addItemButton;

    @FXML
    private Button backToCoursesForAdminButton;

    @FXML
    private TableView<?> coursesTableView1;

    @FXML
    private Button deleteItemButton;

    @FXML
    private TextField deliveryDateTextField;

    @FXML
    private Button exit;

    @FXML
    private Label idLabel;

    @FXML
    private ChoiceBox<?> itemChoiceBox;

    @FXML
    private TableColumn<?, ?> itemColumn;

    @FXML
    private TableColumn<?, ?> numberColumn;

    @FXML
    private TextField numberTextField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField transpTimeTextField;

    @FXML
    void initialize() {

    }

}
