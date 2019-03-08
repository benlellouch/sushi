module sushi {
        requires javafx.fxml;
        requires javafx.controls;
        requires javafx.graphics;
        requires javafx.base;
        requires javafx.media;

        opens comp1206.sushi.server;
        opens comp1206.sushi;
        opens comp1206.sushi.common;

}