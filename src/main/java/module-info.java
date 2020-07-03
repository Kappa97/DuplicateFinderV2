module licenta.DuplicateFinder {
    requires javafx.controls;
    requires javafx.fxml;

    opens licenta.DuplicateFinder to javafx.fxml;
    exports licenta.DuplicateFinder;
}