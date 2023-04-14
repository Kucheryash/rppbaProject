package other;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WinChanger {
    public static void changeWindow(Class className, Button button, String fname, boolean ismodal) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(className.getResource("/" + fname));
        fxmlLoader.load();

        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));

        if (ismodal) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }
        else {
            button.getScene().getWindow().hide();
        }
        stage.show();
    }
}