package comp1206.sushi.Tabs;

import comp1206.sushi.common.Ingredient;
import comp1206.sushi.common.Postcode;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private IngredientTab ingredientTab;

    public SupplierTab(String name, ServerInterface server, IngredientTab ingredientTab){
        super(name);
        this.server = server;
        this.ingredientTab = ingredientTab;

        supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());
        postcodeObservableList = FXCollections.observableArrayList(server.getPostcodes());

        HBox superBox = new HBox();
        superBox.setPadding(new Insets(10,10,10,10));
        superBox.setSpacing(10);

        VBox inputBox = new VBox();
        inputBox.setSpacing(10);
        HBox buttonBox = new HBox();
        supplierTableView = new TableView<>();

        TableColumn<Supplier,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Supplier, Postcode> postcodeColumn = new TableColumn<>("Postcode");
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));

        TableColumn<Supplier, Number> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        supplierTableView.getColumns().addAll(nameColumn, postcodeColumn, distanceColumn);
        supplierTableView.setItems(supplierObservableList);

        // text field for supplier name
        input = new TextField();
        input.setPromptText("Ex: Redstar Foodservice Ltd");

        // add and remove buttons + styling of those buttons
        Button addButton = new Button("Update");
        addButton.setPrefWidth(95);
        Button removeButton = new Button ("Remove");
        removeButton.setPrefWidth(95);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(addButton,removeButton);
        //combo box for supplier postcode
        postcodeBox = new ComboBox<>(postcodeObservableList);
        postcodeBox.setPromptText("Select a postcode");
        postcodeBox.setPrefWidth(200);

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(event -> removeButtonClicked());

        inputBox.getChildren().addAll(input, postcodeBox, buttonBox);
        superBox.getChildren().addAll(supplierTableView, inputBox);
        this.setContent(superBox);

    }

    public void addButtonClicked(){
//        Postcode postcode = new Postcode(input.getText());
        server.addSupplier(input.getText(), postcodeBox.getValue() );
        supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());
        supplierTableView.setItems(supplierObservableList);
        ingredientTab.setSupplierComboBox(supplierObservableList);
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
            Supplier supplier = supplierTableView.getSelectionModel().getSelectedItem();
            for(Ingredient temp: server.getIngredients()){

                if (supplier == temp.getSupplier()){
                    throw new ServerInterface.UnableToDeleteException("lol");
                }

            }
            server.removeSupplier(supplier);
            supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());
            supplierTableView.setItems(supplierObservableList);
            ingredientTab.setSupplierComboBox(supplierObservableList);
        }catch(ServerInterface.UnableToDeleteException e){
            System.out.println("Was unable to remove that.");
        }
    }

    public void setPostcodeBox(ObservableList<Postcode> postcodeObservableList){
        postcodeBox.getItems().removeAll(this.postcodeObservableList);
        postcodeBox.getItems().addAll(postcodeObservableList);

    }
}
