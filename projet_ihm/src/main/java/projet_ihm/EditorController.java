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
        // Image image = new Image(App.class.getResource("floor.png").toExternalForm());
        // ImageView view = new ImageView(image);
        // view.setPreserveRatio(true);
        int size = 10;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button button = new Button();
                button.setText("Button" + i + j);
                // button.setGraphic(view);
                gridpane.add(button, i, j);
            }
        }
    }

    @FXML
    public void initialize() {
        createGridPane();
    }
}