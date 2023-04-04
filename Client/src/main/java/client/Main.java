package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 340, 300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Connect.client = new Client("localhost", 1280);
        launch();
    }
}