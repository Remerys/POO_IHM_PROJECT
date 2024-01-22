module projet_ihm {
    requires javafx.controls;
    requires javafx.fxml;

    opens projet_ihm to javafx.fxml;
    exports projet_ihm;
}
