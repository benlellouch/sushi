package comp1206.sushi.Tabs;

import comp1206.sushi.common.Drone;
import comp1206.sushi.common.Postcode;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;

public class DroneTab extends MainTab {

    private TableView<Drone> droneTableView;
    private ObservableList<Drone> droneObservableList;
    private TextField input;
    private ServerInterface server;

    public DroneTab(String name, ServerInterface server){
        super(name);
        this.server = server;
        droneObservableList = FXCollections.observableArrayList(server.getDrones());

        HBox superBox = new HBox();
        superBox.setPadding(new Insets(10,10,10,10));
        superBox.setSpacing(10);

        VBox inputBox = new VBox();
        droneTableView = new TableView<>();

        TableColumn<Drone,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Drone, Number> speedColumn = new TableColumn<>("Speed");
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));

        TableColumn<Drone, Number> progressColumn = new TableColumn<>("Progress");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));

        TableColumn<Drone, Number> capacityColumn = new TableColumn<>("Capacity");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Drone, String> statusColumn = new TableColumn<>("Status");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<Drone, Postcode> sourceColumn = new TableColumn<>("Source");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        TableColumn<Drone, Number> destinationColumn = new TableColumn<>("Destination");
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));

        droneTableView.getColumns().addAll(nameColumn, speedColumn, progressColumn, capacityColumn, statusColumn, sourceColumn, destinationColumn);
        droneTableView.setItems(droneObservableList);

        input = new TextField();
        input.setPromptText("Ex: SO17 4SZ");

        Button addButton = new Button("Update");
        Button removeButton = new Button ("Remove");

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(event -> removeButtonClicked());

        inputBox.getChildren().addAll(input, addButton, removeButton);
        superBox.getChildren().addAll(droneTableView, inputBox);
        this.setContent(superBox);

    }

    public void addButtonClicked(){
//        Postcode postcode = new Postcode(input.getText());
        try {
            server.addDrone(NumberFormat.getInstance().parse(input.getText()));
            droneObservableList = FXCollections.observableArrayList(server.getDrones());
            droneTableView.setItems(droneObservableList);
            for (Drone temp : server.getDrones()) {
                System.out.println(temp.getName());
            }
//        droneTableView.getItems().add(postcode);
            input.clear();
        }catch (ParseException e){
            System.out.println("Was unable to parse number ");
        }
    }

    public void removeButtonClicked(){
//        ObservableList<Postcode> selectedProduct, allProduct;
//        allProduct = droneTableView.getItems();
//        selectedProduct= droneTableView.getSelectionModel().getSelectedItems();
//        selectedProduct.forEach(allProduct::remove);
        try{
            server.removeDrone(droneTableView.getSelectionModel().getSelectedItem());
            droneObservableList = FXCollections.observableArrayList(server.getDrones());
            droneTableView.setItems(droneObservableList);
        }catch(ServerInterface.UnableToDeleteException e){
            System.out.println("Was unable to remove that.");
        }
    }
}
