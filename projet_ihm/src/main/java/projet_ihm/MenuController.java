package projet_ihm;

import java.io.IOException;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void switchToEditor() throws IOException {
        App.setRoot("editor");
    }
}
