package projet_ihm;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EditorController {

    @FXML
    GridPane gridpane;

    @FXML
    HBox icons;

    String currentImage = "floor";
    String path = "/images/%s.png";

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void createGridPane() {
        Image image = new Image(getClass().getResource(String.format(this.path, "floor")).toExternalForm());
        int gridSize = 41;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                ImageView background = new ImageView(image);
                background.setPreserveRatio(true);

                ImageView view = new ImageView(image);
                view.setPreserveRatio(true);
                view.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        setImage(view);
                    }
                });

                Group group = new Group();
                group.getChildren().add(background);
                group.getChildren().add(view);
                // ne fonctionne pas
                group.setStyle("-fx-border-style : solid; -fx-border-width:100; -fx-border-color: black");

                gridpane.add(group, i, j);
            }
        }
    }

    @FXML
    private void createIcons() {
        String[] imagesStr = { "exit", "floor", "food", "key", "potion_defense", "potion_life" };
        for (String imageStr : imagesStr) {
            Image image = new Image(getClass().getResource(String.format(this.path, imageStr)).toExternalForm());
            ImageView view = new ImageView(image);
            view.setFitHeight(50);
            view.setFitWidth(50);

            Button button = new Button();
            button.setGraphic(view);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    changeCurrentImage(imageStr);
                }
            });
            icons.getChildren().add(button);
        }
    }

    @FXML
    public void initialize() {
        createGridPane();
        createIcons();
        handleZoom();

    }

    @FXML
    private void handleZoom() {
        gridpane.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent e) {
                // System.out.println(e.getDeltaX() + " " + e.getDeltaY());
                if(!e.isControlDown()) return;
                if (e.getDeltaY() > 0)
                    zoomIn();
                else
                    zoomOut();
            }
        });
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

    private void changeCurrentImage(String imageStr) {
        this.currentImage = imageStr;
    }

    @FXML
    private void setImage(ImageView view) {
        Image image = new Image(
                getClass().getResource(String.format(this.path,
                        this.currentImage)).toExternalForm());
        view.setImage(image);
    }
}