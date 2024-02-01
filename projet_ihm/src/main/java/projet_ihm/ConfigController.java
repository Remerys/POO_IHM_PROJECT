package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ConfigController {

    public Button apply;
    public Button back;
    @FXML
    GridPane uiControlsPane;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    public void initialize() {
    }
}
