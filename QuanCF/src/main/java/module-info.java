module com.mycompany.quancf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.mycompany.quancf to javafx.fxml;
    exports com.mycompany.quancf;
    exports com.qcf.pojo;
}
