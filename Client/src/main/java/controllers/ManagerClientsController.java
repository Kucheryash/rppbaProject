package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManagerClientsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableView<?> coursesTableView;

    @FXML
    private Button editButton;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label errorDeleteLabel;

    @FXML
    private Label errorEditLabel;

    @FXML
    private Label errorFindLabel;

    @FXML
    private Button exit;

    @FXML
    private Button findButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TextField phoneTextField;

    @FXML
    void initialize() {

    }

}
