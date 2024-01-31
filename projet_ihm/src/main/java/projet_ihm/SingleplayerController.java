package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
public class SingleplayerController {

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    
    @FXML
    public void initialize() {

    }
    
}
