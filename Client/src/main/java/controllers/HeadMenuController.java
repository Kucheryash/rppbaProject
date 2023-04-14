package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HeadMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button abrogateResButton;

    @FXML
    private Button acceptResButton;

    @FXML
    private TableView<?> coursesTableView;

    @FXML
    private Label errCertificate;

    @FXML
    private Label errorFindLabel;

    @FXML
    private Label errorStudyLabel;

    @FXML
    private Button exit;

    @FXML
    private Button exitAccButton;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> managerColumn;

    @FXML
    private Button rejectResButton;

    @FXML
    private Button showContetntButton;

    @FXML
    private ChoiceBox<?> statusChoiceBox;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    void initialize() {

    }

}
