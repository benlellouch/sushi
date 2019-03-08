package comp1206.sushi;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.mock.MockServer;
import comp1206.sushi.server.ServerWindow;
import javafx.application.Application;
import javafx.collections.ObservableList;

public class ServerApplication {


    private ObservableList<Postcode> data;


    public static void main(String[] argv) {
        Application.launch(ServerWindow.class, MockServer.class.getName());
//		launch(argv);


    }
}

