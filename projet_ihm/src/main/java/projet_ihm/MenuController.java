package projet_ihm;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML
    Button quit;

    @FXML
    private void switchToEditor() throws IOException {
        App.setRoot("editor");
    }

    @FXML
    private void switchToSingleplayer() throws IOException {
        App.setRoot("single");
    }

    @FXML
    private void switchToMultiplayer() throws IOException {
        App.setRoot("multi");
    }

    @FXML
    private void switchToHallOfFame() throws IOException {
        App.setRoot("hall_of_fame");
    }

    @FXML
    public void initialize() {
        quit.setOnMouseClicked(event -> Platform.exit());
    }
}
