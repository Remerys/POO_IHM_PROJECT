package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;

public class EmptyController {
    
    @FXML
    public void initialize() {

    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    
}
