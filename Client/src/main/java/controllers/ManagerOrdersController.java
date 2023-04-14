package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManagerOrdersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addOrderButton;

    @FXML
    private TableView<?> coursesTableView;

    @FXML
    private TableColumn<?, ?> dateColumn;

    @FXML
    private Button editOrderButton;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private Label errorDeleteLabel;

    @FXML
    private Label errorEditLabel;

    @FXML
    private Label errorFindLabel;

    @FXML
    private Button exit;

    @FXML
    private Button mainMenuButton;

    @FXML
    private TableColumn<?, ?> numberColumn;

    @FXML
    private ChoiceBox<?> stateChoiceBox;

    @FXML
    private TableColumn<?, ?> stateColumn;

    @FXML
    void initialize() {

    }

}
