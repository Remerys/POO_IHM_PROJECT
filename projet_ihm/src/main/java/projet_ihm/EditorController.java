package projet_ihm;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EditorController {

    @FXML
    GridPane gridpane;

    @FXML
    HBox icons;

    @FXML
    Label itemSelected;

    private String currentImage = "floor";
    private int rotation = 0;
    private MultipleImages currentPreview;
    private int sizeX;
    private int sizeY;

    private final String[] potions = {"defense", "life", "magic", "physical", "poison", "speed"};

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    public void initialize() {

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
                // sort
                background.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited());
                view.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited());
                preview.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> handlePreviewExited());

                // entre
                background.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview));
                view.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview));
                preview.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> handlePreviewEntered(preview));

                // tourne
                background.addEventFilter(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview));
                view.addEventFilter(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview));
                preview.addEventFilter(ScrollEvent.SCROLL, event -> handlePreviewEntered(preview));

                // view
                background.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> placeImage(view));
                view.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> placeImage(view));
                preview.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> placeImage(view));

                StackPane layer = new StackPane();
                layer.getChildren().addAll(background, view, preview);

                gridpane.add(layer, i, j);
            }
        }
    }

    @FXML
    private void createIcons() {
        String[] names = { "exit", "floor", "wall", "door", "food", "key", "potion_life", "treasure", "smart_bomb", "ghost", "daemon", "grunt", "lobber", "death", "spawner_ghost", "spawner_grunt" };
        for (String name : names) {
            Image image = getImage(name);
            MultipleImages view = new MultipleImages(image);
            view.setImageIndex(0);
            view.setFitHeight(45);
            view.setFitWidth(45);

            Button button = new Button();
            button.setGraphic(view);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeCurrentImage(name));
            icons.getChildren().add(button);
        }
    }

    @FXML
    private void newMap() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        Label label = new Label("");

        Label width = new Label("Width : ");
        TextField sizeXField = new TextField();
        Label height = new Label("Height : ");
        TextField sizeYField = new TextField();

        Button create = new Button("Create");
        Button cancel = new Button("Cancel");

        create.setOnAction(e -> {
            try {
                this.sizeX = Integer.parseInt(sizeXField.getText());
                this.sizeY = Integer.parseInt(sizeYField.getText());
                if (this.sizeX > 0 && this.sizeY > 0) {
                    this.launchGrid();
                    stage.close();
                } else {
                    label.setText("Les nombres doivent être positifs");
                }
            } catch (NumberFormatException ex) {
                label.setText("Ceci n'est pas un nombre.");
            }
        });
        cancel.setOnAction(e -> stage.close());

        pane.add(label, 0, 0);

        pane.add(width, 0, 1);
        pane.add(sizeXField, 1, 1);

        pane.add(height, 0, 2);
        pane.add(sizeYField, 1, 2);

        pane.add(create, 0, 3);
        pane.add(cancel, 1, 3);

        Scene scene = new Scene(pane, 200, 200);
        stage.setTitle("Editor");
        stage.setScene(scene);
        stage.show();
    }

    private void launchGrid() {
        this.createGridPane();
        this.createIcons();
        this.handleZoom();
        this.handleRotate();
    }

    private void changeCurrentImage(String name) {
        this.currentImage = name;
        itemSelected.setText(name);
    }


    private void placeImage(MultipleImages view) {
        String url = this.currentPreview.getImage().getUrl();
//        System.out.println(url);
        if (url.contains("potion")) {
            this.changePotion(view);
        } else {
            this.setImage(view);
        }
//        System.out.println(view.getImage().getUrl());
    }

    private Image getImage(String name) {
        String path = "/images/%s.png";
        URL url = getClass().getResource(String.format(path, name));
        assert url != null;
        return new Image(url.toExternalForm());
    }

    @FXML
    private void setImage(MultipleImages view) {
        Image image = getImage(this.currentImage);

        if (!this.currentImage.equals("floor")) {
            view.changeImage(image);
            view.setImageIndex(this.rotation);

        } else if (!getUrl(view).contains("none")) {
            image = getImage("none");
//            System.out.println("REMOVE IMAGE");
            view.changeImage(image);
        }
    }

    private void changePotion(MultipleImages view) {
        System.out.println("ROTATION : " + this.rotation);
        int numPotion = this.rotation;
        if (numPotion < 0) {
            numPotion *= -1;
        }
        numPotion %= this.potions.length;
        System.out.println("NUMPOTION : " + numPotion);
        Image potion = this.getImage("potion_" + this.potions[numPotion]);
        view.changeImage(potion);
    }

    private void setPreview(MultipleImages preview) {
        this.currentPreview = preview;
        double opacity = 0.66;
        if (getUrl(preview).contains("potion")) {
            this.setPreviewPotion(preview, opacity);
        } else {
            this.setPreviewAux(preview, opacity);
        }
    }

    private void setPreviewPotion(MultipleImages preview, double opacity) {
        this.changePotion(preview);
        preview.setOpacity(opacity);
    }

    private void setPreviewAux(MultipleImages preview, double opacity) {
        Image image = getImage(this.currentImage);
        preview.changeImage(image);
        preview.setOpacity(opacity);
        preview.setImageIndex(this.rotation);
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

    private void increaseRotation() {
        this.rotation += 1;
    }

    private void decreaseRotation() {
        this.rotation -= 1;
    }

    private String getUrl(MultipleImages view) {
        return view.getImage().getUrl();
    }

    private boolean isFloor(MultipleImages view) {
        return getUrl(view).contains("floor");
    }

    /*
    HANDLE
     */

    private void handlePreviewEntered(MultipleImages preview) {
        if (!this.currentImage.equals("floor")) {
            this.setPreview(preview);
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
        gridpane.addEventHandler(ScrollEvent.SCROLL, e -> {
            if (!e.isControlDown())
                return;
            if (e.getDeltaY() > 0)
                zoomIn();
            else
                zoomOut();
        });
    }

    @FXML
    private void handleRotate() {
        gridpane.addEventHandler(ScrollEvent.SCROLL, e -> {
            if (e.getDeltaY() > 0)
                increaseRotation();
            else
                decreaseRotation();
        });
    }
}