module sushi {
        requires javafx.fxml;
        requires javafx.controls;
        requires javafx.graphics;
        requires javafx.base;
        requires javafx.media;
    requires java.desktop;

    opens comp1206.sushi.server;
        opens comp1206.sushi;
        opens comp1206.sushi.common;
        opens comp1206.sushi.Tabs;


}