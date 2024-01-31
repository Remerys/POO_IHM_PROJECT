package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SingleplayerController {

    @FXML
    BorderPane root;

    @FXML
    HBox characters;

    @FXML
    HBox bottomBar;

    @FXML
    Region spring;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    public void initialize() {
        HBox.setHgrow(spring, Priority.ALWAYS);
        createChracters();
        bottomBar.setPadding(new Insets(20, 20, 80, 20));
    }

    private void createChracters() {
        String[] imagesStr = { "warrior", "valkyrie", "elf", "wizard" };
        String path = "/images/%s.png";
        characters.setAlignment(Pos.CENTER);

        // left spring
        Region firstSpring = new Region();
        HBox.setHgrow(firstSpring, Priority.ALWAYS);
        characters.getChildren().add(firstSpring);

        for (String imageStr : imagesStr) {
            Label name = new Label(imageStr.toUpperCase());
            name.setTextFill(Color.WHITE);
            name.setFont(new Font("Arial", 30));
            name.setMaxWidth(Double.MAX_VALUE);
            name.setAlignment(Pos.CENTER);
            Image image = new Image(getClass().getResource(String.format(path, imageStr)).toExternalForm());
            ImageView icon = new ImageView(image);
            icon.setFitHeight(45);
            icon.setFitWidth(45);
            Button button = new Button();
            button.setGraphic(icon);
            button.setPadding(new Insets(100, 50, 100, 50));

            // top & bottom spring of each characters
            Region verticalSpring1 = new Region();
            VBox.setVgrow(verticalSpring1, Priority.ALWAYS);
            Region verticalSpring2 = new Region();
            VBox.setVgrow(verticalSpring2, Priority.ALWAYS);

            VBox pane = new VBox(20, verticalSpring1, name, button, verticalSpring2);

            characters.getChildren().add(pane);

            // right spring for each character
            Region spring = new Region();
            HBox.setHgrow(spring, Priority.ALWAYS);
            characters.getChildren().add(spring);
        }
    }

}
