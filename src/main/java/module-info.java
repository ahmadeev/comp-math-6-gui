module com.example.compmathlab4gui {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens runnable to javafx.fxml;
    exports runnable;
}