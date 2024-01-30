package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
public class SingleplayerController {

    @FXML
    HBox bottomBar;

    @FXML
    Region spring;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    
    @FXML
    public void initialize() {
        HBox.setHgrow(spring, Priority.ALWAYS);
    }
    
}
