package controllers.old;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import other.WinChanger;
import models.old.CharterDocs;

public class DocsCharterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_deletedoc;

    @FXML
    private Button btn_finddoc;

    @FXML
    private TableColumn<CharterDocs, URL> column_doc;

    @FXML
    private TableColumn<CharterDocs, Date> column_end_date;

    @FXML
    private TableColumn<CharterDocs, String> column_name;

    @FXML
    private TableColumn<CharterDocs, String> column_department;

    @FXML
    private TableColumn<CharterDocs, String> column_status;

    @FXML
    private TextField field_deletedoc;

    @FXML
    private TextField field_finddoc;

    @FXML
    private TableView<CharterDocs> table_docs;

    ObservableList<CharterDocs> docList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        if (Connect.role == 2) {
            field_deletedoc.setVisible(false);
            btn_deletedoc.setVisible(false);
        }

        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        column_end_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        column_doc.setCellValueFactory(new PropertyValueFactory<>("document"));
        addButtonToTable();
        addCheckBoxToTable();
        column_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        column_status.setVisible(false);
        table_docs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table_docs.setItems(getInf());

        btn_back.setOnAction(event ->{
            try {
                WinChanger.changeWindow(getClass(), btn_back, "choosedb.fxml", "Choose table", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btn_finddoc.setOnAction(event ->{
            if(field_finddoc.getText() != "") {
                Connect.client.sendMessage("findDoc2");
                findDoc();
            }else ShowErrors.showAlertWithNullInput();
        });

        btn_deletedoc.setOnAction(event ->{
            if(field_deletedoc.getText() != "") {
                Connect.client.sendMessage("deleteDoc2");
                deleteDoc();
            }else ShowErrors.showAlertWithNullInput();
        });
    }

    private ObservableList<CharterDocs> getInf() {
        ObservableList<CharterDocs> docsList = FXCollections.observableArrayList();
        ArrayList<CharterDocs> documentsList = (ArrayList<CharterDocs>) Connect.client.readObject();
        docsList.addAll(documentsList);
        table_docs.setItems(docsList);
        return docsList;
    }

    private void findDoc() {
        String name = field_finddoc.getText(), m="";
        Connect.client.sendObject(name);
        try {
            m = Connect.client.readMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (m.equals("OK")) {
            try {
                docList.clear();
                ArrayList<CharterDocs> document = (ArrayList<CharterDocs>) Connect.client.readObject();
                System.out.println("Найден документ " + field_finddoc.getText());
                docList.addAll(document);
                for (int i = 0; i < docList.size(); i++)
                    table_docs.setItems(docList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else ShowErrors.showAlertWithNoDoc();
        field_finddoc.clear();
    }

    private void deleteDoc() {
        String name = field_deletedoc.getText(), mes = "";
        Connect.client.sendObject(name);
        System.out.println("Зарос на удаление документа "+name+" отправлен.");

        try {
            mes = Connect.client.readMessage();
        } catch (IOException ex) {
            System.out.println("Error in reading");
        }
        if (mes.equals("OK")) {
            table_docs.getItems().clear();
            ShowErrors.correctOperation();
            Connect.client.sendMessage("docs2Inf");
            try{
                WinChanger.changeWindow(getClass(), btn_deletedoc, "charterdocs.fxml", "Documents for review", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else ShowErrors.showAlertWithNoDoc();
        field_deletedoc.clear();
        System.out.println(mes);
    }


    private void addButtonToTable() {
        TableColumn<CharterDocs, Void> column_btn = new TableColumn("Документ");
        Callback<TableColumn<CharterDocs, Void>, TableCell<CharterDocs, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<CharterDocs, Void> call(final TableColumn<CharterDocs, Void> param) {
                final TableCell<CharterDocs, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Открыть");
                    {
                        btn.setOnAction(event -> {
                            CharterDocs data = getTableView().getItems().get(getIndex());
                            try {
                                openFile(data.getDocument());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(btn);
                    }
                };
                return cell;
            }
        };
        column_btn.setCellFactory(cellFactory);
        table_docs.getColumns().add(3,column_btn);
    }

    public static void openFile(String path) throws Exception {
        try
        {
            File file = new File(path);
            if(!Desktop.isDesktopSupported())
            {
                System.out.println("not supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists())
                desktop.open(file);
            else ShowErrors.showAlertWithNoExistFile();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addCheckBoxToTable(){
        TableColumn<CharterDocs, Void> column_checkbox = new TableColumn("статус");
        Callback<TableColumn<CharterDocs, Void>, TableCell<CharterDocs, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<CharterDocs, Void> call(final TableColumn<CharterDocs, Void> param) {
                final TableCell<CharterDocs, Void> cell = new TableCell<>() {
                    private final CheckBox box = new CheckBox();
                    {
                        box.setOnAction(event->{
                            Connect.client.sendMessage("setNewStatus");
                            String table = "doc2";
                            CharterDocs data = getTableView().getItems().get(getIndex());
                            String name = data.getName(), status = data.getStatus();
                            Connect.client.sendObject(table);
                            Connect.client.sendObject(name);
                            Connect.client.sendObject(status);
                            System.out.println("Статус устава '"+name+"' изменён.");
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else {
                            setAlignment(Pos.CENTER);
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };

        column_checkbox.setCellFactory(cellFactory);
        table_docs.getColumns().add(4, column_checkbox);

    }
}

