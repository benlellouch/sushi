package comp1206.sushi.server;

import comp1206.sushi.PostcodeTab;
import comp1206.sushi.common.Postcode;
import comp1206.sushi.common.UpdateEvent;
import comp1206.sushi.common.UpdateListener;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerWindow extends Application implements UpdateListener {

    private static final long serialVersionUID = -4661566573959270000L;
    private ServerInterface server;
    @FXML private Button updateButton;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws ReflectiveOperationException, IOException {
        String serverClassName = getParameters().getRaw().get(0);
        Class<? extends ServerInterface> serverClass = Class.forName(serverClassName).asSubclass(ServerInterface.class);
        this.server = serverClass.getConstructor().newInstance();

//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/window.fxml"));
        VBox root = new VBox();
        TabPane tabs = new TabPane();
       PostcodeTab tab = new PostcodeTab("Postcode");
        tabs.getTabs().add(tab);
        root.getChildren().add(tabs);



        stage.setTitle(server.getRestaurantName() + "Server");
        stage.setScene(new Scene(root));
        server.addUpdateListener(this);
        stage.show();
    }

    public void init(){

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    public ServerWindow() {

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

    @FXML
    private TableView<Postcode> tableView;
    @FXML
    private TextField postcodeField;
    @FXML
    private TextField distanceField;

    public void addPostcode(ActionEvent event){
        ObservableList<Postcode> data = tableView.getItems();
        data.add(new Postcode(postcodeField.getText()));
        this.server.addPostcode(postcodeField.getText());
        postcodeField.setText("");
    }



}


