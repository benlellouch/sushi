package comp1206.sushi.Tabs;

import comp1206.sushi.common.Postcode;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SupplierTab extends MainTab {


    private TableView<Supplier> supplierTableView;
    private ObservableList<Supplier> supplierObservableList;
    private ObservableList<Postcode> postcodeObservableList;
    private ComboBox<Postcode> postcodeBox;
    private TextField input;
    private ServerInterface server;

    public SupplierTab(String name, ServerInterface server){
        super(name);
        this.server = server;

        supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());
        postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());

        HBox superBox = new HBox();

        VBox inputBox = new VBox();
        supplierTableView = new TableView<>();

        TableColumn<Supplier,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Supplier, Postcode> postcodeColumn = new TableColumn<>("Postcode");
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));

        TableColumn<Supplier, Number> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        supplierTableView.getColumns().addAll(nameColumn, postcodeColumn, distanceColumn);
        supplierTableView.setItems(supplierObservableList);

        input = new TextField();
        input.setPromptText("Ex: SO17 4SZ");

        Button addButton = new Button("Update");
        Button removeButton = new Button ("Remove");

        postcodeBox = new ComboBox<>(postcodeObservableList);
        postcodeBox.setPromptText("Select a postcode");

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(event -> removeButtonClicked());

        inputBox.getChildren().addAll(input, postcodeBox, addButton, removeButton);
        superBox.getChildren().addAll(supplierTableView, inputBox);
        this.setContent(superBox);

    }

    public void addButtonClicked(){
//        Postcode postcode = new Postcode(input.getText());
        server.addSupplier(input.getText(), postcodeBox.getValue() );
        supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());
        supplierTableView.setItems(supplierObservableList);
        for (Supplier temp : server.getSuppliers()){
            System.out.println(temp.getName());
        }
//        supplierTableView.getItems().add(postcode);

        input.clear();
        postcodeBox.setValue(null);
    }

    public void removeButtonClicked(){
//        ObservableList<Postcode> selectedProduct, allProduct;
//        allProduct = supplierTableView.getItems();
//        selectedProduct= supplierTableView.getSelectionModel().getSelectedItems();
//        selectedProduct.forEach(allProduct::remove);
        try{
            server.removeSupplier(supplierTableView.getSelectionModel().getSelectedItem());
            supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());
            supplierTableView.setItems(supplierObservableList);
        }catch(ServerInterface.UnableToDeleteException e){
            System.out.println("Was unable to remove that.");
        }
    }
}
