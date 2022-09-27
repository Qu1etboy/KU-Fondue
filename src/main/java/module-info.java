module cs.ku {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;

    opens ku.cs to javafx.fxml;
    exports ku.cs;
    exports ku.cs.controllers;
    opens ku.cs.controllers to javafx.fxml;
    exports ku.cs.models;
    opens ku.cs.models to javafx.fxml;
    exports ku.cs.services;
    opens ku.cs.services to javafx.fxml;
    exports ku.cs.controllers.complaintcategory;
    opens ku.cs.controllers.complaintcategory to javafx.fxml;
    exports ku.cs.controllers.agency;
    opens ku.cs.controllers.agency to javafx.fxml;
    exports ku.cs.controllers.setting;
    opens ku.cs.controllers.setting to javafx.fxml;

}