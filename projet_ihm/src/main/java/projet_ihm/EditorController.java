package projet_ihm;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EditorController {



    @FXML
    GridPane gridpane;

    @FXML
    HBox icons;

    String currentImage = "floor";

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void createGridPane() {
        Image image = new Image(getClass().getResource("/images/floor.png").toExternalForm());
        int gridSize = 41;
        //int buttonSize = 450 / gridSize;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                ImageView view = new ImageView(image);
                view.setPreserveRatio(true);
                //view.setFitHeight(buttonSize);
                //view.setFitWidth(buttonSize);
                gridpane.add(view, i, j);
            }
        }
    }

    @FXML
    private void createIcons() {
        String[] imagesStr = { "exit", "floor", "food", "key", "potion_defense", "potion_life" };
        for (String imageStr : imagesStr) {
            Image image = new Image(getClass().getResource(String.format("/images/%s.png", imageStr)).toExternalForm());
            ImageView view = new ImageView(image);
            view.setFitHeight(50);
            view.setFitWidth(50);

            Button button = new Button();
            button.setGraphic(view);
            icons.getChildren().add(button);
        }
    }

    @FXML
    public void initialize() {
        createGridPane();
        createIcons();
    }

    @FXML
    public void zoomIn() {
        double delta = 1.2;
        double scale = gridpane.getScaleX();
        gridpane.setScaleX(scale * delta);
        gridpane.setScaleY(scale * delta);
    }

    @FXML
    public void zoomOut() {
        double delta = 1.2;
        double scale = gridpane.getScaleX();
        gridpane.setScaleX(scale / delta);
        gridpane.setScaleY(scale / delta);
    }

    // @FXML
    // public void setImage(int x, int y) {
    // Image image = new Image(
    // getClass().getResource(String.format("/images/%s.png",
    // this.currentImage)).toExternalForm());
    // ImageView view = new ImageView(image);
    // gridpane.add(view, x, y);

    // }
}