module com.example.refactor_vizualize_graph_ktln {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.refactor_vizualize_graph_ktln to javafx.fxml;
    exports com.example.refactor_vizualize_graph_ktln;
}