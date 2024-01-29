package projet_ihm;

import java.io.IOException;
import javafx.fxml.FXML;

public class EditorController {

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void createGridPane() {

    }
}