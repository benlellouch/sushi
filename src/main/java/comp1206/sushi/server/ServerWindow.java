package comp1206.sushi.server;

import comp1206.sushi.common.UpdateEvent;
import comp1206.sushi.common.UpdateListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerWindow extends Stage implements UpdateListener {

    private static final long serialVersionUID = -4661566573959270000L;
    private ServerInterface server;



    public ServerWindow (ServerInterface server){
        this.server=server;
        server.addUpdateListener(this);
        //start();
        startTimer();
    }

    public void start()  {
    try {
       Parent root = FXMLLoader.load(getClass().getResource("/fxml/window.fxml"));
//        GridPane root = new GridPane();
//        root.setAlignment(Pos.CENTER);
//        root.setVgap(10);
//        root.setHgap(10);
//
//        Label greeting = new Label("Welcome to JavaFX!");
//        greeting.setTextFill(Color.GREEN);
//        greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
//
//        root.getChildren().add(greeting);
        this.setTitle(server.getRestaurantName() + "Server");
        this.setScene(new Scene(root, 700, 275));
        this.show();
    }catch (Exception e){}


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
