package projet_ihm;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML
    BorderPane root;

    @FXML
    public void initialize() {
        this.loadGame();
    }

    private void loadGame() {

        GridPane gridPane = new GridPane();
        String style = "-fx-border-color: black; -fx-border-width: 1;";
        gridPane.setStyle(style);

        String path = "/images/floor.png";
        Image floor = new Image(getClass().getResource(path).toExternalForm());
        int gridSize = 10;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                ImageView background = new ImageView(floor);
                StackPane container = new StackPane(background);
                container.setStyle(style);
                container.setPadding(new javafx.geometry.Insets(0));
                gridPane.add(container, i, j);
            }
        }

        // Ajout de la grille au centre
        root.setCenter(gridPane);
    }
    
}
