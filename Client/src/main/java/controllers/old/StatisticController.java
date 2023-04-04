package controllers.old;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.Connect;
import javafx.fxml.FXML;
        import javafx.scene.chart.PieChart;
        import javafx.scene.control.Button;
import other.WinChanger;

public class StatisticController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_savestat;

    @FXML
    private PieChart statistic;

    @FXML
    void initialize() {
        ArrayList<AbstractMap.SimpleEntry<String, Double>> data
                = (ArrayList<AbstractMap.SimpleEntry<String, Double>>) Connect.client.readObject();

        for (AbstractMap.SimpleEntry<String, Double> point: data) {
            statistic.getData().add(new PieChart.Data(point.getKey(), point.getValue()));
        }

        btn_savestat.setOnAction(event->{
            Connect.client.sendMessage("saveStat");
            String mes = "";
            try {
                mes = Connect.client.readMessage();
            } catch(IOException ex){
                System.out.println("Ничего нет");
            }
            System.out.println(mes);
            if (mes.equals("Ошибка при составлении отчета"))
                ShowErrors.showAlertWithData();
            else {
                ShowErrors.correctOperation();
            }
        });

        btn_back.setOnAction(event->{
            try {
                WinChanger.changeWindow(getClass(), btn_back, "admin.fxml", "Administrator", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
