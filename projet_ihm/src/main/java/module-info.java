module projet_ihm {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens projet_ihm to javafx.fxml;

    exports projet_ihm;
}
