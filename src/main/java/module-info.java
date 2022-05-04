module mx.com.camacho.mdnotes {
    requires javafx.controls;
    requires javafx.fxml;

    opens mx.com.camacho.mdnotes to javafx.fxml;
    exports mx.com.camacho.mdnotes;

    exports mx.com.camacho.mdnotes.notelist.controllers;
    opens mx.com.camacho.mdnotes.notelist.controllers to javafx.fxml;

    exports mx.com.camacho.mdnotes.notelist.components;
    opens mx.com.camacho.mdnotes.notelist.components to javafx.fxml;

    exports mx.com.camacho.mdnotes.model;
    exports mx.com.camacho.mdnotes.constants;
}