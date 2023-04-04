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
import models.old.Documents;

public class DocumentsController {

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
    private CheckBox checkbox_donedocs;

    @FXML
    private TableColumn<Documents, String> column_company;

    @FXML
    private TableColumn<Documents, String> column_name;

    @FXML
    private TableColumn<Documents, Date> column_reg_date;

    @FXML
    private TableColumn<Documents, Date> column_end_date;

    @FXML
    private TableColumn<Documents, String> column_doc;

    @FXML
    private TableColumn<Documents, String> column_status;

    @FXML
    private TableColumn<Documents, String> column_worker;

    @FXML
    private TextField field_deletedoc;

    @FXML
    private TextField field_finddoc;

    @FXML
    private TableView<Documents> table_docs;

    ObservableList<Documents> docList = FXCollections.observableArrayList();
    ArrayList<Documents> docBoxList = new ArrayList<>();

    @FXML
    void initialize() {
        if (Connect.role == 2) {
            field_deletedoc.setVisible(false);
            btn_deletedoc.setVisible(false);
        }
        if (Connect.visible == 1)
            btn_back.setVisible(false);

        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_worker.setCellValueFactory(new PropertyValueFactory<>("worker"));
        column_company.setCellValueFactory(new PropertyValueFactory<>("company"));
        column_reg_date.setCellValueFactory(new PropertyValueFactory<>("reg_date"));
        column_end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        addButtonToTable();
        addCheckBoxToTable();
        column_doc.setCellValueFactory(new PropertyValueFactory<>("doc"));
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
                Connect.client.sendMessage("findDoc1");
                findDoc();
            }else ShowErrors.showAlertWithNullInput();
        });

        btn_deletedoc.setOnAction(event ->{
            if(field_deletedoc.getText() != "") {
                Connect.client.sendMessage("deleteDoc1");
                deleteDoc();
            }else ShowErrors.showAlertWithNullInput();
        });

        checkbox_donedocs.setOnAction(event->{
            if (checkbox_donedocs.isSelected() == true)
                showDoneDocs();
            else{
                Connect.client.sendMessage("docs1Inf");
                table_docs.setItems(getInf());
            }
        });

    }

    private ObservableList<Documents> getInf() {
        ObservableList<Documents> docsList = FXCollections.observableArrayList();
        ArrayList<Documents> documentsList = (ArrayList<Documents>) Connect.client.readObject();
        docsList.addAll(documentsList);
        docsList.addAll(docBoxList);

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
                ArrayList<Documents> document = (ArrayList<Documents>) Connect.client.readObject();
                System.out.println("Найден документ '" + name + "'.");
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
        System.out.println("Зарос на удаление договора "+name+" отправлен.");

        try {
            mes = Connect.client.readMessage();
        } catch (IOException ex) {
            System.out.println("Error in reading");
        }
        if (mes.equals("OK")) {
            table_docs.getItems().clear();
            ShowErrors.correctOperation();
            Connect.client.sendMessage("docs1Inf");
            try{
                WinChanger.changeWindow(getClass(), btn_deletedoc, "documents.fxml", "Documents with companies", false);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else ShowErrors.showAlertWithNoDoc();
        field_deletedoc.clear();
        System.out.println(mes);
    }

    private void showDoneDocs() {
        table_docs.setItems(null);
        Documents doc = new Documents();
        Connect.client.sendMessage("showDoneDocs");
        doc.setStatus("true");
        Connect.client.sendObject(doc);
        try {
            docList.clear();
            ArrayList<Documents> documents = (ArrayList<Documents>) Connect.client.readObject();
            System.out.println("Показаны выполненные договоры.");
            docList.addAll(documents);
            for (int i = 0; i<docList.size(); i++)
                table_docs.setItems(docList);
        }catch (Exception e){
            System.out.println("Exception");
        }
    }

    private void addButtonToTable() {
        TableColumn<Documents, Void> column_btn = new TableColumn("документ");
        Callback<TableColumn<Documents, Void>, TableCell<Documents, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Documents, Void> call(final TableColumn<Documents, Void> param) {
                final TableCell<Documents, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Открыть");
                    {
                        btn.setOnAction(event -> {
                            Documents data = getTableView().getItems().get(getIndex());
                            try {
                                openFile(data.getDoc());
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
                        else {
                            setAlignment(Pos.CENTER);
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        column_btn.setCellFactory(cellFactory);
        table_docs.getColumns().add(5, column_btn);
    }

    public static void openFile(String path) throws Exception {
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

    public void addCheckBoxToTable(){
        TableColumn<Documents, Void> column_checkbox = new TableColumn("статус");
        Callback<TableColumn<Documents, Void>, TableCell<Documents, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Documents, Void> call(final TableColumn<Documents, Void> param) {
                final TableCell<Documents, Void> cell = new TableCell<>() {
                    private final CheckBox box = new CheckBox();
                    {
                        box.setOnAction(event->{
                            Connect.client.sendMessage("setNewStatus");
                            String table = "doc1";
                            Documents data = getTableView().getItems().get(getIndex());
                            String name = data.getName(), status = data.getStatus();
                            Connect.client.sendObject(table);
                            Connect.client.sendObject(name);
                            Connect.client.sendObject(status);
                            System.out.println("Статус договора '"+name+"' изменён.");
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
        table_docs.getColumns().add(6, column_checkbox);

    }
}