package projet_ihm;

import java.io.IOException;
import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditorController {

    @FXML
    GridPane gridpane;

    @FXML
    HBox icons;

    String currentImage = "floor";
    String path = "/images/%s.png";
    int rotation = 0;
    MultipleImages currentPreview;
    private int sizeX;
    private int sizeY;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void createGridPane() {
        Image floor = getImage("floor");
        Image none = getImage("none");
        for (int i = 0; i < this.sizeX; i++) {
            for (int j = 0; j < this.sizeY; j++) {
                ImageView background = new ImageView(floor);
                background.setPreserveRatio(true);
                MultipleImages view = new MultipleImages(none);
                view.setPreserveRatio(true);
                MultipleImages preview = new MultipleImages(none);
                preview.setPreserveRatio(true);

                // ATTENTION les zones transparentes ne sont pas clickable
                // => Plusieurs couches clickables

                // preview
                background.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited()); // sort
                view.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited());
                preview.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited());

                background.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview)); // entre
                view.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview));
                preview.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview));

                background.addEventFilter(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview)); // tourne
                view.addEventFilter(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview));
                preview.addEventFilter(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview));

                // view
                background.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> setImage(view));
                view.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> setImage(view));
                preview.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> setImage(view));

                Group group = new Group();
                group.getChildren().addAll(background, view, preview);
                // ne fonctionne pas
                group.setStyle("-fx-border-style : solid; -fx-border-width:100; -fx-border-color: black");

                gridpane.add(group, i, j);
            }
        }
    }

    private Image getImage(String name) {
        URL url = getClass().getResource(String.format(this.path, name));
        assert url != null;
        return new Image(url.toExternalForm());
    }

    @FXML
    private void createIcons() {
        String[] imagesStr = { "floor", "exit", "food", "key", "potion_life", "potion_magic", "potion_physical",
                "potion_poison", "potion_speed", "potion_defense", "door" };
        for (String imageStr : imagesStr) {
            Image image = getImage(imageStr);
            MultipleImages view = new MultipleImages(image);
            view.setImageIndex(0);
            view.setFitHeight(45);
            view.setFitWidth(45);

            Button button = new Button();
            button.setGraphic(view);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeCurrentImage(imageStr));
            icons.getChildren().add(button);
        }
    }

    @FXML
    public void initialize() throws IOException {
        this.menuStart();
        System.out.println(this.sizeX + " " + this.sizeY);
        if (this.sizeX <= 0 || this.sizeY <= 0) {
            this.switchToMenu();
        }


    }

    private void launchGrid() {
        this.createGridPane();
        this.createIcons();
        this.handleZoom();
        this.handleRotate();
    }

    @FXML
    private void menuStart() {
        Stage stage = new Stage();
        VBox pane = new VBox();
        Label label = new Label("");

        TextField sizeXField = new TextField();
        TextField sizeYField = new TextField();

        Button button = new Button("OK");
        button.setOnAction(e -> {
            try {
                this.sizeX = Integer.parseInt(sizeXField.getText());
                this.sizeY = Integer.parseInt(sizeYField.getText());
                if (this.sizeX > 0 && this.sizeY > 0) {
                    stage.close();
                    this.launchGrid();
                } else {
                    label.setText("Les nombres doivent être positifs");
                }
            } catch (NumberFormatException ex) {
                label.setText("Ceci n'est pas un nombre.");
            }
        });

        pane.getChildren().addAll(label, sizeXField, sizeYField, button);

        stage.setOnCloseRequest(e -> e.consume()); //ne peut pas se fermer

        Scene scene = new Scene(pane, 200, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void handlePreviewEntered(MultipleImages preview) {
        if (!this.currentImage.equals("floor")) {
            this.currentPreview = preview;
            double opacity = 0.66;

            Image image = getImage(this.currentImage);
            preview.changeImage(image);
            preview.setOpacity(opacity);
            preview.setImageIndex(this.rotation);
        }
    }

    private void handlePreviewExited() {
        if (!this.currentImage.equals("floor")) {
            Image image = getImage("none");
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
        Image image = getImage(this.currentImage);

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