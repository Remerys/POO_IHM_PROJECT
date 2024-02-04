package projet_ihm;

import java.io.IOException;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreditsController {
    private String creditsTxtPath = "projet_ihm\\src\\main\\resources\\credits.txt";

	@FXML
	private TextArea textArea;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
	public void initialize() {
        Platform.runLater(() -> {
            this.loadTextFromFile(this.creditsTxtPath);

            this.smoothScrollToBottom();

            this.disableTextSelection(this.textArea);
        });
    }

    private void loadTextFromFile(String filePath) {
        try {
            // Lire le contenu du fichier
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Affecter le contenu du fichier au TextArea
            this.textArea.setText(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void smoothScrollToBottom() {
        if (this.textArea != null) {
            ScrollBar scrollBar = (ScrollBar) this.textArea.lookup(".scroll-bar:vertical");

            if (scrollBar != null) {
                // Créer une animation pour ajuster progressivement la valeur de la barre de défilement
                Timeline timeline = new Timeline();
                KeyValue keyValue = new KeyValue(scrollBar.valueProperty(), scrollBar.getMax());
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(20), keyValue); // Ajustez la durée de l'animation

                // Ajouter le KeyFrame à la Timeline
                timeline.getKeyFrames().add(keyFrame);

                // Démarrer l'animation
                timeline.play();
            }
        }
    }

    private void disableTextSelection(TextArea textArea) {
        textArea.setEventDispatcher((event, tail) -> {
            if (event instanceof MouseEvent) {
                MouseEvent mouseEvent = (MouseEvent) event;
                EventType<? extends MouseEvent> eventType = mouseEvent.getEventType();

                // Consommer les événements de souris liés à la sélection de texte
                if (eventType == MouseEvent.MOUSE_PRESSED ||
                        eventType == MouseEvent.MOUSE_DRAGGED ||
                        eventType == MouseEvent.MOUSE_RELEASED) {
                    mouseEvent.consume();
                    return null;
                }
            }

            return null;
        });
    }
}
