module org.example.cp_lab_4 {
    exports org.example.cp_lab_4.interfaces;
    exports org.example.cp_lab_4.models;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.cp_lab_4 to javafx.fxml;
    exports org.example.cp_lab_4;
    exports org.example.cp_lab_4.controllers;
    opens org.example.cp_lab_4.controllers to javafx.fxml;
}