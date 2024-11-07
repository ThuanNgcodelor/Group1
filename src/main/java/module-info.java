module com.example.cp2396g11gr1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.cp2396g11gr1.controller to javafx.fxml;
    exports com.example.cp2396g11gr1.controller;

    opens com.example.cp2396g11gr1.model.category to javafx.base;
    exports com.example.cp2396g11gr1.model.category;

    opens com.example.cp2396g11gr1.model.Area to javafx.base;
    exports com.example.cp2396g11gr1.model.Area;

    opens com.example.cp2396g11gr1.model.Chip to javafx.base;
    exports com.example.cp2396g11gr1.model.Chip;

    opens com.example.cp2396g11gr1.model.product to javafx.base;
    exports com.example.cp2396g11gr1.model.product;

    opens com.example.cp2396g11gr1.model.account to javafx.base;
    exports com.example.cp2396g11gr1.model.account;

    opens com.example.cp2396g11gr1.model.bill to javafx.base;
    exports com.example.cp2396g11gr1.model.bill;


    opens com.example.cp2396g11gr1 to javafx.fxml;
    opens com.example.cp2396g11gr1.main to javafx.fxml;
    exports com.example.cp2396g11gr1.main;
}
