package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ConfigController {

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
