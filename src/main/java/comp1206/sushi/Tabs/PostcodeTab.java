package comp1206.sushi.Tabs;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class PostcodeTab extends MainTab {


    private TableView<Postcode> postcodeTableView;
    private ObservableList<Postcode> postcodeObservableList;
    private TextField input;
    private ServerInterface server;
    private SupplierTab supplierTab;

    public PostcodeTab(String name, ServerInterface server, SupplierTab supplierTab){
        super(name);
        this.server = server;
        this.supplierTab = supplierTab;


        postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());

        HBox superBox = new HBox();

        VBox inputBox = new VBox();
        postcodeTableView = new TableView<>();

        TableColumn<Postcode,String> postcodeColumn = new TableColumn<>("Postcode");
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Postcode, Map> latitudeColumn = new TableColumn<>("LatLong");
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latLong"));

        TableColumn<Postcode, Number> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        postcodeTableView.getColumns().addAll(postcodeColumn, latitudeColumn, distanceColumn);
        postcodeTableView.setItems(postcodeObservableList);

        input = new TextField();
        input.setPromptText("Ex: SO17 4SZ");

        Button addButton = new Button("Update");
        Button removeButton = new Button ("Remove");

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(event -> removeButtonClicked());

        inputBox.getChildren().addAll(input, addButton, removeButton);
        superBox.getChildren().addAll(postcodeTableView , inputBox);
        this.setContent(superBox);

    }

    public void addButtonClicked(){
//        Postcode postcode = new Postcode(input.getText());
        server.addPostcode(input.getText());
        postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());
        postcodeTableView.setItems(postcodeObservableList);
        supplierTab.setPostcodeBox(postcodeObservableList);
        for (Postcode temp : server.getPostcodes()){
            System.out.println(temp.getName());
        }
//        postcodeTableView.getItems().add(postcode);
        input.clear();
    }

    public void removeButtonClicked(){
//        ObservableList<Postcode> selectedProduct, allProduct;
//        allProduct = postcodeTableView.getItems();
//        selectedProduct= postcodeTableView.getSelectionModel().getSelectedItems();
//        selectedProduct.forEach(allProduct::remove);
        try{
            Postcode postcode = postcodeTableView.getSelectionModel().getSelectedItem();
            for (Supplier temp : server.getSuppliers()) {

                if(postcode == temp.getPostcode()){
                    throw new ServerInterface.UnableToDeleteException("Bruv you're trying to delete a post code that is being in use");
                }

            }
//            server.removePostcode(postcodeTableView.getSelectionModel().getSelectedItem());
            server.removePostcode(postcode);
            postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());
            postcodeTableView.setItems(postcodeObservableList);
            supplierTab.setPostcodeBox(postcodeObservableList);
        }catch(ServerInterface.UnableToDeleteException e){
            System.out.println("Was unable to remove that.");
        }
    }
}
