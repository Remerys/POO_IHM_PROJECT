package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML
    BorderPane root;

    @FXML
    GridPane gameGrid;

    @FXML
    public void initialize() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                this.showPausePopup();
            }
        });
        this.loadGame();
    }

    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    private void loadGame() {
        String style = "-fx-border-color: black; -fx-border-width: 1;";
        gameGrid.setStyle(style);
        gameGrid.setHgap(1);
        gameGrid.setVgap(1);
        String floorPath = "/images/floor.png";
        Image floor = new Image(getClass().getResource(floorPath).toExternalForm());
        int gridSize = 10;

        // Ajoutez les ImageView pour représenter le fond de la carte ici
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                ImageView cell = new ImageView(floor);
                cell.setFitWidth(50);
                cell.setFitHeight(50);
                StackPane container = new StackPane(cell);
                container.getStyleClass().add("character");
                container.setStyle(style);
                gameGrid.add(container, i, j);
            }
        }
    }

    private void showPausePopup() {
        // Créer une fenêtre pop-up avec un titre
        Alert pauseAlert = new Alert(AlertType.CONFIRMATION);
        pauseAlert.setTitle("Pause");
        pauseAlert.setHeaderText("Need a break ?");

        // Ajouter des boutons personnalisés avec des actions associées
        ButtonType muteButton = new ButtonType("Mute Sound");
        ButtonType backButton = new ButtonType("Back");
        ButtonType mainMenuButton = new ButtonType("Main Menu");
        pauseAlert.getButtonTypes().setAll(muteButton, backButton, mainMenuButton);

        // Afficher la fenêtre pop-up
        pauseAlert.showAndWait().ifPresent(buttonType -> {
            // Gérer les actions en fonction du bouton cliqué
            if (buttonType == muteButton) {
                // Action pour le bouton "Mute Sound"
                System.out.println("MUTE");
            } else if (buttonType == backButton) {
                // Action pour le bouton "Back"
                System.out.println("Back");
            } else if (buttonType == mainMenuButton) {
                // Action pour le bouton "Main Menu"
                try {
                    this.switchToMenu();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
