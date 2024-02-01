package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
public class SingleplayerController {

    @FXML
    VBox warriorPane, valkyriePane, elfPane, wizardPane;

    private String selected = "warrior";
    private VBox selectedVBox = warriorPane;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    public void initialize() {
        this.addHover(warriorPane);
        this.addHover(valkyriePane);
        this.addHover(elfPane);
        this.addHover(wizardPane);
        this.selectCharacter(warriorPane);
    }

    @FXML
    private void startGame() throws IOException {
        // TODO : transmettre selected String
        System.out.println(selected);
        App.setRoot("game");
    }

    private void selectCharacter(VBox Pane) {
        String border = "-fx-border-width: 0;";
        warriorPane.setStyle(border);
        valkyriePane.setStyle(border);
        elfPane.setStyle(border);
        wizardPane.setStyle(border);
        border = "-fx-border-color: black; -fx-border-width: 5; -fx-border-style: segments(10, 15, 15, 15)  line-cap round;";
        Pane.setStyle(border);
        selectedVBox = Pane;
        if (Pane == warriorPane) {
            selected = "warrior";
        } else if (Pane == valkyriePane) {
            selected = "valkyrie";
        } else if (Pane == elfPane) {
            selected = "elf";
        } else if (Pane == wizardPane) {
            selected = "wizard";
        }
    }

    private void addHover(VBox Pane) {
        Pane.setOnMouseEntered(event -> {
            if (Pane != selectedVBox) {
                String border = "-fx-border-color: white; -fx-border-width: 2; -fx-border-style: solid;";
                Pane.setStyle(border);
            }
        });
        Pane.setOnMouseExited(event -> {
            if (Pane != selectedVBox) {
                String border = "-fx-border-width: 0;";
                Pane.setStyle(border);
            }
        });
        Pane.setOnMouseClicked(event -> {
            this.selectCharacter(Pane);
            selectedVBox = Pane;
        });
    }

}
