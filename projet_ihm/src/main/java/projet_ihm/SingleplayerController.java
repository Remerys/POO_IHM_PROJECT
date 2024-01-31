package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
public class SingleplayerController {

    @FXML
    VBox warriorPane, valkyriePane, elfPane, wizardPane;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    public void initialize() {
        this.addEvent(warriorPane);
        this.addEvent(valkyriePane);
        this.addEvent(elfPane);
        this.addEvent(wizardPane);
    }

    @FXML
    private void startGame() {
        System.out.println("Game start");
    }

    private void addEvent(VBox Pane) {
        Pane.setOnMouseEntered(event -> {
            String border = "-fx-border-color: white; -fx-border-width: 2; -fx-border-style: solid;";
            Pane.setStyle(border);
        });
        Pane.setOnMouseExited(event -> {
            String border = "-fx-border-color: white; -fx-border-width: 0; -fx-border-style: solid;";
            Pane.setStyle(border);
        });
    }

}
