package projet_ihm;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class EditorController {

    @FXML
    GridPane gridpane;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void createGridPane() {
        Image image = new Image(getClass().getResource("/images/floor.png").toExternalForm());
        int gridSize = 41;
        int buttonSize = 450 / gridSize;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                ImageView view = new ImageView(image);
                view.setPreserveRatio(true);
                view.setFitHeight(buttonSize);
                view.setFitWidth(buttonSize);
                gridpane.add(view, i, j);
            }
        }
    }

    @FXML
    public void initialize() {
        createGridPane();
    }
}