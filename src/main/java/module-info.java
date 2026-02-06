module eus.ehu.bum1_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires okhttp3;

    opens eus.ehu.bum1_fx.presentation to javafx.fxml;
    exports eus.ehu.bum1_fx.presentation;
    exports eus.ehu.bum1_fx.business_logic;
}
