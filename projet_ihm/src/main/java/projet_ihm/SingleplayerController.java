package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SingleplayerController {

    @FXML
    VBox warriorPane, valkyriePane, elfPane, wizardPane;

    @FXML
    Label info;

    private String selected = "warrior";
    private VBox selectedVBox = warriorPane;

    private String borderSelected = "-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.721), 2, 1, 0px, 0px);";

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
        selectedVBox = warriorPane;
        this.selectCharacter(warriorPane);
    }

    @FXML
    private void startGame() throws IOException {
        // TODO : transmettre selected String
        System.out.println(selected);
        App.setRoot("game");
    }

    private void selectCharacter(VBox Pane) {
        selectedVBox.setStyle("");
        Pane.setStyle(borderSelected);
        selectedVBox = Pane;
        if (Pane == warriorPane) {
            selected = "warrior";
            String warriorInfo = "Defense ++++ | Damage +++ | Speed ++ | Range Attack +";
            info.setText(warriorInfo);
        } else if (Pane == valkyriePane) {
            selected = "valkyrie";
            String valkyrieInfo = "Defense +++ | Damage ++ | Speed +++ | Range Attack ++";
            info.setText(valkyrieInfo);
        } else if (Pane == elfPane) {
            selected = "elf";
            String elfInfo = "Defense + | Damage ++ | Speed +++ | Range Attack ++++";
            info.setText(elfInfo);
        } else if (Pane == wizardPane) {
            selected = "wizard";
            String wizardInfo = "Defense + | Damage ++++ | Speed ++ | Range Attack +++";
            info.setText(wizardInfo);

        }
    }

    private void addEvent(VBox Pane) {
        Pane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            this.selectCharacter(Pane);
            selectedVBox = Pane;
        });
    }

}
