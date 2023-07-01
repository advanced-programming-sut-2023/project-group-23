module ApProjectPhase1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires com.google.gson;
    requires org.apache.commons.codec;

    exports View.LoginMenu;
    opens View.LoginMenu to javafx.fxml;
    exports View.GameJFX;
    opens View.GameJFX to javafx.fxml;
    exports Model;
    opens Model to com.google.gson;
    exports Controller;
    exports View.ProfileMenu;
    exports View.MainMenu;
    exports View.ShopMenu;
}