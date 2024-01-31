package projet_ihm;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;

public class HallOfFameController {
	// @FXML
	// private GridPane titleContainer;

	@FXML
	private GridPane dataContainer;

	@FXML
	private ScrollPane scrollPane;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
	public void initialize() {
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 4; column++) {
                if (row == 0) {
                    if (column == 0) {
                        Label label = new Label("Rank");
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    }
                    if (column == 1) {
                        Label label = new Label("Player");
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    }
                    if (column == 2) {
                        Label label = new Label("Score");
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    }
                    if (column == 3) {
                        Label label = new Label("Date");
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    }
                } else {
                    if (column == 0) {
                        Label label = new Label(String.valueOf(row));
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    } else if (column == 1) {
                        Label label = new Label("---");
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    } else if (column == 2) {
                        Label label = new Label(String.valueOf(0));
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    } else if (column == 3) {
                        Label label = new Label("---");
                        label.getStyleClass().add("menuText");
                        GridPane.setHalignment(label, HPos.CENTER);
                        dataContainer.getChildren().add(label);
                        GridPane.setColumnIndex(label, column);
                        GridPane.setRowIndex(label, row);
                    }
                }
            }
        }
    }
}
