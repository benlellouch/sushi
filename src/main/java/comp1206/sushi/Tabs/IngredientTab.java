package comp1206.sushi.Tabs;

import comp1206.sushi.common.Ingredient;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;

public class IngredientTab extends MainTab {


    private TableView<Ingredient> ingredientTableView;
    private ObservableList<Ingredient> ingredientObservableList;
    private ObservableList<Supplier> supplierObservableList;
    private ComboBox<Supplier> supplierComboBox;
    private TextField nameInput;
    private TextField stringInput;
    private TextField restockTInput;
    private TextField restockAInput;
    private ServerInterface server;

    public IngredientTab(String name, ServerInterface server){
        super(name);
        this.server = server;

        ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
        supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());

        HBox superBox = new HBox();

        VBox inputBox = new VBox();
        ingredientTableView = new TableView<>();

        TableColumn<Ingredient,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Ingredient,String> unitColumn = new TableColumn<>("Unit");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Ingredient, Supplier> supplierColumn = new TableColumn<>("Supplier");
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Ingredient, Number> restockThresholdColumn = new TableColumn<>("Restock Threshold");
        restockThresholdColumn.setCellValueFactory(new PropertyValueFactory<>("restockThreshold"));

        TableColumn<Ingredient, Number> restockAmountColumn = new TableColumn<>("Restock Amount");
        restockAmountColumn.setCellValueFactory(new PropertyValueFactory<>("restockAmount"));

        ingredientTableView.getColumns().addAll(nameColumn,unitColumn, supplierColumn, restockThresholdColumn, restockAmountColumn);
        ingredientTableView.setItems(ingredientObservableList);

        nameInput = new TextField();
        nameInput.setPromptText("Ex: SO17 4SZ");

        stringInput = new TextField();
        stringInput.setPromptText("Enter a unit");

        restockTInput = new TextField();
        restockTInput.setPromptText("Enter a Threshold");

        restockAInput = new TextField();
        restockAInput.setPromptText("Enter an Amount");

        Button addButton = new Button("Update");
        Button removeButton = new Button ("Remove");

        supplierComboBox = new ComboBox<>(supplierObservableList);
        supplierComboBox.setPromptText("Select a supplier");

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(event -> removeButtonClicked());

        inputBox.getChildren().addAll(nameInput, stringInput, restockTInput, restockAInput, supplierComboBox, addButton, removeButton);
        superBox.getChildren().addAll(ingredientTableView, inputBox);
        this.setContent(superBox);

    }

    public void addButtonClicked(){
//        Postcode postcode = new Postcode(nameInput.getText());
        try {
            server.addIngredient(nameInput.getText(), stringInput.getText(), supplierComboBox.getValue(), NumberFormat.getInstance().parse(restockTInput.getText()), NumberFormat.getInstance().parse(restockAInput.getText()));
            ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
            ingredientTableView.setItems(ingredientObservableList);
            for (Ingredient temp : server.getIngredients()) {
                System.out.println(temp.getName());
            }
//        ingredientTableView.getItems().add(postcode);

            nameInput.clear();
            stringInput.clear();
            restockAInput.clear();
            restockTInput.clear();
            supplierComboBox.setValue(null);
        } catch (ParseException e){
            System.out.println("Was unable to parse the numbers in the text input");
        }
    }

    public void removeButtonClicked(){
//        ObservableList<Postcode> selectedProduct, allProduct;
//        allProduct = ingredientTableView.getItems();
//        selectedProduct= ingredientTableView.getSelectionModel().getSelectedItems();
//        selectedProduct.forEach(allProduct::remove);
        try{
            server.removeIngredient(ingredientTableView.getSelectionModel().getSelectedItem());
            ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
            ingredientTableView.setItems(ingredientObservableList);
        }catch(ServerInterface.UnableToDeleteException e){
            System.out.println("Was unable to remove that.");
        }
    }
}
