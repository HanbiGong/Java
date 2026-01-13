module ca.senecacollege.application.workshop3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens ca.senecacollege.application.workshop3 to javafx.fxml;
    opens ca.senecacollege.application.workshop3.controllers to javafx.fxml;
    opens ca.senecacollege.application.workshop3.models to javafx.base;

    exports ca.senecacollege.application.workshop3;
    exports ca.senecacollege.application.workshop3.controllers;
}
