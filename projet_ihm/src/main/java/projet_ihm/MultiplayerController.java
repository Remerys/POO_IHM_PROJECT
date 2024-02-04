package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MultiplayerController {

    @FXML
    HBox backBar;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    public void initialize() {
        Region spring = new Region();
        HBox.setHgrow(spring, Priority.ALWAYS);
        backBar.getChildren().add(spring);
    }
}
