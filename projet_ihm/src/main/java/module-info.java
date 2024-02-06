module projet_ihm {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    //requires json.simple;
    requires org.json;

    opens projet_ihm to javafx.fxml;

    exports projet_ihm;
}
