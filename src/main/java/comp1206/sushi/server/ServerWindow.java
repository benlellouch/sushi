package comp1206.sushi.server;

import comp1206.sushi.common.UpdateEvent;
import comp1206.sushi.common.UpdateListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerWindow extends Application implements UpdateListener {

    private static final long serialVersionUID = -4661566573959270000L;
    private ServerInterface server;

    public ServerWindow(ServerInterface server){
        this.server = server;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ServerWindow.fxml"));
        primaryStage.setTitle("Server Window");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public void startTimer() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        int timeInterval = 5;

        scheduler.scheduleAtFixedRate(() -> refreshAll(), 0, timeInterval, TimeUnit.SECONDS);
    }

    /**
     * Refresh all parts of the server application based on receiving new data, calling the server afresh
     */
    public void refreshAll() {

    }

    @Override
    /**
     * Respond to the model being updated by refreshing all data displays
     */
    public void updated(UpdateEvent updateEvent) {
        refreshAll();
    }
}
