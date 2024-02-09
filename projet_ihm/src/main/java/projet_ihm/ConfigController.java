package projet_ihm;

/* Imports related to file management */
import java.io.*;
import java.util.Objects;
import org.json.JSONObject;

/* Imports related to JavaFX */
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller associated with the "Controls" view.
 * */
public class ConfigController {
    @FXML
    private GridPane uiControlsPane;
    private static final String userSettingsFilePath = Objects.requireNonNull(App.class.getResource("/user_settings.json")).getFile();
    private JSONObject settingsObject;

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void saveLocalJSONToFile() throws IOException {

        try {
            FileWriter fileWriter = new FileWriter(ConfigController.userSettingsFilePath);

            System.out.println(this.settingsObject);

            fileWriter.write(this.settingsObject.toString(4));

            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the content of the user settings json file into the local JSONObject attribute
     * */
    private void loadLocalJSONFromFile() throws IOException {
        FileReader fileReader = new FileReader(ConfigController.userSettingsFilePath);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonContent.append(line);
        }

        fileReader.close();

        this.settingsObject = new JSONObject(jsonContent.toString());
    }

    private JSONObject getSettingsToJSON() {
        return this.settingsObject;
    }

    /**
     * Creates every keybind labels and place them into the settings grid.
     * */
    private void createKeybindLabels() throws IOException {
        // Every action possible for each player in the game
        String[] actions = {"up", "down", "left", "right", "attack", "resurect", "usebomb"};

        for(int playerId = 1; playerId <= 4; playerId++) {
            for(int actionId = 0; actionId < actions.length; actionId++) {

                // We create a new Label (placeholder name just in case
                // something goes wrong when loading the user settings)
                Label l = new Label("---");

                String newLabelId = "p"+playerId+"_"+actions[actionId];

                // We give a unique identifier to each label created
                l.setId(newLabelId);

                // We apply some styleClass to it
                l.getStyleClass().add("clickableMenuText");

                // We attach an event handler to it
                l.setOnMouseClicked((MouseEvent e) -> {
                    try {
                        promptNewKeybind(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                // Attempts to set the value of the label to the saved value
                l.setText(getKeybind(newLabelId));

                // And then we add it to the grid (and the internal labels list)
                this.uiControlsPane.add(l, playerId, actionId+1);
            }
        }
    }

    public String getKeybind(String action) {
        // Casting to string because JSONObject.get returns an object
        return (String) this.settingsObject.get(action);
    }

    private void setKeybind(String action, KeyCode newKeybind) {
        this.settingsObject.put(action, newKeybind);
    }

    private void changeKeybindLabel(Label keybindLabel, KeyCode newKeybind) {
        keybindLabel.setText(newKeybind.toString());
    }

    @FXML
    private void promptNewKeybind(MouseEvent event) throws IOException {
        // Get the source of the event (the clicked label)
        Label clickedLabel = (Label) event.getSource();
        // Get the ID of the clicked label
        String labelId = clickedLabel.getId();

        BorderPane keybindPopup = new BorderPane();
        Label popupDescription = new Label("Modifying keybind for action " + labelId+"\n(press ESCAPE to cancel)");

        keybindPopup.setCenter(popupDescription);

        Stage popupStage = new Stage();
        Scene popupScene = new Scene(keybindPopup, 300, 50);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Modifying keybind...");
        popupStage.setResizable(false);

        // Set the owner of the popup stage to the main stage
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        popupStage.initOwner(primaryStage);

        // Set modality to APPLICATION_MODAL to make the popup block input events to other windows
        popupStage.initModality(Modality.APPLICATION_MODAL);

        popupStage.show();

        // Registering a single keystroke
        popupScene.setOnKeyPressed((KeyEvent keyEvent) -> {
            KeyCode keyCode = keyEvent.getCode();

            // Pressing escape will thus allow the user to cancel
            if(keyCode != KeyCode.ESCAPE) {
                setKeybind(labelId, keyCode);
                changeKeybindLabel(clickedLabel, keyCode);
            }

            popupStage.close();
        });

    }

    @FXML
    public void initialize() throws IOException {
        loadLocalJSONFromFile();
        createKeybindLabels();
    }
}
