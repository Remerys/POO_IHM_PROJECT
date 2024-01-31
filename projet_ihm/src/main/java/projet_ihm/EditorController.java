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
    int rotation = 0;
    MultipleImages currentPreview;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void createGridPane() {
        Image floor = new Image(getClass().getResource(String.format(this.path, "floor")).toExternalForm());
        Image none = new Image(getClass().getResource(String.format(this.path, "none")).toExternalForm());
        int gridSize = 41;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                ImageView background = new ImageView(floor);
                background.setPreserveRatio(true);
                MultipleImages view = new MultipleImages(none);
                view.setPreserveRatio(true);
                MultipleImages preview = new MultipleImages(none);
                preview.setPreserveRatio(true);

                // ATTENTION les zones transparentes ne sont pas clickable
                // => Plusieurs couches clickables

                // preview
                background.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited(preview)); // sort
                view.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited(preview));
                preview.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited(preview));

                background.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview)); // entre
                view.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview));
                preview.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview));

                background.addEventHandler(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview)); // tourne
                view.addEventHandler(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview));
                preview.addEventHandler(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview));

                // view
                background.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> setImage(view));
                view.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> setImage(view));
                preview.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> setImage(view));

                Group group = new Group();
                group.getChildren().addAll(background, view, preview);
                // ne fonctionne pas
                group.setStyle("-fx-border-style : solid; -fx-border-width:100; -fx-border-color: black");

                gridpane.add(group, i, j);
            }
        }
    }

    @FXML
    private void createIcons() {
        String[] imagesStr = { "floor", "exit", "food", "key", "potion_life", "potion_magic", "potion_physical",
                "potion_poison", "potion_speed", "potion_defense", "door" };
        for (String imageStr : imagesStr) {
            Image image = new Image(getClass().getResource(String.format(this.path, imageStr)).toExternalForm());
            MultipleImages view = new MultipleImages(image);
            view.setFitHeight(45);
            view.setFitWidth(45);

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
        handleRotate();

    }

    private void handlePreviewEntered(MultipleImages preview) {
        if (this.currentImage != "floor") {
            this.currentPreview = preview;
            double opacity = 0.66;

            Image image = new Image(
                    getClass().getResource(String.format(this.path, this.currentImage)).toExternalForm());
            preview.changeImage(image);
            preview.setOpacity(opacity);
            preview.setImageIndex(this.rotation);
        }
    }

    private void handlePreviewExited(MultipleImages preview) {
        if (this.currentImage != "floor") {
            Image image = new Image(
                    getClass().getResource(String.format(this.path, "none")).toExternalForm());
            this.currentPreview.changeImage(image);
        }
    }

    @FXML
    private void handleZoom() {
        gridpane.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent e) {
                if (!e.isControlDown())
                    return;
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
    private void setImage(MultipleImages view) {
        Image image = new Image(
                getClass().getResource(String.format(this.path,
                        this.currentImage)).toExternalForm());

        view.changeImage(image);
        view.setImageIndex(this.rotation);
    }

    @FXML
    private void handleRotate() {
        gridpane.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent e) {
                if (e.getDeltaY() > 0)
                    increaseRotation();
                else
                    decreaseRotation();
            }
        });
    }

    private void increaseRotation() {
        this.rotation += 1;
    }

    private void decreaseRotation() {
        this.rotation -= 1;
    }
}